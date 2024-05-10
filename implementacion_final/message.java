package model.semantica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import model.Procesamiento;
import model.sintaxis.SintaxisAbstracta.Campos;
import model.sintaxis.SintaxisAbstracta.Error_tipo;
import model.sintaxis.SintaxisAbstracta.Exp;
import model.sintaxis.SintaxisAbstracta.Ok_tipo;
import model.sintaxis.SintaxisAbstracta.Param;
import model.sintaxis.SintaxisAbstracta.Tipo;
import model.sintaxis.SintaxisAbstracta.A_tipo;
import model.sintaxis.SintaxisAbstracta.Acceso;
import model.sintaxis.SintaxisAbstracta.And;
import model.sintaxis.SintaxisAbstracta.Asig;
import model.sintaxis.SintaxisAbstracta.B_tipo;
import model.sintaxis.SintaxisAbstracta.Bloque;
import model.sintaxis.SintaxisAbstracta.Bq_instr;
import model.sintaxis.SintaxisAbstracta.Camp;
import model.sintaxis.SintaxisAbstracta.Cl;
import model.sintaxis.SintaxisAbstracta.Dif;
import model.sintaxis.SintaxisAbstracta.Div;
import model.sintaxis.SintaxisAbstracta.Dl;
import model.sintaxis.SintaxisAbstracta.Entero;
import model.sintaxis.SintaxisAbstracta.Eva;
import model.sintaxis.SintaxisAbstracta.False;
import model.sintaxis.SintaxisAbstracta.Id_tipo;
import model.sintaxis.SintaxisAbstracta.Iden;
import model.sintaxis.SintaxisAbstracta.If_el;
import model.sintaxis.SintaxisAbstracta.If_instr;
import model.sintaxis.SintaxisAbstracta.Ig;
import model.sintaxis.SintaxisAbstracta.In_tipo;
import model.sintaxis.SintaxisAbstracta.Indexacion;
import model.sintaxis.SintaxisAbstracta.Indireccion;
import model.sintaxis.SintaxisAbstracta.L_campos;
import model.sintaxis.SintaxisAbstracta.L_decs;
import model.sintaxis.SintaxisAbstracta.L_exps;
import model.sintaxis.SintaxisAbstracta.L_instrs;
import model.sintaxis.SintaxisAbstracta.L_param;
import model.sintaxis.SintaxisAbstracta.Menos_unario;
import model.sintaxis.SintaxisAbstracta.Mn;
import model.sintaxis.SintaxisAbstracta.Mnig;
import model.sintaxis.SintaxisAbstracta.Mod;
import model.sintaxis.SintaxisAbstracta.Mul;
import model.sintaxis.SintaxisAbstracta.My;
import model.sintaxis.SintaxisAbstracta.Myig;
import model.sintaxis.SintaxisAbstracta.Nl_instr;
import model.sintaxis.SintaxisAbstracta.No_decs;
import model.sintaxis.SintaxisAbstracta.No_exps;
import model.sintaxis.SintaxisAbstracta.No_instrs;
import model.sintaxis.SintaxisAbstracta.No_param;
import model.sintaxis.SintaxisAbstracta.Not;
import model.sintaxis.SintaxisAbstracta.Null_exp;
import model.sintaxis.SintaxisAbstracta.Nw;
import model.sintaxis.SintaxisAbstracta.Or;
import model.sintaxis.SintaxisAbstracta.P_dec;
import model.sintaxis.SintaxisAbstracta.P_tipo;
import model.sintaxis.SintaxisAbstracta.N_tipo;
import model.sintaxis.SintaxisAbstracta.Param_ref;
import model.sintaxis.SintaxisAbstracta.Param_simple;
import model.sintaxis.SintaxisAbstracta.Prog;
import model.sintaxis.SintaxisAbstracta.R_tipo;
import model.sintaxis.SintaxisAbstracta.Rd;
import model.sintaxis.SintaxisAbstracta.Real;
import model.sintaxis.SintaxisAbstracta.Resta;
import model.sintaxis.SintaxisAbstracta.Si_decs;
import model.sintaxis.SintaxisAbstracta.Si_exps;
import model.sintaxis.SintaxisAbstracta.Si_instrs;
import model.sintaxis.SintaxisAbstracta.Si_param;
import model.sintaxis.SintaxisAbstracta.String_exp;
import model.sintaxis.SintaxisAbstracta.String_tipo;
import model.sintaxis.SintaxisAbstracta.Struct_tipo;
import model.sintaxis.SintaxisAbstracta.Suma;
import model.sintaxis.SintaxisAbstracta.T_dec;
import model.sintaxis.SintaxisAbstracta.True;
import model.sintaxis.SintaxisAbstracta.Un_campo;
import model.sintaxis.SintaxisAbstracta.Un_param;
import model.sintaxis.SintaxisAbstracta.Una_dec;
import model.sintaxis.SintaxisAbstracta.Una_exp;
import model.sintaxis.SintaxisAbstracta.Una_instr;
import model.sintaxis.SintaxisAbstracta.V_dec;
import model.sintaxis.SintaxisAbstracta.Wh;
import model.sintaxis.SintaxisAbstracta.Wr;
import utils.Pair;
import view.Printer;

