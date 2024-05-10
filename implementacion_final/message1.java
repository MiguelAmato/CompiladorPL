package model.sintaxis;

import java.io.IOException;
import java.util.Map;
import model.Procesamiento;
import view.Printer;

public abstract class SintaxisAbstracta {
    private interface Printable{
        void imprime(Printer output) throws IOException;
    }

    public static abstract class Nodo {
        private int fila;
        private int col;
        private Nodo vinculo;
        private Tipo tipo;
        private int dir;
		    private int dir_ant;
		    private int max_dir_ant;
		    private int max_dir;
        private int nivel;
        private int tam;
        private int prim;
        private int sig;

        public Nodo() {
            fila = col = -1;
            vinculo = null;
            tipo = null;
            dir = nivel = tam = prim = sig = 0;
        }

        public Nodo ponFila(int fila) {
            this.fila = fila;
            return this;
        }

        public Nodo ponCol(int col) {
            this.col = col;
            return this;
        }

        public int leeFila() {
            return fila;
        }
        public int leeCol() {
            return col;
        }
        public Nodo getVinculo(){
            return vinculo;
        }
        public void setVinculo(Nodo nodo){
            this.vinculo = nodo;
        }
        public int getDir(){
            return dir;
        }
		public int getMaxDir(){ return max_dir; }
		public void setMaxDir(int max_dir){ this.max_dir = max_dir; }
		public int getMaxDirAnt(){ return max_dir_ant; }
		public void setMaxDirAnt(int max_dir_ant){ this.max_dir_ant = max_dir_ant; }
		public int getDirAnt(){ return dir_ant; }
		public void setDirAnt(int dir_ant){ this.dir_ant = dir_ant; }
        public void setTipo(Tipo tipo){
            this.tipo = tipo;
        }
        public Tipo getTipo(){
            return tipo;
        }
        public void setDir(int dir){
            this.dir = dir;
        }
        public int getNivel(){
            return nivel;
        }
        public void setNivel(int nivel){
            this.nivel = nivel;
        }
        public int getTam(){
            return tam;
        }
        public void setTam(int tam){
            this.tam = tam;
        }
        public int getPrim(){
            return prim;
        }
        public void setPrim(int prim){
            this.prim = prim;
        }
        public int getSig(){
            return sig;
        }
        public void setSig(int sig){
            this.sig = sig;
        }
    }

