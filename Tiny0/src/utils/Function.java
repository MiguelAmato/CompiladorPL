package utils;

import java.io.IOException;

import model.lexico.UnidadLexica;

public interface Function
{
    public UnidadLexica apply() throws IOException;
}
