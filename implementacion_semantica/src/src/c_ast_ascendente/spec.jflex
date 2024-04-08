package c_ast_ascendente;

%%
%line
%column
%class AnalizadorLexico
%public
%type UnidadLexica
%unicode
%cup

%{
	private GestionErroresEval errores;
	public String getLexema() {return yytext();}
	public int getFila() {return yyline+1;}
	public int getColumna() {return yycolumn+1;}	
	public void fijaGestionErrores(GestionErroresEval errores) { this.errores = errores; }
%}

%eofval{
	return new UnidadLexica(getFila(),getColumna(),ClaseLexica.EOF, "<EOF>");
%eofval}

%init{
%init}

letra = ([A-Z]|[a-z]|_)
digitoPositivo = [1-9]
digito = {digitoPositivo}|0
parteEntera = ({digitoPositivo}{digito}*|0)
parteDecimal = ({digito}*{digitoPositivo}|0)
and = (a|A)(n|N)(d|D)
or = (o|O)(r|R)
not = (n|N)(o|O)(t|T)
int = (i|I)(n|N)(t|T)
real = (r|R)(e|E)(a|A)(l|L)
bool = (b|B)(o|O)(o|O)(l|L)
string = (s|S)(t|T)(r|R)(i|I)(n|N)(g|G)
null = (n|N)(u|U)(l|L)(l|L)
false = (f|F)(a|A)(l|L)(s|S)(e|E)
true = (t|T)(r|R)(u|U)(e|E)
proc = (p|P)(r|R)(o|O)(c|C)
if = (i|I)(f|F)
else =  (e|E)(l|L)(s|S)(e|E)
while = (w|W)(h|H)(i|I)(l|L)(e|E)
struct = (s|S)(t|T)(r|R)(u|U)(c|C)(t|T)
new = (n|N)(e|E)(w|W)
delete = (d|D)(e|E)(l|L)(e|E)(t|T)(e|E)
read = (r|R)(e|E)(a|A)(d|D)
write = (w|W)(r|R)(i|I)(t|T)(e|E)
nl = (n|N)(l|L)
type = (t|T)(y|Y)(p|P)(e|E) 
call = (c|C)(a|A)(l|L)(l|L)

decimal = \.{parteDecimal}
exponencial = (e|E)[\+\-]?{parteEntera}

identificador = {letra}({letra}|{digito})*
literalEntero = [\+\-]?{parteEntera}
literalReal = ({literalEntero}{decimal})|({literalEntero}{exponencial})|({literalEntero}{decimal}{exponencial})
literalCadena = \"[^\"]*\"

suma = \+
resta = \-
mul = \*
div = \/
mod = \%
parAp = \(
parCierre = \)
asig = \=
coma = \,
PyComa= \;
corAp = \[
corCierre = \]
punto = \.
puntero = \^
llaveAp = \{
llaveCierre = \}
referencia = \&
cambioSec = \&\&
Eval = \@
menor = \<
mayor = \>
menorIgual = \<\=
mayorIgual = \>\=
igualdad = \=\=
distinto = \!\=

separador = [ ,\t,\r,\b,\n]
comentario = \#\#([^\n])*
%%
{int}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.INT, "<int>"); }
{real}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.REAL, "<real>"); }
{bool}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.BOOL, "<bool>"); }
{string}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.STRING, "<string>"); }
{and}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.AND, "<and>"); }
{or}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.OR, "<or>"); }
{not}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.NOT, "<not>"); }
{null}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.NULL, "<null>"); }
{true}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.TRUE, "<true>"); }
{false}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.FALSE, "<false>"); }
{proc}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.PROC, "<proc>"); }
{if}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.IF, "<if>"); }
{else}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.ELSE, "<else>"); }
{while}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.WHILE, "<while>"); }
{struct}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.STRUCT, "<struct>"); }
{new}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.NEW, "<new>"); }
{delete}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.DELETE, "<delete>"); }
{read}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.READ, "<read>"); }
{write}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.WRITE, "<write>"); }
{nl}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.NL, "<nl>"); }
{type}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.TYPE, "<type>"); }
{call}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.CALL, "<call>"); }
{suma}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.SUMA, "+"); }
{resta}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.RESTA, "-"); }
{mul}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.MULTIPLICACION, "*"); }
{div}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.DIVISION, "/"); }
{mod}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.MODULO, "%"); }
{parAp}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.PARENTESIS_APERTURA, "("); }
{parCierre}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.PARENTESIS_CIERRE, ")"); }
{asig}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.ASIG, "="); }
{coma}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.COMA, ","); }
{PyComa}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.PUNTO_Y_COMA, ";"); }
{corAp}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.CORCHETE_APERTURA, "["); }
{corCierre}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.CORCHETE_CIERRE, "]"); }
{punto}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.PUNTO, "."); }
{puntero}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.PUNTERO, "^"); }
{llaveAp}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.LLAVE_APERTURA, "{"); }
{llaveCierre}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.LLAVE_CIERRE, "}"); }
{referencia}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.REFERENCIA, "&"); }
{cambioSec}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.CAMBIO_DE_SECUENCIA, "&&"); }
{Eval}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.EVAL, "@"); }
{menor} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.MENOR, "<"); }
{mayor} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.MAYOR, ">"); }
{menorIgual} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.MENOR_O_IGUAL, "<="); }
{mayorIgual} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.MAYOR_O_IGUAL, ">="); }
{igualdad} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.IGUALDAD, "=="); }
{distinto} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.DISTINTO, "!="); }
{identificador} { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.IDENTIFICADOR,getLexema()); }
{literalEntero}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.LITERAL_ENTERO,getLexema()); }
{literalReal}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.LITERAL_REAL,getLexema()); }
{literalCadena}  { return new UnidadLexica(getFila(),getColumna(),ClaseLexica.LITERAL_CADENA,getLexema()); }
{separador} {}
{comentario} {}
[^] {errores.errorLexico(getFila(), getColumna(), "0");}

