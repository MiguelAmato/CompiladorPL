package asint;

import c_ast_ascendente.UnidadLexica.StringLocalizado;

public class ClaseSemanticaEval extends SintaxisAbstractaEval {
    public ClaseSemanticaEval() {
        super();
    }

	public Exp op_rel(String op, Exp opnd1, Exp opnd2) {
		switch(op) {
			case "<": return menor(opnd1,opnd2);
			case ">": return mayor(opnd1,opnd2);
			case "<=": return menor_igual(opnd1,opnd2);
			case ">=": return mayor_igual(opnd1,opnd2);
			case "==": return igual(opnd1,opnd2);
			case "!=": return distinto(opnd1,opnd2);
			default: throw new UnsupportedOperationException("Bad op");
		}
	}

	public Exp op_mul(String op, Exp opnd1, Exp opnd2) {
		switch(op) {
			case "*": return mul(opnd1,opnd2);
			case "/": return div(opnd1,opnd2);
			case "%": return mod(opnd1,opnd2);
			default: throw new UnsupportedOperationException("Bad op");
		}
	}

	public Exp op_inv(String op, Exp opnd) {
		switch(op) {
			case "-": return menos(opnd);
			case "not": return not(opnd);
			default: throw new UnsupportedOperationException("Bad op");
		}
	}

}