package model.lexico;

public class UnidadLexicaUnivaluada extends UnidadLexica {
	
	public UnidadLexicaUnivaluada(int fila, int columna, ClaseLexica clase){
		super(fila,columna,clase);
	}
	 
	public String lexema() { throw new UnsupportedOperationException(); }
	
	public String toString() {
		return "[clase:" + clase() + ",fila:" + fila() + ",col:" + columna() + "]";
	}
}
