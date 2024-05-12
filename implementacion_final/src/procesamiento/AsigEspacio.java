package procesamiento;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

public class AsigEspacio extends ProcesamientoDef{
	
	int dir;
	int max_dir;
	int nivel;

	public AsigEspacio(Prog prog) {
		dir = 0;
		max_dir = 0;
		nivel = 0;
		procesa(prog);
	}

	// Voy a seguir el orden de la memoria, luego si quieren se organiza en bloques mejor

	public void procesa(Prog prog) {
		prog.decs().procesa(this);
		prog.instrOpt().procesa(this);
	}

	public void procesa(Si_decs decs) {
		procesa1(decs.decs());
		procesa2(decs.decs());
	}

	public void procesa1(LDecs decs) {
		if (decs instanceof Muchas_decs)
			procesa1((Muchas_decs) decs);
		else if (decs instanceof Una_dec)
			procesa1((Una_dec) decs);
	}

	public void procesa1(Muchas_decs decs) {
		procesa1(decs.decs());
		procesa1(decs.dec());
	}

	public void procesa1(Una_dec dec) {
		procesa1(dec.dec());
	}

	public void procesa1(Dec dec) {
		if (dec instanceof Dec_type)
			procesa1((Dec_type) dec);
		else if (dec instanceof Dec_id)
			procesa1((Dec_id) dec);
		else if (dec instanceof Dec_proc)
			procesa1((Dec_proc) dec);
	}

	public void procesa1(Dec_type dec) {
		asig_tam1(dec.tipo());
	}

	public void procesa1(Dec_id dec) {
		asig_tam1(dec.tipo());
		dec.setDir(dir);
		dec.setNivel(nivel);
		añadir_dir(dec.tipo().getTam());
	}

	public void procesa1(Dec_proc dec) {
		dec.setDirAnt(dir);
		dec.setMaxDirAnt(max_dir)	;
		nivel++;
		dec.setNivel(nivel);
		dir = 0;
		max_dir = 0;
		procesa1(dec.paramF());
		procesa2(dec.paramF());
		dec.prog().procesa(this);
		dec.setTam(max_dir);
		dir = dec.getDirAnt();
		max_dir = dec.getMaxDirAnt();
		nivel--;
	}

	public void asig_tam1(Tipo tipo) {
		if (tipo instanceof Tipo_array)
			asig_tam1((Tipo_array) tipo);
		else if (tipo instanceof Tipo_punt)
			asig_tam1((Tipo_punt) tipo);
		else if (tipo instanceof Tipo_int)
			asig_tam1((Tipo_int) tipo);
		else if (tipo instanceof Tipo_real)
			asig_tam1((Tipo_real) tipo);
		else if (tipo instanceof Tipo_bool)
			asig_tam1((Tipo_bool) tipo);
		else if (tipo instanceof Tipo_string)
			asig_tam1((Tipo_string) tipo);
		else if (tipo instanceof Tipo_id)
			asig_tam1((Tipo_id) tipo);
		else if (tipo instanceof Tipo_struct)
			asig_tam1((Tipo_struct) tipo);
	}

	public void asig_tam1(Tipo_array tipo) {
		asig_tam1(tipo.tipo());
		tipo.setTam(tipo.tipo().getTam() * Integer.parseInt(tipo.id()));
	}

	public void asig_tam1(Tipo_punt tipo) {
		if (!(ref(tipo.tipo()) instanceof Tipo_id)) // OJO CON EL PIOJO
			asig_tam1(tipo.tipo());
		else 
			tipo.setTam(1);
	}

	public void asig_tam1(Tipo_int tipo) {
		tipo.setTam(1);
	}

	public void asig_tam1(Tipo_real tipo) {
		tipo.setTam(1);
	}

	public void asig_tam1(Tipo_bool tipo) {
		tipo.setTam(1);
	}

	public void asig_tam1(Tipo_string tipo) {
		tipo.setTam(1);
	}

