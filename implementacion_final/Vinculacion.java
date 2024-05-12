package model.semantica;

import model.Procesamiento;
import model.semantica.errores.Errores;
import model.sintaxis.SintaxisAbstracta.LParam_opt;
import model.sintaxis.SintaxisAbstracta.LParam;
import model.sintaxis.SintaxisAbstracta.Param;
import model.sintaxis.SintaxisAbstracta.Campo;
import model.sintaxis.SintaxisAbstracta.Campos;
import model.sintaxis.SintaxisAbstracta.Dec;
import model.sintaxis.SintaxisAbstracta.Decs;
import model.sintaxis.SintaxisAbstracta.Nodo;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Vinculacion implements Procesamiento {
    private static class Ambito {
        private Ambito puntero;
        private final Map<String, Nodo> mapa;

        public Ambito() {
            this.puntero = null;
            this.mapa = new HashMap<>();
        }

        public Ambito getAmbito(){
            return puntero;
        }

        public void setAmbito(Ambito ambito){
            this.puntero = ambito;
        }

        public void inserta(String id, Nodo nodo){
            mapa.put(id, nodo);
        }

        public boolean contiene(String id){
            return mapa.containsKey(id);
        }

        public Nodo get(String id){
            if(!mapa.containsKey(id)){
                if(puntero == null)
                    return null;
                return puntero.get(id);
            }
            else return mapa.get(id);
        }
    }

    private static class TablaSimbolos {
        private Ambito ambito;

        public TablaSimbolos(){
            this.ambito = null;
        }

        public void abreAmbito(){
            Ambito nAmbito = new Ambito();
            nAmbito.setAmbito(ambito);
            ambito = nAmbito;
        }

        public void cierraAmbito(){
            ambito = ambito.getAmbito();
        }

        public void inserta(String id, Nodo nodo){
            ambito.inserta(id, nodo);
        }

        public boolean contiene(String id){
            return ambito.contiene(id);
        }

        public Nodo vinculoDe(String id){
            return ambito.get(id);
        }
    }

    private TablaSimbolos ts;
    private final Errores errores;

    public Vinculacion(Errores errores){
        this.errores = errores;
    }

    @Override
    public void procesa(Prog prog) throws IOException {
        ts = new TablaSimbolos();
        ts.abreAmbito();
        prog.bloque().procesa(this);
    }

    @Override
    public void procesa(Bloque bloque) throws IOException {
        ts.abreAmbito();
        bloque.decsOpt().procesa(this);
        bloque.instrsOpt().procesa(this);
        ts.cierraAmbito();
    }

    @Override
    public void procesa(Si_decs decs) throws IOException {
        preprocesa(decs.decs());
        decs.decs().procesa(this);
    }

    @Override
    public void procesa(No_decs decs) throws IOException {}

    private void preprocesa(Decs decs) throws IOException {
        if(claseDe(decs, L_decs.class)){
            preprocesa(decs.decs());
            preprocesa(decs.dec());
        }
        else{
            preprocesa(decs.dec());
        }
    }

    private void preprocesa(Dec dec) throws IOException {
        if(claseDe(dec, T_dec.class)){
            preprocesa(dec.tipo());
            if(ts.contiene(dec.iden())){
                errores.addErrorVinculacion(dec);
                return;
            }
            ts.inserta(dec.iden(), dec);
        }
        else if(claseDe(dec, V_dec.class)){
            preprocesa(dec.tipo());
            if(ts.contiene(dec.iden())){
                errores.addErrorVinculacion(dec);
                return;
            }
            ts.inserta(dec.iden(), dec);
        }
        else{
            if(ts.contiene(dec.iden())){
                errores.addErrorVinculacion(dec);
            }
            ts.inserta(dec.iden(), dec);
            ts.abreAmbito();
            preprocesa(dec.lParamOpt());
            dec.bloque().procesa(this);
            ts.cierraAmbito();
        }
    }

    private void preprocesa(Tipo tipo) throws IOException {
        if(claseDe(tipo, A_tipo.class)){
            preprocesa(tipo.tipo());
            if(Integer.parseInt(tipo.capacidad()) < 0){
                errores.addErrorPretipado(tipo);
            }
        }
        else if(claseDe(tipo, P_tipo.class)) {
            if(!claseDe(tipo.tipo(), Id_tipo.class)){
                preprocesa(tipo.tipo());
            }
        }
        else if(claseDe(tipo, Id_tipo.class)) {
            Nodo vinculo = ts.vinculoDe(tipo.iden());
            if(vinculo == null) {
                errores.addErrorVinculacion(tipo);
                return;
            }
            if(!claseDe(vinculo, T_dec.class)) {
                errores.addErrorPretipado(tipo);
                return;
            }
            tipo.setVinculo(vinculo);
        }
        else if(claseDe(tipo, Struct_tipo.class)){
            preprocesa(tipo.campos(), (Struct_tipo) tipo);
        }
    }

    private void preprocesa(Campos campos, Struct_tipo struct) throws IOException {
        if(claseDe(campos, L_campos.class)){
            preprocesa(campos.campos(), struct);
            preprocesa(campos.campo(), struct);
        }
        else {
            preprocesa(campos.campo(), struct);
        }
    }

    private void preprocesa(Campo campo, Struct_tipo struct) throws IOException {
        preprocesa(campo.tipo());
        if(struct.existeCampo(campo.iden())){
            errores.addErrorPretipado(campo);
            return;
        }
        struct.addCampo(campo.iden(), campo);
    }

    private void preprocesa(LParam_opt lParam) throws IOException{
        if(claseDe(lParam, Si_param.class)){
            preprocesa(lParam.lParam());
        }
    }

    private void preprocesa(LParam lParam) throws IOException{
        if(claseDe(lParam, L_param.class)){
            preprocesa(lParam.lParam());
            preprocesa(lParam.param());
        }
        else{
            preprocesa(lParam.param());
        }
    }

    private void preprocesa(Param param) throws IOException{
        preprocesa(param.tipo());
        if(ts.contiene(param.iden())){
            errores.addErrorVinculacion(param);
            return;
        }
        ts.inserta(param.iden(), param);
    }

    @Override
    public void procesa(L_decs decs) throws IOException {
        decs.decs().procesa(this);
        decs.dec().procesa(this);
    }

    @Override
    public void procesa(Una_dec decs) throws IOException {
        decs.dec().procesa(this);
    }

    @Override
    public void procesa(T_dec dec) throws IOException {
        dec.tipo().procesa(this);
    }

    @Override
    public void procesa(V_dec dec) throws IOException {
        dec.tipo().procesa(this);
    }

    @Override
    public void procesa(P_dec dec) throws IOException {}

    @Override
    public void procesa(A_tipo tipo) throws IOException {
        tipo.tipo().procesa(this);
    }

    @Override
    public void procesa(P_tipo tipo) throws IOException {
        if(claseDe(tipo.tipo(), Id_tipo.class)){
            Nodo vinculo = ts.vinculoDe(tipo.tipo().iden());
            if(vinculo == null){
                errores.addErrorVinculacion(tipo.tipo());
                return;
            }
            if(!claseDe(vinculo, T_dec.class)) {
                errores.addErrorPretipado(tipo);
                return;
            }
            tipo.tipo().setVinculo(vinculo);
        }
        else {
            tipo.tipo().procesa(this);
        }
    }

    @Override
    public void procesa(In_tipo tipo) throws IOException {}

    @Override
    public void procesa(R_tipo tipo) throws IOException {}

    @Override
    public void procesa(B_tipo tipo) throws IOException { }

    @Override
    public void procesa(String_tipo tipo) throws IOException { }

    @Override
    public void procesa(Id_tipo tipo) throws IOException { }

    @Override
    public void procesa(Struct_tipo tipo) throws IOException {
        tipo.campos().procesa(this);
    }

    @Override
    public void procesa(L_campos campos) throws IOException {
        campos.campos().procesa(this);
        campos.campo().procesa(this);
    }

    @Override
    public void procesa(Un_campo campos) throws IOException {
        campos.campo().procesa(this);
    }

    @Override
    public void procesa(Camp campo) throws IOException {
        campo.tipo().procesa(this);
    }

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
        lParam.param().procesa(this);
    }

    @Override
    public void procesa(Param_simple param) throws IOException {
        param.tipo().procesa(this);
    }

    @Override
    public void procesa(Param_ref param) throws IOException {
        param.tipo().procesa(this);
    }

    @Override
    public void procesa(Si_instrs instrs) throws IOException {
        instrs.instrs().procesa(this);
    }

    @Override
    public void procesa(No_instrs instrs) throws IOException { }

    @Override
    public void procesa(L_instrs instrs) throws IOException {
        instrs.instrs().procesa(this);
        instrs.instr().procesa(this);
    }

    @Override
    public void procesa(Una_instr instrs) throws IOException {
        instrs.instr().procesa(this);
    }

    @Override
    public void procesa(Eva instr) throws IOException {
        instr.exp().procesa(this);
    }

    @Override
    public void procesa(If_instr instr) throws IOException {
        instr.exp().procesa(this);
        instr.bloque().procesa(this);
    }

    @Override
    public void procesa(If_el instr) throws IOException {
        instr.exp().procesa(this);
        instr.bloque().procesa(this);
        instr.bloqueElse().procesa(this);
    }

    @Override
    public void procesa(Wh instr) throws IOException {
        instr.exp().procesa(this);
        instr.bloque().procesa(this);
    }

    @Override
    public void procesa(Rd instr) throws IOException {
        instr.exp().procesa(this);
    }

    @Override
    public void procesa(Wr instr) throws IOException {
        instr.exp().procesa(this);
    }

    @Override
    public void procesa(Nw instr) throws IOException {
        instr.exp().procesa(this);
    }

    @Override
    public void procesa(Dl instr) throws IOException {
        instr.exp().procesa(this);
    }

    @Override
    public void procesa(Nl_instr instr) throws IOException {}

    @Override
    public void procesa(Cl instr) throws IOException {
        Nodo vinculo = ts.vinculoDe(instr.iden());
        if(vinculo == null){
            errores.addErrorVinculacion(instr);
            return;
        }
        instr.setVinculo(ts.vinculoDe(instr.iden()));
        instr.expsOpt().procesa(this);
    }

    @Override
    public void procesa(Bq_instr instr) throws IOException {
        instr.bloque().procesa(this);
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
    }

    @Override
    public void procesa(Asig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(My exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mn exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Myig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mnig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Ig exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Dif exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Suma exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Resta exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(And exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Or exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mul exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Div exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mod exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Menos_unario exp) throws IOException {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Not exp) throws IOException {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Indexacion exp) throws IOException {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Acceso exp) throws IOException {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Indireccion exp) throws IOException {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Entero exp) throws IOException {}

    @Override
    public void procesa(Real exp) throws IOException {}

    @Override
    public void procesa(True exp) throws IOException {}

    @Override
    public void procesa(False exp) throws IOException {}

    @Override
    public void procesa(String_exp exp) throws IOException {}

    @Override
    public void procesa(Iden exp) throws IOException {
        Nodo vinculo = ts.vinculoDe(exp.iden());
        if(vinculo == null){
            errores.addErrorVinculacion(exp);
            return;
        }
        exp.setVinculo(vinculo);
    }

    @Override
    public void procesa(Null_exp exp) throws IOException {}

    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }
}
