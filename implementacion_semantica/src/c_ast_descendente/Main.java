package c_ast_descendente;
import java.io.FileReader;
public class Main{
   public static void main(String[] args) throws Exception {
      Asts asint = new Asts(new FileReader("/home/dai/projects/compilador_pl/implementacion_semantica/src/input.txt"));
      asint.disable_tracing();
      System.out.println(asint.analiza());
   }
}