package model.sintactico;

import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;

import model.error.GestionErroresEval;
import model.lexico.*;
public class AnalizadorSintacticoEval {
	private UnidadLexica anticipo;
	 // token adelantado
	private AnalizadorLexico alex; // analizador léxico
	private GestionErroresEval errores; // gestor de errores
	private Set<ClaseLexica> esperados; // clases léxicas esperadas
	 
	public AnalizadorSintacticoEval(Reader input) {
		 // se crea el gestor de errores
		 errores = new GestionErroresEval();
		 // se crea el analizador léxico
		 //alex = new AnalizadorLexico(input);
		 // se fija el gestor de errores en el analizador léxico
		 // (debe añadirse el método 'fijaGestionErrores' a
		 //  dicho analizador)
		 alex.fijaGestionErrores(errores);
		 // se crea el conjunto de clases léxicas esperadas
		 // (estará incializado a vacío)
		 esperados = EnumSet.noneOf(ClaseLexica.class);
		 // Se lee el primer token adelantado
		 //sigToken();
	}
}
