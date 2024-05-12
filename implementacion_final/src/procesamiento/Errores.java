package procesamiento;

import java.util.ArrayList;
import java.util.List;

public class Errores{

    List<Error> errores;
    List<Error> errores1;

    public Errores() {
        this.errores = new ArrayList<Error>();
        this.errores1 = new ArrayList<Error>();
    }

    public void addError(int fila, int col, String mensaje) {
        Error error = new Error(fila, col, mensaje);
        errores.add(error);
    }

    public void addError1(int fila, int col, String mensaje) {
        Error error = new Error(fila, col, mensaje);
        errores1.add(error);
    }

    private void sortErrores() {
        errores.sort(null);
    }

    private void sortErrores1() {
        errores1.sort(null);
    }

    public void printErrores() {
        sortErrores();
        
        for (Error error : errores) {
            System.out.println(error.mensaje);
        }
    }
    
    public void printErrores1() {
        sortErrores1();

        for (Error error : errores1) {
            System.out.println(error.mensaje);
        }
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
    }

    public boolean hayErrores1() {
        return !errores1.isEmpty();
    }


    private class Error implements Comparable<Error> {
        int fila, col;
        String mensaje;

        public Error(int fila, int col, String mensaje) {
            this.fila = fila;
            this.col = col;
            this.mensaje = mensaje;
        }

        @Override
        public int compareTo(Error otroError) {
            // Primero comparamos por fila
            int comparacionPorFila = Integer.compare(this.fila, otroError.fila);
            if (comparacionPorFila != 0) {
                return comparacionPorFila;
            }
            // Si las filas son iguales, comparamos por columna
            return Integer.compare(this.col, otroError.col);
        }
    }
}