public class ComprobacionTipos implements Procesamiento {
    private final Printer output;
    private List<Param> listaParam;
    private List<Exp> listaExp;
    private List<Pair<Tipo, Tipo>> checkeo;

    public ComprobacionTipos(Printer output){
        this.output = output;
    }
    @Override
    public void procesa(Prog prog) throws IOException {
        prog.bloque().procesa(this);
        prog.setTipo(prog.bloque().getTipo());
    }

    @Override
    public void procesa(Bloque bloque) throws IOException {
        bloque.decsOpt().procesa(this);
        bloque.instrsOpt().procesa(this);
        bloque.setTipo(ambos_ok(bloque.decsOpt().getTipo(), bloque.instrsOpt().getTipo()));
    }

    @Override
    public void procesa(Si_decs decs) throws IOException {
        decs.decs().procesa(this);
        decs.setTipo(decs.decs().getTipo());
    }

    @Override
    public void procesa(No_decs decs) throws IOException {
        decs.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(L_decs decs) throws IOException {
        decs.decs().procesa(this);
        decs.dec().procesa(this);
        decs.setTipo(ambos_ok(decs.decs().getTipo(), decs.dec().getTipo()));
    }

    @Override
    public void procesa(Una_dec decs) throws IOException {
        decs.dec().procesa(this);
        decs.setTipo(decs.dec().getTipo());
    }

    @Override
    public void procesa(T_dec dec) throws IOException {
        dec.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(V_dec dec) throws IOException {
        dec.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(P_dec dec) throws IOException {
        dec.bloque().procesa(this);
        dec.setTipo(dec.bloque().getTipo());
    }

    @Override
    public void procesa(A_tipo tipo) throws IOException {}

    @Override
    public void procesa(P_tipo tipo) throws IOException {}

    @Override
    public void procesa(In_tipo tipo) throws IOException {}

    @Override
    public void procesa(R_tipo tipo) throws IOException {}

    @Override
    public void procesa(B_tipo tipo) throws IOException {}

    @Override
    public void procesa(String_tipo tipo) throws IOException {}

    @Override
    public void procesa(Id_tipo tipo) throws IOException { }

    @Override
    public void procesa(Struct_tipo tipo) throws IOException {}

    @Override
    public void procesa(L_campos campos) throws IOException {}

    @Override
    public void procesa(Un_campo campos) throws IOException {}

    @Override
    public void procesa(Camp campo) throws IOException {}

    @Override
    public void procesa(Si_param lParam) throws IOException {
        lParam.lParam().procesa(this);
    }

    @Override
    public void procesa(No_param lParam) throws IOException {}

    @Override
    public void procesa(L_param lParam) throws IOException {
        lParam.lParam().procesa(this);
        lParam.param().procesa(this);
    }

    @Override
    public void procesa(Un_param lParam) throws IOException {
        listaParam.add(lParam.param());
    }

    @Override
    public void procesa(Param_simple param) throws IOException {}

    @Override
    public void procesa(Param_ref param) throws IOException {}

    @Override
    public void procesa(Si_instrs instrs) throws IOException {
        instrs.instrs().procesa(this);
        instrs.setTipo(instrs.instrs().getTipo());
    }

    @Override
    public void procesa(No_instrs instrs) throws IOException {
        instrs.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(L_instrs instrs) throws IOException {
        instrs.instrs().procesa(this);
        instrs.instr().procesa(this);
        instrs.setTipo(ambos_ok(instrs.instrs().getTipo(), instrs.instr().getTipo()));
    }

    @Override
    public void procesa(Una_instr instrs) throws IOException {
        instrs.instr().procesa(this);
        instrs.setTipo(instrs.instr().getTipo());
    }

    @Override
    public void procesa(Eva instr) throws IOException {
        instr.exp().procesa(this);
        instr.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(If_instr instr) throws IOException {
        instr.exp().procesa(this);
        instr.bloque().procesa(this);
        if(!claseDe(ref(instr.exp().getTipo()), B_tipo.class)){
            aviso_error(ref(instr.exp().getTipo()), "Se esta intentado poner una condicion de un if que no es un"
                + "booleano\n");
            instr.setTipo(new Error_tipo());
        }
        else instr.setTipo(instr.bloque().getTipo());
    }

    @Override
    public void procesa(If_el instr) throws IOException {
        instr.exp().procesa(this);
        instr.bloque().procesa(this);
        instr.bloqueElse().procesa(this);
        if(!claseDe(ref(instr.exp().getTipo()), B_tipo.class)){
            aviso_error(ref(instr.exp().getTipo()), "Se esta intentado poner una condicion de un if que no es un"
                + "booleano\n");
            instr.setTipo(new Error_tipo());
        }
        else instr.setTipo(ambos_ok(instr.bloque().getTipo(), instr.bloqueElse().getTipo()));
    }

    @Override
    public void procesa(Wh instr) throws IOException {
        instr.exp().procesa(this);
        instr.bloque().procesa(this);
        if(!claseDe(ref(instr.exp().getTipo()), B_tipo.class)){
            aviso_error(ref(instr.exp().getTipo()), "Se esta intentado poner una condicion de un if que no es un"
                + "booleano\n");
            instr.setTipo(new Error_tipo());
        }
        else instr.setTipo(instr.bloque().getTipo());
    }

    @Override
    public void procesa(Rd instr) throws IOException {
        instr.exp().procesa(this);
        if(!esDesignador(instr.exp())){
            aviso_error(ref(instr.exp().getTipo()), "La expresion que se esta pasando para leer no es un designador\n");
            instr.setTipo(new Error_tipo());
        }
        if(claseDe(ref(instr.exp().getTipo()), In_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else if(claseDe(ref(instr.exp().getTipo()), R_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else if(claseDe(ref(instr.exp().getTipo()), B_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else if(claseDe(ref(instr.exp().getTipo()), String_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else {
            aviso_error(ref(instr.exp().getTipo()), "La expresion que se esta pasando para leer no es de tipo"
                + "imprimible\n");
            instr.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Wr instr) throws IOException {
        instr.exp().procesa(this);
        if(claseDe(ref(instr.exp().getTipo()), In_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else if(claseDe(ref(instr.exp().getTipo()), R_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else if(claseDe(ref(instr.exp().getTipo()), B_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else if(claseDe(ref(instr.exp().getTipo()), String_tipo.class)){
            instr.setTipo(new Ok_tipo());
        }
        else {
            aviso_error(ref(instr.exp().getTipo()), "La expresion que se esta pasando para leer no es de tipo"
                + "imprimible\n");
            instr.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Nw instr) throws IOException {
        instr.exp().procesa(this);
        if(!claseDe(ref(instr.exp().getTipo()), P_tipo.class)) {
            aviso_error(ref(instr.exp().getTipo()), "Esta intentando alocar algo que no es un puntero\n");
            instr.setTipo(new Error_tipo());
        }
        else instr.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(Dl instr) throws IOException {
        instr.exp().procesa(this);
        if(!claseDe(ref(instr.exp().getTipo()), P_tipo.class)) {
            aviso_error(ref(instr.exp().getTipo()), "Esta intentando desalocar algo que no es un puntero\n");
            instr.setTipo(new Error_tipo());
        }
        else instr.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(Nl_instr instr) throws IOException {
        instr.setTipo(new Ok_tipo());
    }

    @Override
    public void procesa(Cl instr) throws IOException {
        listaExp = new ArrayList<>();
        listaParam = new ArrayList<>();
        instr.expsOpt().procesa(this);
        ((P_dec) instr.getVinculo()).lParamOpt().procesa(this);
        if(listaParam.size() != listaExp.size()) {
            aviso_error("El número de argumentos no coincide con el número de parametros\n");
            instr.setTipo(new Error_tipo());
        }
        else{
            instr.setTipo(new Ok_tipo());
            for(int i = 0; i < listaParam.size(); ++i){
                if(!compatible(listaExp.get(i).getTipo(), listaParam.get(i).tipo())){
                    aviso_error(listaExp.get(i).getTipo(), "El argumento " + i + "-esimo no es de tipo compatible con el parametro\n");
                    instr.setTipo(new Error_tipo());
                }
            }
        }
    }

    @Override
    public void procesa(Bq_instr instr) throws IOException {
        instr.bloque().procesa(this);
        instr.setTipo(instr.bloque().getTipo());
    }

    @Override
    public void procesa(Si_exps exps) throws IOException {
        exps.exps().procesa(this);
    }

    @Override
    public void procesa(No_exps exps) throws IOException {}

    @Override
    public void procesa(L_exps exps) throws IOException {
        exps.exps().procesa(this);
        exps.exp().procesa(this);
    }

    @Override
    public void procesa(Una_exp exps) throws IOException {
        exps.exp().procesa(this);
        listaExp.add(exps.exp());
    }

    @Override
    public void procesa(Asig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(!esDesignador(exp.opnd0())){
            aviso_error("Se esperaba un designador\n");
            exp.setTipo(new Error_tipo());
        }
        if(!compatible(exp.opnd0().getTipo(), exp.opnd1().getTipo())){
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(),"Tipos no compatibles\n");
            exp.setTipo(new Error_tipo());
        }
        exp.setTipo(exp.opnd0().getTipo());
    }

    @Override
    public void procesa(My exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)){
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new B_tipo());
            }
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), String_tipo.class) &&
            claseDe(ref(exp.opnd1().getTipo()), String_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Mn exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)) {
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new B_tipo());
            }
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), String_tipo.class) &&
            claseDe(ref(exp.opnd1().getTipo()), String_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Myig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)) {
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new B_tipo());
            }
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), String_tipo.class) &&
            claseDe(ref(exp.opnd1().getTipo()), String_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Mnig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)) {
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new B_tipo());
            }
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), String_tipo.class) &&
            claseDe(ref(exp.opnd1().getTipo()), String_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Ig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)) {
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new B_tipo());
            }
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), String_tipo.class) &&
            claseDe(ref(exp.opnd1().getTipo()), String_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), P_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), P_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), P_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), N_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), N_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), P_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), N_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), N_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Dif exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)) {
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new B_tipo());
            }
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), String_tipo.class) &&
            claseDe(ref(exp.opnd1().getTipo()), String_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), P_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), P_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), P_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), N_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), N_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), P_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), N_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), N_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Suma exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)) {
            exp.setTipo(new In_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)){
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new R_tipo());
            }
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Resta exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)) {
            exp.setTipo(new In_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)){
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new R_tipo());
            }
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(And exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Or exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), B_tipo.class)) {
            exp.setTipo(new B_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Mul exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)) {
            exp.setTipo(new In_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)){
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new R_tipo());
            }
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Div exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)) {
            exp.setTipo(new In_tipo());
        }
        else if(claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)){
            if(claseDe(ref(exp.opnd1().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd1().getTipo()), R_tipo.class)) {
                exp.setTipo(new R_tipo());
            }
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Mod exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)) {
            exp.setTipo(new In_tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Menos_unario exp) throws IOException {
        exp.opnd0().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), In_tipo.class) || claseDe(ref(exp.opnd0().getTipo()), R_tipo.class)) {
            exp.setTipo(exp.opnd0().getTipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Not exp) throws IOException {
        exp.opnd0().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), B_tipo.class)) {
            exp.setTipo(exp.opnd0().getTipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Tipado de al menos un operando no valido\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Indexacion exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(!claseDe(ref(exp.opnd0().getTipo()), A_tipo.class)) {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "El operando no se puedo indexar\n");
            exp.setTipo(new Error_tipo());
        }
        if(!claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)){
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "Se esta intentando indexar sin pasar un entero\n");
            exp.setTipo(new Error_tipo());
        }
        exp.setTipo(ref(exp.opnd0().getTipo()).tipo());
    }

    @Override
    public void procesa(Acceso exp) throws IOException {
        exp.opnd0().procesa(this);
        if(!claseDe(ref(exp.opnd0().getTipo()), Struct_tipo.class)) {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "El operando no puede acceder a campos\n");
            exp.setTipo(new Error_tipo());
        }
        Struct_tipo struct = (Struct_tipo) ref(exp.opnd0().getTipo());
        exp.setTipo(struct.getTipoDe(exp.iden()));
    }

    @Override
    public void procesa(Indireccion exp) throws IOException {
        exp.opnd0().procesa(this);
        if(claseDe(ref(exp.opnd0().getTipo()), P_tipo.class)){
            exp.setTipo(ref(exp.opnd0().getTipo()).tipo());
        }
        else {
            aviso_error(exp.opnd0().getTipo(), exp.opnd1().getTipo(), "El operando no es un puntero\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Entero exp) throws IOException {
        exp.setTipo(new In_tipo());
    }

    @Override
    public void procesa(Real exp) throws IOException {
        exp.setTipo(new R_tipo());
    }

    @Override
    public void procesa(True exp) throws IOException {
        exp.setTipo(new B_tipo());
    }

    @Override
    public void procesa(False exp) throws IOException {
        exp.setTipo(new B_tipo());
    }

    @Override
    public void procesa(String_exp exp) throws IOException {
        exp.setTipo(new String_tipo());
    }

    @Override
    public void procesa(Iden exp) throws IOException {
        if(claseDe(exp.getVinculo(), V_dec.class)){
            V_dec vDec = (V_dec) exp.getVinculo();
            exp.setTipo(vDec.tipo());
        }
        else if(claseDe(exp.getVinculo(), Param_simple.class)){
            Param_simple param = (Param_simple) exp.getVinculo();
            exp.setTipo(param.tipo());
        }
        else if(claseDe(exp.getVinculo(), Param_ref.class)){
            Param_ref param = (Param_ref) exp.getVinculo();
            exp.setTipo(param.tipo());
        }
        else {
            aviso_error("El identificador esta vinculado con una declaración de procedimiento o tipo "
                + "pero se espera que fuera variable o parámetro\n");
            exp.setTipo(new Error_tipo());
        }
    }

    @Override
    public void procesa(Null_exp exp) throws IOException {
        exp.setTipo(new N_tipo());
    }

    private Tipo ref(Tipo t){
        if(claseDe(t, Id_tipo.class)){
            T_dec tDec = (T_dec) t.getVinculo();
            return ref(tDec.tipo());
        }
        else return t;
    }

    private boolean esDesignador(Exp exp){
        return claseDe(exp, Iden.class) || claseDe(exp, Acceso.class) || claseDe(exp, Indexacion.class) ||
            claseDe(exp, Asig.class);
    }

    private boolean compatible(Tipo a, Tipo b){
        checkeo = new ArrayList<>();
        return unificables(a, b);
    }

    private boolean unificables(Tipo a, Tipo b){
        if(claseDe(ref(a), In_tipo.class) && claseDe(ref(b), In_tipo.class))
            return true;
        else if(claseDe(ref(a), R_tipo.class) && claseDe(ref(b), In_tipo.class))
            return true;
        else if(claseDe(ref(a), R_tipo.class) && claseDe(ref(b), R_tipo.class))
            return true;
        else if(claseDe(ref(a), B_tipo.class) && claseDe(ref(b), B_tipo.class))
            return true;
        else if(claseDe(ref(a), String_tipo.class) && claseDe(ref(b), String_tipo.class))
            return true;
        else if(claseDe(ref(a), Struct_tipo.class) && claseDe(ref(b), Struct_tipo.class)) {
            return sonUnificablesCampos(ref(a).campos(), ref(b).campos());
        }
        else if(claseDe(ref(a), A_tipo.class) && claseDe(ref(b), A_tipo.class)){
            return sonUnificables(ref(a).tipo(), ref(b).tipo());
        }
        else if(claseDe(ref(a), P_tipo.class) && claseDe(ref(b), N_tipo.class)){
            return true;
        }
        else if(claseDe(ref(a), P_tipo.class) && claseDe(ref(b), P_tipo.class)) {
            return sonUnificables(ref(a).tipo(), ref(b).tipo());
        }
        return false;
    }

    private boolean sonUnificables(Tipo a, Tipo b){
        if(!checkeo.contains(new Pair<>(a, b))){
            checkeo.add(new Pair<>(a, b));
            return unificables(a, b);
        }
        else{
            return true;
        }
    }

    private boolean sonUnificablesCampos(Campos c0, Campos c1){
        if(claseDe(c0, L_campos.class) && claseDe(c1, L_campos.class)){
            return sonUnificablesCampos(c0.campos(), c1.campos()) && sonUnificables(c0.campo().tipo(), c1.campo().tipo());
        }
        else if(claseDe(c0, Un_campo.class) && claseDe(c1, Un_campo.class)) {
            return sonUnificables(c0.campo().tipo(), c1.campo().tipo());
        }
        else throw new RuntimeException("Los campos de las estructuras no coinciden\n");
    }

    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }

    private Tipo ambos_ok(Tipo t0, Tipo t1){
        if(!claseDe(t0, Error_tipo.class) && !claseDe(t1, Error_tipo.class))
            return new Ok_tipo();
        else
            return new Error_tipo();
    }

    private void aviso_error(String message) throws IOException {
        output.write(message);
    }

    private void aviso_error(Tipo t, String message) throws IOException {
        if(!claseDe(t, Error_tipo.class)){
            output.write(message);
        }
    }

    private void aviso_error(Tipo t0, Tipo t1, String message) throws IOException {
        if(!claseDe(t0, Error_tipo.class) && !claseDe(t1, Error_tipo.class)){
            output.write(message);
        }
    }
}
