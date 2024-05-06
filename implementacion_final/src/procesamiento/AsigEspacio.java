package procesamiento;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaEval.*;

import asint.ProcesamientoDef;

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

	// ================================== AsigEspacio1 ==================================

	

	public void procesa(Prog prog) {
		prog.decs().procesa(this);
		prog.instrOpt().procesa(this);
	}
	

	public void procesa(Si_decs decs) {
		procesa1(decs.decs());
		procesa2(decs.decs());
	}
	
	public void procesa(No_decs decs) {
	}


	public void procesa1(Muchas_decs decs) {
		procesa1(decs.decs());
		procesa1(decs.dec());
	}

	public void procesa1(Una_dec dec) {
		procesa1(dec.dec());
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

	public void asig_tam1(Tipo_array tipo) {
		procesa1(tipo.tipo());
		tipo.setTam(tipo.tipo().getTam() * Integer.parseInt(tipo.id()));
	}

	public void asig_tam1(Tipo_punt tipo) {
		if(!tipo.tipo() == tipo.tipo().getVinculo()) // comprobar //////////////////////////asdñlfjalsñkdjfañsldjkf
				procesa1(tipo.tipo());
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
		tipo.setTam(1);
	}

	public void asig_tam1(Tipo_struct tipo) {
		procesa1(tipo.lStruct());
		tipo.setTam(tipo.lStruct().getTam());
	}
	
	public void asig_tam1(Lista_struct lStruct) {
		procesa1(lStruct.lStruct());
		lStruct.campo().procesa(this);
	}

	public void asig_tam1(Info_struct struct) {
		procesa1(struct.campo(), struct.campo().getTam());
		struct.setTam(struct.campo().getTam());
	}

	public void procesa1(Campo campo, int desp){
		procesa1(campo.tipo());
		campo.setTam(campo.tipo().getTam());
		campo.setDesp(desp);
	}

	public void procesa1(Si_parF parF) {
		procesa1(parF.lparam());
	}

	public void procesa1(No_parF parF) {
	}
	
	public void procesa1(Muchos_param lparam) {
		procesa1(lparam.params());
		procesa1(lparam.param());
	}

	public void procesa1(Un_param param) {
		param.param().procesa(this);
	}

	public void procesa1(Param_ref param) {
		procesa1(param.tipo());
		param.setDir(dir);
		param.setNivel(nivel); 
		añadir_dir(dir);
	}

	public void procesa1(Param_cop param) {
		procesa1(param.tipo());
		param.setDir(dir); 
		param.setNivel(nivel); 
		añadir_dir(dir);
	}

	public void procesa2(Muchas_decs decs){
		procesa2(decs.decs());
		procesa2(decs.dec());
	}

	public void procesa2(Una_dec dec){
		procesa2(dec.dec());
	}

	public void procesa2(Dec_type dec){
		asig_tam2(dec.tipo());
	}

	public void procesa2(Dec_id dec){
		asig_tam2(dec.tipo());
	}

	public void procesa2(Dec_proc dec){
	}

	public void asig_tam2(Tipo_array tipo){
		procesa2(tipo.tipo());
	}

	public void asig_tam2(Tipo_punt tipo){ // ?? slñdkjfaslñdkfjasñldkfjasñldkfjasñlfjk
		// No sé 
	}

	public void asig_tam2(Tipo_int tipo){
	}

	public void asig_tam2(Tipo_real tipo){
	}

	public void asig_tam2(Tipo_bool tipo){
	}

	public void asig_tam2(Tipo_string tipo){
	}

	public void asig_tam2(Tipo_id tipo){
	}

	public void asig_tam2(Lista_struct lStruct){
		procesa2(lStruct.lStruct());
		procesa2(lStruct.campo());
	}

	public void asig_tam2(Info_struct struct){
		procesa2(struct.campo());
	}

	public void asig_tam2(Campo campo){
		procesa2(campo.tipo());
	}

	public void procesa2(No_parF parF){
	}
	
	public void procesa2(Si_parF parF){
		procesa2(parF.lparam());
	}

	public void procesa2(Muchos_param lparam){
		procesa2(lparam.params());
		procesa2(lparam.param());
	}

	public void procesa2(Un_param param){
		procesa2(param.param());
	}

	public void procesa2(Param_ref param){
		procesa2(param.tipo());
	}

	public void procesa(Si_inst instr){
		procesa2(instr.lInstr());
	}

	public void procesa(No_inst instr){
	}

	public void procesa(Muchas_instr lInstr){
		procesa2(lInstr.instr());
		procesa2(lInstr.lInstr());
	}

	public void procesa(Una_instr instr){
		procesa2(instr.instr());
	}

	public void procesa(Instr_eval instr){
	}

	public void procesa(Instr_if instr){
		instr.setDirAnt(dir);
		instr.prog().procesa(this);
		dir = instr.getMaxDirAnt();
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
		instr.exp().procesa(this);
		dir = instr.getDirAnt();
	}

	public void procesa(Instr_rd instr){
		
	}

	public void procesa(Instr_wr instr){
	}

	public void procesa(Instr_nl instr){
	}

	public void procesa(Instr_new instr){
	}

	public void procesa(Instr_del instr){
	}

	public void procesa(Instr_call instr){
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
}
