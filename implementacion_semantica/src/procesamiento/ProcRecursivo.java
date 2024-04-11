package procesamiento;

import asint.SintaxisAbstractaEval;


public class ProcRecursivo extends SintaxisAbstractaEval {

    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }

    public void imprime(Prog p){
        System.out.println("{");
        imprime(p.decs());
        imprime(p.instrOpt());
        System.out.println("}");
    }

    public void imprime(Decs ds){ //TODO
        if (claseDe(ds, Si_decs.class)) {
            imprime(((Si_decs)ds).decs());
            System.out.println("&&");
        }
    }

    public void imprime(LDecs ls){
        if (claseDe(ls, Una_dec.class))
            imprime(((Una_dec)ls).dec());
        else {
            imprime(((Muchas_decs)ls).decs());
            System.out.println(";");
            imprime(((Muchas_decs)ls).dec());
        }
    }

    public void imprime(Dec d){
        if (claseDe(d, Dec_id.class)){
            imprime(((Dec_id) d).tipo());
            System.out.println(((Dec_id)d).id());
        }
        else if (claseDe(d, Dec_type.class)){
            System.out.println("<type>");
            imprime(((Dec_type)d).tipo());
            System.out.println(((Dec_type)d).id()); //TODO revisar pintar id varias veces
        }
        else if (claseDe(d, Dec_proc.class)){
            System.out.println("<proc>");
            System.out.println(((Dec_proc)d).id());
            System.out.println("(");
            imprime(((Dec_proc)d).paramF());
            System.out.println(")");
						imprime(((Dec_proc)d).prog());
        }
    }

    public void imprime(ParamF pf){
        if(claseDe(pf, Si_parF.class))
            imprime(((Si_parF)pf).lparam());
    }

    public void imprime(LParam lp){
        if (claseDe(lp, Un_param.class)){
            imprime(((Un_param)lp).param());
        }
        else {
            imprime(((Muchos_param)lp).params());
            System.out.println(",");
            imprime(((Muchos_param)lp).param());
        }
    }

    public void imprime(Param p){
        if(claseDe(p, Param_cop.class)){
            imprime(((Param_cop)p).tipo());
            System.out.println(((Param_cop)p).id());
        }
        else if(claseDe(p, Param_ref.class)){
            imprime(((Param_ref)p).tipo());
            System.out.println("&");
            System.out.println(((Param_ref)p).id());
        }
    }

    public void imprime(Tipo t){
        if(claseDe(t, Tipo_int.class)){
            System.out.println("<int>");
        }
        else if(claseDe(t, Tipo_bool.class)){
            System.out.println("<bool>");
        }
        else if(claseDe(t, Tipo_real.class)){
            System.out.println("<real>");
        }
        else if(claseDe(t, Tipo_string.class)){
            System.out.println("<string>");
        }
        else if (claseDe(t, Tipo_id.class)) {
    		System.out.println(((Tipo_id) t).id());
    	}
        else if (claseDe(t, Tipo_struct.class)) {
    		System.out.println("<struct>");
    		System.out.println("{");
    		imprime(((Tipo_struct) t).lStruct());
    		System.out.println("}");
    	}
        else if (claseDe(t, Tipo_punt.class)) {
    		System.out.println("^");
    		imprime(((Tipo_punt) t).tipo());
    	}
        else if(claseDe(t, Tipo_array.class)){
    		imprime(((Tipo_array) t).tipo());
    		System.out.println("[");
    		System.out.println(((Tipo_array) t).id());
    		System.out.println("]");
        }
    }

    public void imprime(LStruct ls){
        if(claseDe(ls, Info_struct.class)){
            imprime(((Info_struct)ls).campo());
        }
        else {
            imprime(((Lista_struct)ls).lStruct());
            System.out.println(",");
            imprime(((Lista_struct)ls).campo());
        }
    }

    public void imprime(Campo c){
        imprime(c.tipo());
        System.out.println(c.id());
    }

    public void imprime(InstrOpt io){
        if(claseDe(io, Si_inst.class)){
            imprime(((Si_inst)io).lInstr());
        }
    }

    public void imprime(LInstr li){
        if(claseDe(li, Una_instr.class)){
            imprime(((Una_instr)li).instr());
        }
        else {
            imprime(((Muchas_instr)li).lInstr());
            System.out.println(";");
            imprime(((Muchas_instr)li).instr());
        }
    }

    public void imprime(Instr i){
        if(claseDe(i, Instr_comp.class)){
            imprime(((Instr_comp)i).prog());
        }
        else if(claseDe(i, Instr_call.class)){
            System.out.println("<call>");
            System.out.println(((Instr_call)i).id());
            System.out.println("(");
            imprime(((Instr_call)i).paramR());
            System.out.println(")");
        }
        else if(claseDe(i, Instr_del.class)){
            System.out.println("<delete>");
            imprime(((Instr_del)i).exp());
        }
        else if(claseDe(i, Instr_new.class)){
            System.out.println("<new>");
            imprime(((Instr_new)i).exp());
        }
        else if(claseDe(i, Instr_nl.class)){
            System.out.println("");
        }
        else if(claseDe(i, Instr_wr.class)){
            System.out.println("<write>");
            imprime(((Instr_wr)i).exp());
        }
        else if(claseDe(i, Instr_rd.class)){
            System.out.println("<read>");
            imprime(((Instr_rd)i).exp());
        }
        else if(claseDe(i, Instr_wh.class)){
            System.out.println("<while>");
            imprime(((Instr_wh)i).exp());
            imprime(((Instr_wh)i).prog());
        }
        else if(claseDe(i, Instr_else.class)){
            System.out.println("<if>");
            imprime(((Instr_else)i).exp());
            imprime(((Instr_else)i).prog1());
            System.out.println("<else>");
            imprime(((Instr_else)i).prog2());
        }
        else if(claseDe(i, Instr_if.class)){
            System.out.println("<if>");
            imprime(((Instr_if)i).exp());
            imprime(((Instr_if)i).prog());
        }
        else if(claseDe(i, Instr_eval.class)){
            System.out.println("@");
            imprime(((Instr_eval)i).exp());
        }
    }

    public void imprime(ParamR pr){
        if(claseDe(pr, Si_param_re.class)){
            imprime(((Si_param_re)pr).lParamR());
        }
    }

    public void imprime(LParamR lpr){
        if(claseDe(lpr, Un_param_re.class)){
            imprime(((Un_param_re)lpr).exp());
        }
        else {
            imprime(((Muchos_param_re)lpr).lParamR());
            System.out.println(",");
            imprime(((Muchos_param_re)lpr).exp());
        }
    }

    public void imprime(Exp e){
        if(claseDe(e, Indir.class)){
            System.out.println("^");
            imprime(((Indir)e).exp());
        }
				else if(claseDe(e, Asig.class)){
					imprimeExpBin(((Asig)e).exp1(), "=", ((Asig)e).exp2(),1,0);
				}
        else if(claseDe(e, Reg.class)){
            imprime(((Reg)e).exp());
            System.out.println(".");
            System.out.println(((Reg)e).id());
        }
        else if(claseDe(e, Index.class)){
            imprime(((Index)e).exp1());
            System.out.println("[");
            imprime(((Index)e).exp2());
            System.out.println("]");
        }
        else if(claseDe(e, Menos.class)){ // TODO revisar el imprimeExpPre
            // System.out.println("-");
            // imprime(((Menos)e).exp());
						imprimeExpPre(((Menos)e).exp(), "-",5);
        }
        else if(claseDe(e, Not.class)){
						imprimeExpPre(((Not)e).exp(), "not",5);
        }
        else if(claseDe(e, Or.class)){
            imprimeExpBin(((Or)e).exp1(), "or",((Or)e).exp2(),4,4);
        }
        else if(claseDe(e, And.class)){
						imprimeExpBin(((And)e).exp1(), "and",((And)e).exp2(),4,3);
        }
        else if(claseDe(e, Mod.class)){
						imprimeExpBin(((Mod)e).exp1(), "%", ((Mod)e).exp2(),4,5);
        }
        else if(claseDe(e, Div.class)){
            imprimeExpBin(((Div)e).exp1(), "/", ((Div)e).exp2(),4,5);
        }
        else if(claseDe(e, Mul.class)){
					imprimeExpBin(((Mul)e).exp1(), "*", ((Mul)e).exp2(),4,5);
        }
        else if(claseDe(e, Resta.class)){
					imprimeExpBin(((Resta)e).exp1(), "-", ((Resta)e).exp2(),3,3);
        }
        else if(claseDe(e, Suma.class)){
					imprimeExpBin(((Suma)e).exp1(), "+", ((Suma)e).exp2(),2,3);
        }
        else if(claseDe(e, Distinto.class)){
					imprimeExpBin(((Distinto)e).exp1(), "!=", ((Distinto)e).exp2(),1,2);

        }
        else if(claseDe(e, Igual.class)){
					imprimeExpBin(((Igual)e).exp1(), "==", ((Igual)e).exp2(),1,2);
        }
				else if(claseDe(e, Mayor.class)){
					imprimeExpBin(((Mayor)e).exp1(), ">", ((Mayor)e).exp2(),1,2);
				}
				else if(claseDe(e, Menor.class)){
					imprimeExpBin(((Menor)e).exp1(), "<", ((Menor)e).exp2(),1,2);
				}
        else if(claseDe(e, Mayor_igual.class)){
					imprimeExpBin(((Mayor_igual)e).exp1(), ">=", ((Mayor_igual)e).exp2(),1,2);
        }
				else if(claseDe(e, Menor_igual.class)){
					imprimeExpBin(((Menor_igual)e).exp1(), "<=", ((Menor_igual)e).exp2(),1,2);
        }		
				else if(claseDe(e, True.class)){
					System.out.println("<true>");
				}
				else if(claseDe(e, False.class)){
					System.out.println("<false>");
				}
				else if(claseDe(e, Literal_real.class)){
					System.out.println(((Literal_real)e).num());
				}
				else if(claseDe(e, Literal_ent.class)){
					System.out.println(((Literal_ent)e).num());
				}
				else if(claseDe(e, Literal_cadena.class)){
					System.out.println(((Literal_cadena)e).id());
				}
				else if(claseDe(e, Id.class)){
					System.out.println(((Id)e).id());
				}
				else if(claseDe(e, Null.class)){
					System.out.println("<null>");
				}
    }

		public void imprimeExpBin(Exp e1, String op, Exp e2, int p1, int p2){
				imprimeOpnd(e1,p1);
				System.out.println(op);
				imprimeOpnd(e2,p2);
		}

		public void imprimeExpPre(Exp e, String op, int p){
				System.out.println(op);
				imprimeOpnd(e,p);
		}

		public void imprimeOpnd(Exp e, int p){
				if (prioridad(e) < p) {
					System.out.println("(");
				}
				imprime(e);
				if (prioridad(e) < p) {
					System.out.println(")");
				}
		}

		public int prioridad (Exp e) {
				if(claseDe(e, Asig.class)){
					return 0;
				}
				else if (claseDe(e, Mayor.class)){
					return 1;
				}
				else if (claseDe(e, Menor.class)){
					return 1;
				}
				else if (claseDe(e, Mayor_igual.class)){
					return 1;
				}
				else if (claseDe(e, Menor_igual.class)){
					return 1;
				}
				else if (claseDe(e, Igual.class)){
					return 1;
				}
				else if (claseDe(e, Distinto.class)){
					return 1;
				}
				else if (claseDe(e, Suma.class)){
					return 2;
				}
				else if (claseDe(e, Resta.class)){
					return 2;
				}
				else if (claseDe(e, And.class)){
					return 3;
				}
				else if (claseDe(e, Or.class)){
					return 3;
				}
				else if (claseDe(e, Mul.class)){
					return 4;
				}
				else if (claseDe(e, Mod.class)){
					return 4;
				}
				else if (claseDe(e, Div.class)){
					return 4;
				}
				else if (claseDe(e, Menos.class)){
					return 5;
				}
				else if (claseDe(e, Not.class)){
					return 5;
				}
				else if (claseDe(e, Index.class)){
					return 6;
				}
				else if (claseDe(e, Reg.class)){
					return 6;
				}
				else if (claseDe(e, Indir.class)){
					return 6;
				}
				else {
					return 1000;
				}
		}
	
		public void imprime(Literal_real r){
				System.out.println(r.num());
		}

		public void simprime(Literal_ent e){
				System.out.println(e.num());
		}

		public void imprime(True t){
				System.out.println("<true>");
		}

		public void imprime(False f){
				System.out.println("<false>");
		}

		public void imprime(Literal_cadena c){
				System.out.println(c.id());
		}
	
		public void imprime(Id i){
				System.out.println(i.id());
		}

		public void imprime(Null n){
				System.out.println("<null>");
		}

}
