package procesamiento;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class Tipado extends ProcesamientoDef{

    private Set<Par<Tipo, Tipo>> tipos;
    private List<Exp> lExp;
    private List<Param> lParam;
    Errores errores;

    public Tipado(Prog prog, Errores errores) {
        super();
        this.errores = errores;
        procesa(prog);
    }

    private Tipo ambosTipo_ok(Tipo t0, Tipo t1) {
        if (t0.getClass() == Tipo_error.class || t1.getClass() == Tipo_error.class)
            return new Tipo_error();
        else
            return new Tipo_ok();
    }

    private boolean es_designador(Exp exp) {
        if (exp.getClass() == Id.class)
            return true;
        else if (exp.getClass() == Index.class)
            return true;
        else if (exp.getClass() == Reg.class)
            return true;
        else if (exp.getClass() == Asig.class)
            return true;
        return false;
    }

    private Tipo ref(Tipo tipo) {
        if (tipo.getClass() == Tipo_id.class)
            return ref(((Dec_type)tipo.getVinculo()).tipo());
        return tipo;
    }

    private void aviso_error(Nodo a){
        String s = "Errores_tipado fila:" + a.leeFila() + " col:" + a.leeCol();
        errores.addError(a.leeFila(), a.leeCol(), s);
    }

    private boolean compatible(Tipo t0, Tipo t1) {
        tipos = new HashSet<Par<Tipo, Tipo>>();
        return unificables(t0, t1);
    }

    private boolean unificables(Tipo t0, Tipo t1) {
        Tipo tipo0 = ref(t0);
        Tipo tipo1 = ref(t1);
       
        if (tipo0.getClass() == Tipo_int.class && tipo1.getClass() == Tipo_int.class)
            return true;
        else if (tipo0.getClass() == Tipo_real.class && tipo1.getClass() == Tipo_int.class)
            return true;
        else if (tipo0.getClass() == Tipo_int.class && tipo1.getClass() == Tipo_real.class)
            return true;
        else if (tipo0.getClass() == Tipo_real.class && tipo1.getClass() == Tipo_real.class)
            return true;
        else if (tipo0.getClass() == Tipo_bool.class && tipo1.getClass() == Tipo_bool.class)
            return true;
        else if (tipo0.getClass() == Tipo_string.class && tipo1.getClass() == Tipo_string.class)
            return true;
        else if (tipo0.getClass() == Tipo_punt.class && tipo1.getClass() == Tipo_null.class)
            return true;
        else if (tipo0.getClass() == Tipo_null.class && tipo1.getClass() == Tipo_punt.class)
            return true;
        else if (tipo0.getClass() == Tipo_punt.class && tipo1.getClass() == Tipo_punt.class)
            return son_unificables(tipo0.tipo(), tipo1.tipo());
        else if (tipo0.getClass() == Tipo_array.class && tipo1.getClass() == Tipo_array.class)
            return son_unificables(tipo0.tipo(), tipo1.tipo());
        else if (tipo0.getClass() == Tipo_struct.class && tipo1.getClass() == Tipo_struct.class)
            return struct_son_unificables(((Tipo_struct)tipo0).lStruct(), ((Tipo_struct)tipo1).lStruct()); 
        return false;
    }
    private boolean son_unificables(Tipo t0, Tipo t1){
        if(!tipos.contains(new Par<>(t0, t1))){
            tipos.add(new Par<>(t0, t1));
            return unificables(t0, t1);
        }
        return true;
    }

    private boolean struct_son_unificables(LStruct l0, LStruct l1){
        Lista_struct ls0 = (Lista_struct)l0;
        Lista_struct ls1 = (Lista_struct)l1;

        if(ls0.getClass() == Lista_struct.class && ls1.getClass() == Lista_struct.class){
            return struct_son_unificables(ls0.lStruct(), ls1.lStruct()) && son_unificables(ls0.campo().tipo(), ls1.campo().tipo());
        }
        else if(l0.getClass() == Info_struct.class && l1.getClass() == Info_struct.class){
            return son_unificables(ls0.campo().tipo(), ls1.campo().tipo());
        }
        else 
            return false;
    }

    @Override
    public void procesa(Prog a) {
        a.decs().procesa(this);
        a.instrOpt().procesa(this);
        a.setTipo(ambosTipo_ok(a.decs().getTipo(), a.instrOpt().getTipo()));
    }

    @Override
    public void procesa(Si_decs a) {
        a.decs().procesa(this);
        a.setTipo(a.decs().getTipo());
    }

    @Override
    public void procesa(No_decs a) {
        a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Muchas_decs a) {
        a.decs().procesa(this);
        a.dec().procesa(this);
        a.setTipo(ambosTipo_ok(a.decs().getTipo(), a.dec().getTipo()));
    }

    @Override
    public void procesa(Una_dec a) {
        a.dec().procesa(this);
        a.setTipo(a.dec().getTipo());
    }

    @Override
    public void procesa(Dec_id a) {
        a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Dec_type a) {
        a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Dec_proc a) {
        a.prog().procesa(this);
        a.setTipo(a.prog().getTipo());
    }

    @Override
    public void procesa(Si_parF a){
        a.lparam().procesa(this);
    }

    @Override
    public void procesa(Muchos_param a){
        a.params().procesa(this);
        a.param().procesa(this);
    }
    
    @Override
    public void procesa(Un_param a){
        lParam.add(a.param());   
    }
    
    @Override
    public void procesa(Si_inst a) {
        a.lInstr().procesa(this);
        a.setTipo(a.lInstr().getTipo());
    }

    @Override
    public void procesa(No_inst a) {
        a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Muchas_instr a) {
        a.lInstr().procesa(this);
        a.instr().procesa(this);
        a.setTipo(ambosTipo_ok(a.instr().getTipo(), a.lInstr().getTipo()));
    }

    @Override
    public void procesa(Una_instr a) {
        a.instr().procesa(this);
        a.setTipo(a.instr().getTipo());
    }


    @Override
    public void procesa(Instr_eval a) {
        a.exp().procesa(this);
        a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Instr_if a) {
        a.exp().procesa(this);
        a.prog().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_bool.class)
            a.setTipo(a.prog().getTipo());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
        a.setTipo(ambosTipo_ok(a.exp().getTipo(), a.prog().getTipo()));
    }

    @Override
    public void procesa(Instr_else a) {
        a.exp().procesa(this);
        a.prog1().procesa(this);
        a.prog2().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_bool.class)
            a.setTipo(ambosTipo_ok(a.prog1().getTipo(), a.prog2().getTipo()));
        else
            a.setTipo(new Tipo_error());
    }

    @Override
    public void procesa(Instr_wh a) {
        a.exp().procesa(this);
        a.prog().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_bool.class)
            a.setTipo(a.prog().getTipo());
        else
            a.setTipo(new Tipo_error());
    }

    @Override
    public void procesa(Instr_nl a) {
        a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Instr_new a) {
        a.exp().procesa(this);
        
        if (ref(a.exp().getTipo()).getClass() == Tipo_punt.class)
            a.setTipo(new Tipo_ok());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Instr_rd a) {
        a.exp().procesa(this);
        Tipo t = ref(a.exp().getTipo());

        if(!es_designador(a.exp())){
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
        else if(t.getClass() != Tipo_int.class && t.getClass() != Tipo_real.class && t.getClass() != Tipo_string.class && t.getClass() != Tipo_bool.class){
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
        else
            a.setTipo(new Tipo_ok());
    }

    @Override
    public void procesa(Instr_wr a) {
        a.exp().procesa(this);
        Tipo t = ref(a.exp().getTipo());

        if (t.getClass() == Tipo_int.class || t.getClass() == Tipo_real.class || t.getClass() == Tipo_string.class || t.getClass() == Tipo_bool.class)
            a.setTipo(new Tipo_ok());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Instr_del a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_punt.class) 
            a.setTipo(new Tipo_ok());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Instr_call a) {
        lExp = new ArrayList<>();
        lParam = new ArrayList<>();

        a.paramR().procesa(this);
        ((Dec_proc) a.getVinculo()).paramF().procesa(this);
        if (lParam.size() != lExp.size()){
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
        else{
            for (int i = 0; i < lParam.size(); i++)
                if (!compatible(lParam.get(i).getTipo(), lExp.get(i).tipo())){
                    aviso_error(a);
                    a.setTipo(new Tipo_error());
                    return;
                }
            a.setTipo(new Tipo_ok());
        }
    }

    @Override
    public void procesa(Instr_comp a) {
        a.prog().procesa(this);
        a.setTipo(a.prog().getTipo());
    }

    @Override
    public void procesa(Si_param_re a) { 
        a.lParamR().procesa(this);
        // a.setTipo(a.lParamR().getTipo()); TODO no se si comentarlo o no
    }

    @Override
    public void procesa(No_param_re a) { 
        // a.setTipo(new Tipo_ok());
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
        // a.setTipo(ambosTipo_ok(a.lParamR().getTipo(), a.exp().getTipo()));
    }

    @Override
    public void procesa(Asig a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (es_designador(a.exp1()) && compatible(a.exp1().getTipo(), a.exp2().getTipo()))
            a.setTipo(a.exp1().getTipo());
        else{
            aviso_error(a); 
            a.setTipo(new Tipo_error());
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

        Tipo tipo_exp1 = ref(exp1.getTipo());
        Tipo tipo_exp2 = ref(exp2.getTipo());

        if ((tipo_exp1.getClass() == Tipo_int.class) || (tipo_exp1.getClass() == Tipo_real.class)){
            if ((tipo_exp2.getClass() == Tipo_int.class) || (tipo_exp2.getClass() == Tipo_real.class))
                a.setTipo(new Tipo_bool());
        }
        else if ((tipo_exp1.getClass() == Tipo_bool.class) && (tipo_exp2.getClass() == Tipo_bool.class))
            a.setTipo(new Tipo_bool());
        else if ((tipo_exp1.getClass() == Tipo_string.class) && (tipo_exp2.getClass() == Tipo_string.class))
            a.setTipo(new Tipo_bool());
        else if (op_igualdad){
            if ((tipo_exp1.getClass() == Tipo_punt.class) && (tipo_exp2.getClass() == Tipo_punt.class))
                a.setTipo(new Tipo_bool());
            else if ((tipo_exp1.getClass() == Tipo_punt.class) && (tipo_exp2.getClass() == Tipo_null.class))
                a.setTipo(new Tipo_bool());
            else if ((tipo_exp1.getClass() == Tipo_null.class) && (tipo_exp2.getClass() == Tipo_punt.class))
                a.setTipo(new Tipo_bool());
            else if ((tipo_exp1.getClass() == Tipo_null.class) && (tipo_exp2.getClass() == Tipo_null.class))
                a.setTipo(new Tipo_bool());
            else{
                aviso_error(a);
                a.setTipo(new Tipo_error());
            }
        }
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
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

        Tipo tipo_exp1 = ref(exp1.getTipo());
        Tipo tipo_exp2 = ref(exp2.getTipo());

        if (tipo_exp1.getClass() == Tipo_int.class && tipo_exp2.getClass() == Tipo_int.class)
            a.setTipo(new Tipo_int());
        else if (tipo_exp1.getClass() == Tipo_real.class && tipo_exp2.getClass() == Tipo_real.class)
            a.setTipo(new Tipo_real());
        else if (tipo_exp1.getClass() == Tipo_int.class && tipo_exp2.getClass() == Tipo_real.class)
            a.setTipo(new Tipo_real());
        else if (tipo_exp1.getClass() == Tipo_real.class && tipo_exp2.getClass() == Tipo_int.class)
            a.setTipo(new Tipo_real());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Mod a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (ref(a.exp1().getTipo()).getClass() == Tipo_int.class && ref(a.exp2().getTipo()).getClass() == Tipo_int.class)
            a.setTipo(new Tipo_int());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
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

        if (ref(exp1.getTipo()).getClass() == Tipo_bool.class && ref(exp2.getTipo()).getClass() == Tipo_bool.class)
            a.setTipo(new Tipo_bool());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Menos a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_int.class || ref(a.exp().getTipo()).getClass() == Tipo_real.class)
            a.setTipo(a.exp().getTipo());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Not a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_bool.class)
            a.setTipo(new Tipo_bool());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Index a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (ref(a.exp1().getTipo()).getClass() == Tipo_array.class && ref(a.exp2().getTipo()).getClass() == Tipo_int.class)
            a.setTipo(ref(a.exp1().getTipo()).tipo());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Reg a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_struct.class){
            Tipo_struct tipo = (Tipo_struct) ref(a.exp().getTipo());
            Tipo t = tipo.getTipoCampo(a.id());
            if (t == null){
                aviso_error(a);
                a.setTipo(new Tipo_error());
            }
            else
                a.setTipo(tipo.getTipoCampo(a.id()));
        }else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Indir a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()).getClass() == Tipo_punt.class)
            a.setTipo(ref(a.exp().getTipo()).tipo());
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }

    @Override
    public void procesa(Literal_real a) {
        a.setTipo(new Tipo_int());
    }

    @Override
    public void procesa(Null a) {
        a.setTipo(new Tipo_null());
    }

    @Override
    public void procesa(Id a) {
        if(a.getVinculo().getClass() == Dec_id.class){
            Dec_id dec = (Dec_id) a.getVinculo();
            a.setTipo(dec.tipo());
        } 
        else if (a.getVinculo().getClass() == Param_cop.class){
            Param_cop param_cop = (Param_cop) a.getVinculo();
            a.setTipo(param_cop.tipo());
        }
        else if (a.getVinculo().getClass() == Param_ref.class){
            Param_ref param_ref = (Param_ref) a.getVinculo();
            a.setTipo(param_ref.tipo());
        }
        else{
            aviso_error(a);
            a.setTipo(new Tipo_error());
        }
    }   

    @Override
    public void procesa(False a) {
        a.setTipo(new Tipo_bool());
    }

    @Override
    public void procesa(True a) {
        a.setTipo(new Tipo_bool());
    }

    @Override
    public void procesa(Literal_cadena a) {
        a.setTipo(new Tipo_string());
    }

    @Override
    public void procesa(Literal_ent a) {
        a.setTipo(new Tipo_int());
    }    
}