package model.error;

import java.util.Set;

import model.lexico.ClaseLexica;

public class GestionErroresEval {
	
	   public static class ErrorLexico extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ErrorLexico(String msg) {
	           super(msg);
	       }
	   } 
	   
	   public static class ErrorSintactico extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ErrorSintactico(String msg) {
	           super(msg);
	       }
	   } 
	   
	   public void errorLexico(int fila, int col, char car) {
	     throw new ErrorLexico("ERROR fila "+fila+","+col+": Caracter inexperado: "+car); 
	   }  
	   
	   public void errorSintactico(int fila, int col, ClaseLexica encontrada, Set<ClaseLexica> esperadas) {
	     StringBuilder sb = new StringBuilder();  
	     sb.append("ERROR fila "+fila+","+col+": Encontrado "+encontrada+" Se esperada: ");
	     for(ClaseLexica esperada: esperadas)
	         sb.append(esperada+" ");
	     throw new ErrorSintactico(sb.toString());
	   }
	   
	   public void errorFatal(Exception e) {
	     System.out.println(e);
	     e.printStackTrace();
	     System.exit(1);
	   }
}
