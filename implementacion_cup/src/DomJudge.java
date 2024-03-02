import alex.AnalizadorLexico;
import asint.AnalizadorSintacticoEval;
import asint.AnalizadorSintacticoEvalDJ;
import errors.GestionErroresEval.ErrorLexico;
import errors.GestionErroresEval.ErrorSintactico;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DomJudge {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(System.in);
   //Reader input = new InputStreamReader(new FileInputStream("input.txt"));
     AnalizadorLexico alex = new AnalizadorLexico(input);
     AnalizadorSintacticoEval asint = new AnalizadorSintacticoEvalDJ(alex);
	 //asint.setScanner(alex);
     try {    
        asint.debug_parse();
     }
     catch(ErrorLexico e) {
        System.out.println("ERROR_LEXICO"); 
     }
     catch(ErrorSintactico e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
 }

}
