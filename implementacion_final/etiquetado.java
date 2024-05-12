package model.semantica;

import java.io.IOException;
import java.util.Stack;

import model.Procesamiento;
import model.sintaxis.SintaxisAbstracta;
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
import model.sintaxis.SintaxisAbstracta.Exp;
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
import model.sintaxis.SintaxisAbstracta.Tipo;
import model.sintaxis.SintaxisAbstracta.True;
import model.sintaxis.SintaxisAbstracta.Un_campo;
import model.sintaxis.SintaxisAbstracta.Un_param;
import model.sintaxis.SintaxisAbstracta.Una_dec;
import model.sintaxis.SintaxisAbstracta.Una_exp;
import model.sintaxis.SintaxisAbstracta.Una_instr;
import model.sintaxis.SintaxisAbstracta.V_dec;
import model.sintaxis.SintaxisAbstracta.Wh;
import model.sintaxis.SintaxisAbstracta.Wr;

public class Etiquetado implements Procesamiento {
    private int etq = 0;
    private Stack<P_dec> procesosPendientes = new Stack<>();

    @Override
    public void procesa(Prog prog) throws IOException {
        prog.setPrim(etq);
        prog.bloque().procesa(this);
        etq++;
        while(!procesosPendientes.empty()){
            P_dec procesoActual = procesosPendientes.pop();
            procesoActual.setPrim(etq);
            etq++;
            procesoActual.bloque().procesa(this);
            etq += 2;
            procesoActual.setSig(etq);
        }
        prog.setSig(etq);
    }

    @Override
    public void procesa(Bloque bloque) throws IOException {
        bloque.setPrim(etq);
        bloque.decsOpt().procesa(this);
        bloque.instrsOpt().procesa(this);
        bloque.setSig(etq);
    }

    @Override
    public void procesa(Si_decs decs) throws IOException {
        decs.decs().procesa(this);
    }

    @Override
    public void procesa(No_decs decs) throws IOException {}

    @Override
    public void procesa(L_decs decs) throws IOException {
        decs.decs().procesa((this));
        decs.dec().procesa(this);
    }

    @Override
    public void procesa(Una_dec decs) throws IOException {
        decs.dec().procesa(this);
    }

    @Override
    public void procesa(T_dec dec) throws IOException {}

    @Override
    public void procesa(V_dec dec) throws IOException {}