    /*
      Géneros
    */
    public static abstract class Decs_opt extends Nodo {
        public abstract Decs decs();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Decs extends Nodo {
        public abstract Decs decs();
        public abstract Dec dec();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Dec extends Nodo {
        public abstract Tipo tipo();
        public abstract String iden();
        public abstract LParam_opt lParamOpt();
        public  abstract Bloque bloque();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Tipo extends Nodo {
        public abstract Tipo tipo();
        public abstract String iden();
        public abstract String capacidad();
        public abstract Campos campos();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Campos extends Nodo {
        public abstract Campos campos();
        public abstract Campo campo();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Campo extends Nodo {
        private int desp;
        public Campo(){
            super();
            this.desp = 0;
        }
        public abstract Tipo tipo();
        public abstract String iden();
        public void setDesp(int desp){ this.desp = desp; }
        public int getDesp(){ return desp; }
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class LParam_opt extends Nodo {
        public abstract LParam lParam();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class LParam extends Nodo {
        public abstract LParam lParam();
        public abstract Param param();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Param extends Nodo {
        public abstract Tipo tipo();
        public abstract String iden();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Instrs_opt extends Nodo {
        public abstract Instrs instrs();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Instrs extends Nodo {
        public abstract Instrs instrs();
        public abstract Instr instr();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Instr extends Nodo {
        public abstract Exp exp();
        public abstract Bloque bloque();
        public abstract Bloque bloqueElse();
        public abstract String iden();
        public abstract Exps_opt expsOpt();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Exps_opt extends Nodo {
        public abstract Exps exps();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Exps extends Nodo {
        public abstract Exps exps();
        public abstract Exp exp();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    public static abstract class Exp extends Nodo {
        public abstract String iden();
        public abstract String valor();
        public abstract Exp opnd0();
        public abstract Exp opnd1();
        public abstract int prioridad();
        public abstract void imprime(Printer output) throws IOException;
        public abstract void procesa(Procesamiento p) throws IOException;
    }

    /*
      Constructores
    */
    public static class Prog extends Nodo {
        private final Bloque bq;
        public Prog(Bloque bloque) {
            super();
            this.bq = bloque;
        }
        public String toString() {
            return "prog("+bq+")";
        }
        public Bloque bloque() { return bq; }
        public void imprime(Printer output) throws IOException {
            bq.imprime(output);
        }
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Bloque extends Nodo {
        private final Decs_opt decs;
        private final Instrs_opt instrs;
        public Bloque(Decs_opt decs, Instrs_opt instrs) {
            super();
            this.decs = decs;
            this.instrs = instrs;
        }
        public String toString() {
            return "bloq("+decs+","+instrs+")";
        }
        public Decs_opt decsOpt() { return decs; }
        public Instrs_opt instrsOpt() { return instrs; }
        public void imprime(Printer output) throws IOException {
            output.write("{\n");
            decs.imprime(output);
            instrs.imprime(output);
            output.write("}\n");
        }
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Si_decs extends Decs_opt {
        private final Decs decs;
        public Si_decs(Decs decs) {
            super();
            this.decs = decs;
        }
        public String toString() {
            return "si_decs("+decs+")";
        }
        @Override
        public Decs decs() { return decs; }
        @Override
        public void imprime(Printer output) throws IOException {
            decs.imprime(output);
            output.write("&&\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class No_decs extends Decs_opt {
        public No_decs() {
            super();
        }
        public String toString() {
            return "no_decs()";
        }
        @Override
        public Decs decs() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {}
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class L_decs extends Decs {
        private final Decs decs;
        private final Dec dec;
        public L_decs(Decs decs, Dec dec) {
            super();
            this.decs = decs;
            this.dec = dec;
        }
        public String toString() {
            return "l_decs("+decs+","+dec+")";
        }
        @Override
        public Decs decs() { return decs; }
        @Override
        public Dec dec() { return dec; }
        @Override
        public void imprime(Printer output) throws IOException {
            decs.imprime(output);
            output.write(";\n");
            dec.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Una_dec extends Decs {
        private final Dec dec;
        public Una_dec(Dec dec) {
            super();
            this.dec = dec;
        }
        public String toString() {
            return "una_dec("+dec+")";
        }
        @Override
        public Decs decs() { throw new UnsupportedOperationException(); }
        @Override
        public Dec dec() { return dec; }
        @Override
        public void imprime(Printer output) throws IOException {
            dec.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class T_dec extends Dec {
        private final Tipo tipo;
        private final String iden;
        public T_dec(Tipo tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        @Override
        public String toString() {
            return "t_dec("+tipo+","+iden+")";
        }
        public Tipo tipo() { return tipo; }
        @Override
        public String iden() { return iden; }
        @Override
        public LParam_opt lParamOpt() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<type>\n");
            tipo.imprime(output);
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class V_dec extends Dec {
        private final Tipo tipo;
        private final String iden;
        public V_dec(Tipo tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "v_decs("+tipo+","+iden+")";
        }
        @Override
        public Tipo tipo() { return tipo; }
        @Override
        public String iden() { return iden; }
        @Override
        public LParam_opt lParamOpt() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            tipo.imprime(output);
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class P_dec extends Dec {
        private final String iden;
        private final LParam_opt param;
        private final Bloque bloque;
        public P_dec(String iden, LParam_opt param, Bloque bloque) {
            super();
            this.iden = iden;
            this.param = param;
            this.bloque = bloque;
        }
        public String toString() {
            return "p_decs("+iden+","+param+","+bloque+")";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { return iden; }
        @Override
        public LParam_opt lParamOpt() { return param; }
        @Override
        public Bloque bloque() { return bloque; }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<proc>\n");
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
            output.write("(\n");
            param.imprime(output);
            output.write(")\n");
            bloque.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Ok_tipo extends Tipo {
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }

        @Override
        public String iden() { throw new UnsupportedOperationException(); }

        @Override
        public String capacidad() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Campos campos() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void imprime(Printer output) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void procesa(Procesamiento p) throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    public static class Error_tipo extends Tipo {
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }

        @Override
        public String iden() { throw new UnsupportedOperationException(); }

        @Override
        public String capacidad() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Campos campos() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void imprime(Printer output) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void procesa(Procesamiento p) throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    public static class A_tipo extends Tipo {
        private final Tipo tipo;
        private final String capacidad;
        public A_tipo(Tipo tipo, String capacidad) {
            super();
            this.tipo = tipo;
            this.capacidad = capacidad;
        }
        public String toString() {
            return "a_tipo("+tipo+","+capacidad+")";
        }
        @Override
        public Tipo tipo() { return tipo; }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { return capacidad; }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            tipo.imprime(output);
            output.write("[\n");
            output.write(capacidad + "\n");
            output.write("]"+"$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class P_tipo extends Tipo {
        private final Tipo tipo;
        public P_tipo(Tipo tipo) {
            super();
            this.tipo = tipo;
        }
        public String toString() {
            return "p_tipo("+tipo+")";
        }
        @Override
        public Tipo tipo() { return tipo; }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("^\n");
            tipo.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class N_tipo extends Tipo {
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }

        @Override
        public String iden() { throw new UnsupportedOperationException(); }

        @Override
        public String capacidad() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Campos campos() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void imprime(Printer output) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void procesa(Procesamiento p) throws IOException {
            throw new UnsupportedOperationException();
        }
    }


    public static class In_tipo extends Tipo {
        public In_tipo() {
            super();
        }
        public String toString() {
            return "in_tipo()";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<int>\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class R_tipo extends Tipo {
        public R_tipo() {
            super();
        }
        public String toString() {
            return "r_tipo()";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<real>\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class B_tipo extends Tipo {
        public B_tipo() {
            super();
        }
        public String toString() {
            return "b_tipo()";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<bool>\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class String_tipo extends Tipo {
        public String_tipo() {
            super();
        }
        public String toString() {
            return "string_tipo()";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<string>\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Id_tipo extends Tipo {
        private final String iden;
        public Id_tipo(String iden) {
            super();
            this.iden = iden;
        }
        public String toString() {
            return "id_tipo("+iden+")";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { return iden; }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Struct_tipo extends Tipo {
        private final Campos campos;
        private Map<String, Campo> mCampos;
        public Struct_tipo(Campos campos) {
            super();
            this.campos = campos;
        }
        public String toString() {
            return "struct_tipo("+campos+")";
        }
        @Override
        public Tipo tipo() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String capacidad() { throw new UnsupportedOperationException(); }
        @Override
        public Campos campos() { return campos; }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<struct>\n");
            output.write("{\n");
            campos.imprime(output);
            output.write("}\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }

        public Tipo getTipoDe(String iden) {
            return mCampos.get(iden).tipo();
        }

        public int getDesplazamientoDe(String iden) {
            return mCampos.get(iden).getDesp();
        }

        public void setMapaCampos(Map<String, Campo> mCampos) {
            this.mCampos = mCampos;
        }
    }

    public static class L_campos extends Campos {
        private final Campos campos;
        private final Campo campo;
        public L_campos(Campos campos, Campo campo) {
            super();
            this.campos = campos;
            this.campo = campo;
        }
        public String toString() {
            return "l_campos("+campos+","+campo+")";
        }
        @Override
        public Campos campos() { return campos; }
        @Override
        public Campo campo() { return campo; }
        @Override
        public void imprime(Printer output) throws IOException {
            campos.imprime(output);
            output.write(",\n");
            campo.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Un_campo extends Campos {
        private final Campo campo;
        public Un_campo(Campo campo) {
            super();
            this.campo = campo;
        }
        public String toString() {
            return "un_campo("+campo+")";
        }
        @Override
        public Campos campos() { throw new UnsupportedOperationException(); }
        @Override
        public Campo campo() { return campo; }
        @Override
        public void imprime(Printer output) throws IOException {
            campo.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Camp extends Campo {
        private final Tipo tipo;
        private final String iden;
        public Camp(Tipo tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "camp("+tipo+","+iden+")";
        }
        @Override
        public Tipo tipo() { return tipo; }
        @Override
        public String iden() { return iden; }
        @Override
        public void imprime(Printer output) throws IOException {
            tipo.imprime(output);
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Si_param extends LParam_opt {
        private final LParam lParam;
        public Si_param(LParam lParam) {
            super();
            this.lParam = lParam;
        }
        public String toString() {
            return "si_param("+lParam+")";
        }
        @Override
        public LParam lParam() { return lParam; }
        @Override
        public void imprime(Printer output) throws IOException {
            lParam.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class No_param extends LParam_opt {
        public No_param() {
            super();
        }
        public String toString() {
            return "no_param()";
        }
        @Override
        public LParam lParam() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {}
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class L_param extends LParam {
        private final LParam lParam;
        private final Param param;
        public L_param(LParam lParam, Param param) {
            super();
            this.lParam = lParam;
            this.param = param;
        }
        public String toString() {
            return "l_param("+lParam+","+param+")";
        }
        @Override
        public LParam lParam() { return lParam; }
        @Override
        public Param param() { return param; }
        @Override
        public void imprime(Printer output) throws IOException {
            lParam.imprime(output);
            output.write(",\n");
            param.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Un_param extends LParam {
        private final Param param;
        public Un_param(Param param) {
            super();
            this.param = param;
        }
        public String toString() {
            return "un_param("+param+")";
        }
        @Override
        public LParam lParam() { throw new UnsupportedOperationException(); }
        @Override
        public Param param() { return param; }
        @Override
        public void imprime(Printer output) throws IOException {
            param.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Param_simple extends Param{
        private final Tipo tipo;
        private final String iden;
        public Param_simple(Tipo tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "param_simple("+tipo+","+iden+")";
        }
        @Override
        public Tipo tipo() { return  tipo; }
        @Override
        public String iden() { return iden; }
        @Override
        public void imprime(Printer output) throws IOException {
            tipo.imprime(output);
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Param_ref extends Param{
        private final Tipo tipo;
        private final String iden;
        public Param_ref(Tipo tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "param_ref("+tipo+","+iden+")";
        }
        @Override
        public Tipo tipo() { return  tipo; }
        @Override
        public String iden() { return iden; }
        @Override
        public void imprime(Printer output) throws IOException {
            tipo.imprime(output);
            output.write("&\n");
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Si_instrs extends Instrs_opt {
        private final Instrs instrs;
        public Si_instrs(Instrs instrs) {
            super();
            this.instrs = instrs;
        }
        public String toString() {
            return "si_instrs("+instrs+")";
        }
        @Override
        public Instrs instrs() { return instrs; }
        @Override
        public void imprime(Printer output) throws IOException {
            instrs.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class No_instrs extends Instrs_opt {
        public No_instrs() {
            super();
        }
        public String toString() {
            return "no_instrs()";
        }
        @Override
        public Instrs instrs() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {}
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class L_instrs extends Instrs {
        private final Instrs instrs;
        private final Instr instr;
        public L_instrs(Instrs instrs, Instr instr) {
            super();
            this.instrs = instrs;
            this.instr = instr;
        }
        public String toString() {
            return "l_instrs("+instrs+","+instr+")";
        }
        @Override
        public Instrs instrs() { return instrs; }
        @Override
        public Instr instr() { return instr; }
        @Override
        public void imprime(Printer output) throws IOException {
            instrs.imprime(output);
            output.write(";\n");
            instr.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Una_instr extends Instrs {
        private final Instr instr;
        public Una_instr(Instr instr) {
            super();
            this.instr = instr;
        }
        public String toString() {
            return "una_instr("+instr+")";
        }
        @Override
        public Instrs instrs() { throw new UnsupportedOperationException(); }
        @Override
        public Instr instr() { return instr; }
        @Override
        public void imprime(Printer output) throws IOException {
            instr.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Eva extends Instr {
        private final Exp exp;
        public Eva(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "eva("+exp+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("@\n");
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class If_instr extends Instr {
        private final Exp exp;
        private final Bloque bloque;
        public If_instr(Exp exp, Bloque bloque) {
            super();
            this.exp = exp;
            this.bloque = bloque;
        }
        public String toString() {
            return "if("+exp+","+bloque+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { return bloque; }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<if>\n");
            exp.imprime(output);
            bloque.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class If_el extends Instr {
        private final Exp exp;
        private final Bloque bloqueIf;
        private final Bloque bloqueElse;
        public If_el(Exp exp, Bloque bloqueIf, Bloque bloqueElse) {
            super();
            this.exp = exp;
            this.bloqueIf = bloqueIf;
            this.bloqueElse = bloqueElse;
        }
        public String toString() {
            return "if_el("+exp+","+bloqueIf+","+bloqueElse+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { return bloqueIf; }
        @Override
        public Bloque bloqueElse() { return bloqueElse; }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<if>\n");
            exp.imprime(output);
            bloqueIf.imprime(output);
            output.write("<else>\n");
            bloqueElse.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Wh extends Instr {
        private final Exp exp;
        private final Bloque bloque;
        public Wh(Exp exp, Bloque bloque) {
            super();
            this.exp = exp;
            this.bloque = bloque;
        }
        public String toString() {
            return "wh("+exp+","+bloque+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { return bloque; }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<while>\n");
            exp.imprime(output);
            bloque.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Rd extends Instr {
        private final Exp exp;
        public Rd(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "rd("+exp+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<read>\n");
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Wr extends Instr {
        private final Exp exp;
        public Wr(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "wr("+exp+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<write>\n");
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Nw extends Instr {
        private final Exp exp;
        public Nw(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "nw("+exp+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<new>\n");
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Dl extends Instr {
        private final Exp exp;
        public Dl(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "dl("+exp+")";
        }
        @Override
        public Exp exp() { return exp; }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<delete>\n");
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Nl_instr extends Instr {
        public Nl_instr() {
            super();
        }
        public String toString() {
            return "nl()";
        }
        @Override
        public Exp exp() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<nl>\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Cl extends Instr {
        private final String iden;
        private final Exps_opt exps;
        public Cl(String iden, Exps_opt exps) {
            super();
            this.iden = iden;
            this.exps = exps;
        }
        public String toString() {
            return "cl("+iden+","+exps+")";
        }
        @Override
        public Exp exp() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloque() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() { return iden; }
        @Override
        public Exps_opt expsOpt() { return exps; }
        @Override
        public void imprime(Printer output) throws IOException {
            output.write("<call>\n");
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
            output.write("(\n");
            exps.imprime(output);
            output.write(")\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Bq_instr extends Instr {
        private final Bloque bloque;
        public Bq_instr(Bloque bloque) {
            super();
            this.bloque = bloque;
        }
        public String toString() {
            return "bq_instr("+bloque+")";
        }
        @Override
        public Exp exp() { throw new UnsupportedOperationException(); }
        @Override
        public Bloque bloque() { return bloque; }
        @Override
        public Bloque bloqueElse() { throw new UnsupportedOperationException(); }
        @Override
        public String iden() {throw new UnsupportedOperationException(); }
        @Override
        public Exps_opt expsOpt() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {
            bloque.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Si_exps extends Exps_opt {
        private final Exps exps;
        public Si_exps(Exps exps) {
            super();
            this.exps = exps;
        }
        public String toString() {
            return "si_exps("+exps+")";
        }
        @Override
        public Exps exps() { return exps; }
        @Override
        public void imprime(Printer output) throws IOException {
            exps.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class No_exps extends Exps_opt {
        public No_exps() {
            super();
        }
        public String toString() {
            return "no_exps()";
        }
        @Override
        public Exps exps() { throw new UnsupportedOperationException(); }
        @Override
        public void imprime(Printer output) throws IOException {}
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class L_exps extends Exps {
        private final Exps exps;
        private final Exp exp;
        public L_exps(Exps exps, Exp exp) {
            super();
            this.exps = exps;
            this.exp = exp;
        }
        public String toString() {
            return "l_exps("+exps+","+exp+")";
        }
        @Override
        public Exps exps() { return exps; }
        @Override
        public Exp exp() { return exp; }
        @Override
        public void imprime(Printer output) throws IOException {
            exps.imprime(output);
            output.write(",\n");
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Una_exp extends Exps {
        private final Exp exp;
        public Una_exp(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "una_exp("+exp+")";
        }
        @Override
        public Exps exps() { throw new UnsupportedOperationException(); }
        @Override
        public Exp exp() { return exp; }
        @Override
        public void imprime(Printer output) throws IOException {
            exp.imprime(output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;
        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { return opnd0; }
        @Override
        public Exp opnd1() { return opnd1;}
    }

    private static abstract class ExpPre extends Exp {
        protected Exp opnd;
        public ExpPre(Exp opnd) {
            super();
            this.opnd = opnd;
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { return opnd; }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
    }

    public static class Asig extends ExpBin {
        public Asig(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "asig("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 0; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "=", opnd1, 1, 0, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class My extends ExpBin {
        public My(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "my("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 1; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, ">", opnd1, 1, 2, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Mn extends ExpBin {
        public Mn(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mn("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 1; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "<", opnd1, 1, 2, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Myig extends ExpBin {
        public Myig(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "myig("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 1; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, ">=", opnd1, 1, 2, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Mnig extends ExpBin {
        public Mnig(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mnig("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 1; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "<=", opnd1, 1, 2, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Ig extends ExpBin {
        public Ig(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "ig("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 1; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "==", opnd1, 1, 2, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Dif extends ExpBin {
        public Dif(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "dif("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 1; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "!=", opnd1, 1, 2, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Suma extends ExpBin {
        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "suma("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 2; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "+", opnd1, 2, 3, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }
    public static class Resta extends ExpBin {
        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "resta("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 2; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "-", opnd1, 3, 3, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class And extends ExpBin {
        public And(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "and("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 3; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "<and>", opnd1, 4, 3, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Or extends ExpBin {
        public Or(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "or("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 3; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "<or>", opnd1, 4, 4, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Mul extends ExpBin {
        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mul("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 4; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "*", opnd1, 4, 5, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }
    public static class Div extends ExpBin {
        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "div("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 4; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "/", opnd1, 4, 5, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Mod extends ExpBin {
        public Mod(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mod("+opnd0+","+opnd1+")";
        }
        @Override
        public int prioridad() {  return 4; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpBin(opnd0, "%", opnd1, 4, 5, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Menos_unario extends ExpPre {
        public Menos_unario(Exp opnd) {
            super(opnd);
        }
        public String toString() {
            return "menos_unario("+opnd+")";
        }
        @Override
        public int prioridad() {  return 5; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpPre(opnd, "-", 5, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Not extends ExpPre {
        public Not(Exp opnd) {
            super(opnd);
        }
        public String toString() {
            return "not("+opnd+")";
        }
        @Override
        public int prioridad() {  return 5; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeExpPre(opnd, "<not>", 5, leeFila(), leeCol(), output);
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Indexacion extends Exp {
        private final Exp opnd;
        private final Exp pos;
        public Indexacion(Exp opnd, Exp pos) {
            super();
            this.opnd = opnd;
            this.pos = pos;
        }
        public String toString() {
            return "indexacion("+opnd+","+pos+")";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { return opnd; }
        @Override
        public Exp opnd1() { return pos; }
        @Override
        public int prioridad() {  return 6; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeOpnd(opnd, 6, output);
            output.write("["+"$f:" + leeFila() + ",c:" + leeCol() + "$\n");
            imprimeOpnd(pos, 0, output);
            output.write("]\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Acceso extends Exp {
        private final Exp opnd;
        private final String acceso;
        public Acceso(Exp opnd, String acceso) {
            super();
            this.opnd = opnd;
            this.acceso = acceso;
        }
        public String toString() {
            return "acceso("+opnd+","+acceso+")";
        }
        @Override
        public String iden() { return acceso; }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { return opnd; }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() { return 6; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeOpnd(opnd, 6, output);
            output.write(".\n");
            output.write(acceso + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Indireccion extends Exp {
        private final Exp opnd;
        public Indireccion(Exp opnd) {
            super();
            this.opnd = opnd;
        }
        public String toString() {
            return "indireccion("+opnd+")";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { return opnd; }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 6; }
        @Override
        public void imprime(Printer output) throws IOException {
            imprimeOpnd(opnd, 6, output);
            output.write("^"+"$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Real extends Exp {
        private final String num;
        public Real(String num) {
            super();
            this.num = num;
        }
        public String toString() {
            return "real("+num+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { return num; }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write(valor() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Entero extends Exp {
        private final String num;
        public Entero(String num) {
            super();
            this.num = num;
        }
        public String toString() {
            return "entero("+num+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { return num; }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write(valor() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class True extends Exp {
        public True() {
            super();
        }
        public String toString() {
            return "true("+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write("<true>$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class False extends Exp {
        public False() {
            super();
        }
        public String toString() {
            return "false("+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write("<false>$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class String_exp extends Exp {
        private final String string;
        public String_exp(String string) {
            super();
            this.string = string;
        }
        public String toString() {
            return "string("+string+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { return string; }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write(valor() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Iden extends Exp {
        private final String id;
        public Iden(String id) {
            super();
            this.id = id;
        }
        public String toString() {
            return "iden("+id+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { return id; }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write(iden() + "$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public static class Null_exp extends Exp {
        public Null_exp() {
            super();
        }
        public String toString() {
            return "null("+"["+leeFila()+","+leeCol()+"])";
        }
        @Override
        public String iden() { throw new UnsupportedOperationException(); }
        @Override
        public String valor() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd0() { throw new UnsupportedOperationException(); }
        @Override
        public Exp opnd1() { throw new UnsupportedOperationException(); }
        @Override
        public int prioridad() {  return 7; }
        @Override
        public void imprime(Printer output) throws IOException{
            output.write("<null>$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
        @Override
        public void procesa(Procesamiento p) throws IOException{ p.procesa(this); }
    }

    public Prog prog(Bloque bloque){ return new Prog(bloque); }
    public Bloque bloque(Decs_opt decs, Instrs_opt instrs){
        return new Bloque(decs, instrs);
    }
    public Decs_opt si_decs(Decs decs){ return new Si_decs(decs); }
    public Decs_opt no_decs(){
        return new No_decs();
    }
    public Decs l_decs(Decs decs, Dec dec){
        return new L_decs(decs, dec);
    }
    public Decs una_dec(Dec dec){
        return new Una_dec(dec);
    }
    public Dec t_dec(Tipo tipo, String iden){
        return new T_dec(tipo, iden);
    }
    public Dec v_dec(Tipo tipo, String iden){
        return new V_dec(tipo, iden);
    }
    public Dec p_dec(String iden, LParam_opt param, Bloque bloque){
        return new P_dec(iden, param, bloque);
    }
    public Tipo a_tipo(Tipo tipo, String cap){
        return new A_tipo(tipo, cap);
    }
    public Tipo p_tipo(Tipo tipo){
        return new P_tipo(tipo);
    }
    public Tipo in_tipo(){
        return new In_tipo();
    }
    public Tipo r_tipo(){
        return new R_tipo();
    }
    public Tipo b_tipo(){
        return new B_tipo();
    }
    public Tipo string_tipo(){
        return new String_tipo();
    }
    public Tipo id_tipo(String iden){
        return new Id_tipo(iden);
    }
    public Tipo struct_tipo(Campos campos){
        return new Struct_tipo(campos);
    }
    public Campos l_campos(Campos campos, Campo campo){
        return new L_campos(campos, campo);
    }
    public Campos un_campo(Campo campo){
        return new Un_campo(campo);
    }
    public Campo camp(Tipo tipo, String iden){
        return new Camp(tipo, iden);
    }
    public LParam_opt si_param(LParam lParam){
        return new Si_param(lParam);
    }
    public LParam_opt no_param(){
        return new No_param();
    }
    public LParam l_param(LParam lParam, Param param){
        return new L_param(lParam, param);
    }
    public LParam un_param(Param param){
        return new Un_param(param);
    }
    public Param param_simple(Tipo tipo, String iden){
        return new Param_simple(tipo, iden);
    }
    public Param param_ref(Tipo tipo, String iden){
        return new Param_ref(tipo, iden);
    }
    public Instrs_opt si_instrs(Instrs instrs){
        return new Si_instrs(instrs);
    }
    public Instrs_opt no_instrs(){
        return new No_instrs();
    }
    public Instrs l_instrs(Instrs instrs, Instr instr){
        return new L_instrs(instrs, instr);
    }
    public Instrs una_instr(Instr instr){
        return new Una_instr(instr);
    }
    public Instr eva(Exp exp){ return new Eva(exp); }
    public Instr if_instr(Exp exp, Bloque bloque){
        return new If_instr(exp, bloque);
    }
    public Instr if_el(Exp exp, Bloque bloqueIf, Bloque bloqueElse){
        return new If_el(exp, bloqueIf, bloqueElse);
    }
    public Instr wh(Exp exp, Bloque bloque){
        return new Wh(exp, bloque);
    }
    public Instr rd(Exp exp){
        return new Rd(exp);
    }
    public Instr wr(Exp exp){
        return new Wr(exp);
    }
    public Instr nw(Exp exp){
        return new Nw(exp);
    }
    public Instr dl(Exp exp){
        return new Dl(exp);
    }
    public Instr nl(){
        return new Nl_instr();
    }
    public Instr cl(String iden, Exps_opt exps){
        return new Cl(iden, exps);
    }
    public Instr bq_instr(Bloque bloque){
        return new Bq_instr(bloque);
    }
    public Exps_opt si_exps(Exps exps){
        return new Si_exps(exps);
    }
    public Exps_opt no_exps(){
        return new No_exps();
    }
    public Exps l_exps(Exps exps, Exp exp){
        return new L_exps(exps, exp);
    }
    public Exps una_exp(Exp exp){
        return new Una_exp(exp);
    }
    public Exp asig(Exp opnd0, Exp opnd1){
        return new Asig(opnd0, opnd1);
    }
    public Exp my(Exp opnd0, Exp opnd1){
        return new My(opnd0, opnd1);
    }
    public Exp mn(Exp opnd0, Exp opnd1){
        return new Mn(opnd0, opnd1);
    }
    public Exp myig(Exp opnd0, Exp opnd1){
        return new Myig(opnd0, opnd1);
    }
    public Exp mnig(Exp opnd0, Exp opnd1){
        return new Mnig(opnd0, opnd1);
    }
    public Exp ig(Exp opnd0, Exp opnd1){
        return new Ig(opnd0, opnd1);
    }
    public Exp dif(Exp opnd0, Exp opnd1){
        return new Dif(opnd0, opnd1);
    }
    public Exp suma(Exp opnd0, Exp opnd1) {
        return new Suma(opnd0,opnd1);
    }
    public Exp resta(Exp opnd0, Exp opnd1) {
        return new Resta(opnd0,opnd1);
    }
    public Exp and(Exp opnd0, Exp opnd1) {
        return new And(opnd0,opnd1);
    }
    public Exp or(Exp opnd0, Exp opnd1) {
        return new Or(opnd0,opnd1);
    }
    public Exp mul(Exp opnd0, Exp opnd1) {
        return new Mul(opnd0,opnd1);
    }
    public Exp div(Exp opnd0, Exp opnd1) {
        return new Div(opnd0,opnd1);
    }
    public Exp mod(Exp opnd0, Exp opnd1) {
        return new Mod(opnd0,opnd1);
    }
    public Exp menos_unario(Exp opnd) {
        return new Menos_unario(opnd);
    }
    public Exp not(Exp opnd) {
        return new Not(opnd);
    }
    public Exp indexacion(Exp opnd, Exp pos){
        return new Indexacion(opnd, pos);
    }
    public Exp acceso(Exp opnd, String acceso){
        return new Acceso(opnd, acceso);
    }
    public Exp indireccion(Exp opnd){
        return new Indireccion(opnd);
    }
    public Exp real(String num) {
        return new Real(num);
    }
    public Exp entero(String num) {
        return new Entero(num);
    }
    public Exp true_exp(){
        return new True();
    }
    public Exp false_exp(){
        return new False();
    }
    public Exp string(String string) {
        return new String_exp(string);
    }
    public Exp iden(String iden) {
        return new Iden(iden);
    }
    public Exp nulo() {
        return new Null_exp();
    }

    private static void imprimeExpPre(Exp opnd, String op, int np, int fila, int columna, Printer output) throws IOException {
        output.write(op + "$f:" + fila + ",c:" + columna + "$\n");
        imprimeOpnd(opnd, np, output);
    }

    private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1, int fila, int columna, Printer output) throws IOException {
        imprimeOpnd(opnd0, np0, output);
        output.write(op + "$f:" + fila + ",c:" + columna + "$\n");
        imprimeOpnd(opnd1, np1, output);
    }

    private static void imprimeOpnd(Exp opnd, int minPrior, Printer output) throws IOException {
        if (opnd.prioridad() < minPrior){
            output.write("(\n");
        }
        opnd.imprime(output);
        if (opnd.prioridad() < minPrior){
            output.write(")\n");
        }
    }
}
