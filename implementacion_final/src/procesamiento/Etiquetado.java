package procesamiento;

import java.util.Stack;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class Etiquetado extends ProcesamientoDef{
	private int etq = 0;
    private Stack<Dec_proc> pila = new Stack<Dec_proc>();

    @Override
    public void procesa(Prog prog){
        prog.setPrim(etq);
        prog.decs().procesa(this);
        prog.instrOpt().procesa(this);
        etq++;
        while(!pila.isEmpty()){
            Dec_proc dec = pila.pop();
            dec.setPrim(etq);
            etq++;
            dec.prog().procesa(this);
            etq+=2;
            dec.setSig(etq);
        }
        prog.setSig(etq);
    }
    @Override
    public void procesa(No_decs a){}
    
    @Override
    public void procesa(Si_decs a){
        a.decs().procesa(this);
    }

    @Override
    public void procesa(Muchas_decs a){
        a.decs().procesa(this);
        a.dec().procesa(this);
    }

    @Override
    public void procesa(Una_dec a){
        a.dec().procesa(this);
    }

    @Override
    public void procesa(Dec_id a){}
    @Override
    public void procesa(Dec_type a){}
    @Override
    public void procesa(Dec_proc a){
        pila.push(a);
    }

    @Override
    public void procesa(Si_inst a){
        a.setPrim(etq);
        a.lInstr().procesa(this);
        a.setSig(etq);
    }

    @Override
    public void procesa(No_inst a){}

    @Override
    public void procesa(Muchas_instr a){
        a.setPrim(etq);
        a.lInstr().procesa(this);
        a.instr().procesa(this);
        a.setSig(etq);
    }

    @Override
    public void procesa(Una_instr a){
        a.setPrim(etq);
        a.instr().procesa(this);
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_comp a){
        a.setPrim(etq);
        a.prog().procesa(this);
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_call a){
        a.setPrim(etq);
        etq++;

        Dec_proc dec = (Dec_proc) a.getVinculo();
        Si_parF si = null;
        if (dec.paramF().getClass() == Si_parF.class){
            si = (Si_parF) dec.paramF();
        }
        Si_param_re si2 = null;
        if (a.paramR().getClass() == Si_param_re.class){
            si2 = (Si_param_re) a.paramR();
        }
        if (si != null && si2 != null){
            etiquetado_paso_param(si, si2);
        }
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_del a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etq+=4;
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_new a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etq+=2;
        a.setSig(etq);
    }
    public void procesa(Instr_nl a){}

    @Override
    public void procesa(Instr_wr a){
        a.setPrim(etq);
        a.exp().procesa(this);
        if (es_designador(a.exp()))
            etq++;
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_rd a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etq+=2;
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_wh a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etiquetado_acc_val(a.exp());
        etq++;
        a.prog().procesa(this);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_else a){
        a.exp().procesa(this);
        etiquetado_acc_val(a.exp());
        etq++;
        a.prog1().procesa(this);
        etq++;
        a.setPrim(etq);
        a.prog2().procesa(this);
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_if a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etiquetado_acc_val(a.exp());
        etq++;
        a.prog().procesa(this);
        a.setSig(etq);
    }

    @Override
    public void procesa(Instr_eval a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Indir a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Reg a){
        a.setPrim(etq);
        a.exp().procesa(this);
        etq+=2;
        a.setSig(etq);
    }

    @Override
    public void procesa(Index a){
        a.setPrim(etq);
        a.exp1().procesa(this);
        a.exp2().procesa(this);
        if (es_designador(a.exp1()))
            etq++;
        etq+=3;
        a.setSig(etq);
    }

    @Override
    public void procesa(Menos a){
        a.setPrim(etq);
        a.exp().procesa(this);
        if (es_designador(a.exp()))
            etq++;
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Not a){
        a.setPrim(etq);
        a.exp().procesa(this);
        if (es_designador(a.exp()))
            etq++;
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Or a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(And a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Mod a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Div a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Mul a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Resta a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Suma a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Distinto a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Igual a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Menor_igual a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Mayor_igual a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Menor a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Mayor a){
        a.setPrim(etq);
        etiquetado_opnds(a.exp1(), a.exp2(), a);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Asig a){
        a.setPrim(etq);
        a.exp1().procesa(this);
        etq++;
        a.exp2().procesa(this);
        if(es_designador(a.exp1()))
            etq++;
        else{
            if ((ref(a.exp1().getTipo()).getClass() == Tipo_real.class) && (ref(a.exp2().getTipo()).getClass() == Tipo_int.class))
                etq+=2;
            else
                etq++;
        }
        a.setSig(etq);
    }

    @Override
    public void procesa(Null a){
        a.setPrim(etq);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Id a){
        a.setPrim(etq);

        if (a.getVinculo().getClass() == Dec_id.class){
            Dec_id dec = (Dec_id) a.getVinculo();
            etiquetado_acc_id(dec);
        }
        else if (a.getVinculo().getClass() == Param_cop.class){
            Param_cop param = (Param_cop) a.getVinculo();
            etiquetado_acc_id(param);
        }
        else{
            Param_ref param = (Param_ref) a.getVinculo();
            etiquetado_acc_id(param);
        }
        a.setSig(etq);
    }

    @Override
    public void procesa(False a){
        a.setPrim(etq);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(True a){
        a.setPrim(etq);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Literal_cadena a){
        a.setPrim(etq);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Literal_real a){
        a.setPrim(etq);
        etq++;
        a.setSig(etq);
    }

    @Override
    public void procesa(Literal_ent a){
        a.setPrim(etq);
        etq++;
        a.setSig(etq);
    }

    //Funciones auxiliares
    private void etiquetado_paso_param(Si_parF si, Si_param_re si2){
        if ((si.lparam().getClass() == Muchos_param.class) && (si2.lParamR().getClass() == Muchos_param_re.class)){
            etiquetado_paso_param((Muchos_param) si.lparam(), (Muchos_param_re) si2.lParamR());
        }
        else if ((si.lparam().getClass() == Un_param.class) && (si2.lParamR().getClass() == Un_param_re.class)){
            etiquetado_paso_param((Un_param) si.lparam(), (Un_param_re) si2.lParamR());
        }
    }

    private void etiquetado_paso_param(Muchos_param pf, Muchos_param_re pr){
        if ((pf.params().getClass() == Muchos_param.class) && (pr.lParamR().getClass() == Muchos_param_re.class)){
            etiquetado_paso_param((Muchos_param) pf.params(), (Muchos_param_re) pr.lParamR());
        }
        else if ((pf.params().getClass() == Un_param.class) && (pr.lParamR().getClass() == Un_param_re.class)){
            etiquetado_paso_param((Un_param) pf.params(), (Un_param_re) pr.lParamR());
        }
        etq+=3;
        pr.exp().procesa(this);
        etq++;
    } 

    private void etiquetado_paso_param(Un_param pf, Un_param_re pr){
        etq += 3;
        pr.exp().procesa(this);
        etq++;
    }

    private void etiquetado_opnds(Exp opnd0, Exp opnd1, Exp exp){
        opnd0.procesa(this);
        etiquetado_acc_val(opnd0);
        if (exp.getClass() == Suma.class || exp.getClass() == Resta.class || exp.getClass() == Mul.class || exp.getClass() == Div.class)
            if ((ref(exp.getTipo()).getClass() == Tipo_real.class) && (ref(opnd0.getTipo()).getClass() == Tipo_int.class))
                etq++;
        opnd1.procesa(this);
        etiquetado_acc_val(opnd1);
        if (exp.getClass() == Suma.class || exp.getClass() == Resta.class || exp.getClass() == Mul.class || exp.getClass() == Div.class)
            if ((ref(exp.getTipo()).getClass() == Tipo_real.class) && (ref(opnd1.getTipo()).getClass() == Tipo_int.class))
                etq++;
    }

    private void etiquetado_acc_id(Dec_id dec){
        if (dec.getNivel() == 0)
            etq++;
        else
            etiquetado_acc_var(dec);
    }

    private void etiquetado_acc_id(Param_cop param){
        etiquetado_acc_var(param);
    }

    private void etiquetado_acc_id(Param_ref param){
        etiquetado_acc_var(param);
        etq++;
    }

    private void etiquetado_acc_var(Nodo dec){
        etq += 3;
    }

    private void etiquetado_acc_val(Exp exp){
        if (es_designador(exp))
            etq++;
    }

    private boolean es_designador(Exp exp){
        if (exp.getClass() == Indir.class)
            return es_designador(((Indir)exp).exp());
        return (exp.getClass() == Id.class) || (exp.getClass() == Index.class) || (exp.getClass() == Reg.class) || (exp.getClass() == Asig.class);
    }

    private Tipo ref(Tipo t){
        if (t.getClass() == Tipo_id.class){
            Dec_type ti = (Dec_type) t.getVinculo();
            return ref(ti.tipo());
        }
        return t;
    }

}
