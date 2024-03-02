import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoTinyDJ;
import asint.ParseException;
import asint.TokenMgrError;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
      //AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new InputStreamReader(System.in));
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new InputStreamReader(new FileInputStream("src/imput.txt")));
      asint.analiza();
     }
     catch(ParseException e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
     catch(TokenMgrError e) {
        System.out.println("ERROR_LEXICO"); 
     }
   }
}