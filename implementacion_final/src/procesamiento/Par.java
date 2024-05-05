package procesamiento;

public class Par<T, U> {
    // Atributos
    private T primero;
    private U segundo;
    
    // Constructor
    public Par(T primero, U segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }
    
    // Métodos
    public T getPrimero() {
        return primero;
    }
    
    public void setPrimero(T primero) {
        this.primero = primero;
    }
    
    public U getSegundo() {
        return segundo;
    }
    
    public void setSegundo(U segundo) {
        this.segundo = segundo;
    }
    
    @Override
    public String toString() {
        return "(" + primero + ", " + segundo + ")";
    }
}
