package procesamiento;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class Tipado extends ProcesamientoDef{

    private TipoEnum ambosOK(TipoEnum t0, TipoEnum t1) {
        if (t0 == TipoEnum.ERROR || t1 == TipoEnum.ERROR)
            return TipoEnum.ERROR;
        else
            return TipoEnum.OK;
    }

    private void aviso_error(TipoEnum t0, TipoEnum t1){
        // if (t0 != TipoEnum.ERROR && t1 != TipoEnum.ERROR)
            //error;
    }

    private void aviso_error(TipoEnum t){
        // if (t != TipoEnum.ERROR)
        //     error;
    }

    private void aviso_error(){
        //error;
    }

    private TipoEnum ref(TipoEnum tipo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ref'");
    }

    private boolean compatible(TipoEnum t0, TipoEnum t1) {
        TipoEnum tipo0 = ref(t0);
        TipoEnum tipo1 = ref(t1);

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
        //TODO
        //STRUCT 
        //ARRAY
        //PUNTERO
        return false;
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

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_BOOL)
            a.setTipo(ambosOK(a.prog1().getTipo(), a.prog2().getTipo()));
        else
            a.setTipo(TipoEnum.ERROR);
    }

    @Override
    public void procesa(Instr_wh a) {
        a.exp().procesa(this);
        a.prog().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_BOOL)
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
        a.exp().procesa(this); //TODO no se cual es el tipo de punt
        
        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_PUNT)
            a.setTipo(TipoEnum.OK);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_rd a) {
        a.exp().procesa(this);

        if (a.exp().es_designador()){
            if (ref(a.exp().getTipo()) == TipoEnum.TIPO_INT || ref(a.exp().getTipo()) == TipoEnum.TIPO_REAL || ref(a.exp().getTipo()) == TipoEnum.TIPO_STRING)
                a.setTipo(TipoEnum.OK);
            else{
                //aviso_error();
                a.setTipo(TipoEnum.ERROR);
            }
        }
        else{
            //error; TODO
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_wr a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_INT || ref(a.exp().getTipo()) == TipoEnum.TIPO_REAL || ref(a.exp().getTipo()) == TipoEnum.TIPO_STRING || ref(a.exp().getTipo()) == TipoEnum.TIPO_BOOL)
            a.setTipo(TipoEnum.OK);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_del a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_PUNT) //TODO no se cual es el tipo de punt
            a.setTipo(TipoEnum.OK);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Instr_call a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Instr_comp a) {
        a.prog().procesa(this);
        a.setTipo(a.prog().getTipo());
    }

    @Override
    public void procesa(Si_param_re a) { //TODO por que no hay no_param_re?
        a.lParamR().procesa(this);
        a.setTipo(a.lParamR().getTipo());
    }

    @Override
    public void procesa(No_param_re a) { //TODO por que no hay no_param_re?
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

        if (a.exp1().es_designador() && compatible(a.exp1().getTipo(), a.exp2().getTipo()))
            a.setTipo(a.exp1().getTipo());
        else{
            //aviso_error(); 
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
        exp1.procesa(this); //TODO no se si es this o a
        exp2.procesa(this);

        TipoEnum tipo_exp1 = ref(exp1.getTipo());
        TipoEnum tipo_exp2 = ref(exp2.getTipo());

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
                //aviso_error();
                a.setTipo(TipoEnum.ERROR);
            }
        }
        else{
            //aviso_error();
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
        exp1.procesa(this); //TODO no se si es this o a
        exp2.procesa(this);

        TipoEnum tipo_exp1 = ref(exp1.getTipo());
        TipoEnum tipo_exp2 = ref(exp2.getTipo());

        if (tipo_exp1 == TipoEnum.TIPO_INT && tipo_exp2 == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT);
        else if (tipo_exp1 == TipoEnum.TIPO_REAL && tipo_exp2 == TipoEnum.TIPO_REAL)
            a.setTipo(TipoEnum.TIPO_REAL);
        else if (tipo_exp1 == TipoEnum.TIPO_INT && tipo_exp2 == TipoEnum.TIPO_REAL)
            a.setTipo(TipoEnum.TIPO_REAL);
        else if (tipo_exp1 == TipoEnum.TIPO_REAL && tipo_exp2 == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_REAL);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Mod a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (ref(a.exp1().getTipo()) == TipoEnum.TIPO_INT && ref(a.exp2().getTipo()) == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT);
        else{
            //aviso_error();
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
        exp1.procesa(this); //TODO no se si es this o a
        exp2.procesa(this);

        if (ref(exp1.getTipo()) == TipoEnum.TIPO_BOOL && ref(exp2.getTipo()) == TipoEnum.TIPO_BOOL)
            a.setTipo(TipoEnum.TIPO_BOOL);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Menos a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT);
        else if (ref(a.exp().getTipo()) == TipoEnum.TIPO_REAL)
            a.setTipo(TipoEnum.TIPO_REAL);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Not a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_BOOL)
            a.setTipo(TipoEnum.TIPO_BOOL);
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Index a) {
        a.exp1().procesa(this);
        a.exp2().procesa(this);

        if (ref(a.exp1().getTipo()) == TipoEnum.TIPO_ARRAY && ref(a.exp2().getTipo()) == TipoEnum.TIPO_INT)
            a.setTipo(TipoEnum.TIPO_INT); //TODO el tipo base del array, no TIPO_INT
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Reg a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_STRUCT)
            a.setTipo(TipoEnum.TIPO_INT); //TODO el tipo del campo, no TIPO_INT
        else{
            //aviso_error();
            a.setTipo(TipoEnum.ERROR);
        }
    }

    @Override
    public void procesa(Indir a) {
        a.exp().procesa(this);

        if (ref(a.exp().getTipo()) == TipoEnum.TIPO_PUNT)
            a.setTipo(TipoEnum.TIPO_INT); //TODO el tipo base del puntero, no TIPO_INT
        else{
            //aviso_error();
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
        // if (a.getVinculo().getTipo() == DecEnum.VAR) TODO
        //     a.setTipo(((Dec_tipado)a.getVinculo()).tipo().getTipo());
        // else{
        //     //aviso_error();
        //     a.setTipo(TipoEnum.ERROR);
        // }
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
