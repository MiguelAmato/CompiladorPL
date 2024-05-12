package procesamiento;

import java.util.ArrayList;
import java.util.List;

public class Errores{

    List<Error> errores;

    public Errores() {
        this.errores = new ArrayList<Error>();
    }

    public void addError(int fila, int col, String mensaje) {
        Error error = new Error(fila, col, mensaje);
        errores.add(error);
    }

    private void sortErrores() {
        errores.sort(null);
    }

    public void printErrores() {
        sortErrores();

        for (Error error : errores) {
            System.out.println(error.mensaje);
        }
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
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
