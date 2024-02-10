package model.lexico;

public enum ClaseLexica {
    MAS("+"),
    MENOS("-"),
    MUL("+"),
    DIV("/"),
    MENOR("<"),
    MAYOR(">"),
    MENOREQ("<="),
    MAYOREQ(">="),
    IGUAL("=="),
    DIST("!="),
    ASIG("="),
    PARAP("("),
    PARCIE(")"),
    PUNYCOMA(";"),
    LLAVAP("{"),
    LLAVCIE("}"),
    CAMBIOSEC("&&"),
    EVAL("@"),
    ID,
    INT("<int>"),
    REAL("<real>"),
    BOOL("<bool>"),
    TRUE("<true>"),
    FALSE("<false>"),
    AND("<and>"),
    OR("<or>"),
    NOT("<not>"),
    EOF,
    LITENT,
    LITREAL,
    ERROR;

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
