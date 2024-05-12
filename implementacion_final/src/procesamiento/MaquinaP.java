package procesamiento;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import asint.SintaxisAbstractaEval.Tipo_int;
import asint.SintaxisAbstractaEval.Tipo_real;
import asint.SintaxisAbstractaEval.Tipo_string;

public class MaquinaP {
    public static class EAccesoIlegitimo extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    public static class EAccesoMemorianoInicializada extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public EAccesoMemorianoInicializada(int pc, int dir){
            super("instruccion:" + pc + " en la direccion " + dir);
        }
    }

    public static class EAccesoFueraDeRango extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    public interface Instruccion {
        void ejecuta();
    }
    
    private List<Instruccion> codigoP;
    private Stack<Valor> pilaEvaluacion;
    private Valor[] datos;
    private int pc;

    private GestorMemoriaDinamica gestorMemoriaDinamica;
    private GestorPilaActivaciones gestorPilaActivaciones;

    private class Valor {
        public int valorInt() {throw new EAccesoIlegitimo();}  
        public boolean valorBool() {throw new EAccesoIlegitimo();} 
        public String valorString() {throw new EAccesoIlegitimo();}
        public double valorReal() {throw new EAccesoIlegitimo();}
    }

    private class ValorInt extends Valor {
        private int valor;
        public ValorInt(int valor) {
            this.valor = valor;
        }

        public int valorInt() {
            return valor;
        }

        public String toString() {
            return String.valueOf(valor);
        }
    }

    private class ValorBool extends Valor {
        private boolean valor;
        public ValorBool(boolean valor) {
            this.valor = valor;
        }
        
        public boolean valorBool() {
            return valor;
        }

        public String toString() {
            return String.valueOf(valor);
        }
    }

    private class ValorReal extends Valor {
        private double valor;
        public ValorReal(double valor) {
            this.valor = valor;
        }

        public double valorReal() {
            return valor;
        }

        public String toString() {
            return String.valueOf(valor);
        }
    }

    private class ValorString extends Valor {
        private String valor;
        public ValorString(String valor) {
            this.valor = valor;
        }

        public String valorString() {
            return valor;
        }

        public String toString() {
            if(valor.length() > 0 && valor.charAt(0) == '"')
                return valor.substring(1, valor.length() - 1);
            else 
                return valor;
        }
    }

    private class ISuma implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if(opnd1 instanceof ValorReal && opnd2 instanceof ValorReal)
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() + opnd2.valorReal()));
            else 
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() + opnd2.valorInt()));
            pc++;
        }
        public String toString() {
            return "suma";
        }
    }

    private ISuma ISUMA;

    private class IResta implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          if (opnd1.getClass() == ValorReal.class || opnd2.getClass() == ValorReal.class)
              pilaEvaluacion.push(new ValorReal(opnd1.valorReal() - opnd2.valorReal()));
          else
              pilaEvaluacion.push(new ValorInt(opnd1.valorInt() - opnd2.valorInt()));
          pc++;
       } 
       public String toString() {return "resta";};
    }

    private IResta IRESTA;

    private class IMul implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if(opnd1 instanceof ValorReal && opnd2 instanceof ValorReal)
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() * opnd2.valorReal()));
            else 
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() * opnd2.valorInt()));
            pc++;
        }
        public String toString() {
            return "mul";
        }
    }

    private IMul IMUL;

    private class IDiv implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if(opnd1 instanceof ValorReal && opnd2 instanceof ValorReal)
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() / opnd2.valorReal()));
            else 
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() / opnd2.valorInt()));
            pc++;
        }
        public String toString() {
            return "div";
        }
    }

    private IDiv IDIV;

    private class IAnd implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorBool(opnd1.valorBool() && opnd2.valorBool()));
            pc++;
        }
        public String toString() {
            return "and";
        }
    }

    private IAnd IAND;

    private class IOr implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorBool(opnd1.valorBool() || opnd2.valorBool()));
            pc++;
        }
        public String toString() {
            return "or";
        }
    }

    private IOr IOR;

    private class IMod implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          pilaEvaluacion.push(new ValorInt(opnd1.valorInt() % opnd2.valorInt()));
          pc++;
       } 
       public String toString() {return "mod";};
    } 

    private IMod IMOD;

    private class IApilaInt implements Instruccion {
        private int valor;
        public IApilaInt(int valor) {
          this.valor = valor;  
        }
        public void ejecuta() {
           pilaEvaluacion.push(new ValorInt(valor)); 
           pc++;
        } 
        public String toString() {return "apila-int("+valor+")";};
    }

    private class IApilaReal implements Instruccion {
        private double valor;
        public IApilaReal(double valor) {
          this.valor = valor;  
        }
        public void ejecuta() {
           pilaEvaluacion.push(new ValorReal(valor)); 
           pc++;
        } 
        public String toString() {return "apila-real("+valor+")";};
    }

    private class IApilaString implements Instruccion {
        private String valor;
        public IApilaString(String valor) {
          this.valor = valor;  
        }
        public void ejecuta() {
           pilaEvaluacion.push(new ValorString(valor)); 
           pc++;
        } 
        public String toString() {return "apila-string("+valor+")";};
    }

    private class IApilaBool implements Instruccion {
        private boolean valor;
        public IApilaBool(boolean valor) {
          this.valor = valor;  
        }
        public void ejecuta() {
           pilaEvaluacion.push(new ValorBool(valor)); 
           pc++;
        } 
        public String toString() {return "apila-bool("+valor+")";};
    }

    private class IIrA implements Instruccion {
        private int dir;
        public IIrA(int dir) {
            this.dir = dir;
        }
        public void ejecuta() {
            pc = dir;
        }
        public String toString() {
            return "ir-a(" + dir + ")";
        }
    }

    private class IIrF implements Instruccion {
        private int dir;
        public IIrF(int dir) {
            this.dir = dir;
        }
        public void ejecuta() {
            if(! pilaEvaluacion.pop().valorBool())
                pc = dir;
            else
                pc++;
        }
        public String toString() {
            return "ir-f(" + dir + ")";
        }
    }

    private class ICopia implements Instruccion {
        private int n;
        public ICopia(int n) {
          this.n = n;  
        }
        public void ejecuta() {
              int origen = pilaEvaluacion.pop().valorInt();
              int destino = pilaEvaluacion.pop().valorInt();
              if ((origen + (n-1)) >= datos.length)
                  throw new EAccesoFueraDeRango();
              if ((destino + (n-1)) >= datos.length)
                  throw new EAccesoFueraDeRango();
              for (int i=0; i < n; i++) 
                  datos[destino+i] = datos[origen+i]; 
              pc++;
        } 
        public String toString() {return "copia("+n+")";};
    }    

    private class IAlloc implements Instruccion {
        private int n;

        public IAlloc(int n) {
          this.n = n;  
        }

        public void ejecuta() {
          int inicio = gestorMemoriaDinamica.alloc(n);
          pilaEvaluacion.push(new ValorInt(inicio));
          pc++;
        } 
        public String toString() {return "alloc("+n+")";};
    }

    private class IDealloc implements Instruccion {
        private int n;

        public IDealloc(int n) {
          this.n = n;  
        }

        public void ejecuta() {
          int inicio = pilaEvaluacion.pop().valorInt();
          gestorMemoriaDinamica.free(inicio, n);
          pc++;
        } 
        public String toString() {return "dealloc(" + n + ")";};
    }

    private class IActiva implements Instruccion {
        private int nivel;
        private int tDatos;
        private int dirretorno;
        public IActiva(int nivel, int tdatos, int dirretorno) {
            this.nivel = nivel;
            this.tDatos = tdatos;
            this.dirretorno = dirretorno;
        }
        public void ejecuta() {
           int base = gestorPilaActivaciones.creaRegistroActivacion(tDatos);
           datos[base] = new ValorInt(dirretorno);
           datos[base+1] = new ValorInt(gestorPilaActivaciones.display(nivel));
           pilaEvaluacion.push(new ValorInt(base+2));
           pc++;
        }
        public String toString() {
           return "activa("+nivel+","+tDatos+","+dirretorno+")";                 
        }
    }

    private class IDesactiva implements Instruccion {
        private int nivel;
        private int tDatos;

        public IDesactiva(int nivel, int tDatos) {
            this.nivel = nivel;
            this.tDatos = tDatos;
        }

        public void ejecuta() {
           int base = gestorPilaActivaciones.liberaRegistroActivacion(tDatos);
           gestorPilaActivaciones.fijaDisplay(nivel,datos[base+1].valorInt());
           pilaEvaluacion.push(datos[base]); 
           pc++;
        }

        public String toString() {
           return "desactiva("+nivel+","+tDatos+")";                 
        }
    }

    private class IDesapilad implements Instruccion {
        private int nivel;
        public IDesapilad(int nivel) {
          this.nivel = nivel;  
        }
        public void ejecuta() {
          gestorPilaActivaciones.fijaDisplay(nivel,pilaEvaluacion.pop().valorInt());  
          pc++;
        }
        public String toString() {
           return "desapilad("+nivel+")";                 
        }
    }

    private class IDup implements Instruccion {
        public void ejecuta() {
            pilaEvaluacion.push(pilaEvaluacion.peek());
            pc++;
        }
        public String toString() {
           return "dup";                 
        }
    }
    private IDup IDUP;

    private Instruccion ISTOP;
    private class IStop implements Instruccion {
        public void ejecuta() {
            pc = codigoP.size();
        }
        public String toString() {
           return "stop";                 
        }
    }

    private class IApilaDisp implements Instruccion {
        private int nivel;
        public IApilaDisp(int nivel) {
          this.nivel = nivel;  
        }
        public void ejecuta() {
          pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
          pc++;
        }
        public String toString() {
           return "apilad("+nivel+")";
        }
 
    }


   private class IIrind implements Instruccion {
       public void ejecuta() {
          pc = pilaEvaluacion.pop().valorInt();  
       }
       public String toString() {
          return "ir-ind";                 
       }
   }
   private Instruccion IIRD;
   
   private class IDesapila implements Instruccion {
	   public void ejecuta() {
          pilaEvaluacion.pop();
          pc++;
       }
       public String toString() {
          return "desapila";                 
       }
   }
   private Instruccion DESAPILA;

    private class IIgual implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          if (opnd1.getClass() == ValorBool.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorBool() == opnd2.valorBool()));
          else if (opnd1.getClass() == ValorString.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorString().equals(opnd2.valorString())));
          else if (opnd1.getClass() == ValorInt.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorInt() == opnd2.valorInt()));
          else
              pilaEvaluacion.push(new ValorBool(opnd1.valorReal() == opnd2.valorReal()));
          pc++;
       } 
       public String toString() {return "igual";};
    }

    private IIgual IIGUAL;

    private class IDesigual implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          if (opnd1.getClass() == ValorBool.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorBool() != opnd2.valorBool()));
          else if (opnd1.getClass() == ValorString.class)
              pilaEvaluacion.push(new ValorBool(!opnd1.valorString().equals(opnd2.valorString())));
          else if (opnd1.getClass() == ValorInt.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorInt() != opnd2.valorInt()));
          else
              pilaEvaluacion.push(new ValorBool(opnd1.valorReal() != opnd2.valorReal()));
          pc++;
       } 
       public String toString() {return "resta";};
    }

    private IDesigual IDESIGUAL;

    private class IMayor implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          if (opnd1.getClass() == ValorReal.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorReal() > opnd2.valorReal()));
          else if (opnd1.getClass() == ValorInt.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));
          else if (opnd1.getClass() == ValorString.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareToIgnoreCase(opnd2.valorString()) > 0));
          else if (opnd1.getClass() == ValorBool.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorBool() && !opnd2.valorBool()));
          pc++;
       } 
       public String toString() {return "mayor";};
    }
    private IMayor IMAYOR;

    private class IMenor implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          if (opnd1.getClass() == ValorReal.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorReal() < opnd2.valorReal()));
          else if (opnd1.getClass() == ValorInt.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));
          else if (opnd1.getClass() == ValorString.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareToIgnoreCase(opnd2.valorString()) < 0));
          else if (opnd1.getClass() == ValorBool.class)
              pilaEvaluacion.push(new ValorBool(!opnd1.valorBool() && opnd2.valorBool()));
          pc++;
       } 
       public String toString() {return "menor";};
    }
    private IMenor IMENOR;

    private class IMayorIgual implements Instruccion {
       public void ejecuta() {
          Valor opnd2 = pilaEvaluacion.pop(); 
          Valor opnd1 = pilaEvaluacion.pop();
          if (opnd1.getClass() == ValorReal.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorReal() >= opnd2.valorReal()));
          else if (opnd1.getClass() == ValorInt.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorInt() >= opnd2.valorInt()));
          else if (opnd1.getClass() == ValorString.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareToIgnoreCase(opnd2.valorString()) >= 0));
          else if (opnd1.getClass() == ValorBool.class)
              pilaEvaluacion.push(new ValorBool(opnd1.valorBool() || !opnd2.valorBool()));
          pc++;
       } 
       public String toString() {return "mayorIgual";};
    }
    private IMayorIgual IMAYORIGUAL;

    private IMenorIgual IMENORIGUAL;
   private class IMenorIgual implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         if (opnd1.getClass() == ValorReal.class)
        	 pilaEvaluacion.push(new ValorBool(opnd1.valorReal() <= opnd2.valorReal()));
         else if (opnd1.getClass() == ValorInt.class)
        	 pilaEvaluacion.push(new ValorBool(opnd1.valorInt() <= opnd2.valorInt()));
         else if (opnd1.getClass() == ValorString.class)
        	 pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareToIgnoreCase(opnd2.valorString()) <= 0));
         else if (opnd1.getClass() == ValorBool.class)
        	 pilaEvaluacion.push(new ValorBool(!opnd1.valorBool() || opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "menorIgual";};
   }

   private INot INOT;
   private class INot implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(!opnd1.valorBool()));
         pc++;
      } 
      public String toString() {return "not";};
   }

   private INeg IMENOS;
   private class INeg implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop();
         if (opnd1.getClass() == ValorReal.class)
        	 pilaEvaluacion.push(new ValorReal(-opnd1.valorReal()));
         else
        	 pilaEvaluacion.push(new ValorInt(-opnd1.valorInt()));
         pc++;
      } 
      public String toString() {return "menosUnario";};
   }

   private class IRead implements Instruccion {
	   Class<?> clase;
	   public IRead(Class<?> clase) {
		   this.clase = clase;
	   }
      public void ejecuta() {
         if (clase == Tipo_string.class)
        	 pilaEvaluacion.push(new ValorString(in.nextLine()));
         else if (clase == Tipo_int.class) {
        	 pilaEvaluacion.push(new ValorInt(in.nextInt()));
        	 in.nextLine();
         }
         else if (clase == Tipo_real.class) {
        	 pilaEvaluacion.push(new ValorReal(in.nextDouble()));
        	 in.nextLine();
         }
         pc++;
      } 
      public String toString() {return "read";};
   }

   private IWrite IWRITE;
   private class IWrite implements Instruccion {
      public void ejecuta() {
         Valor val = pilaEvaluacion.pop();
         System.out.print(val.toString());
         pc++;
      } 
      public String toString() {return "write";};
   }

   public Instruccion desapila() {return DESAPILA;}
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaReal(double val) {return new IApilaReal(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilaString(String val) {return new IApilaString(val);}
   public Instruccion copia(int tam) {return new ICopia(tam);}
   public Instruccion suma() {return ISUMA;}
   public Instruccion resta() {return IRESTA;}
   public Instruccion mul() {return IMUL;}
   public Instruccion div() {return IDIV;}
   public Instruccion mod() {return IMOD;}
   public Instruccion and() {return IAND;}
   public Instruccion or() {return IOR;}
   public Instruccion mayor() {return IMAYOR;}
   public Instruccion menor() {return IMENOR;}
   public Instruccion mayorIgual() {return IMAYORIGUAL;}
   public Instruccion menorIgual() {return IMENORIGUAL;}
   public Instruccion igual() {return IIGUAL;}
   public Instruccion desigual() {return IDESIGUAL;}
   public Instruccion not() {return INOT;}
   public Instruccion menosUnario() {return IMENOS;}
   public Instruccion alloc(int tam) {return new IAlloc(tam);} 
   public Instruccion dealloc(int tam) {return new IDealloc(tam);} 
   public Instruccion irA(int dir) {return new IIrA(dir);}
   public Instruccion irF(int dir) {return new IIrF(dir);}
   public Instruccion irD() {return IIRD;}
   public Instruccion activa(int nivel,int tam, int dirretorno) {return new IActiva(nivel,tam,dirretorno);}
   public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel,tam);}
   public Instruccion apilaDisp(int nivel) {return new IApilaDisp(nivel);}
   public Instruccion desapilaDisp(int nivel) {return new IDesapilad(nivel);}
   public Instruccion dup() {return IDUP;}
   public Instruccion stop() {return ISTOP;}
   public Instruccion read(Class<?> c) {return new IRead(c);}
   public Instruccion write() {return IWRITE;}
   public void emit(Instruccion i) {
      codigoP.add(i); 
   }

   private int tdatos;
   private int theap;
   private int numDisplays;
   private Scanner in;

   public MaquinaP(Reader in, int tdatos, int tampila, int theap, int numDisplays) {
      this.tdatos = tdatos;
      this.theap = theap;
      this.numDisplays = numDisplays;
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tdatos+tampila+theap];
      this.pc = 0;
      ISUMA = new ISuma();
      IAND = new IAnd();
      IMUL = new IMul();
      IIRD = new IIrind();
      IDUP = new IDup();
      ISTOP = new IStop();
      DESAPILA = new IDesapila();
      IRESTA = new IResta();
      IDIV = new IDiv();
      IMOD = new IMod();
      IOR = new IOr();
      IMAYOR = new IMayor();
      IMENOR = new IMenor();
      IMENORIGUAL = new IMenorIgual();
      IMAYORIGUAL = new IMayorIgual();
      IIGUAL = new IIgual();
      IDESIGUAL = new IDesigual();
      INOT = new INot();
      IMENOS = new INeg();
      IWRITE = new IWrite();
      gestorPilaActivaciones = new GestorPilaActivaciones(tdatos,(tdatos+tampila)-1,numDisplays); 
      gestorMemoriaDinamica = new GestorMemoriaDinamica(tdatos+tampila,(tdatos+tampila+theap)-1);
      this.in = new Scanner(in);
   }

   public void ejecuta() {
    int i = 0;
    while(pc != codigoP.size()) {
        if (i >= 1113)
        codigoP.get(pc).ejecuta();
        i++;
    } 
}
public void muestraCodigo() {
  System.out.println("CodigoP");
  for(int i=0; i < codigoP.size(); i++) {
     System.out.println(" "+i+":"+codigoP.get(i));
  }
}
public void muestraEstado() {

    System.out.println("Tamano de los datos:"+tdatos);  
    System.out.println("Tamano del heap:"+theap); 
    System.out.println("PP:"+gestorPilaActivaciones.pp());      
    System.out.print("Displays:");

    for (int i=1; i <= numDisplays; i++) {
        System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
        }

    System.out.println();
    System.out.println("Pila de evaluacion");

    for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
        }
    
    System.out.println("Datos");

    for(int i=0; i < datos.length; i++) {
     System.out.println(" "+i+":"+datos[i]);
    }

    System.out.println("PC:"+pc);
}



     
}