	public void asig_tam1(Tipo_id tipo) { 
		Tipo t = ((Dec_type) tipo.getVinculo()).tipo();
		t.procesa(this);
		tipo.setTam(t.getTam());
	}

	public void asig_tam1(Tipo_struct tipo) {
		asig_tam1(tipo.lStruct());
		tipo.setTam(tipo.lStruct().getTam());
	}

	public void asig_tam1(LStruct lStruct) {
		if (lStruct instanceof Lista_struct)
			asig_tam1((Lista_struct) lStruct);
		else if (lStruct instanceof Info_struct)
			asig_tam1((Info_struct) lStruct);	
	}
	
	public void asig_tam1(Lista_struct lStruct) {
		asig_tam1(lStruct.lStruct());
		asig_tam1(lStruct.campo(), lStruct.campo().getTam());
		lStruct.setTam(lStruct.lStruct().getTam() + lStruct.campo().getTam());
	}

	public void asig_tam1(Info_struct struct) {
		asig_tam1(struct.campo(), 0);
		struct.setTam(struct.campo().getTam());
	}

	public void asig_tam1(Campo campo, int desp) {
		asig_tam1(campo.tipo());
		campo.setTam(campo.tipo().getTam());
		campo.setDesp(desp);
	}

	public void procesa1(ParamF parF) {
		if (parF instanceof Si_parF)
			procesa1((Si_parF) parF);
		else if (parF instanceof No_parF)
			procesa1((No_parF) parF);
	}

	public void procesa1(Si_parF parF) {
		procesa1(parF.lparam());
	}

	public void procesa1(LParam lparam) {
		if (lparam instanceof Muchos_param)
			procesa1((Muchos_param) lparam);
		else if (lparam instanceof Un_param)
			procesa1((Un_param) lparam);
	}
	
	public void procesa1(Muchos_param lparam) {
		procesa1(lparam.params());
		procesa1(lparam.param());
	}

	public void procesa1(Un_param param) {
		procesa1(param.param());
	}

	public void procesa1(Param param) {
		if (param instanceof Param_ref)
			procesa1((Param_ref) param);
		else if (param instanceof Param_cop)
			procesa1((Param_cop) param);
	}

	public void procesa1(Param_ref param) {
		asig_tam1(param.tipo());
		param.setDir(dir);
		param.setNivel(nivel); 
		añadir_dir(dir);
	}

	public void procesa1(Param_cop param) {
		asig_tam1(param.tipo());
		param.setDir(dir); 
		param.setNivel(nivel); 
		añadir_dir(dir);
	}

	public void procesa2(LDecs decs) {
		if (decs instanceof Muchas_decs)
			procesa2((Muchas_decs) decs);
		else if (decs instanceof Una_dec)
			procesa2((Una_dec) decs);
	}

	public void procesa2(Muchas_decs decs){
		procesa2(decs.decs());
		procesa2(decs.dec());
	}

	public void procesa2(Una_dec dec){
		procesa2(dec.dec());
	}

	public void procesa2(Dec dec){
		if(dec instanceof Dec_type)
			procesa2((Dec_type) dec);
		else if(dec instanceof Dec_id)
			procesa2((Dec_id) dec);
		else if(dec instanceof Dec_proc)
			procesa2((Dec_proc) dec);
	}

	public void procesa2(Dec_type dec){
		asig_tam2(dec.tipo());
	}

	public void procesa2(Dec_id dec){
		asig_tam2(dec.tipo());
	}

	public void asig_tam2(Tipo tipo) {
		if (tipo instanceof Tipo_array)
			asig_tam2((Tipo_array) tipo);
		else if (tipo instanceof Tipo_punt)
			asig_tam2((Tipo_punt) tipo);
		else if (tipo instanceof Tipo_int)
			asig_tam2((Tipo_int) tipo);
		else if (tipo instanceof Tipo_real)
			asig_tam2((Tipo_real) tipo);
		else if (tipo instanceof Tipo_bool)
			asig_tam2((Tipo_bool) tipo);
		else if (tipo instanceof Tipo_string)
			asig_tam2((Tipo_string) tipo);
		else if (tipo instanceof Tipo_id)
			asig_tam2((Tipo_id) tipo);
		else if (tipo instanceof Tipo_struct)
			asig_tam2((Tipo_struct) tipo);
	}

