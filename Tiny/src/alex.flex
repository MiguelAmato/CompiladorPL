package model.lexico;

%%
%line
%column
%class AnalizadorLexico
%type UnidadLexica
%unicode

%{
	public String getLexema() {return yytext();}
	public int getFila() {return yyline+1;}
	public int getColumna() {return yycolumn+1;}	
%}

%eofval{
	return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.EOF);
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
exponencial = (e|E)[\+,\-]?{parteEntera}

identificador = {letra}({letra}|{digito})*
literalEntero = [\+,\-]?{parteEntera}
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
comentario = ##([^\n])*
%%
{int}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.INT); }
{real}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.REAL); }
{bool}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.BOOL); }
{string}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.STRING); }
{and}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.AND); }
{or}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.OR); }
{not}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.NOT); }
{null}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.NULL); }
{true}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.TRUE); }
{false}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.FALSE); }
{proc}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.PROC); }
{if}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.IF); }
{else}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.ELSE); }
{while}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.WHILE); }
{struct}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.STRUCT); }
{new}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.NEW); }
{delete}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.DELETE); }
{read}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.READ); }
{write}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.WRITE); }
{nl}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.NL); }
{type}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.TYPE); }
{call}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.CALL); }
{suma}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.SUMA); }
{resta}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.RESTA); }
{mul}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.MULTIPLICACION); }
{div}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.DIVISION); }
{mod}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.MODULO); }
{parAp}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.PARENTESIS_APERTURA); }
{parCierre}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.PARENTESIS_CIERRE); }
{asig}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.ASIG); }
{coma}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.COMA); }
{PyComa}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.PUNTO_Y_COMA); }
{corAp}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.CORCHETE_APERTURA); }
{corCierre}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.CORCHETE_CIERRE); }
{punto}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.PUNTO); }
{puntero}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.PUNTERO); }
{llaveAp}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.LLAVE_APERTURA); }
{llaveCierre}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.LLAVE_CIERRE); }
{referencia}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.REFERENCIA); }
{cambioSec}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.CAMBIO_DE_SECUENCIA); }
{Eval}  { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.EVAL); }
{menor} { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.MENOR); }
{mayor} { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.MAYOR); }
{menorIgual} { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.MENOR_O_IGUAL); }
{mayorIgual} { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.MAYOR_O_IGUAL); }
{igualdad} { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.IGUALDAD); }
{distinto} { return new UnidadLexicaUnivaluada(getFila(),getColumna(),ClaseLexica.DISTINTO); }
{identificador} { return new UnidadLexicaMultivaluada(getFila(),getColumna(),ClaseLexica.IDENTIFICADOR,getLexema()); }
{literalEntero}  { return new UnidadLexicaMultivaluada(getFila(),getColumna(),ClaseLexica.LITERAL_ENTERO,getLexema()); }
{literalReal}  { return new UnidadLexicaMultivaluada(getFila(),getColumna(),ClaseLexica.LITERAL_REAL,getLexema()); }
{literalCadena}  { return new UnidadLexicaMultivaluada(getFila(),getColumna(),ClaseLexica.LITERAL_CADENA,getLexema()); }
{separador} {}
{comentario} {}
[^] {UnidadLexicaUnivaluada.error(getFila(), getColumna());}
