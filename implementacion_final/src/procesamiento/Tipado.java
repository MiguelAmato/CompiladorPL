package procesamiento;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class Tipado extends ProcesamientoDef{

    Set<Par<TipoEnum, TipoEnum>> tipos;

    public Tipado() {
        super();
    }

    private TipoEnum ambosOK(TipoEnum t0, TipoEnum t1) {
        if (t0 == TipoEnum.ERROR || t1 == TipoEnum.ERROR)
            return TipoEnum.ERROR;
        else
            return TipoEnum.OK;
    }

    private TipoEnum ref(Nodo tipo) {
        if (tipo.getClass() == Tipo_id.class)
            return ref(((Dec_type)tipo.getVinculo()).tipo());
        return tipo.getTipo();
    }

    private void aviso_error(Nodo a){
        System.err.println("Error de tipos en " + a.leeFila() + " y columna " + a.leeCol());
    }

    private boolean compatible(Nodo t0, Nodo t1) {
        TipoEnum tipo0 = ref(t0);
        TipoEnum tipo1 = ref(t1);

        tipos = new HashSet<Par<TipoEnum, TipoEnum>>();

        tipos.add(new Par<TipoEnum, TipoEnum>(tipo0, tipo1));

        if (tipo0 == TipoEnum.TIPO_INT && tipo1 == TipoEnum.TIPO_INT)
            return true;
        else if (tipo0 == TipoEnum.TIPO_REAL && tipo1 == TipoEnum.TIPO_INT)
            return true;
        else if (tipo0 == TipoEnum.TIPO_INT && tipo1 == TipoEnum.TIPO_REAL)
            return true;
        else if (tipo0 == TipoEnum.TIPO_REAL && tipo1 == TipoEnum.TIPO_REAL)
            return true;
        else if (tipo0 == TipoEnum.TIPO_BOOL && tipo1 == TipoEnum.TIPO_BOOL)
            return true;
        else if (tipo0 == TipoEnum.TIPO_STRING && tipo1 == TipoEnum.TIPO_STRING)
            return true;
        else if (tipo0 == TipoEnum.TIPO_PUNT && tipo1 == TipoEnum.NULL)
            return true;
        else if (tipo0 == TipoEnum.NULL && tipo1 == TipoEnum.TIPO_PUNT)
            return true;
        else if (tipo0 == TipoEnum.TIPO_PUNT && tipo1 == TipoEnum.TIPO_PUNT)
            return compatible_puntero(t0, t1);
        else if (tipo0 == TipoEnum.TIPO_ARRAY && tipo1 == TipoEnum.TIPO_ARRAY)
            return compatible_array(t0, t1);
        else if (tipo0 == TipoEnum.TIPO_STRUCT && tipo1 == TipoEnum.TIPO_STRUCT)
            return compatible_struct(t0, t1);
        return false;
    }

    private boolean compatible_struct(Nodo t0, Nodo t1) {
        if (tipos.contains(new Par<TipoEnum, TipoEnum>(ref(t0), ref(t1))))
            return true;

        Tipo_struct tipo0 = (Tipo_struct)t0;
        Tipo_struct tipo1 = (Tipo_struct)t1;

        if (tipo0.lStruct().getCampos().size() != tipo1.lStruct().getCampos().size())
            return false;

        Iterator<Campo> it0 = tipo0.lStruct().getCampos().values().iterator();
        Iterator<Campo> it1 = tipo1.lStruct().getCampos().values().iterator();

        while (it0.hasNext() && it1.hasNext()){
            Campo c0 = it0.next();
            Campo c1 = it1.next();

            if (!compatible(c0, c1))
                return false;
        }
        return true;
    }

    private boolean compatible_array(Nodo t0, Nodo t1) {
        if (tipos.contains(new Par<TipoEnum, TipoEnum>(ref(t0), ref(t1))))
            return true;
        return compatible(t0.tipo(), t1.tipo());
    }

    private boolean compatible_puntero(Nodo t0, Nodo t1) {
        if (tipos.contains(new Par<TipoEnum, TipoEnum>(ref(t0), ref(t1))))
            return true;
        return compatible(t0.tipo(), t1.tipo());
    }

    @Override
    public void procesa(Prog a) {
        a.decs().procesa(this);
        a.instrOpt().procesa(this);
        a.setTipo(ambosOK(a.decs().getTipo(), a.instrOpt().getTipo()));
    }

    @Override
    public void procesa(Si_decs a) {
        a.decs().procesa(this);
        a.setTipo(a.decs().getTipo());
    }

    @Override
    public void procesa(No_decs a) {
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Muchas_decs a) {
        a.decs().procesa(this);
        a.dec().procesa(this);
        a.setTipo(ambosOK(a.decs().getTipo(), a.dec().getTipo()));
    }

    @Override
    public void procesa(Una_dec a) {
        a.dec().procesa(this);
        a.setTipo(a.dec().getTipo());
    }

    @Override
    public void procesa(Dec_id a) {
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Dec_type a) {
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Dec_proc a) {
        a.prog().procesa(this);
        a.setTipo(a.prog().getTipo());
    }
    
    @Override
    public void procesa(Si_inst a) {
        a.lInstr().procesa(this);
        a.setTipo(a.lInstr().getTipo());
    }

    @Override
    public void procesa(No_inst a) {
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Muchas_instr a) {
        a.instr().procesa(this);
        a.lInstr().procesa(this);
        a.setTipo(ambosOK(a.instr().getTipo(), a.lInstr().getTipo()));
    }

    @Override
    public void procesa(Una_instr a) {
        a.instr().procesa(this);
        a.setTipo(a.instr().getTipo());
    }


    @Override
    public void procesa(Instr_eval a) {
        a.exp().procesa(this);
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Instr_if a) {
        a.exp().procesa(this);
        a.prog().procesa(this);
        a.setTipo(ambosOK(a.exp().getTipo(), a.prog().getTipo()));
    }

    @Override
    public void procesa(Instr_else a) {
        a.exp().procesa(this);
        a.prog1().procesa(this);
        a.prog2().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_BOOL)
            a.setTipo(ambosOK(a.prog1().getTipo(), a.prog2().getTipo()));
        else
            a.setTipo(TipoEnum.ERROR);
    }

    @Override
    public void procesa(Instr_wh a) {
        a.exp().procesa(this);
        a.prog().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_BOOL)
            a.setTipo(a.prog().getTipo());
        else
            a.setTipo(TipoEnum.ERROR);
    }

    @Override
    public void procesa(Instr_nl a) {
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Instr_new a) {
        a.exp().procesa(this);
        
        if (ref(a.exp()) == TipoEnum.TIPO_PUNT)
            a.setTipo(TipoEnum.OK);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_rd a) {
        a.exp().procesa(this);

        if (a.exp().es_designador()){
            if (ref(a.exp()) == TipoEnum.TIPO_INT || ref(a.exp()) == TipoEnum.TIPO_REAL || ref(a.exp()) == TipoEnum.TIPO_STRING)
                a.setTipo(TipoEnum.OK);
            else{
                aviso_error(a);
                a.setTipo(TipoEnum.ERROR);
            }
        }
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_wr a) {
        a.exp().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_INT || ref(a.exp()) == TipoEnum.TIPO_REAL || ref(a.exp()) == TipoEnum.TIPO_STRING || ref(a.exp()) == TipoEnum.TIPO_BOOL)
            a.setTipo(TipoEnum.OK);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_del a) {
        a.exp().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_PUNT) 
            a.setTipo(TipoEnum.OK);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_call a) {
        a.paramR().procesa(this);
        if (a.paramR().getTipo() == TipoEnum.OK)
            a.setTipo(tipo_params(((Dec_proc) a.getVinculo()).paramF(), a.paramR()));
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    private TipoEnum tipo_params(ParamF paramF, ParamR paramR){
        if (paramF.getClass() == Si_parF.class && paramR.getClass() == Si_param_re.class)
            return tipo_si_params(((Si_parF)paramF).lparam(), ((Si_param_re)paramR).lParamR());
        else if (paramF.getClass() == No_parF.class && paramR.getClass() == No_param_re.class)
            return TipoEnum.OK;
        return TipoEnum.ERROR;
    }

    private TipoEnum tipo_si_params(LParam lparam, LParamR lParamR) {
        if (lparam.getClass() == Un_param.class && lParamR.getClass() == Un_param_re.class)
            return tipo_param(((Un_param)lparam).param(), ((Un_param_re)lParamR).exp());
        else if (lparam.getClass() == Muchos_param.class && lParamR.getClass() == Muchos_param_re.class){
            TipoEnum t1 = tipo_si_params(((Muchos_param)lparam).params(), ((Muchos_param_re)lParamR).lParamR());
            TipoEnum t2 = tipo_param(((Muchos_param)lparam).param(), ((Muchos_param_re)lParamR).exp());
            return ambosOK(t1, t2);
        }
        return TipoEnum.ERROR;
    }

    private TipoEnum tipo_param(Param param, Exp exp) {
        if (param.getClass() == Param_cop.class && compatible(param, exp))
            return TipoEnum.OK;
        else if (param.getClass() == Param_ref.class && exp.es_designador() && compatible(param, exp)){
            Class<?> tipo1 = ref(param).getClass(), tipo2 = ref(exp).getClass();
            if (!(tipo1 == Tipo_real.class && tipo2 == Tipo_real.class) && (tipo1 == Tipo_real.class || tipo2 == Tipo_real.class))
                return TipoEnum.ERROR;
            return TipoEnum.OK;
        }
        return TipoEnum.ERROR;
    }

    @Override
    public void procesa(Instr_comp a) {
        a.prog().procesa(this);
        a.setTipo(a.prog().getTipo());
    }

    @Override
    public void procesa(Si_param_re a) { 
        a.lParamR().procesa(this);
        a.setTipo(a.lParamR().getTipo());
    }

    @Override
    public void procesa(No_param_re a) { 
        a.setTipo(TipoEnum.OK);
    }

    @Override
    public void procesa(Un_param_re a) {
        a.exp().procesa(this);
        a.setTipo(a.exp().getTipo());
    }

    @Override
    public void procesa(Muchos_param_re a) {
        a.lParamR().procesa(this);
        a.exp().procesa(this);
        a.setTipo(ambosOK(a.lParamR().getTipo(), a.exp().getTipo()));
    }

    @Override
    public void procesa(Asig a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (a.exp1().es_designador() && compatible(a.exp1(), a.exp2()))
            a.setTipo(a.exp1().getTipo());
        else{
            aviso_error(a); 
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Mayor a) {
        tipado_comparacion(a.exp1(), a.exp2(), a, false);
    }

    @Override
    public void procesa(Menor_igual a) {
        tipado_comparacion(a.exp1(), a.exp2(), a, false);
    }

    @Override
    public void procesa(Mayor_igual a) {
        tipado_comparacion(a.exp1(), a.exp2(), a, false);
    }

    @Override
    public void procesa(Menor a) {
        tipado_comparacion(a.exp1(), a.exp2(), a, false);
    }

    @Override
    public void procesa(Distinto a) {
        tipado_comparacion(a.exp1(), a.exp2(), a, true);
    }

    @Override
    public void procesa(Igual a) {
        tipado_comparacion(a.exp1(), a.exp2(), a, true);
    }

    private void tipado_comparacion(Exp exp1, Exp exp2, Exp a, boolean op_igualdad) {
        exp1.procesa(this);
        exp2.procesa(this);

        TipoEnum tipo_exp1 = ref(exp1);
        TipoEnum tipo_exp2 = ref(exp2);

        if (tipo_exp1 == TipoEnum.TIPO_INT || tipo_exp1 == TipoEnum.TIPO_REAL)
            if (tipo_exp2 == TipoEnum.TIPO_INT || tipo_exp2 == TipoEnum.TIPO_REAL)
                a.setTipo(TipoEnum.TIPO_BOOL);
        else if ((tipo_exp1 == TipoEnum.TIPO_BOOL) && (tipo_exp2 == TipoEnum.TIPO_BOOL))
            a.setTipo(TipoEnum.TIPO_BOOL);
        else if ((tipo_exp1 == TipoEnum.TIPO_STRING) && (tipo_exp2 == TipoEnum.TIPO_STRING))
            a.setTipo(TipoEnum.TIPO_BOOL);
        else if (op_igualdad){
            if ((tipo_exp1 == TipoEnum.TIPO_PUNT) && (tipo_exp2 == TipoEnum.TIPO_PUNT))
                a.setTipo(TipoEnum.TIPO_BOOL);
            else if ((tipo_exp1 == TipoEnum.TIPO_PUNT) && (tipo_exp2 == TipoEnum.NULL))
                a.setTipo(TipoEnum.TIPO_BOOL);
            else if ((tipo_exp1 == TipoEnum.NULL) && (tipo_exp2 == TipoEnum.TIPO_PUNT))
                a.setTipo(TipoEnum.TIPO_BOOL);
            else{
                aviso_error(a);
                a.setTipo(TipoEnum.ERROR);
            }
        }
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Div a) {
        tipado_aritmetico(a.exp1(), a.exp2(), a);
    }

    @Override
    public void procesa(Mul a) {
        tipado_aritmetico(a.exp1(), a.exp2(), a);
    }

    @Override
    public void procesa(Resta a) {
        tipado_aritmetico(a.exp1(), a.exp2(), a);
    }

    @Override
    public void procesa(Suma a) {
        tipado_aritmetico(a.exp1(), a.exp2(), a);
    }

    private void tipado_aritmetico(Exp exp1, Exp exp2, Exp a) {
        exp1.procesa(this);
        exp2.procesa(this);

        TipoEnum tipo_exp1 = ref(exp1);
        TipoEnum tipo_exp2 = ref(exp2);

        if (tipo_exp1 == TipoEnum.TIPO_INT && tipo_exp2 == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT);
        else if (tipo_exp1 == TipoEnum.TIPO_REAL && tipo_exp2 == TipoEnum.TIPO_REAL)
            a.setTipo(TipoEnum.TIPO_REAL);
        else if (tipo_exp1 == TipoEnum.TIPO_INT && tipo_exp2 == TipoEnum.TIPO_REAL)
            a.setTipo(TipoEnum.TIPO_REAL);
        else if (tipo_exp1 == TipoEnum.TIPO_REAL && tipo_exp2 == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_REAL);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Mod a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (ref(a.exp1()) == TipoEnum.TIPO_INT && ref(a.exp2()) == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Or a) {
        tipado_logico(a.exp1(), a.exp2(), a);
    }

    @Override
    public void procesa(And a) {
        tipado_logico(a.exp1(), a.exp2(), a);
    }

    private void tipado_logico(Exp exp1, Exp exp2, Exp a) {
        exp1.procesa(this);
        exp2.procesa(this);

        if (ref(exp1) == TipoEnum.TIPO_BOOL && ref(exp2) == TipoEnum.TIPO_BOOL)
            a.setTipo(TipoEnum.TIPO_BOOL);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Menos a) {
        a.exp().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT);
        else if (ref(a.exp()) == TipoEnum.TIPO_REAL)
            a.setTipo(TipoEnum.TIPO_REAL);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Not a) {
        a.exp().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_BOOL)
            a.setTipo(TipoEnum.TIPO_BOOL);
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Index a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (ref(a.exp1()) == TipoEnum.TIPO_ARRAY && ref(a.exp2()) == TipoEnum.TIPO_INT)
            a.setTipo(a.exp2().getTipo());
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Reg a) {
        a.exp().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_STRUCT){
            Tipo_struct tipo = (Tipo_struct) (Nodo) a.exp();
            
            Campo c = tipo.lStruct().getCampos().get(a.id());
            if (c != null)
                a.setTipo(c.getTipo());
            else{
                aviso_error(a);
                a.setTipo(TipoEnum.ERROR);
            }
        }else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Indir a) {
        a.exp().procesa(this);

        if (ref(a.exp()) == TipoEnum.TIPO_PUNT)
            a.setTipo(a.exp().tipo().getTipo());
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Literal_real a) {
        a.setTipo(TipoEnum.TIPO_REAL);
    }

    @Override
    public void procesa(Null a) {
        a.setTipo(TipoEnum.NULL);
    }

    @Override
    public void procesa(Id a) {
        if (a.getVinculo() instanceof Dec_id)
            a.setTipo(((Dec_id)a.getVinculo()).tipo().getTipo());
        else{
            aviso_error(a);
            a.setTipo(TipoEnum.ERROR);
        }
    }   

    @Override
    public void procesa(False a) {
        a.setTipo(TipoEnum.TIPO_BOOL);
    }

    @Override
    public void procesa(True a) {
        a.setTipo(TipoEnum.TIPO_BOOL);
    }

    @Override
    public void procesa(Literal_cadena a) {
        a.setTipo(TipoEnum.TIPO_STRING);
    }

    @Override
    public void procesa(Literal_ent a) {
        a.setTipo(TipoEnum.TIPO_INT);
    }    
}