
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import model.errors.GestionErroresEval;
import model.errors.GestionErroresEval.ErrorLexico;
import model.lexico.AnalizadorLexico;
import model.lexico.ClaseLexica;
import model.lexico.UnidadLexica;

public class DomJudge {
	private static void imprime(UnidadLexica unidad) {
		switch(unidad.clase()) {
		   case IDENTIFICADOR: case LITERAL_ENTERO: case LITERAL_REAL: case LITERAL_CADENA: System.out.println(unidad.lexema()); break;
                   default: System.out.println(unidad.clase().getImage());
		}
	}	

   public static void main(String[] args) throws FileNotFoundException, IOException {
     Reader input  = new InputStreamReader(System.in);
	 //Reader input = new InputStreamReader(new FileInputStream("input.txt"));
     AnalizadorLexico al = new AnalizadorLexico(input);
     GestionErroresEval errores = new GestionErroresEval();
     al.fijaGestionErrores(errores);
     UnidadLexica unidad = null;
     boolean error;
     do {
       error = false;  
       try {  
         unidad = al.yylex();
	     imprime(unidad);
       }
       catch(ErrorLexico e) {
              System.out.println("ERROR");
              error = true;
       }
     }
     while (error || unidad.clase() != ClaseLexica.EOF);
    }      
} 
