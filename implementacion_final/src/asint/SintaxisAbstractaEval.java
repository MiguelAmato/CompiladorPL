package asint;

import java.util.HashMap;
import java.util.Map;

public class SintaxisAbstractaEval {

	private static void imprimeOpnd(Exp opnd, int np) {

			if(opnd.prioridad() < np) {
				System.out.println("(");
			};
			opnd.imprime();
			if(opnd.prioridad() < np) {
				System.out.println(")");
			};
		}

		private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1, int fila, int col) {
			imprimeOpnd(opnd0,np0);
			System.out.print(op);
			imprimeVinculo(fila, col);
			imprimeOpnd(opnd1,np1);
		}

		private static void imprimeExpPre(Exp opnd, String op, int np, int fila, int col) {
			System.out.print(op);
			imprimeVinculo(fila, col);
			imprimeOpnd(opnd,np);
		}

		private static void imprimeVinculo(Nodo nodo) {
			System.out.println("$f:" + nodo.leeFila() + ",c:" + nodo.leeCol() + "$");
	}

		private static void imprimeVinculo(int fila, int col) {
				System.out.println("$f:" + fila + ",c:" + col + "$");
		}

		

	public static abstract class Nodo {
		private Tipo tipo;
		private Nodo vinculo;

		private int fila;
		private int col;

		private int dir;
		private int dir_ant;
		private int max_dir_ant;
		private int max_dir;
        private int nivel;
        private int tam;
		private int des;
		
		public Nodo() {
			fila = col = -1;
			vinculo = null;
			tipo = null;
		}

		public Nodo ponFila(int fila) {
			this.fila = fila;
			return this;
		}

		public abstract void imprime();

		public Map<String, Campo> getCampos(){
			return null;
		}

		public Tipo tipo(){
			return null;
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

		public void setTipo(Tipo tipo) {
			this.tipo = tipo;
		}

		public Tipo getTipo() {
			return tipo;
		}

		public void setVinculo(Nodo vinculo) {
			this.vinculo = vinculo;
		}

		public Nodo getVinculo() {
			return vinculo;
		}
		
		public int getMaxDir(){ 
			return max_dir;
		}
		
		public void setMaxDir(int max_dir){
			this.max_dir = max_dir;
		}
		
		public int getMaxDirAnt(){ 
			return max_dir_ant;
		}
		
		public void setMaxDirAnt(int max_dir_ant){
			this.max_dir_ant = max_dir_ant; 
		}
		
		public int getDirAnt(){
			return dir_ant; 
		}
		
		public void setDirAnt(int dir_ant){ 
			this.dir_ant = dir_ant; 
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
		public int getDesp() {
			return des;
		}
		public void setDesp(int des) {
			this.des = des;
		}

		// public abstract void procesa(Procesamiento p);
		// public abstract void imprime();
	}

	public static class Literal_ent extends Exp {
		private String num;

		public Literal_ent(String num) {
			super();
			this.num = num;
		}

		public String num() {
			return num;
		}

		public void imprime() {
			System.out.print(num);
			imprimeVinculo(this);

		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "literal_ent(" + num + "[" + leeFila() + "," + leeCol() + "])";
		}
	}

	public static class Literal_real extends Exp {
		private String num;

		public Literal_real(String num) {
			super();
			this.num = num;
		}

		public String num() {
			return num;
		}

		public void imprime() {
			System.out.print(num);
			imprimeVinculo(this);

		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "literal_real(" + num + "[" + leeFila() + "," + leeCol() + "])";
		}
	}

	public static class Literal_cadena extends Exp {
		private String id;

		public Literal_cadena(String id) {
			super();
			this.id = id;
		}

		public String id() {
			return id;
		}

		public void imprime() {
			System.out.print(id);
			imprimeVinculo(this);

		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "literal_cadena(" + id + "[" + leeFila() + "," + leeCol() + "])";
		}
	}

	public static class True extends Exp {
		public True() {
			super();
		}

		public void imprime() {
			System.out.print("<true>");
			imprimeVinculo(this);

		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "true[" + leeFila() + "," + leeCol() + "]";
		}
	}

	public static class False extends Exp {
		public False() {
			super();
		}

		public void imprime() {
			System.out.print("<false>");
			imprimeVinculo(this);
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "false[" + leeFila() + "," + leeCol() + "]";
		}
	}

	public static class Id extends Exp {
		private String id;

		public Id(String id) {
			super();
			this.id = id;
		}

		public String id() {
			return id;
		}

		public void imprime() {
			System.out.print(id);
			imprimeVinculo(this);
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "id(" + id + "[" + leeFila() + "," + leeCol() + "])";
		}

		@Override
		public boolean es_designador() {
			return true;
		}
	}

	public static class Null extends Exp {

		public Null() {
			super();
		}

		public void imprime() {
			System.out.print("<null>");
			imprimeVinculo(this);
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "null()";
		}
	}

	public static class Asig extends Exp {
		private Exp exp1, exp2;

		public Asig(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void imprime() {
			imprimeExpBin(exp1, "=", exp2, 1, 0, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 0;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "asig(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Mayor extends Exp {
		private Exp exp1, exp2;

		public Mayor(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void imprime() {
			imprimeExpBin(exp1, ">", exp2, 1, 2, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 1;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "mayor(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Menor extends Exp {
		private Exp exp1, exp2;

		public Menor(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			imprimeExpBin(exp1, "<", exp2, 1, 2, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 1;
		}

		public String toString() {
			return "menor(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Mayor_igual extends Exp {
		private Exp exp1, exp2;

		public Mayor_igual(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}	
		
		public void imprime() {
			imprimeExpBin(exp1, ">=", exp2, 1, 2, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 1;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "mayor_igual(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Menor_igual extends Exp {
		private Exp exp1, exp2;

		public Menor_igual(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			imprimeExpBin(exp1, "<=", exp2, 1, 2, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 1;
		}

		public String toString() {
			return "menor_igual(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Igual extends Exp {
		private Exp exp1, exp2;

		public Igual(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			imprimeExpBin(exp1, "==", exp2, 1, 2, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 1;
		}

		public String toString() {
			return "igual(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Distinto extends Exp {
		private Exp exp1, exp2;

		public Distinto(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}


		public void imprime() {
			imprimeExpBin(exp1, "!=", exp2, 1, 2, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 1;
		}
		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "distinto(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Suma extends Exp {
		private Exp exp1, exp2;

		public Suma(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}


		public void imprime() {
			imprimeExpBin(exp1, "+", exp2, 2, 3, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "suma(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Resta extends Exp {
		private Exp exp1, exp2;

		public Resta(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			imprimeExpBin(exp1, "-", exp2, 3, 3, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 2;
		}

		public String toString() {
			return "resta(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Mul extends Exp {
		private Exp exp1, exp2;

		public Mul(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void imprime() {
			imprimeExpBin(exp1, "*", exp2, 4, 5, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 4;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "mul(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Div extends Exp {
		private Exp exp1, exp2;

		public Div(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}


		public void imprime() {
			imprimeExpBin(exp1, "/", exp2, 4, 5, this.leeFila(), this.leeCol());
		}

		
		public int prioridad() {
			return 4;
		}


		public String toString() {
			return "div(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Mod extends Exp {
		private Exp exp1, exp2;

		public Mod(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}


		public void imprime() {
			imprimeExpBin(exp1, "%", exp2, 4, 5, this.leeFila(), this.leeCol());
		}

		public int prioridad() {
			return 4;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "mod(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class And extends Exp {
		private Exp exp1, exp2;

		public And(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}


		public void imprime() {
			imprimeExpBin(exp1, "<and>", exp2, 4, 3, this.leeFila(), this.leeCol());
		}

		
		public int prioridad() {
			return 3;
		}

		public String toString() {
			return "and(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Or extends Exp {
		private Exp exp1, exp2;

		public Or(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			imprimeExpBin(exp1, "<or>", exp2, 4, 4, this.leeFila(), this.leeCol());
		}


		public int prioridad() {
			return 3;
		}

		public String toString() {
			return "or(" + exp1 + "," + exp2 + ")";
		}
	}

	public static class Not extends Exp {
		private Exp exp;

		public Not(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
				imprimeExpPre(exp, "<not>", 5, this.leeFila(), this.leeCol());
		}


		public int prioridad() {
			return 5;
		}


		public String toString() {
			return "not(" + exp + ")";
		}
	}

	public static class Menos extends Exp {
		private Exp exp;

		public Menos(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			imprimeExpPre(exp, "-", 5, this.leeFila(), this.leeCol());
		}

		
		public int prioridad() {
			return 5;
		}

		public String toString() {
			return "menos(" + exp + ")";
		}
	}

	public static class Index extends Exp {
		private Exp exp1, exp2;

		public Index(Exp exp1, Exp exp2) {
			super();
			this.exp1 = exp1;
			this.exp2 = exp2;
		}

		public Exp exp1() {
			return exp1;
		}

		public Exp exp2() {
			return exp2;
		}

		public void imprime() {
			// imprimeOpnd(exp1, 6);
			exp1.imprime();
			System.out.print("[");
			imprimeVinculo(this);
			// imprimeOpnd(exp2, 6);
			exp2.imprime();
			System.out.println("]");
		}

		
		public int prioridad() {
			return 6;
		}


		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "index(" + exp1 + "," + exp2 + ")";
		}

		@Override
		public boolean es_designador() {
			return true;
		}
	}

	public static class Reg extends Exp {
		private Exp exp;
		private String id;

		public Reg(Exp exp, String id) {
			super();
			this.exp = exp;
			this.id = id;
		}

		public Exp exp() {
			return exp;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			// imprimeOpnd(exp, 6);
			exp.imprime();
			System.out.println(".");
			System.out.print(id);
			imprimeVinculo(this);
		}

		public int prioridad() {
			return 6;
		}

		public String toString() {
			return "reg(" + exp + "," + id + ")";
		}

		@Override
		public boolean es_designador() {
			return true;
		}
	}

	public static class Indir extends Exp {
		private Exp exp;

		public Indir(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			exp.imprime();
			System.out.print("^");
			imprimeVinculo(this);
		}

		
		public int prioridad() {
			return 6;
		}


		public String toString() {
			return "indir(" + exp + ")";
		}

		@Override
		public boolean es_designador() {
			return true;
		}
	}

	public static abstract class Exp extends Nodo {
		public Exp() {
			super();
		}

		public void procesa(Procesamiento p){		}

		public int prioridad() {
			return 7;
		}

        public boolean es_designador() {
            return false;
        }
	}

	public static class Muchos_param_re extends LParamR {
		LParamR lParamR;
		Exp exp;

		public Muchos_param_re(LParamR lParamR, Exp exp) {
			super();
			this.lParamR = lParamR;
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public LParamR lParamR() {
			return lParamR;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			lParamR.imprime();
			System.out.println(",");
			exp.imprime();
		}

		public String toString() {
			return "muchos_param_re(" + lParamR + "," + exp + ")";
		}
	}

	public static class Un_param_re extends LParamR {
		Exp exp;

		public Un_param_re(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		
		public void imprime() {
			exp.imprime();
		}


		public String toString() {
			return "un_param_re(" + exp + ")";
		}
	}

	public abstract static class LParamR extends Nodo {
		public LParamR() {
			super();
		}
		public void procesa(Procesamiento p){}

		
	}

	public static class Si_param_re extends ParamR {
		LParamR lParamR;

		public Si_param_re(LParamR lParamR) {
			super();
			this.lParamR = lParamR;
		}

		public LParamR lParamR() {
			return lParamR;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			lParamR.imprime();
		}	

		public String toString() {
			return "si_param_re(" + lParamR + ")";
		}
	}

	public static class No_param_re extends ParamR {
		public No_param_re() {
			super();
		}
		public void imprime() {
		}
	}

	public abstract static class ParamR extends Nodo {
		public ParamR() {
			super();
		}

		public void procesa(Procesamiento p){}		
	}

	public static class Instr_eval extends Instr {
		Exp exp;

		public Instr_eval(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("@");
			exp.imprime();
		}

		public String toString() {
			return "instr_eval(" + exp + ")";
		}
	}

	public static class Instr_if extends Instr {
		Exp exp;
		Prog prog;

		public Instr_if(Exp exp, Prog prog) {
			super();
			this.exp = exp;
			this.prog = prog;
		}

		public Exp exp() {
			return exp;
		}

		public Prog prog() {
			return prog;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<if>");
			exp.imprime();
			prog.imprime();
		}

		public String toString() {
			return "instr_if(" + exp + "," + prog + ")";
		}
	}

	public static class Instr_else extends Instr {
		Exp exp;
		Prog prog1, prog2;

		public Instr_else(Exp exp, Prog prog1, Prog prog2) {
			super();
			this.exp = exp;
			this.prog1 = prog1;
			this.prog2 = prog2;
		}

		public Exp exp() {
			return exp;
		}

		public Prog prog1() {
			return prog1;
		}

		public Prog prog2() {
			return prog2;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<if>");
			exp.imprime();
			prog1.imprime();
			System.out.println("<else>");
			prog2.imprime();
		}

		public String toString() {
			return "instr_else(" + exp + "," + prog1 + "," + prog2 + ")";
		}
	}

	public static class Instr_wh extends Instr {
		Exp exp;
		Prog prog;

		public Instr_wh(Exp exp, Prog prog) {
			super();
			this.exp = exp;
			this.prog = prog;
		}

		public Exp exp() {
			return exp;
		}

		public Prog prog() {
			return prog;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<while>");
			exp.imprime();
			prog.imprime();
		}

		public String toString() {
			return "instr_wh(" + exp + "," + prog + ")";
		}
	}

	public static class Instr_rd extends Instr {
		Exp exp;

		public Instr_rd(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<read>");
			exp.imprime();
		}

		public String toString() {
			return "instr_rd(" + exp + ")";
		}
	}

	public static class Instr_wr extends Instr {
		Exp exp;

		public Instr_wr(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<write>");
			exp.imprime();
		}

		public String toString() {
			return "instr_wr(" + exp + ")";
		}
	}

	public static class Instr_nl extends Instr {
		public Instr_nl() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<nl>");
		}

		public String toString() {
			return "instr_nl()";
		}
	}

	public static class Instr_new extends Instr {
		Exp exp;

		public Instr_new(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<new>");
			exp.imprime();
		}

		public String toString() {
			return "instr_new(" + exp + ")";
		}
	}

	public static class Instr_del extends Instr {
		Exp exp;

		public Instr_del(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<delete>");
			exp.imprime();
		}

		public String toString() {
			return "instr_del(" + exp + ")";
		}
	}

	public static class Instr_call extends Instr {
		String id;
		ParamR paramR;

		public Instr_call(String id, ParamR paramR) {
			super();
			this.id = id;
			this.paramR = paramR;
		}

		public String id() {
			return id;
		}

		public ParamR paramR() {
			return paramR;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<call>");
			System.out.print(id);
			imprimeVinculo(this);
			System.out.println("(");
			paramR.imprime();
			System.out.println(")");
		}

		public String toString() {
			return "instr_call(" + id + "," + paramR + ")";
		}
	}

	public static class Instr_comp extends Instr {
		Prog prog;

		public Instr_comp(Prog prog) {
			super();
			this.prog = prog;
		}

		public Prog prog() {
			return prog;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			prog.imprime();	
		}

		public String toString() {
			return "instr_comp(" + prog + ")";
		}
	}

	public abstract static class Instr extends Nodo {
		public Instr() {
			super();
		}


		public void procesa(Procesamiento p){}

	}

	public static class Una_instr extends LInstr {
		Instr instr;

		public Una_instr(Instr instr) {
			super();
			this.instr = instr;
		}

		public Instr instr() {
			return instr;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			instr.imprime();
		}

		public String toString() {
			return "una_instr(" + instr + ")";
		}
	}

	public static class Muchas_instr extends LInstr {
		LInstr lInstr;
		Instr instr;

		public Muchas_instr(LInstr lInstr, Instr instr) {
			super();
			this.lInstr = lInstr;
			this.instr = instr;
		}

		public LInstr lInstr() {
			return lInstr;
		}

		public Instr instr() {
			return instr;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			lInstr.imprime();
			System.out.println(";");
			instr.imprime();
		}

		public String toString() {
			return "muchas_instr(" + lInstr + "," + instr + ")";
		}
	}

	public abstract static class LInstr extends Nodo {
		public LInstr() {
			super();
		}

		public void procesa(Procesamiento p){}


	}

	public static class No_inst extends InstrOpt {
		public No_inst() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
		}

		public String toString() {
			return "no_inst()";
		}
	}

	public static class Si_inst extends InstrOpt {
		LInstr lInstr;

		public Si_inst(LInstr lInstr) {
			super();
			this.lInstr = lInstr;
		}

		public LInstr lInstr() {
			return lInstr;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			lInstr.imprime();
		}

		public String toString() {
			return "si_inst(" + lInstr + ")";
		}
	}

	public abstract static class InstrOpt extends Nodo {
		public InstrOpt() {
			super();
		}

		public void procesa(Procesamiento p){}
	}

	public static class Campo extends Nodo {
		Tipo tipo;
		String id;

		public Campo(Tipo tipo, String id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			tipo.imprime();
			System.out.print(id);
			imprimeVinculo(this);
		}

		public String toString() {
			return "campo(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "]" + ")";
		}
	}

	public static class Lista_struct extends LStruct {
		LStruct lStruct;
		Campo campo;

		public Lista_struct(LStruct lStruct, Campo campo) {
			super();
			this.lStruct = lStruct;
			this.campo = campo;
		}

		public LStruct lStruct() {
			return lStruct;
		}

		public Campo campo() {
			return campo;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			lStruct.imprime();
			System.out.println(",");
			campo.imprime();
		}

		public String toString() {
			return "lista_struct(" + lStruct + "," + campo + ")";
		}
	}

	public static class Info_struct extends LStruct {
		Campo campo;

		public Info_struct(Campo campo) {
			super();
			this.campo = campo;
		}

		public Campo campo() {
			return campo;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			campo.imprime();
		}

		public String toString() {
			return "info_struct(" + campo + ")";
		}
	}

	public abstract static class LStruct extends Nodo {

		Map<String, Campo> campos;

		public LStruct() {
			super();
			campos = new HashMap<String, Campo>();
		}

		public Map<String, Campo> getCampos() {
			return campos;
		}

		public void procesa(Procesamiento p){}
	}

	public static class Tipo_struct extends Tipo {
		LStruct lStruct;

		public Tipo_struct(LStruct lStruct) {
			super();
			this.lStruct = lStruct;
		}

		public LStruct lStruct() {
			return lStruct;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<struct>");
			System.out.println("{");
			lStruct.imprime();
			System.out.println("}");  
		}

		public String toString() {
			return "tipo_struct(" + lStruct + ")";
		}

		public Tipo getTipode(String id) {
			return lStruct.getCampos().get(id).tipo();
		}

	}

	public static class Tipo_id extends Tipo {
		String id;

		public Tipo_id(String id) {
			super();
			this.id = id;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.print(id);
			imprimeVinculo(this);
		}

		public String toString() {
			return "tipo_id(" + id + ")";
		}

	}

	public static class Tipo_string extends Tipo {
		public Tipo_string() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<string>");
		}

		public String toString() {
			return "tipo_string()";
		}

	}

	public static class Tipo_bool extends Tipo {
		public Tipo_bool() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<bool>");
		}

		public String toString() {
			return "tipo_bool()";
		}

	}

	public static class Tipo_real extends Tipo {
		public Tipo_real() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<real>");
		}

		public String toString() {
			return "tipo_real()";
		}

	}

	public static class Tipo_int extends Tipo {
		public Tipo_int() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<int>");
		}

		public String toString() {
			return "tipo_int()";
		}

	}

	public static class Tipo_punt extends Tipo {
		Tipo tipo;

		public Tipo_punt(Tipo tipo) {
			super();
			this.tipo = tipo;
		}

		public Tipo tipo() {
			return tipo;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("^");
			tipo.imprime();
		}

		public String toString() {
			return "tipo_punt(" + tipo + ")";
		}

	}

	public static class Tipo_array extends Tipo {
		Tipo tipo;
		String id;

		public Tipo_array(Tipo tipo, String id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			tipo.imprime();
			System.out.println("[");
			System.out.println(id);
			System.out.print("]");
			imprimeVinculo(this);
		}

		public String toString() {
			return "tipo_array(" + tipo + "," + id + ")";
		}

	}

	public abstract static class Tipo extends Nodo {
		public Tipo() {
			super();
		}

		public void procesa(Procesamiento p){}

	}

	public static class Param_cop extends Param {
		Tipo tipo;
		String id;

		public Param_cop(Tipo tipo, String id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			tipo.imprime();
			System.out.print(id);
			imprimeVinculo(this);
		}

		public String toString() {
			return "param_cop(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "]" + ")";
		}
	}

	public static class Param_ref extends Param {
		Tipo tipo;
		String id;

		public Param_ref(Tipo tipo, String id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			tipo.imprime();
			System.out.println("&");
			System.out.print(id);
			imprimeVinculo(this);
		}

		public String toString() {
			return "param_ref(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "]" + ")";
		}
	}

	public abstract static class Param extends Nodo {
		public Param() {
			super();
		}

		public void procesa(Procesamiento p){}

	}

	public static class Un_param extends LParam {
		private Param param;

		public Un_param(Param param) {
			super();
			this.param = param;
		}

		public Param param() {
			return param;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			param.imprime();
		}

		public String toString() {
			return "un_param(" + param + ")";
		}
	}

	public static class Muchos_param extends LParam {
		private LParam params;
		private Param param;

		public Muchos_param(LParam params, Param param) {
			super();
			this.params = params;
			this.param = param;
		}

		public LParam params() {
			return params;
		}

		public Param param() {
			return param;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			params.imprime();
			System.out.println(",");
			param.imprime();
		}

		public String toString() {
			return "Muchos_param(" + params + "," + param + ")";
		}
	}

	public abstract static class LParam extends Nodo {
		public LParam() {
			super();
		}

		public void procesa(Procesamiento p){}

	}

	public static class Si_parF extends ParamF {
		private LParam lparam;

		public Si_parF(LParam lparam) {
			super();
			this.lparam = lparam;
		}

		public LParam lparam() {
			return lparam;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			lparam.imprime();
		}

		public String toString() {
			return "si_parF(" + lparam + ")";
		}
	}

	public static class No_parF extends ParamF {
		public No_parF() {
			super();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
		}

		public String toString() {
			return "no_parF()";
		}
	}

	public abstract static class ParamF extends Nodo {
		public ParamF() {
			super();
		}
		public void procesa(Procesamiento p){}

	}

	public static class Dec_proc extends Dec {
		String id;
		ParamF paramF;
		Prog prog;

		public Dec_proc(String id, ParamF paramF, Prog prog) {
			super();
			this.id = id;
			this.paramF = paramF;
			this.prog = prog;
		}

		public ParamF paramF() {
			return paramF;
		}

		public Prog prog() {
			return prog;
		}

		public String id() {
			return id;
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public void imprime() {
			System.out.println("<proc>");
			System.out.print(id);
			imprimeVinculo(this);
			System.out.println("(");
			paramF.imprime();
			System.out.println(")");
			prog.imprime();
		}

		public String toString() {
			return "dec_proc(" + id + "[" + leeFila() + "," + leeCol() + "]" + "," + paramF + "," + prog + ")";
		}
	}

	public static class Dec_type extends Dec {
		Tipo tipo;
		String id;

		public Dec_type(Tipo tipo, String id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public String id() {
			return id;
		}

		public void imprime() {
			System.out.println("<type>");
			tipo.imprime();
			System.out.print(id);
			imprimeVinculo(this);
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "dec_type(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "]" + ")";
		}
	}

	public static class Dec_id extends Dec {
		Tipo tipo;
		String id;

		public Dec_id(Tipo tipo, String id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public String id() {
			return id;
		}

		public void imprime() {
			tipo.imprime();
			System.out.print(id);
			imprimeVinculo(this);
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "dec_id(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "]" + ")";
		}
	}

	public abstract static class Dec extends Nodo {
		public Dec() {
			super();
		}
		public void procesa(Procesamiento p){}

	}

	public static class Una_dec extends LDecs {
		private Dec dec;

		public Una_dec(Dec dec) {
			super();
			this.dec = dec;
		}

		public Dec dec() {
			return dec;
		}

		public void imprime() {
			dec.imprime();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "una_dec(" + dec + ")";
		}
	}

	public static class Muchas_decs extends LDecs {
		private LDecs decs;
		private Dec dec;

		public Muchas_decs(LDecs decs, Dec dec) {
			super();
			this.dec = dec;
			this.decs = decs;
		}

		public LDecs decs() {
			return decs;
		}

		public Dec dec() {
			return dec;
		}

		public void imprime() {
			decs.imprime();
			System.out.println(";");
			dec.imprime();
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "muchas_decs(" + decs + "," + dec + ")";
		}
	}

	public static abstract class LDecs extends Nodo {
		public LDecs() {
			super();
		}
		public void procesa(Procesamiento p){}

	}

	public static class Si_decs extends Decs {
		private LDecs decs;

		public Si_decs(LDecs decs) {
			super();
			this.decs = decs;
		}

		public LDecs decs() {
			return decs;
		}

		public void imprime() {
			decs.imprime();
			System.out.println("&&");
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "si_decs(" + decs + ")";
		}
	}

	public static class No_decs extends Decs {
		public No_decs() {
			super();
		}

		public void imprime() {
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "no_decs()";
		}
	}

	public static abstract class Decs extends Nodo {
		public Decs() {
			super();
		}

		public void procesa (Procesamiento p){
		}
	}

	public static class Analiza extends Nodo {
		private Prog prog;
		public Analiza(Prog prog) {
			super();
			this.prog = prog;
		}
		public void imprime() {
			prog.imprime();
			System.out.println("<EOF>");
		}

		public String toString() {
			return "Analiza (" + prog + ")";
		}
	}

	public static class Prog extends Nodo {
		private Decs decs;
		private InstrOpt instrOpt;

		public Prog(Decs decs, InstrOpt instrOpt) {
			super();
			this.decs = decs;
			this.instrOpt = instrOpt;
		}

		public Decs decs() {
			return decs;
		}

		public InstrOpt instrOpt() {
			return instrOpt;
		}

		public void imprime() {
			System.out.println("{");
			decs.imprime();
			instrOpt.imprime();
			System.out.println("}");
		}

		public void procesa(Procesamiento p){
			p.procesa(this);
		}

		public String toString() {
			return "prog(" + decs + "," + instrOpt + ")";
		}
	}

	public Prog prog(Decs decs, InstrOpt instrOpt) {
		return new Prog(decs, instrOpt);
	}

	public Si_decs si_decs(LDecs decs) {
		return new Si_decs(decs);
	}

	public No_decs no_decs() {
		return new No_decs();
	}

	public Una_dec una_dec(Dec dec) {
		return new Una_dec(dec);
	}

	public Muchas_decs muchas_decs(LDecs decs, Dec dec) {
		return new Muchas_decs(decs, dec);
	}

	public Dec_id dec_id(Tipo tipo, String id) {
		return new Dec_id(tipo, id);
	}

	public Dec_type dec_type(Tipo tipo, String id) {
		return new Dec_type(tipo, id);
	}

	public Dec_proc dec_proc(String id, ParamF paramF, Prog prog) {
		return new Dec_proc(id, paramF, prog);
	}

	public Si_parF si_parF(LParam lparam) {
		return new Si_parF(lparam);
	}

	public No_parF no_parF() {
		return new No_parF();
	}

	public Param_cop param_cop(Tipo tipo, String id) {
		return new Param_cop(tipo, id);
	}

	public Param_ref param_ref(Tipo tipo, String id) {
		return new Param_ref(tipo, id);
	}

	public Tipo_array tipo_array(Tipo tipo, String id) {
		return new Tipo_array(tipo, id);
	}

	public Tipo_punt tipo_punt(Tipo tipo) {
		return new Tipo_punt(tipo);
	}

	public Tipo_int tipo_int() {
		return new Tipo_int();
	}

	public Tipo_real tipo_real() {
		return new Tipo_real();
	}

	public Tipo_bool tipo_bool() {
		return new Tipo_bool();
	}

	public Tipo_string tipo_string() {
		return new Tipo_string();
	}

	public Tipo_id tipo_id(String id) {
		return new Tipo_id(id);
	}

	public Tipo_struct tipo_struct(LStruct lStruct) {
		return new Tipo_struct(lStruct);
	}

	public Info_struct info_struct(Campo campo) {
		return new Info_struct(campo);
	}

	public Lista_struct lista_struct(LStruct lStruct, Campo campo) {
		return new Lista_struct(lStruct, campo);
	}

	public Campo campo(Tipo tipo, String id) {
		return new Campo(tipo, id);
	}

	public Si_inst si_inst(LInstr lInstr) {
		return new Si_inst(lInstr);
	}

	public No_inst no_inst() {
		return new No_inst();
	}

	public Una_instr una_instr(Instr instr) {
		return new Una_instr(instr);
	}

	public Muchas_instr muchas_instr(LInstr lInstr, Instr instr) {
		return new Muchas_instr(lInstr, instr);
	}

	public Instr_comp instr_comp(Prog prog) {
		return new Instr_comp(prog);
	}

	public Instr_call instr_call(String id, ParamR paramR) {
		return new Instr_call(id, paramR);
	}

	public Instr_del instr_del(Exp exp) {
		return new Instr_del(exp);
	}

	public Instr_new instr_new(Exp exp) {
		return new Instr_new(exp);
	}

	public Instr_nl instr_nl() {
		return new Instr_nl();
	}

	public Instr_wr instr_wr(Exp exp) {
		return new Instr_wr(exp);
	}

	public Instr_rd instr_rd(Exp exp) {
		return new Instr_rd(exp);
	}

	public Instr_wh instr_wh(Exp exp, Prog prog) {
		return new Instr_wh(exp, prog);
	}

	public Instr_else instr_else(Exp exp, Prog prog1, Prog prog2) {
		return new Instr_else(exp, prog1, prog2);
	}

	public Instr_if instr_if(Exp exp, Prog prog) {
		return new Instr_if(exp, prog);
	}

	public Instr_eval instr_eval(Exp exp) {
		return new Instr_eval(exp);
	}

	public Un_param_re un_param_re(Exp exp) {
		return new Un_param_re(exp);
	}

	public Muchos_param_re muchos_param_re(LParamR lParamR, Exp exp) {
		return new Muchos_param_re(lParamR, exp);
	}

	public Si_param_re si_param_re(LParamR lParamR) {
		return new Si_param_re(lParamR);
	}

	public No_param_re no_param_re() {
		return new No_param_re();
	}

	public Un_param un_param(Param param) {
		return new Un_param(param);
	}

	public Muchos_param muchos_param(LParam params, Param param) {
		return new Muchos_param(params, param);
	}

	public Asig asig(Exp exp1, Exp exp2) {
		return new Asig(exp1, exp2);
	}

	public Mayor mayor(Exp exp1, Exp exp2) {
		return new Mayor(exp1, exp2);
	}

	public Menor menor(Exp exp1, Exp exp2) {
		return new Menor(exp1, exp2);
	}

	public Mayor_igual mayor_igual(Exp exp1, Exp exp2) {
		return new Mayor_igual(exp1, exp2);
	}

	public Menor_igual menor_igual(Exp exp1, Exp exp) {
		return new Menor_igual(exp1, exp);
	}

	public Igual igual(Exp exp1, Exp exp2) {
		return new Igual(exp1, exp2);
	}

	public Distinto distinto(Exp exp1, Exp exp2) {
		return new Distinto(exp1, exp2);
	}

	public Suma suma(Exp exp1, Exp exp2) {
		return new Suma(exp1, exp2);
	}

	public Resta resta(Exp exp1, Exp exp2) {
		return new Resta(exp1, exp2);
	}

	public Mul mul(Exp exp1, Exp exp2) {
		return new Mul(exp1, exp2);
	}

	public Div div(Exp exp1, Exp exp2) {
		return new Div(exp1, exp2);
	}

	public Mod mod(Exp exp1, Exp exp2) {
		return new Mod(exp1, exp2);
	}

	public And and(Exp exp1, Exp exp2) {
		return new And(exp1, exp2);
	}

	public Or or(Exp exp1, Exp exp2) {
		return new Or(exp1, exp2);
	}

	public Not not(Exp exp) {
		return new Not(exp);
	}

	public Menos menos(Exp exp) {
		return new Menos(exp);
	}

	public Index index(Exp exp1, Exp exp2) {
		return new Index(exp1, exp2);
	}

	public Reg reg(Exp exp, String id) {
		return new Reg(exp, id);
	}

	public Indir indir(Exp exp) {
		return new Indir(exp);
	}

	public Null nulo() {
		return new Null();
	}

	public Id id(String id) {
		return new Id(id);
	}

	public True true_() {
		return new True();
	}

	public False false_() {
		return new False();
	}

	public Literal_cadena literal_cadena(String id) {
		return new Literal_cadena(id);
	}

	public Literal_real literal_real(String num) {
		return new Literal_real(num);
	}

	public Literal_ent literal_ent(String num) {
		return new Literal_ent(num);
	}

}
