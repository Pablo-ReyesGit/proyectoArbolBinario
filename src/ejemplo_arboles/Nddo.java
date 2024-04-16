package ejemplo_arboles;

import java.io.FileWriter;
import java.io.PrintWriter;

class Nodo {
     private static int contadorIds = 0;
String dato;
private Nodo izquierda, derecha;
int id;

public Nodo(String dato) {
this.dato = dato;
this.izquierda = this.derecha = null;
this.id = contadorIds++;    
}

public String getDato() {
return dato;
}

public Nodo getIzquierda() {
return izquierda;
}

public void setIzquierda(Nodo izquierda) {
this.izquierda = izquierda;
}

public Nodo getDerecha() {
return derecha;
}

public void setDato(String Dato) {
this.dato = Dato;
}

public void setDerecha(Nodo derecha) {
this.derecha = derecha;
}

public void imprimirDato() {
System.out.println(this.getDato());
}

public String textoGraphviz(){
    if(izquierda != null && derecha != null){
        return String.valueOf(dato);
    }else {
        String texto = "";
        if(izquierda != null){
            texto = dato + " " + izquierda.textoGraphviz()+"\n";
        }
        if (derecha != null) {
            texto += texto +" "+ derecha.textoGraphviz()+"\n";
        }
        return texto;
    }
}
 public void graficar(String path) {
        FileWriter fichero = null;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("aux_grafico.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoGraphviz());
        } 
        catch (Exception e){
            System.err.println("Error al escribir el archivo aux_grafico.dot");
        }finally{
           try {
                if (null != fichero)
                    fichero.close();
           }catch (Exception e2){
               System.err.println("Error al cerrar el archivo aux_grafico.dot");
           } 
        }                        
        try{
          Runtime rt = Runtime.getRuntime();
          rt.exec( "dot -Tjpg -o "+path+" aux_grafico.dot");
          //Esperamos medio segundo para dar tiempo a que la imagen se genere.
          //Para que no sucedan errores en caso de que se decidan graficar varios
          //árboles sucesivamente.
          Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
        }            
    }
    /**
     * Método que retorna el código que grapviz usará para generar la imagen 
     * del árbol binario de búsqueda.
     * @return 
     */
    private String getCodigoGraphviz() {
        return "digraph grafica{\n" +
               "rankdir=TB;\n" +
               "node [shape = record, style=filled, fillcolor=gray100];\n"+
                getCodigoInterno()+
                "}\n";
    }
    /**
     * Genera el código interior de graphviz, este método tiene la particularidad 
     * de ser recursivo, esto porque recorrer un árbol de forma recursiva es bastante 
     * sencillo y reduce el código considerablemente. 
     * @return 
     */
    private String getCodigoInterno() {
    String etiqueta;
    if(izquierda == null && derecha == null) {
        etiqueta = "nodo" + id + " [ label =\"" + dato + "\"];\n";
    } else {
        etiqueta = "nodo" + id + " [ label =\"<C0>|"+dato+"|<C1>\"];\n";
    }
    if(izquierda != null) {
        etiqueta += izquierda.getCodigoInterno() +
           "nodo" + id + ":C0->nodo" + izquierda.id + ";\n";
    }
    if(derecha != null) {  // Cambiado a verificar 'derecha'
        etiqueta += derecha.getCodigoInterno() +
           "nodo" + id + ":C1->nodo" + derecha.id + ";\n";                    
    }
    return etiqueta;
}
}