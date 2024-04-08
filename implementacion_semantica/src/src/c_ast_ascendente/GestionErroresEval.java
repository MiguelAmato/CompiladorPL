package c_ast_ascendente;


public class GestionErroresEval {
   public class ErrorLexico extends RuntimeException {
       public ErrorLexico(String msg) {
           super(msg);
       }
   } 
   public class ErrorSintactico extends RuntimeException {
       public ErrorSintactico(String msg) {
           super(msg);
       }
   } 
   public void errorLexico(int fila, int columna, String lexema) {
     throw new ErrorLexico("ERROR fila "+fila+": Caracter inexperado: "+lexema); 
   }  
   public void errorSintactico(UnidadLexica unidadLexica) {
     throw new ErrorSintactico("ERROR fila "+unidadLexica.fila()+", columna "+unidadLexica.columna()+" : Elemento inexperado "+unidadLexica.lexema());
   }
}
