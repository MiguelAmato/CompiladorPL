package procesamiento;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class Vinculacion extends ProcesamientoDef {
	
	Stack<Map<String, Nodo>> ts;
	Errores errores;

	public Vinculacion(Prog prog, Errores errores) {
		ts = new Stack<Map<String, Nodo>>();
		this.errores = errores;
		procesa(prog);
	}

	// ================================== VINCULA ==================================

	public void procesa(Prog prog) {
		abreAmbito();
		prog.decs().procesa(this);
		prog.instrOpt().procesa(this);
		cierraAmbito();
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

	public void procesa(Instr instr) {
		if (instr instanceof Instr_eval)
			procesa((Instr_eval)instr);
		else if (instr instanceof Instr_if)
			procesa((Instr_if)instr);
		else if (instr instanceof Instr_else)
			procesa((Instr_else)instr);
		else if (instr instanceof Instr_wh)
			procesa((Instr_wh)instr);
		else if (instr instanceof Instr_rd)
			procesa((Instr_rd)instr);
		else if (instr instanceof Instr_wr)
			procesa((Instr_wr)instr);
		else if (instr instanceof Instr_new)
			procesa((Instr_new)instr);
		else if (instr instanceof Instr_del)
			procesa((Instr_del)instr);
		else if (instr instanceof Instr_call)
			procesa((Instr_call)instr);
		else if (instr instanceof Instr_comp)
			procesa((Instr_comp)instr);
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
		Nodo v = vinculoDe(inst.id());
		if (v == null){
			error(inst);
			return;
		}
		inst.setVinculo(v);
		inst.paramR().procesa(this);
	}

	public void procesa(Instr_comp exp) {
		exp.prog().procesa(this);
	}

	public void procesa(Si_param_re s){
		procesa(s.lParamR());
	}

	public void procesa(ParamR paramR) {
		if (paramR instanceof Si_param_re) {
			procesa(((Si_param_re)paramR).lParamR());
		}
	}

	public void procesa(LParamR lParamR) {
		if (lParamR instanceof Muchos_param_re) {
			procesa(((Muchos_param_re)lParamR).lParamR());
			procesa(((Muchos_param_re)lParamR).exp());
		}
		else if (lParamR instanceof Un_param_re) {
			procesa(((Un_param_re)lParamR).exp());
		}
	}

	public void procesa(Exp exp) {
		if (exp instanceof Asig) {
			procesa((Asig)exp);
		}
		else if (exp instanceof Mayor) {
			procesa((Mayor)exp);
		}
		else if (exp instanceof Menor) {
			procesa((Menor)exp);
		}
		else if (exp instanceof Menor_igual) {
			procesa((Menor_igual)exp);
		}
		else if (exp instanceof Mayor_igual) {
			procesa((Mayor_igual)exp);
		}
		else if (exp instanceof Igual) {
			procesa((Igual)exp);
		}
		else if (exp instanceof Distinto) {
			procesa((Distinto)exp);
		}
		else if (exp instanceof Suma) {
			procesa((Suma)exp);
		}
		else if (exp instanceof And) {
			procesa((And)exp);
		}
		else if (exp instanceof Or) {
			procesa((Or)exp);
		}
		else if (exp instanceof Mul) {
			procesa((Mul)exp);
		}
		else if (exp instanceof Div) {
			procesa((Div)exp);
		}
		else if (exp instanceof Mod) {
			procesa((Mod)exp);
		}
		else if (exp instanceof Menos) {
			procesa((Menos)exp);
		}
		else if (exp instanceof Not) {
			procesa((Not)exp);
		}
		else if (exp instanceof Index) {
			procesa((Index)exp);
		}
		else if (exp instanceof Reg) {
			procesa((Reg)exp);
		}
		else if (exp instanceof Indir) {
			procesa((Indir)exp);
		}
		else if (exp instanceof Id) {
			procesa((Id)exp);
		}
	}

	public void procesa(Asig exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Mayor exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Menor exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Menor_igual exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Mayor_igual exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Igual exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Distinto exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Suma exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Resta exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Mul exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Div exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Mod exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(And exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Or exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Not exp) {
		exp.exp().procesa(this);
	}

	public void procesa(Menos exp) {
		exp.exp().procesa(this);
	}

	public void procesa(Index exp) {
		exp.exp1().procesa(this);
		exp.exp2().procesa(this);
	}

	public void procesa(Reg exp) {
		exp.exp().procesa(this);
	}

	public void procesa(Indir exp) {
		exp.exp().procesa(this);
	}

	public void procesa(Id exp) {
		exp.setVinculo(vinculoDe(exp.id()));
		if (exp.getVinculo() == null) {
			error(exp);
		}
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
			inserta(((Dec_id) dec).id(), ((Dec_id) dec));
		}
		else if (dec instanceof Dec_type) {
			vincula1(((Dec_type) dec).tipo());
			inserta(((Dec_type) dec).id(), ((Dec_type) dec));
		}
		else if (dec instanceof Dec_proc) {
			inserta(((Dec_proc) dec).id(), ((Dec_proc) dec));

			abreAmbito();
			// inserta(((Dec_proc) dec).id(), ((Dec_proc) dec));
			vincula1(((Dec_proc) dec).paramF());
			((Dec_proc) dec).prog().procesa(this);
			cierraAmbito();
		}
	}


	public void vincula1(Tipo tipo) {
		if (tipo instanceof Tipo_array) {
			vincula1(((Tipo_array) tipo).tipo());
			if (Integer.parseInt(((Tipo_array) tipo).id()) < 0) // Si el tamaño del array es negativo error
				errorPretipado(tipo);
		}
		else if (tipo instanceof Tipo_punt) {
			if (!(((Tipo_punt) tipo).tipo() instanceof Tipo_id))
				vincula1(((Tipo_punt) tipo).tipo());
		}
		else if (tipo instanceof Tipo_id) {
			((Tipo_id)tipo).setVinculo(vinculoDe(((Tipo_id) tipo).id())); // Se busca a que Dec esta vinculado el id
			if (!(((Tipo_id)tipo).getVinculo() instanceof Dec_type)) // Si no es una declaracion de tipo significa que estas usando un id como tipo no declarado
				error(tipo);
		}
		else if (tipo instanceof Tipo_struct) {
			vincula1(((Tipo_struct) tipo).lStruct(), ((Tipo_struct) tipo));
		}
	}

	public void vincula1(LStruct lStruct, Tipo_struct struct) {
		
		if (lStruct instanceof Lista_struct) {
			vincula1(((Lista_struct) lStruct).lStruct(), struct);
			vincula1(((Lista_struct) lStruct).campo(), struct);
		}
		else if (lStruct instanceof Info_struct) {
			vincula1(((Info_struct) lStruct).campo(), struct);
		}
	}

	public void vincula1(Campo campo, Tipo_struct struct) {
		vincula1(campo.tipo());
		insertaCampo(struct.getCampos(), campo.id(), campo);
	}

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
		vincula1(param.tipo());
		if (param instanceof Param_cop) {
			inserta(((Param_cop) param).id(), param); // No se si hacer una dec_id esta del todo bien
		}
		else if (param instanceof Param_ref) {
			inserta(((Param_ref) param).id(), param); // No se si hacer una dec_id esta del todo bien
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
		else if (dec instanceof Dec_proc) {
			vincula2(((Dec_proc) dec).paramF());
		}
	}

	public void vincula2(Tipo tipo) {
		if (tipo instanceof Tipo_array) {
			vincula2(((Tipo_array) tipo).tipo());
		}
		else if (tipo instanceof Tipo_punt) {
			if (((Tipo_punt) tipo).tipo() instanceof Tipo_id) {
				((Tipo_punt) tipo).tipo().setVinculo(vinculoDe(((Tipo_id) tipo.tipo()).id()));
				if (!(((Tipo_punt) tipo).tipo().getVinculo() instanceof Dec_type))
					error(tipo.tipo());
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
		ts.push(new HashMap<String, Nodo>());
	}

	private void cierraAmbito() {
		ts.pop();
	}

	// Si el id ya está en el ámbito actual, error. En caso contrario se añade el vinculo con su declaracion
	private void inserta(String id, Nodo dec) {
		if (ts.peek().containsKey(id)) 
			error(dec);
		else 
			ts.peek().put(id, dec);
	}

	private void insertaCampo(Map<String, Campo> campos, String id, Campo campo) {
		if (campos.containsKey(id)) 
			errorPretipado(campo);
		else 
			campos.put(id, campo);
	}

	private Nodo vinculoDe(String id) {
		for (int i = ts.size() - 1; i >= 0; i--) {
			if (ts.get(i).containsKey(id)) 
				return ts.get(i).get(id);
		}
		return null;
	}

	private void error(Nodo nodo) {
		String s = "Errores_vinculacion fila:" + nodo.leeFila() + " col:" + nodo.leeCol() + nodo;
		errores.addError(nodo.leeFila(), nodo.leeCol(), s);
	}

	private void errorPretipado(Nodo nodo) {
		String s = "Errores_pretipado fila:" + nodo.leeFila() + " col:" + nodo.leeCol();
		errores.addError1(nodo.leeFila(), nodo.leeCol(), s);
	}

}