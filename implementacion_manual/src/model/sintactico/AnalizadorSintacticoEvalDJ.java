package model.sintactico;

import java.io.IOException;
import java.io.Reader;

import model.lexico.UnidadLexica;

public class AnalizadorSintacticoEvalDJ extends AnalizadorSintacticoEval {
    public AnalizadorSintacticoEvalDJ(Reader input) throws IOException {
       super(input); 
    }
  protected final void traza_emparejamiento(UnidadLexica unidad) {
      switch(unidad.clase()) {
		   case ID: case LITENT: case LITREAL: System.out.println(unidad.lexema()); break;
                default: System.out.println(unidad.clase().getImage());
	 }
  } 
}
