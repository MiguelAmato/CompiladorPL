package c_ast_descendente;

import java.io.Reader;

public class ASTS_D_DJ extends ASTS_D {
	private void imprime_token(Token t) {
        switch(t.kind) {
            case and: System.out.println("<and>"); break;
            case or: System.out.println("<or>"); break;
            case not: System.out.println("<not>"); break;
            case ent: System.out.println("<int>"); break;
            case real: System.out.println("<real>"); break;
            case bool: System.out.println("<bool>"); break;
            case string: System.out.println("<string>"); break;
            case nulo: System.out.println("<null>"); break;
            case falso: System.out.println("<false>"); break;
            case verdadero: System.out.println("<true>"); break;
            case proc: System.out.println("<proc>"); break;
            case If: System.out.println("<if>"); break;
            case Else: System.out.println("<else>"); break;
            case While: System.out.println("<while>"); break;
            case struct: System.out.println("<struct>"); break;
            case New: System.out.println("<new>"); break;
            case Delete: System.out.println("<delete>"); break;
            case read: System.out.println("<read>"); break;
            case write: System.out.println("<write>"); break;
            case nl: System.out.println("<nl>"); break;
            case type: System.out.println("<type>"); break;
            case call: System.out.println("<call>"); break;
            case EOF: System.out.println("<EOF>"); break;
            default: System.out.println(t.image);
        }
    }
    
    public ASTS_D_DJ(Reader r) {
        super(r);
        disable_tracing();
    }
    final protected void trace_token(Token t, String where) {
        imprime_token(t);
    }
}
