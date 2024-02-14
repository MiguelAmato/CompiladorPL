
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import model.lexico.AnalizadorLexico;
import model.lexico.ClaseLexica;
import model.lexico.UnidadLexica;


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
     UnidadLexica unidad = null;
     do {
         unidad = al.sigToken();
         imprime(unidad);
     }
     while (unidad.clase() != ClaseLexica.EOF);
    }        
} 
