package launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import model.lexico.AnalizadorLexico;
import model.lexico.ClaseLexica;
import model.lexico.UnidadLexica;

public class Main {

	public static void main(String[] args) throws IOException {
	     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
	     AnalizadorLexico al = new AnalizadorLexico(input);
	     UnidadLexica unidad;
	     do { 
	       unidad = al.yylex();
	       System.out.println(unidad);
	     }
	     while (unidad.clase() != ClaseLexica.EOF);
	}

}
