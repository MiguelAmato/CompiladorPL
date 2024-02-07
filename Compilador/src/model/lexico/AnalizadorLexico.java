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
	 private static String NL = System.getProperty("line.separator");
	 
	 private Map<Estado, Function> reconocedor;
	 private Map<String, ClaseLexica> reservadas;
	 
	 public AnalizadorLexico(Reader input) throws IOException {
		 	initReconocedor();
		    this.input = input;
		    lex = new StringBuffer();
		    sigCar = input.read();
		    filaActual=1;
		    columnaActual=1;
	}
	 
	public UnidadLexica sigToken() throws IOException {
		estado = Estado.INICIO;
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		lex.delete(0,lex.length());
		UnidadLexica ul = null;
		while (ul == null) {
			ul = reconocedor.get(estado).apply();
		}
		return ul;
	}
	
	
	private void transita(Estado sig) throws IOException {
		lex.append((char)sigCar);
		sigCar();         
		estado = sig;
   	}
	
   	private void transitaIgnorando(Estado sig) throws IOException {
   		sigCar();         
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		estado = sig;
	}
   	
    private void sigCar() throws IOException {
        sigCar = input.read();
        if (sigCar == NL.charAt(0)) saltaFinDeLinea();
        if (sigCar == '\n') {
           filaActual++;
           columnaActual=0;
        }
        else {
          columnaActual++;  
        }
    }
    
    private void saltaFinDeLinea() throws IOException {
        for (int i=1; i < NL.length(); i++) {
            sigCar = input.read();
            if (sigCar != NL.charAt(i)) error();
        }
        sigCar = '\n';
    }
    
    private boolean hayChar(char c) {return sigCar == c;}
    private boolean hayExp() {return sigCar == 'e' || sigCar == 'E';}
    private boolean hayLetra() {return sigCar >= 'a' && sigCar <= 'z' || sigCar >= 'A' && sigCar <= 'z';}
	private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
	private boolean hayCero() {return sigCar == '0';}
	private boolean hayDigito() {return hayDigitoPos() || hayCero();}
	private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n' || sigCar == '\r'|| sigCar == '\b';}
	private boolean hayEOF() {return sigCar == -1;}
    
    private void error() {
        System.err.println("(" + filaActual + ',' + columnaActual + "):Caracter inexperado");  
    }
	
	private UnidadLexica recInicio() throws IOException {

        if (hayLetra() || hayChar('_')) transita(Estado.REC_ID);
        else if (hayDigitoPos()) transita(Estado.REC_ENT);
        else if (hayCero()) transita(Estado.REC_0);
        else if (hayChar('+')) transita(Estado.REC_MAS);
        else if (hayChar('-')) transita(Estado.REC_MENOS);
        // TODO: Faltan estados por agregar
        else if (hayChar('#')) transitaIgnorando(Estado.REC_COM);
        else if (haySep()) transitaIgnorando(Estado.INICIO);
        else if (hayEOF()) transita(Estado.REC_EOF);
        else error();

		return null;
	}
	
	protected UnidadLexica recId() throws IOException {
		if (hayLetra() || hayDigito() || hayChar('_')) { transita(Estado.REC_ID); return null; }
		ClaseLexica ret = reservadas.get(lex.toString().toLowerCase()); 
		if (ret != null)
			return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ret);
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ID,lex.toString());
	}
	
	protected UnidadLexica reMas() throws IOException {
		if (hayDigito()) { transita(Estado.REC_ENT); return null; }
		if (hayCero()) { transita(Estado.REC_0); return null; }
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);
	}

	protected UnidadLexica recMenos() throws IOException {
		if (hayDigito()) { transita(Estado.REC_ENT); return null; }
		if (hayCero()) { transita(Estado.REC_0); return null; }
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);
	}

	protected UnidadLexica recEnt() throws IOException {
		if (hayDigito()) { transita(Estado.REC_ENT); return null; }
		if (hayChar('.')) { transita(Estado.REC_PDEC); return null; }
		if (hayExp()) { transita(Estado.REC_EXP); return null; }  
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ENT,lex.toString());
	}

	protected UnidadLexica rec0() throws IOException {
		if (hayChar('.')) { transita(Estado.REC_PDEC); return null; }
		if (hayExp()) { transita(Estado.REC_EXP); return null; }  
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ENT,lex.toString());
	}

	protected UnidadLexica recPDEC() throws IOException {
		if (hayDigito()) { transita(Estado.REC_DEC); }
		return null;
	}

	protected UnidadLexica recDEC() throws IOException {
		if (hayDigitoPos()) { transita(Estado.REC_DEC); return null; }
		if (hayCero()) { transita(Estado.REC_0DEC); return null; }
		if (hayExp()) { transita(Estado.REC_EXP); return null; } 
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.REAL,lex.toString());
	}

	protected UnidadLexica rec0DEC() throws IOException {
		if (hayDigitoPos()) { transita(Estado.REC_DEC); }
		else if (hayCero()) { transita(Estado.REC_0DEC); }
		return null;
	}
	
	protected UnidadLexica recExp() throws IOException {
		if (hayChar('+')) transita(Estado.REC_EXPPOS); 
		else if (hayChar('-')) transita(Estado.REC_EXPNEG);
		else if (hayCero()) transita(Estado.REC_0EXP);
		else if (hayDigitoPos()) transita(Estado.REC_ENTEXP);
		return null;
	}

	protected UnidadLexica recExpPos() throws IOException {
		if (hayCero()) transita(Estado.REC_0EXP);
		else if (hayDigitoPos()) transita(Estado.REC_ENTEXP);
		return null;
	}

	protected UnidadLexica recExpNeg() throws IOException {
		if (hayCero()) transita(Estado.REC_0EXP);
		else if (hayDigitoPos()) transita(Estado.REC_ENTEXP);
		return null;
	}

	protected UnidadLexica rec0Exp() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.REAL,lex.toString());
	}

	protected UnidadLexica recEntExp() throws IOException {
		if (hayDigito()) { transita(Estado.REC_ENTEXP); return null; }
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.REAL,lex.toString());
	}

	protected UnidadLexica recAsig() {
		return null;
	}

	protected UnidadLexica recIgual() {
		return null;
	}

	protected UnidadLexica recDist() {
		return null;
	}

	protected UnidadLexica recDistFin() {
		return null;
	}

	protected UnidadLexica recMayor() {
		return null;
	}

	protected UnidadLexica recMayorIgual() {
		return null;
	}

	protected UnidadLexica recMenor() {
		return null;
	}

	protected UnidadLexica recMenorIgual() {
		return null;
	}

	protected UnidadLexica recMul() {
		return null;
	}

	protected UnidadLexica recDiv() {
		return null;
	}

	protected UnidadLexica recPAp() {
		return null;
	}

	protected UnidadLexica recPCie() {
		return null;
	}

	protected UnidadLexica recComa() {
		return null;
	}

	protected UnidadLexica recPYCo() {
		return null;
	}

	protected UnidadLexica recPunto() {
		return null;
	}

	protected UnidadLexica recCambSec() {
		return null;
	}

	protected UnidadLexica recEval() {
		return null;
	}

	protected UnidadLexica recCom() {
		return null;
	}

	protected UnidadLexica recEOF() {
		return null;
	}

	protected UnidadLexica recLlavAp() {
		return null;
	}

	protected UnidadLexica recLlavCie() {
		return null;
	}
	
	private void initReconocedor() {
		reconocedor = new HashMap<Estado, Function>(); 
		reconocedor.put(Estado.INICIO, () -> recInicio());
		reconocedor.put(Estado.REC_MAS, () -> reMas());
		reconocedor.put(Estado.REC_MENOS, () -> recMenos());
		reconocedor.put(Estado.REC_ID, () -> recId());
		reconocedor.put(Estado.REC_ENT, () -> recEnt());
		reconocedor.put(Estado.REC_0, () -> rec0());
		reconocedor.put(Estado.REC_PDEC, () -> recPDEC());
		reconocedor.put(Estado.REC_DEC, () -> recDEC());
		reconocedor.put(Estado.REC_0DEC, () -> rec0DEC());
		reconocedor.put(Estado.REC_ASIG, () -> recAsig());
		reconocedor.put(Estado.REC_IGUAL, () -> recIgual());
		reconocedor.put(Estado.REC_DIST, () -> recDist());
		reconocedor.put(Estado.REC_DISTFIN, () -> recDistFin());
		reconocedor.put(Estado.REC_MAYOR, () -> recMayor());
		reconocedor.put(Estado.REC_MAYORIGUAL, () -> recMayorIgual());
		reconocedor.put(Estado.REC_MENOR, () -> recMenor());
		reconocedor.put(Estado.REC_MENORIGUAL, () -> recMenorIgual());
		reconocedor.put(Estado.REC_MUL, () -> recMul());
		reconocedor.put(Estado.REC_DIV, () -> recDiv());
		reconocedor.put(Estado.REC_PAP, () -> recPAp());
		reconocedor.put(Estado.REC_PCIE, () -> recPCie());
		reconocedor.put(Estado.REC_COMA, () -> recComa());
		reconocedor.put(Estado.REC_PYCO, () -> recPYCo());
		reconocedor.put(Estado.REC_PUNTO, () -> recPunto());
		reconocedor.put(Estado.REC_CAMBSEC, () -> recCambSec());
		reconocedor.put(Estado.REC_EVAL, () -> recEval());
		reconocedor.put(Estado.REC_COM, () -> recCom());
		reconocedor.put(Estado.REC_EOF, () -> recEOF());
		reconocedor.put(Estado.REC_EXP, () -> recExp());
		reconocedor.put(Estado.REC_EXPPOS, () -> recExpPos());
		reconocedor.put(Estado.REC_EXPNEG, () -> recExpNeg());
		reconocedor.put(Estado.REC_0EXP, () -> rec0Exp());
		reconocedor.put(Estado.REC_ENTEXP, () -> recEntExp());
		reconocedor.put(Estado.REC_LLAVAP, () -> recLlavAp());
		reconocedor.put(Estado.RECLLAVCIE, () -> recLlavCie());
		
		reservadas = new HashMap<String, ClaseLexica>() {
			private static final long serialVersionUID = 1L;

		{
			put("int", ClaseLexica.INT);
			put("real", ClaseLexica.REAL);
			put("bool", ClaseLexica.BOOL);
			put("true", ClaseLexica.TRUE);
			put("false", ClaseLexica.FALSE);
			put("and", ClaseLexica.AND);
			put("or", ClaseLexica.OR);
			put("not", ClaseLexica.NOT);
		}};
	}
	
	
}
