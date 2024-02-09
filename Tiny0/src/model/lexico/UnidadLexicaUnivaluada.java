package model.lexico;

public class UnidadLexicaUnivaluada extends UnidadLexica {
	
	public UnidadLexicaUnivaluada(int fila, int columna, ClaseLexica clase){
		super(fila,columna,clase);
	}
	 
	public String lexema() { throw new UnsupportedOperationException(); }
	
	public String toString() {
		if (clase.equals(ClaseLexica.ERROR))
			return "(" + fila() + ',' + columna() + "):Caracter inexperado";
		return "[clase:" + clase() + ",fila:" + fila() + ",col:" + columna() + "]";
	}
	

	
}
