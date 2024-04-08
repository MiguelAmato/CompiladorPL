package c_ast_ascendente;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import model.sintactico.AnalizadorSintacticoEvalDJ;

public class Main {
   public static void main(String[] args) throws Exception {

	Reader input = new InputStreamReader(new FileInputStream("/home/amato/PL1/implementacion_semantica/src/c_ast_ascendente/input.txt"));
	AnalizadorLexico alex = new AnalizadorLexico(input);
 	Asts asint = new Asts(alex);
	//asint.setScanner(alex);
	 System.out.println(asint.parse().value);
 }
}   
   