	public void asig_tam2(Tipo_array tipo){
		asig_tam2(tipo.tipo());
	}

	public void asig_tam2(Tipo_punt tipo){ 
		if (ref(tipo.tipo()) instanceof Tipo_id) { // OJO???
			tipo.tipo().setVinculo();
		}
	}

	public void asig_tam2(Tipo_struct tipo) {
		asig_tam2(tipo.lStruct());
	}

	public void asig_tam2(LStruct lStruct) {
		if (lStruct instanceof Lista_struct)
			asig_tam2((Lista_struct) lStruct);
		else if (lStruct instanceof Info_struct)
			asig_tam2((Info_struct) lStruct);
	}

	public void asig_tam2(Lista_struct lStruct){
		asig_tam2(lStruct.lStruct());
		asig_tam2(lStruct.campo());
	}

	public void asig_tam2(Info_struct struct){
		asig_tam2(struct.campo());
	}

	public void asig_tam2(Campo campo){
		asig_tam2(campo.tipo());
	}

	public void procesa2(ParamF parF) {
		if (parF instanceof Si_parF)
			procesa2((Si_parF) parF);
		else if (parF instanceof No_parF)
			procesa2((No_parF) parF);
	}
	
	public void procesa2(Si_parF parF){
		procesa2(parF.lparam());
	}

	public void procesa2(No_parF parF) {}

	public void procesa2(LParam lparam){
		if(lparam instanceof Muchos_param)
			procesa2((Muchos_param) lparam);
		else if(lparam instanceof Un_param)
			procesa2((Un_param) lparam);
	}

	public void procesa2(Muchos_param lparam){
		procesa2(lparam.params());
		procesa2(lparam.param());
	}

	public void procesa2(Un_param param){
		procesa2(param.param());
	}

	public void procesa2(Param param){
		if(param instanceof Param_ref)
			procesa2((Param_ref) param);
		else if(param instanceof Param_cop)
			procesa2((Param_cop) param);
	}

	public void procesa2(Param_cop param){
		asig_tam2(param.tipo());
	}

	public void procesa2(Param_ref param){
		asig_tam2(param.tipo());
	}

	public void procesa(Si_inst instr){
		procesa2(instr.lInstr());
	}

	public void procesa2(LInstr lInstr){
		// TODO:
	}

	public void procesa(Muchas_instr lInstr){
		lInstr.lInstr().procesa(this);
		lInstr.instr().procesa(this);
	}

	public void procesa(Una_instr instr){
		instr.instr().procesa(this);
	}

	public void procesa(Instr_if instr){
		instr.setDirAnt(dir);
		instr.prog().procesa(this);
		dir = instr.getDirAnt();
	}

	public void procesa(Instr_else instr){
		instr.setDirAnt(dir);
		instr.prog1().procesa(this);
		dir = instr.getDirAnt();
		instr.prog2().procesa(this);
		dir = instr.getDirAnt();
	}

	public void procesa(Instr_wh instr){
		instr.setDir(dir);
		instr.prog().procesa(this);
		dir = instr.getDirAnt();
	}

	public void procesa(Instr_comp instr){
		instr.setDirAnt(dir);
		instr.prog().procesa(this);
		dir = instr.getDirAnt();
	}
	
	 private void añadir_dir(int tam) {
	        dir += tam;
	        if(dir > max_dir)
	            max_dir = dir;
	}

	private Tipo ref(Tipo tipo) {
        if (tipo.getClass() == Tipo_id.class)
            return ref(((Dec_type)tipo.getVinculo()).tipo());
        return tipo.getTipo();
    }
}
