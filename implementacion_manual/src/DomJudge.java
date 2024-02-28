
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import model.error.GestionErroresEval;
import model.error.GestionErroresEval.ErrorLexico;
import model.error.GestionErroresEval.ErrorSintactico;
import model.lexico.AnalizadorLexico;
import model.lexico.ClaseLexica;
import model.lexico.UnidadLexica;
import model.sintactico.*;


public class DomJudge{
	   public static void main(String[] args) throws Exception {
	     try{  
	      AnalizadorSintacticoEval asint = new AnalizadorSintacticoEvalDJ(new InputStreamReader(System.in));
	      //AnalizadorSintacticoEval asint = new AnalizadorSintacticoEvalDJ(new InputStreamReader(new FileInputStream("input.txt")));
	      asint.analiza();
	     }
	     catch(ErrorSintactico e) {
	        System.out.println("ERROR_SINTACTICO"); 
	     }
	     catch(ErrorLexico e) {
	        System.out.println("ERROR_LEXICO"); 
	     }
	   }
	}

/*
	
// README: Si se quiere probar solo el analizador lexico se descomenta esta seccion y se comenta la otra
	
	
public class DomJudge {
	private static void imprime(UnidadLexica unidad) {
		switch(unidad.clase()) {
		   case ID: case LITENT: case LITREAL: System.out.println(unidad.lexema()); break;
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
         unidad = al.sigToken();
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
*/