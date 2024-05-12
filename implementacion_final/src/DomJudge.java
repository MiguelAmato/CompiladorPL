
import java.io.FileInputStream;
import java.io.Reader;

import asint.SintaxisAbstractaEval.Prog;

import java.io.InputStreamReader;

import c_ast_ascendente.AnalizadorLexico;
import c_ast_ascendente.GestionErroresEval.ErrorLexico;
import c_ast_ascendente.GestionErroresEval.ErrorSintactico;
import c_ast_ascendente.*;
import c_ast_descendente.*;
import procesamiento.Errores;
import procesamiento.Impresion;
import procesamiento.ProcRecursivo;
import procesamiento.Tipado;
import procesamiento.Vinculacion;

public class DomJudge {
	public static void main(String[] args) throws Exception {
		// Reader input = new InputStreamReader(System.in);
		Reader input = new InputStreamReader(new FileInputStream("casos_errores/03_errores_tipado1_a.in"));
		char c = (char) input.read();
		if (c == 'a') {
			AnalizadorLexico alex = new AnalizadorLexico(input);
			ASTS_A_DJ asint = new ASTS_A_DJ(alex);
			Prog prog = null;
			try {    
				// prog = (Prog)asint.debug_parse().value;
				prog = (Prog)asint.parse().value;
			}
			catch(ErrorLexico e) {
				System.out.println("ERROR_LEXICO"); 
				System.exit(1);
			}
			catch(ErrorSintactico e) {
				System.out.println("ERROR_SINTACTICO");
				System.exit(1); 
			}
			// System.out.println("IMPRESION RECURSIVA");	
			// ProcRecursivo proc = new ProcRecursivo();
			// proc.imprime(prog); // Recursivo
			// System.out.println("IMPRESION INTERPRETE");	
			// prog.imprime(); // Interprete
			// System.out.println("IMPRESION VISITANTE");	
			// Impresion imp = new Impresion();
			// imp.procesa(prog);
			Errores errores = new Errores();
			new Vinculacion(prog, errores);
			if (errores.hayErrores()) {
				errores.printErrores();
				//System.exit(0);
			}
			if (errores.hayErrores1()) {
				errores.printErrores1();
				System.exit(0);
			}
			new Tipado(prog, errores);
			if (errores.hayErrores()) {
				errores.printErrores();
				System.exit(0);
			}
		}
		else if (c == 'd') {
			ASTS_D_DJ asint = new ASTS_D_DJ(input);
			asint.setTabSize();
			Prog prog = null;
			try {
				prog = asint.analiza();
			}
			catch(ParseException e) {
				System.out.println("ERROR_SINTACTICO");
				System.exit(1); 
			}
			catch(TokenMgrError e) {
				System.out.println("ERROR_LEXICO");
				System.exit(1);
			}
			// System.out.println("IMPRESION RECURSIVA");	
			// ProcRecursivo proc = new ProcRecursivo();
			// proc.imprime(prog); // Recursivo
			
			// System.out.println("IMPRESION INTERPRETE");	
			// prog.imprime(); // Interprete
			
			System.out.println("IMPRESION VISITANTE");	
			Impresion imp = new Impresion();
			imp.procesa(prog);
			Errores errores = new Errores();
			new Vinculacion(prog, errores);
			if (errores.hayErrores()) {
				errores.printErrores();
				System.exit(0);
			}
			new Tipado(prog, errores);
			if (errores.hayErrores()) {
				errores.printErrores();
				System.exit(0);
			}
		}
		else {
			System.err.println("ERROR: El archivo de entrada debe comenzar con 'a' o 'd'");
			System.exit(1);
		}


	}
}
