package procesamiento;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class Vinculacion extends ProcesamientoDef {
	
	Stack<Map<String, Dec>> ts;

	public Vinculacion(Prog prog) {
		ts = new Stack<Map<String, Dec>>();
		procesa(prog);
	}

	// ================================== VINCULA ==================================

	public void procesa(Prog prog) {
		abreAmbito();
		prog.decs().procesa(this);
		prog.instrOpt().procesa(this);
		cierraAmbito();
	}

	public void procesa(No_decs decs) {
	}

	public void procesa(Si_decs decs) {
		vincula1(decs.decs());
		vincula2(decs.decs());
	}

	public void procesa(Si_inst instr) {
		instr.lInstr().procesa(this);
	}

	public void procesa(Muchas_instr instr) {
		instr.lInstr().procesa(this);
		instr.instr().procesa(this);
	}

	public void procesa(Una_instr instr) {
		instr.instr().procesa(this);
	}

	public void procesa(Instr_eval inst) {
		inst.exp().procesa(this);
	}

	public void procesa(Instr_if inst) {
		inst.exp().procesa(this);
		inst.prog().procesa(this);
	}

	public void procesa(Instr_else inst) {
		inst.exp().procesa(this);
		inst.prog1().procesa(this);
		inst.prog2().procesa(this);
	}

	public void procesa(Instr_wh inst) {
		inst.exp().procesa(this);
		inst.prog().procesa(this);
	}

	public void procesa(Instr_rd inst) {
		inst.exp().procesa(this);
	}

	public void procesa(Instr_wr inst) {
		inst.exp().procesa(this);
	}

	public void procesa(Instr_new inst) {
		inst.exp().procesa(this);
	}

	public void procesa(Instr_del inst) {
		inst.exp().procesa(this);
	}

	public void procesa(Instr_call inst) {
		Dec dec = vinculoDe(inst.id());
		if (!(dec instanceof Dec_proc))
			error();
		else
			inst.paramR().procesa(this);
	}

	public void procesa(Instr_comp exp) {
		exp.prog().procesa(this);
	}

	

	// ================================== VINCULA 1 ==================================

	public void vincula1(LDecs decs) {
		if (decs instanceof Muchas_decs) {
			vincula1(((Muchas_decs) decs).decs());
			vincula1(((Muchas_decs) decs).dec());
		} else if (decs instanceof Una_dec) {
			vincula1(((Una_dec) decs).dec());
		}
	}

	public void vincula1(Dec dec) {
		if (dec instanceof Dec_id) {
			vincula1(((Dec_id) dec).tipo());
			inserta(((Dec_id) dec).id(), dec);
		}
		else if (dec instanceof Dec_type) {
			vincula1(((Dec_type) dec).tipo());
			inserta(((Dec_type) dec).id(), dec);
		}
		else if (dec instanceof Dec_proc) {
			inserta(((Dec_proc) dec).id(), dec);
			// FUNCION QUE HACE QUE SE PROCESE UN BLOQUE Y QUE EL AMBITO CONTEMPLE EL ID DEL PROC DECLARADO
			procesaProgProc(((Dec_proc) dec).prog(), ((Dec_proc) dec).id(), dec); 
		}
	}

	// FUNCION QUE HACE QUE SE PROCESE UN BLOQUE Y QUE EL AMBITO CONTEMPLE EL ID DEL PROC DECLARADO
	private void procesaProgProc(Prog prog, String id, Dec dec) {
		abreAmbito();
		inserta(id, dec);
		prog.decs().procesa(this);
		prog.instrOpt().procesa(this);
		cierraAmbito();
	}

	public void vincula1(Tipo tipo) {
		if (tipo instanceof Tipo_array) {
			vincula1(((Tipo_array) tipo).tipo());
			if (Integer.parseInt(((Tipo_array) tipo).id()) < 0)
				error();
		}
		else if (tipo instanceof Tipo_punt) {
			if (!(((Tipo_punt) tipo).tipo() instanceof Tipo_id))
				vincula1(((Tipo_punt) tipo).tipo());
		}
		else if (tipo instanceof Tipo_id) {
			Dec dec = vinculoDe(((Tipo_id) tipo).id()); // Se busca a que Dec esta vinculado el id
			if (!(dec instanceof Dec_type)) // Si no es una declaracion de tipo significa que estas usando un id como tipo no declarado
				error();
		}
	}

	/*
	* Hacer el tipo struct que no se como hacerlo 
	*/

	public void vincula1(ParamF paramF) {
		if (paramF instanceof Si_parF) {
			vincula1(((Si_parF) paramF).lparam());
		}
	}

	public void vincula1(LParam lparam) {
		if (lparam instanceof Muchos_param) {
			vincula1(((Muchos_param) lparam).params());
			vincula1(((Muchos_param) lparam).param());
		}
		else if (lparam instanceof Un_param) {
			vincula1(((Un_param) lparam).param());
		}
	}

	public void vincula1(Param param) {
		if (param instanceof Param_cop) {
			inserta(((Param_cop) param).id(), new Dec_id(((Param_cop) param).tipo(), ((Param_cop) param).id())); // No se si hacer una dec_id esta del todo bien
		}
		else if (param instanceof Param_ref) {
			inserta(((Param_ref) param).id(), new Dec_id(((Param_ref) param).tipo(), ((Param_ref) param).id())); // No se si hacer una dec_id esta del todo bien
		}
	}
	

	// ================================== VINCULA 2 ==================================

	public void vincula2(LDecs decs) {
		if (decs instanceof Muchas_decs) {
			vincula2(((Muchas_decs) decs).decs());
			vincula2(((Muchas_decs) decs).dec());
		} 
		else if (decs instanceof Una_dec) {
			vincula2(((Una_dec) decs).dec());
		}
	}

	public void vincula2(Dec dec) {
		if (dec instanceof Dec_id) {
			vincula2(((Dec_id) dec).tipo());
		}
		else if (dec instanceof Dec_type) {
			vincula2(((Dec_type) dec).tipo());
		}
	}

	public void vincula2(Tipo tipo) {
		if (tipo instanceof Tipo_array) {
			vincula2(((Tipo_array) tipo).tipo());
		}
		else if (tipo instanceof Tipo_punt) {
			if (((Tipo_punt) tipo).tipo() instanceof Tipo_id) {
				Dec aux = vinculoDe(((Tipo_id)(((Tipo_punt) tipo).tipo())).id());
				if (!(aux instanceof Dec_type))
					error();
				else {
					// No entiendo que hay que hacer aqui
				}
			}
			else
				vincula2(((Tipo_punt) tipo).tipo());
		}
		else if (tipo instanceof Tipo_struct) {
			vincula2(((Tipo_struct) tipo).lStruct());
		}
	}

	public void vincula2(LStruct lStruct) {
		if (lStruct instanceof Lista_struct) {
			vincula2(((Lista_struct) lStruct).lStruct());
			vincula2(((Lista_struct) lStruct).campo());
		}
		else if (lStruct instanceof Info_struct) {
			vincula2(((Info_struct) lStruct).campo());
		}
	}

	public void vincula2(Campo campo) {
		vincula2(campo.tipo());
	}

	public void vincula2(ParamF par) {
		if (par instanceof Si_parF) {
			vincula2(((Si_parF) par).lparam());
		}
	}

	public void vincula2(LParam lparam) {
		if (lparam instanceof Muchos_param) {
			vincula2(((Muchos_param) lparam).params());
			vincula2(((Muchos_param) lparam).param());
		}
		else if (lparam instanceof Un_param) {
			vincula2(((Un_param) lparam).param());
		}
	}

	public void vincula2(Param param) {
		if (param instanceof Param_cop) {
			vincula2(((Param_cop) param).tipo());
		}
		else if (param instanceof Param_ref) {
			vincula2(((Param_ref) param).tipo());
		}
	}

	// ================================== AUX ==================================

	private void abreAmbito() {
		ts.push(new HashMap<String, Dec>());
	}

	private void cierraAmbito() {
		ts.pop();
	}

	// Si el id ya está en el ámbito actual, error. En caso contrario se añade el vinculo con su declaracion
	private void inserta(String id, Dec dec) {
		if (ts.peek().containsKey(id)) 
			error();
		else 
			ts.peek().put(id, dec);
	}

	private Dec vinculoDe(String id) {
		return ts.peek().get(id);
	}

	private void error() {
		// TODO
	}

}