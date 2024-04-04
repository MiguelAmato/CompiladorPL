package c_ast_ascendente;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
   public static void main(String[] args) throws Exception {
         Reader input = new InputStreamReader(System.in);
	 AnalizadorLexico alex = new AnalizadorLexico(input);
	AnalizadorSintacticoEval asint = new AnalizadorSintacticoEval(alex);
	 //asint.setScanner(alex);
	 System.out.println(asint.parse().value);
 }
}   
   