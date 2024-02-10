package model.lexico;

public enum ClaseLexica {
    INT("<int>"),
    REAL("<real>"),
    BOOL("<bool>"),
    STRING("<string>"),
    AND("<and>"),
    OR("<or>"),
    NOT("<not>"),
    NULL("<null>"),
    TRUE("<true>"),
    FALSE("<false>"),
    PROC("<proc>"),
    IF("<if>"),
    ELSE("<else>"),
    WHILE("<while>"),
    STRUCT("<struct>"),
    NEW("<new>"),
    DELETE("<delete>"),
    READ("<read>"),
    WRITE("<write>"),
    NL("<nl>"),
    TYPE("<type>"),
    CALL("<call>"),
    SUMA("+"),
    RESTA("-"),
    MULTIPLICACION("+"),
    DIVISION("/"),
    MODULO("%"),
    PARENTESIS_APERTURA("("),
    PARENTESIS_CIERRE(")"),
    ASIG("="),
    COMA(","),
    PUNTO_Y_COMA(";"),
    CORCHETE_APERTURA("["),
    CORCHETE_CIERRE("]"),
    PUNTO("."),
    PUNTERO("^"),
    LLAVE_APERTURA("{"),
    LLAVE_CIERRE("}"),
    REFERENCIA("&"),
    CAMBIO_DE_SECUENCIA("&&"),
    EVAL("@"),
	MENOR("<"),
    MAYOR(">"),
    MENOR_O_IGUAL("<="),
    MAYOR_O_IGUAL(">="),
    IGUALDAD("=="),
    DISTINTO("!="),
    IDENTIFICADOR,
    LITERAL_ENTERO,
    LITERAL_REAL,
    LITERAL_CADENA,
	ERROR,
	EOF;
    
    private String image;

    public String getImage() {
        return image;
    }

    private ClaseLexica() {
        image = toString();
    }

    private ClaseLexica(String image) {
        this.image = image;
    }
}