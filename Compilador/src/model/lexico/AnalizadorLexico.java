package model.lexico;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import utils.Function;

public class AnalizadorLexico {
	
	 private Reader input;
	 private StringBuffer lex;
	 private int sigCar;
	 private int filaInicio;
	 private int columnaInicio; 
	 private int filaActual;
	 private int columnaActual;
	 private Estado estado;
	 
	 private Map<Estado, Function> reconocedor;
	 
	 public AnalizadorLexico(Reader input) throws IOException {
		 	initReconocedor();
		    this.input = input;
		    lex = new StringBuffer();
		    sigCar = input.read();
		    filaActual=1;
		    columnaActual=1;
	 }

	private void initReconocedor() {
		reconocedor = new HashMap<Estado, Function>(){{
			
		}};
		
	}
	
}
