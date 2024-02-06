package model.lexico;

import java.io.IOException;
import java.io.Reader;

public class AnalizadorLexico {
	
	 private Reader input;
	 private StringBuffer lex;
	 private int sigCar;
	 private int filaInicio;
	 private int columnaInicio; 
	 private int filaActual;
	 private int columnaActual;
	 private Estado estado;
	 
	 public AnalizadorLexico(Reader input) throws IOException {
		    this.input = input;
		    lex = new StringBuffer();
		    sigCar = input.read();
		    filaActual=1;
		    columnaActual=1;
	 }
	
}