    @Override
    public void procesa(P_dec dec) throws IOException {
        procesosPendientes.push(dec);
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
    public void procesa(Id_tipo tipo) throws IOException {}

    @Override
    public void procesa(Struct_tipo tipo) throws IOException {}

    @Override
    public void procesa(L_campos campos) throws IOException {}

    @Override
    public void procesa(Un_campo campos) throws IOException {}

    @Override
    public void procesa(Camp campo) throws IOException {}

    @Override
    public void procesa(Si_param lParam) throws IOException {}

    @Override
    public void procesa(No_param lParam) throws IOException {}

    @Override
    public void procesa(L_param lParam) throws IOException {}

    @Override
    public void procesa(Un_param lParam) throws IOException {}

    @Override
    public void procesa(Param_simple param) throws IOException {}

    @Override
    public void procesa(Param_ref param) throws IOException {}

    @Override
    public void procesa(Si_instrs instrs) throws IOException {
        instrs.setPrim(etq);
        instrs.instrs().procesa(this);
        instrs.setSig(etq);
    }

    @Override
    public void procesa(No_instrs instrs) throws IOException {}

    @Override
    public void procesa(L_instrs instrs) throws IOException {
        instrs.setPrim(etq);
        instrs.instrs().procesa(this);
        instrs.instr().procesa(this);
        instrs.setSig(etq);
    }

    @Override
    public void procesa(Una_instr instrs) throws IOException {
        instrs.setPrim(etq);
        instrs.instr().procesa(this);
        instrs.setSig(etq);
    }

    @Override
    public void procesa(Eva instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etq++;
        instr.setSig(etq);
    }

    @Override
    public void procesa(If_instr instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etiquetado_acc_val(instr.exp());
        etq++;
        instr.bloque().procesa(this);
        instr.setSig(etq);
    }

    @Override
    public void procesa(If_el instr) throws IOException {
        instr.exp().procesa(this);
        etiquetado_acc_val(instr.exp());
        etq++;
        instr.bloque().procesa(this);
        etq++;
        instr.setPrim(etq);
        instr.bloqueElse().procesa(this);
        instr.setSig(etq);
    }

    @Override
    public void procesa(Wh instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etiquetado_acc_val(instr.exp());
        etq++;
        instr.bloque().procesa(this);
        etq++;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Rd instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etq += 2;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Wr instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etq += esDesignador(instr.exp()) ? 1 : 0;
        etq++;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Nw instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etq += 2;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Dl instr) throws IOException {
        instr.setPrim(etq);
        instr.exp().procesa(this);
        etq += 4;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Nl_instr instr) throws IOException {
        instr.setPrim(etq);
        etq += 2;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Cl instr) throws IOException {
        instr.setPrim(etq);
        etq++;
        P_dec p = (P_dec) instr.getVinculo();
        Si_param sp = claseDe(p.lParamOpt(), Si_param.class) ? (Si_param) p.lParamOpt() : null;
        Si_exps se = claseDe(instr.expsOpt(), Si_exps.class) ? (Si_exps) instr.expsOpt() : null;
        if (sp != null && se != null)
            etiquetado_paso_param(sp, se);
        etq++;
        instr.setSig(etq);
    }

    @Override
    public void procesa(Bq_instr instr) throws IOException {
        instr.setPrim(etq);
        instr.bloque().procesa(this);
        instr.setSig(etq);
    }

    @Override
    public void procesa(Si_exps exps) throws IOException {}

    @Override
    public void procesa(No_exps exps) throws IOException {}

    @Override
    public void procesa(L_exps exps) throws IOException {}

    @Override
    public void procesa(Una_exp exps) throws IOException {}

    @Override
    public void procesa(Asig exp) throws IOException {
        exp.setPrim(etq);
        exp.opnd0().procesa(this);
        etq++;
        exp.opnd1().procesa(this);
        if(esDesignador(exp.opnd1())){
            if(claseDe(ref(exp.opnd0().getTipo()), R_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)){
                etq++;
            }
            else{
                etq++;
            }
        }
        else{
            if(claseDe(ref(exp.opnd0().getTipo()), R_tipo.class) && claseDe(ref(exp.opnd1().getTipo()), In_tipo.class)){
                etq+=2;
            }
            else{
                etq++;
            }
        }
        exp.setSig(etq);
    }

    @Override
    public void procesa(My exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Mn exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Myig exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Mnig exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Ig exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Dif exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Suma exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Resta exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(And exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Or exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Mul exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Div exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Mod exp) throws IOException {
        exp.setPrim(etq);
        etiquetado_opnds(exp.opnd0(),exp.opnd1(), exp);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Menos_unario exp) throws IOException {
        exp.setPrim(etq);
        exp.opnd0().procesa(this);
        if(esDesignador(exp.opnd0()))
            etq++;
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Not exp) throws IOException {
        exp.setPrim(etq);
        exp.opnd0().procesa(this);
        if(esDesignador(exp.opnd0()))
            etq++;
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Indexacion exp) throws IOException {
        exp.setPrim(etq);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if(esDesignador(exp.opnd1())){
            etq++;
        }
        etq += 3;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Acceso exp) throws IOException {
        exp.setPrim(etq);
        exp.opnd0().procesa(this);
        etq += 2;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Indireccion exp) throws IOException {
        exp.setPrim(etq);
        exp.opnd0().procesa(this);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Entero exp) throws IOException {
        exp.setPrim(etq);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Real exp) throws IOException {
        exp.setPrim(etq);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(True exp) throws IOException {
        exp.setPrim(etq);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(False exp) throws IOException {
        exp.setPrim(etq);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(String_exp exp) throws IOException {
        exp.setPrim(etq);
        etq++;
        exp.setSig(etq);
    }

    @Override
    public void procesa(Iden exp) throws IOException {
        exp.setPrim(etq);
        if(claseDe(exp.getVinculo(), V_dec.class)){
            V_dec vDec = (V_dec) exp.getVinculo();
            etiquetado_acc_id(vDec);
        }
        else if(claseDe(exp.getVinculo(), Param_simple.class)){
            Param_simple pDec = (Param_simple) exp.getVinculo();
            etiquetado_acc_id(pDec);
        }
        else{
            Param_ref pDec = (Param_ref) exp.getVinculo();
            etiquetado_acc_id(pDec);
        }
        exp.setSig(etq);
    }

    @Override
    public void procesa(Null_exp exp) throws IOException {
        exp.setPrim(etq);
        etq++;
        exp.setSig(etq);
    }

    private void etiquetado_paso_param(Si_param lParam, Si_exps exps) throws IOException {
        if(claseDe(lParam.lParam(), L_param.class) && claseDe(exps.exps(), L_exps.class))
            etiquetado_paso_param((L_param) lParam.lParam(), (L_exps) exps.exps());
        else if(claseDe(lParam.lParam(), Un_param.class) && claseDe(exps.exps(), Una_exp.class))
            etiquetado_paso_param((Un_param) lParam.lParam(), (Una_exp) exps.exps());
    }

    private void etiquetado_paso_param(L_param lParam, L_exps exps) throws IOException {
        if(claseDe(lParam.lParam(), L_param.class) && claseDe(exps.exps(), L_exps.class))
            etiquetado_paso_param((L_param) lParam.lParam(), (L_exps) exps.exps());
        else if(claseDe(lParam.lParam(), Un_param.class) && claseDe(exps.exps(), Una_exp.class))
            etiquetado_paso_param((Un_param) lParam.lParam(), (Una_exp) exps.exps());
        etq += 3;
        exps.exp().procesa(this);
        etq++;
    }

    private void etiquetado_paso_param(Un_param param, Una_exp exp) throws IOException {
        etq+=3;
        exp.exp().procesa(this);
        etq++;
    }

    private void etiquetado_opnds(SintaxisAbstracta.Exp opnd0, SintaxisAbstracta.Exp opnd1, SintaxisAbstracta.Exp exp) throws IOException {
        opnd0.procesa(this);
        etiquetado_acc_val(opnd0);
        if(claseDe(exp, Suma.class) || claseDe(exp, Resta.class) || claseDe(exp, Mul.class) || claseDe(exp, Div.class)) {
            if(claseDe(ref(exp.getTipo()), R_tipo.class) && claseDe(ref(opnd0.getTipo()), In_tipo.class)){
                etq++;
            }
        }
        opnd1.procesa(this);
        etiquetado_acc_val(opnd1);
        if(claseDe(exp, Suma.class) || claseDe(exp, Resta.class) || claseDe(exp, Mul.class) || claseDe(exp, Div.class)) {
            if(claseDe(ref(exp.getTipo()), R_tipo.class) && claseDe(ref(opnd1.getTipo()), In_tipo.class)){
                etq++;
            }
        }
    }

    private void etiquetado_acc_id(V_dec dec){
        if(dec.getNivel() == 0)
            etq++;
        else
            etiquetado_acc_var(dec);
    }

    private void etiquetado_acc_id(Param_simple pval){
        etiquetado_acc_var(pval);
    }

    private void etiquetado_acc_id(Param_ref pref){
        etiquetado_acc_var(pref);
        etq++;
    }

    private void etiquetado_acc_var(SintaxisAbstracta.Nodo dec){
        etq += 3;
    }

    private void etiquetado_acc_val(SintaxisAbstracta.Exp exp) throws IOException {
        etq += esDesignador(exp) ? 1 : 0;
    }

    private void etiquetado_campos(SintaxisAbstracta.Campos campos){
        etq++;
    }

    private boolean esDesignador(Exp exp){
        if(claseDe(exp, Indireccion.class))
            return esDesignador(exp.opnd0());
        return claseDe(exp, Iden.class) || claseDe(exp, Acceso.class) || claseDe(exp, Indexacion.class) ||
            claseDe(exp, Asig.class);
    }

    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }

    private Tipo ref(Tipo t){
        if(claseDe(t, Id_tipo.class)){
            T_dec tDec = (T_dec) t.getVinculo();
            return ref(tDec.tipo());
        }
        else return t;
    }
}
