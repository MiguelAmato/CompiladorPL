package c_ast_ascendente;

import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
   public static class StringLocalizado {
     private int fila;
     private int col;
     private String s;
     public StringLocalizado(String s, int fila, int col) {
         this.s = s;
         this.fila = fila;
         this.col = col;  
     }
     public int fila() {return fila;}
     public int col() {return col;}
     public String str() {return s;}
   }
   public UnidadLexica(int fila, int columna, int clase, String lexema) {
     super(clase, new StringLocalizado(lexema,fila,columna));  
   }
   public int clase () {return sym;}
   public int fila() {return ((StringLocalizado)value).fila();}
   public int columna() {return ((StringLocalizado)value).col();}
   public String lexema() {return ((StringLocalizado)value).str();}
}
