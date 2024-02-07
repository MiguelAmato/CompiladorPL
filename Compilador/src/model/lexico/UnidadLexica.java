package model.lexico;

public abstract class UnidadLexica {
	
	protected ClaseLexica clase;
	private int fila;
	private int columna;
	
	public UnidadLexica(int fila, int columna, ClaseLexica clase) {
		this.fila = fila;
		this.columna = columna;
		this.clase = clase;
	}
	
	public abstract String lexema();
	
	public ClaseLexica clase () { return clase; }
	
	public int fila() { return fila; }
	
	public int columna() { return columna; }
	
}
