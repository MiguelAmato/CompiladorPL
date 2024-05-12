package c_ast_ascendente;

import c_ast_ascendente.UnidadLexica.StringLocalizado;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

public class ASTS_A_DJ extends ASTS_A {
	public void debug_message(String msg) {}
    public void debug_shift(Symbol token) {
    //    System.out.println(((StringLocalizado)token.value).str());
    }
    @SuppressWarnings("deprecation")
    public ASTS_A_DJ(Scanner alex) {
        super(alex);
        
    }
}
