package model.sintactico;

import java.io.IOException;
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
	 
	public AnalizadorSintacticoEval(Reader input) throws IOException {
		 errores = new GestionErroresEval();
		 alex = new AnalizadorLexico(input);
		 alex.fijaGestionErrores(errores);
		 esperados = EnumSet.noneOf(ClaseLexica.class);
		 // Se lee el primer token adelantado
		 sigToken();
	}
	
	 public void analiza() {
		 programa();
		 empareja(ClaseLexica.EOF);
	}
	
	private void programa() { // Podria quitarse y solo usar bloque, solo estoy siguiendo la GIC planteada
		bloque();
	}
	
	private void bloque() {
		switch (anticipo.clase()) {
		case LLAVAP:
			empareja(ClaseLexica.LLAVAP);
			declaraciones_opt();
			instrucciones_opt();
			empareja(ClaseLexica.LLAVCIE);
			break;
		default:
			esperados(ClaseLexica.LLAVAP);
			break;
		}	
	}
	
	// ========================== GESTION DE LA SECCION DE DECLARACIONES ==========================
	
	private void declaraciones_opt() {
		switch (anticipo.clase()) {
		case INT: case REAL: case BOOL:
			lista_declaraciones();
			empareja(ClaseLexica.CAMBIOSEC);
			break;
		default:
			esperados(ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.BOOL);
			break;
		}	
	}
	
	private void lista_declaraciones() {
		switch (anticipo.clase()) {
		case INT: case REAL: case BOOL:
			declaracion();
			lista_declaraciones_re();
			break;
		default:
			esperados(ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.BOOL);
			error();
		}	
	}

	private void lista_declaraciones_re() {
		switch (anticipo.clase()) {
		case PUNYCOMA:
			empareja(ClaseLexica.PUNYCOMA);
			declaracion();
			lista_declaraciones_re();
			break;
		default:
			esperados(ClaseLexica.PUNYCOMA);
			break;
		}	
	}
	
	private void declaracion() {
		switch(anticipo.clase()) {
		case INT: case REAL: case BOOL:
			tipo();
			empareja(ClaseLexica.ID);
			break;
		default:
			esperados(ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.BOOL);
			error();
		}
	}
	
	// ========================== GESTION DE TIPOS ==========================

	private void tipo() {
		switch(anticipo.clase()) {
		case INT: empareja(ClaseLexica.INT); break;
		case REAL: empareja(ClaseLexica.REAL); break;
		case BOOL: empareja(ClaseLexica.BOOL); break;
		default:
			esperados(ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.BOOL);
			error();
		}
	}

	// ========================== GESTION DE LA SECCION DE INSTRUCCIONES ==========================
	
	private void instrucciones_opt() {
		switch (anticipo.clase()) {
		case EVAL:
			lista_instrucciones();
			break;
		default:
			esperados(ClaseLexica.EVAL);
			break;
		}	
	}
	
	private void lista_instrucciones() {
		switch (anticipo.clase()) {
		case EVAL:
			instruccion();
			lista_instrucciones_re();
			break;
		default:
			esperados(ClaseLexica.EVAL);
			error();
		}
	}

	private void lista_instrucciones_re() {
		switch (anticipo.clase()) {
		case PUNYCOMA:
			empareja(ClaseLexica.PUNYCOMA);
			instruccion();
			lista_instrucciones_re();
			break;
		default:
			esperados(ClaseLexica.PUNYCOMA);
			break;
		}
	}
	
	private void instruccion() {
		switch(anticipo.clase()) {
		case EVAL: 
			empareja(ClaseLexica.EVAL); 
			E0();
			break;
		default:
			esperados(ClaseLexica.EVAL);
			error();
		}
	}
	
	// ========================== GESTION DE LAS EXPRESIONES ==========================
	
	private void E0() {
		switch(anticipo.clase()) {
		case NOT: case MENOS: case PARAP: case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			E1();
			E0RE();
			break;
		default:
			esperados(ClaseLexica.NOT, ClaseLexica.MENOS, ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}
	
	private void E0RE() {
		switch(anticipo.clase()) {
		case ASIG: 
			empareja(ClaseLexica.ASIG); 
			E0();
			break;
		default:
			esperados(ClaseLexica.ASIG);
			break;
		}
	}
	
	private void E1() {
		switch(anticipo.clase()) {
		case NOT: case MENOS: case PARAP: case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			E2();
			E1RE();
			break;
		default:
			esperados(ClaseLexica.NOT, ClaseLexica.MENOS, ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	private void E1RE() {
		switch(anticipo.clase()) {
		case MAYOR: case MAYOREQ: case MENOR: case MENOREQ: case IGUAL: case DIST:
			op_relacional();
			E2();
			E1RE();
			break;
		default:
			esperados(ClaseLexica.MAYOR, ClaseLexica.MAYOREQ, ClaseLexica.MENOR, ClaseLexica.MENOREQ, ClaseLexica.IGUAL, ClaseLexica.DIST);
			break;
		}
	}
	
	private void E2() {
		switch(anticipo.clase()) {
		case NOT: case MENOS: case PARAP: case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			E3();
			E2RE();
			E2RE1();
			break;
		default:
			esperados(ClaseLexica.NOT, ClaseLexica.MENOS, ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	private void E2RE1() {
		switch(anticipo.clase()) {
		case MAS: 
			empareja(ClaseLexica.MAS); 
			E3();
			E2RE1();
			break;
		default:
			esperados(ClaseLexica.MAS);
			break;
		}
	}

	private void E2RE() {
		switch(anticipo.clase()) {
		case MENOS: 
			empareja(ClaseLexica.MENOS); 
			E3();
			break;
		default:
			esperados(ClaseLexica.MENOS);
			break;
		}
	}

	private void E3() {
		switch(anticipo.clase()) {
		case NOT: case MENOS: case PARAP: case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			E4();
			E3RE();
			break;
		default:
			esperados(ClaseLexica.NOT, ClaseLexica.MENOS, ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	private void E3RE() {
		switch(anticipo.clase()) {
		case AND: 
			empareja(ClaseLexica.AND); 
			E3();
			break;
		case OR: 
			empareja(ClaseLexica.OR); 
			E4();
			break;
		default:
			esperados(ClaseLexica.AND, ClaseLexica.OR);
			break;
		}
	}

	private void E4() {
		switch(anticipo.clase()) {
		case NOT: case MENOS: case PARAP: case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			E5();
			E4RE();
			break;
		default:
			esperados(ClaseLexica.NOT, ClaseLexica.MENOS, ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	private void E4RE() {
		switch(anticipo.clase()) {
		case DIV: case MUL:
			op_nivel4();
			E5();
			E4RE();
			break;
		default:
			esperados(ClaseLexica.DIV, ClaseLexica.MUL);
			break;
		}
	}

	private void E5() {
		switch(anticipo.clase()) {
		case NOT: case MENOS: 
			op_nivel5();
			E5();
			break;
		case PARAP: case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			E6();
			break;
		default:
			esperados(ClaseLexica.NOT, ClaseLexica.MENOS, ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	private void E6() {
		switch(anticipo.clase()) {
		case PARAP:
			empareja(ClaseLexica.PARAP);
			E0();
			empareja(ClaseLexica.PARCIE);
			break;
		case LITENT: case LITREAL: case TRUE: case FALSE: case ID:
			op_basico();
			break;
		default:
			esperados(ClaseLexica.PARAP, ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	private void op_relacional() {
		switch(anticipo.clase()) {
		case MAYOR: empareja(ClaseLexica.MAYOR); break;
		case MAYOREQ: empareja(ClaseLexica.MAYOREQ); break;
		case MENOR: empareja(ClaseLexica.MENOR); break;
		case MENOREQ: empareja(ClaseLexica.MENOREQ); break;
		case IGUAL: empareja(ClaseLexica.IGUAL); break;
		case DIST: empareja(ClaseLexica.DIST); break;
		default:
			esperados(ClaseLexica.MAYOR, ClaseLexica.MAYOREQ, ClaseLexica.MENOR, ClaseLexica.MENOREQ, ClaseLexica.IGUAL, ClaseLexica.DIST);
			error();
		}
	}
	
	private void op_nivel4() {
		switch(anticipo.clase()) {
		case DIV: empareja(ClaseLexica.DIV); break;
		case MUL: empareja(ClaseLexica.MUL); break;
		default:
			esperados(ClaseLexica.DIV, ClaseLexica.MUL);
			error();
		}
	}
	
	private void op_nivel5() {
		switch(anticipo.clase()) {
		case MENOS: empareja(ClaseLexica.MENOS); break;
		case NOT: empareja(ClaseLexica.NOT); break;
		default:
			esperados(ClaseLexica.MENOS, ClaseLexica.NOT);
			error();
		}
	}
	
	private void op_basico() {
		switch(anticipo.clase()) {
		case LITENT: empareja(ClaseLexica.LITENT); break;
		case LITREAL: empareja(ClaseLexica.LITREAL); break;
		case TRUE: empareja(ClaseLexica.TRUE); break;
		case FALSE: empareja(ClaseLexica.FALSE); break;
		case ID: empareja(ClaseLexica.ID); break;
		default:
			esperados(ClaseLexica.LITENT, ClaseLexica.LITREAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.ID);
			error();
		}
	}

	// ========================== FUNCIONES AUXILIARES ==========================

	private void esperados(ClaseLexica ... esperadas) {
	       for(ClaseLexica c: esperadas) {
	           esperados.add(c);
	       }
	}
	
	private void empareja(ClaseLexica claseEsperada) {
	      if (anticipo.clase() == claseEsperada) {
	          traza_emparejamiento(anticipo);
	          sigToken();
	      }    
	      else {
	          esperados(claseEsperada);
	          error();
	      }
   }
   
   private void sigToken() {
      try {
        anticipo = alex.sigToken();
        esperados.clear();
      }
      catch(IOException e) {
        errores.errorFatal(e);
      }
   }
   
    private void error() {
        errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
    }
    
    protected void traza_emparejamiento(UnidadLexica anticipo) {} 
}
