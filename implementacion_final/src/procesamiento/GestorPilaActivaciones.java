package procesamiento;

public class GestorPilaActivaciones {
    private int fin;
    private int[] displays;
    private int pp;
    public GestorPilaActivaciones(int comienzo, int fin, int numDisplays) {
       pp = comienzo; 
       this.fin = fin;
       this.displays = new int[numDisplays];
    }
    public int creaRegistroActivacion(int tamdatos) {
       if ((pp + tamdatos + 1) > fin) throw new StackOverflowError();
       int base = pp;
       pp += tamdatos + 2;
       return base;
    }
    public int liberaRegistroActivacion(int tDatos) {
       pp -= tDatos + 2;
       return pp;
    }
    public void fijaDisplay(int d, int v) {
       displays[d-1] = v; 
    }
    public int display(int d) {
       return displays[d-1]; 
    }
    public int pp() {
       return pp; 
    }
}
