
import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;

import asint.SintaxisAbstractaEval.Prog;
import c_ast_ascendente.AnalizadorLexico;
import procesamiento.ProcRecursivo;
import c_ast_ascendente.*;

public class MainRecursivo {
	public static void main(String[] args) throws Exception {

		if (true) {

			Reader input = new InputStreamReader(new FileInputStream("src/{.txt"));

			AnalizadorLexico alex = new AnalizadorLexico(input);

			Asts asint = new Asts(alex);
			
			Prog prog = (Prog) asint.parse().value;

			System.out.println(prog.toString());

			ProcRecursivo proc = new ProcRecursivo();
			proc.imprime(prog);

		} 
		
		// else {
		// 	ConstructorASTsEval asint = new ConstructorASTsEval(new FileReader(args[1]));
		// 	asint.disable_tracing();
		// 	System.out.println(new Evaluador().evalua(asint.analiza()));
		// }

	}
}
