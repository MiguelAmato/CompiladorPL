package procesamiento;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;


public class Impresion extends ProcesamientoDef{


		private void imprimeOpnd(Exp opnd, int np) {
			if(opnd.prioridad() < np) {System.out.println("(");};
				opnd.procesa(this);
			if(opnd.prioridad() < np) {System.out.println(")");};
		}
		private void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1, int fila, int col) {
			imprimeOpnd(opnd0,np0);
			System.out.print(op);
			imprimeVinculo(fila, col);
			imprimeOpnd(opnd1,np1);
		}

		private void imprimeExpPre(Exp opnd, String op, int np, int fila, int col) {
			System.out.print(op);
			imprimeVinculo(fila, col);
			imprimeOpnd(opnd,np);
		}

		private void imprimeVinculo(Nodo nodo) {
			System.out.println("$f:" + nodo.leeFila() + ",c:" + nodo.leeCol() + "$");
		}

		private void imprimeVinculo(int fila, int col) {
				System.out.println("$f:" + fila + ",c:" + col + "$");
		}

		public void procesa(Prog a){
				System.out.println("{");
				a.decs().procesa(this);
				a.instrOpt().procesa(this);
				System.out.println("}");
		}
		public void procesa(No_decs a){}

		public void procesa(Si_decs a){
				a.decs().procesa(this);
				System.out.println("&&");
		}
		public void procesa(Muchas_decs a){
				a.decs().procesa(this);
				System.out.println(";");
				a.dec().procesa(this);
		} 
		public void procesa(Una_dec a){
				a.dec().procesa(this);
		}
		public void procesa(Dec_id a){
				a.tipo().procesa(this);
				System.out.print(a.id());
				imprimeVinculo(a);
		}
		public void procesa(Dec_type a){
				System.out.println("<type>");
				a.tipo().procesa(this);
				System.out.print(a.id());
				imprimeVinculo(a);
		}
		public void procesa(Dec_proc a){
				System.out.println("<proc>");
				System.out.print(a.id());
				imprimeVinculo(a);
				System.out.println("(");
				a.paramF().procesa(this);
				System.out.println(")");
				a.prog().procesa(this);
		}

		public void procesa(Tipo_array a){
			a.tipo().procesa(this);
			System.out.println("[");
			System.out.println(a.id());
			System.out.print("]");
			imprimeVinculo(a);
		}

		public void procesa(Tipo_punt a){
			System.out.println("^");
			a.tipo().procesa(this);
		}

		public void procesa(Tipo_int a){
			System.out.println("<int>");
		}

		public void procesa(Tipo_real a){
			System.out.println("<real>");
		}

		public void procesa(Tipo_string a){
			System.out.println("<string>");
		}

		public void procesa(Tipo_bool a){
			System.out.println("<bool>");
		}

		public void procesa(Tipo_id a){
			System.out.print(a.id());
			imprimeVinculo(a);
		}

		public void procesa(Tipo_struct a){
			System.out.println("<struct>");
			System.out.println("{");
			a.lStruct().procesa(this);
			System.out.println("}");
		}

		public void procesa(Lista_struct a){
			a.lStruct().procesa(this);
			System.out.println(",");
			a.campo().procesa(this);
		}

		public void procesa(Info_struct a){
			a.campo().procesa(this);
		}

		public void procesa(Campo a){
			a.tipo().procesa(this);
			System.out.print(a.id());
			imprimeVinculo(a);
		}

		public void procesa(No_parF a){}

		public void procesa(Si_parF a){
			a.lparam().procesa(this);
		}
	
		public void procesa(Muchos_param a){
			a.params().procesa(this);
			System.out.println(",");
			a.param().procesa(this);
		}

		public void procesa(Un_param a){
			a.param().procesa(this);
		}

		public void procesa(Param_cop a){
			a.tipo().procesa(this);
			System.out.print(a.id());
			imprimeVinculo(a);
		}

		public void procesa(Param_ref a){
				a.tipo().procesa(this);
				System.out.println("&");
				System.out.print(a.id());
				imprimeVinculo(a);
		}

		public void procesa(Si_inst a){
			a.lInstr().procesa(this);
		}

		public void procesa(No_inst a) {}

		public void procesa(Muchas_instr a){
			a.lInstr().procesa(this);
			System.out.println(";");
			a.instr().procesa(this);
		}

		public void procesa(Una_instr a){
			a.instr().procesa(this);
		}

		public void procesa(Instr_eval a){
			System.out.println("@");
			a.exp().procesa(this);
		}

		public void procesa(Instr_if a){
			System.out.println("<if>");
			a.exp().procesa(this);
			a.prog().procesa(this);
		}

		public void procesa(Instr_else a){
			System.out.println("<if>");
			a.exp().procesa(this);
			a.prog1().procesa(this);
			System.out.println("<else>");
			a.prog2().procesa(this);
		}

		public void procesa(Instr_wh a){
			System.out.println("<while>");
			a.exp().procesa(this);
			a.prog().procesa(this);
		}

		public void procesa(Instr_rd a){
			System.out.println("<read>");
			a.exp().procesa(this);	
		}

		public void procesa(Instr_wr a){
			System.out.println("<write>");
			a.exp().procesa(this);
		}

		public void procesa(Instr_nl a){
			System.out.println("<nl>");
		}

		public void procesa(Instr_new a){
			System.out.println("<new>");
			a.exp().procesa(this);
		}

		public void procesa(Instr_del a){
			System.out.println("<delete>");
			a.exp().procesa(this);
		}

		public void procesa(Instr_call a){
			System.out.println("<call>");
			System.out.print(a.id());
			imprimeVinculo(a);
			System.out.println("(");
			a.paramR().procesa(this);
			System.out.println(")");
		}

		public void procesa(Instr_comp a){
			a.prog().procesa(this);
		}
		
		public void procesa(Si_param_re a){
			a.lParamR().procesa(this);
		}

		public void procesa(No_param_re a) {}

		public void procesa(Muchos_param_re a){
			a.lParamR().procesa(this);
			System.out.println(",");
			a.exp().procesa(this);
		}

		public void procesa(Un_param_re a){
			a.exp().procesa(this);
		}
		
		public void procesa(Asig a){
			imprimeExpBin(a.exp1(), "=", a.exp2(), 1, 0, a.leeFila(), a.leeCol());
		}

		public void procesa(Mayor a){
			imprimeExpBin(a.exp1(), ">", a.exp2(), 1, 2, a.leeFila(), a.leeCol());
		}

		public void procesa(Menor a){
			imprimeExpBin(a.exp1(), "<", a.exp2(), 1, 2, a.leeFila(), a.leeCol());
		}

		public void procesa(Mayor_igual a){
			imprimeExpBin(a.exp1(), ">=", a.exp2(), 1, 2, a.leeFila(), a.leeCol());;
		}

		public void procesa(Menor_igual a){
			imprimeExpBin(a.exp1(), "<=", a.exp2(), 1, 2, a.leeFila(), a.leeCol());;
		}

		public void procesa(Igual a){
			imprimeExpBin(a.exp1(), "==", a.exp2(), 1, 2, a.leeFila(), a.leeCol());;
		}

		public void procesa(Distinto a){
			imprimeExpBin(a.exp1(), "!=", a.exp2(), 1, 2, a.leeFila(), a.leeCol());;
		}

		public void procesa(Suma a){
			imprimeExpBin(a.exp1(), "+", a.exp2(), 2, 3, a.leeFila(), a.leeCol());
		}

		public void procesa(Resta a){
			imprimeExpBin(a.exp1(), "-", a.exp2(), 3, 3, a.leeFila(), a.leeCol());
		}

		public void procesa(And a){
			imprimeExpBin(a.exp1(), "<and>", a.exp2(), 4, 3, a.leeFila(), a.leeCol());
		}

		public void procesa(Or a){
			imprimeExpBin(a.exp1(), "<or>", a.exp2(), 4, 4, a.leeFila(), a.leeCol());;
		}

		
		public void procesa(Mul a){
			imprimeExpBin(a.exp1(), "*", a.exp2(), 4, 5, a.leeFila(), a.leeCol());
		}

		public void procesa(Mod a){
			imprimeExpBin(a.exp1(), "%", a.exp2(), 4, 5, a.leeFila(), a.leeCol());
		}

		public void procesa(Div a){
			imprimeExpBin(a.exp1(), "/", a.exp2(), 4, 5, a.leeFila(), a.leeCol());
		}

		public void procesa(Menos a){
			imprimeExpPre(a.exp(), "-", 5, a.leeFila(), a.leeCol());
		}

		public void procesa(Not a){
			imprimeExpPre(a.exp(), "<not>", 5, a.leeFila(), a.leeCol());
		}

		public void procesa(Index a){
			a.exp1().procesa(this);
			System.out.print("[");
			imprimeVinculo(a);
			a.exp2().procesa(this);
			System.out.println("]");
		}


		public void procesa(Reg a){
			a.exp().procesa(this);
			System.out.println(".");
			System.out.print(a.id());
			imprimeVinculo(a);
		}

		public void procesa(Indir a){
			a.exp().procesa(this);
			System.out.print("^");
			imprimeVinculo(a);
		}

		public void procesa(Literal_real a){
			System.out.print(a.num());
			imprimeVinculo(a);
		}

		public void procesa(Literal_ent a){
			System.out.print(a.num());
			imprimeVinculo(a);
		}

		public void procesa(True a){
			System.out.print("<true>");
			imprimeVinculo(a);
		}

		public void procesa(False a){
			System.out.print("<false>");
			imprimeVinculo(a);
		}

		public void procesa(Literal_cadena a){
			System.out.print(a.id());
			imprimeVinculo(a);
		}

		public void procesa(Id a){
			System.out.print(a.id());
			imprimeVinculo(a);
		}

		public void procesa(Null a){
			System.out.print("<null>");
			imprimeVinculo(a);
		}
}
