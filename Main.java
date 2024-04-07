import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.io.InputStreamReader;

import asint.SintaxisAbstractaEval.Prog;
import c_ast_ascendente.AnalizadorLexico;
import c_ast_ascendente.AnalizadorSintacticoEval;
import procesamiento.Impresion;
import procesamiento.ProcRecursivo;
import c_ast_ascendente.*;

public class Main {
	public static void main(String[] args) throws Exception {
		// Prog prog;
		// if(args[1].equals("asc")) {
		// 	Reader input = new InputStreamReader(new FileInputStream(args[0]));
		// 	AnalizadorLexico alex = new AnalizadorLexico(input);
		// 	c_ast_ascendente.ConstructorASTs asint = new c_ast_ascendente.ConstructorASTs(alex);
		// 	prog = (Prog)asint.parse().value;
		// }
		// else {
		// 	c_ast_descendente.ConstructorASTs asint = new c_ast_descendente.ConstructorASTs(new FileReader(args[0]));
		// 	asint.disable_tracing();
		// 	prog = asint.analiza();
		// }

		// if(args[2].equals("rec")) {
		if(true) {

			// if(args[0].equals("asc")) {
			if(true) {

					// Reader input = new InputStreamReader(new FileInputStream(args[1]));
					Reader input = new InputStreamReader(new FileInputStream("src/sample1a.txt"));

					AnalizadorLexico alex = new AnalizadorLexico(input);
					Asts asint = new Asts(alex);

					Prog prog = (Prog)asint.parse().value;

					// new ProcRecursivo().imprime(prog);
					System.out.println(asint.parse().value);
				}
		}
		else if(args[2].equals("int")){
			if(args[0].equals("asc")) {
				Reader input = new InputStreamReader(new FileInputStream(args[1]));
				AnalizadorLexico alex = new AnalizadorLexico(input);
				Asts asint = new Asts(alex);

				Prog prog = (Prog)asint.parse().value;

				prog.imprime();
			}
			
		}
		else {
			if(args[0].equals("asc")) {
				Reader input = new InputStreamReader(new FileInputStream(args[1]));
				AnalizadorLexico alex = new AnalizadorLexico(input);
				Asts asint = new Asts(alex);

				Prog prog = (Prog)asint.parse().value;

				new Impresion().procesa(prog);
			}
		}
	}
}