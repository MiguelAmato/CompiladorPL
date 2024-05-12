package procesamiento;

public class GestorMemoriaDinamica {
    private final static boolean DEBUG=false;
    private static class Hueco {
      private int origen;
      private int tam;
      private Hueco sig;
      public Hueco(int origen, int tam) {
         this.origen = origen;
         this.tam = tam;
         sig = null;
      }
    }
    private Hueco huecos;
    public GestorMemoriaDinamica(int origen, int fin) {
       huecos = new Hueco(origen,(fin-origen)+1);
       if (DEBUG) {
           System.out.print("COMIENZO:");
           muestraHuecos();
           System.out.println("----");
       }
    }
    public int alloc(int tam) {
      Hueco h = huecos;
      Hueco prev = null;
      while (h != null && h.tam < tam) {
          prev = h;
          h = h.sig;
      }    
      if (h==null) throw new OutOfMemoryError("alloc "+tam);
      int dir = h.origen;
      h.origen += tam;
      h.tam -= tam;
      if (h.tam == 0) {
         if (prev == null) huecos = h.sig;
         else prev.sig = h.sig;
      }
      if (DEBUG) {
          System.out.println("alloc("+tam+")="+dir);
          muestraHuecos();
          System.out.println("----");
    }
      return dir;
      }
    public void free(int dir, int tam) {
     Hueco h = huecos;
     Hueco prev = null;
     while (h != null && h.origen < dir) {
        prev = h;
        h = h.sig;
     }
     if (prev != null && prev.origen + prev.tam == dir) {
         prev.tam += tam;
         if (h != null && prev.origen + prev.tam == h.origen) {
             prev.tam += h.tam;
             prev.sig = h.sig;
         }
     }
     else if (h != null && dir + tam == h.origen) {
         h.origen = dir;
         h.tam += tam;
     }
     else {
        Hueco nuevo = new Hueco(dir,tam);
        nuevo.sig = h;
        if (prev==null) {
           huecos = nuevo;
        }
        else {
            prev.sig = nuevo;
        }
     }  
     if (DEBUG) {
         System.out.println("free("+dir+","+tam+")");  
         muestraHuecos();
         System.out.println("----");
     }
  }
  public void muestraHuecos() {
     Hueco h = huecos;
     while (h != null) {
       System.out.print("<"+h.origen+","+h.tam+","+(h.origen+h.tam-1)+">");
       h = h.sig;
     }
     System.out.println();
  }  
  
  public static void main(String[] args) {
      GestorMemoriaDinamica g = new GestorMemoriaDinamica(0,100);
      g.muestraHuecos();
      int a = g.alloc(1);
      g.muestraHuecos();
      int b = g.alloc(1);
      g.muestraHuecos();
      int c = g.alloc(1);
      g.muestraHuecos();
      int d = g.alloc(1);
      g.muestraHuecos();
      g.free(c, 1);
      g.muestraHuecos();
      c= g.alloc(1);
      g.muestraHuecos();
      g.free(b, 1);
      g.muestraHuecos();
      g.free(d, 1);
      g.muestraHuecos();
      g.free(a, 1);
      g.muestraHuecos();
      int e = g.alloc(10);
      g.muestraHuecos();
      g.free(c, 1);
      g.muestraHuecos();
      g.free(e, 10);
      g.muestraHuecos();
      int w = g.alloc(100);
      g.muestraHuecos();
      int z = g.alloc(1);
      g.muestraHuecos();
      g.free(z, 1);
      g.muestraHuecos();
      g.free(w,100);
      g.muestraHuecos();
  }
}    
         
             
      
     
     
     
     
     