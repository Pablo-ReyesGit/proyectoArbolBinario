package ejemplo_arboles;

import java.io.FileWriter;
import java.io.PrintWriter;



class Arbol {
Nodo raiz;
private int posicion;
private StringBuilder ordenProvisional;
private String Recorrido;
        

public Arbol() {
    this.raiz = null;
}

public int NodosCompletos(Nodo n){
    if(n == null){
        return 0;
    } else {
        if (n.getIzquierda() != null && n.getDerecha() != null) 
            return NodosCompletos(n.getIzquierda()) + NodosCompletos(n.getDerecha()) + 1;
            return NodosCompletos(n.getDerecha()) + NodosCompletos(n.getIzquierda());  
    }
}

public String ObtenerCodigoGraphviz(){
    String texto = "digraph G\n"
            +"{\n"
            +"      node [shape = circle]\n"
            +"      node [style = filled]\n"
            +"      node [fillcolor = \"#EEEEE\"]\n"
            +"      node [color = \"#EEEEE\"]\n"
            +"      node [color = \"#31CEFO\"]\n";
    
    if (raiz != null) {
        texto += raiz.textoGraphviz();
    }
    texto += "\n}";
    
    return texto;   
}

public boolean existe(String busqueda) {
return existe(this.raiz, busqueda);
}

private boolean existe(Nodo n, String busqueda) {
if (n == null) {
return false;
}
if (n.getDato().equals(busqueda)) {
return true;
} else if (busqueda.compareTo(n.getDato()) < 0) {
    posicion =+ 1;
    Recorrido += "- L";
return existe(n.getIzquierda(), busqueda);
} else {
    posicion =+ 1;
    Recorrido += "- R";
return existe(n.getDerecha(), busqueda);   
}
}

    public void insertar(String dato) {
    if (this.raiz == null) {
    this.raiz = new Nodo(dato);
    } else {
    this.insertar(this.raiz, dato);
    }
    }

    private void insertar(Nodo padre, String dato) {
    if (dato.compareTo(padre.getDato()) > 0) {
    if (padre.getDerecha() == null) {
    padre.setDerecha(new Nodo(dato));
    } else {
    this.insertar(padre.getDerecha(), dato);
    }

    } else {
    if (padre.getIzquierda() == null) {
    padre.setIzquierda(new Nodo(dato));
    } else {
    this.insertar(padre.getIzquierda(), dato);
    }
    }
    }

private void preorden(Nodo n) {
if (n != null) {
ordenProvisional.append(n.dato).append("\n");
preorden(n.getIzquierda());
preorden(n.getDerecha());
}
}

private void inorden(Nodo n) {
if (n != null) {
inorden(n.getIzquierda());
ordenProvisional.append(n.dato).append("\n");
inorden(n.getDerecha());
}
}

private void postorden(Nodo n) {
if (n != null) {
postorden(n.getIzquierda());
postorden(n.getDerecha());
ordenProvisional.append(n.dato).append("\n");
}
}

public StringBuilder preorden() {
    ordenProvisional = new StringBuilder();
    this.preorden(this.raiz);
    return ordenProvisional;
}

public StringBuilder inorden() {
    ordenProvisional = new StringBuilder();
    this.inorden(this.raiz);
    return ordenProvisional;
}

public StringBuilder postorden() {
    ordenProvisional = new StringBuilder();
    this.postorden(this.raiz);
    return ordenProvisional;
}

public void vaciar() {
    raiz = null; // Elimina la raíz del árbol, lo que eventualmente eliminará todos los nodos
}

public String Recorrido(){
    return Recorrido;
    
}

public void graficar(String path) {
        raiz.graficar(path);
    }

public String buscar(String busqueda) {
    return buscar(this.raiz, busqueda);
}

private String buscar(Nodo n, String busqueda) {
    if (n == null) {
        return "";
    }
    if (n.getDato().equals(busqueda)) {
        return "";
    } else if (busqueda.compareTo(n.getDato()) < 0) {
        return "L-" + buscar(n.getIzquierda(), busqueda);
    } else {
        return "R-" + buscar(n.getDerecha(), busqueda);
    }
}

public void borrar(String dato) {
    this.raiz = borrar(this.raiz, dato);
}

private Nodo borrar(Nodo nodo, String dato) {
    if (nodo == null) {
        return null;
    }

    if (dato.compareTo(nodo.getDato()) < 0) {
        nodo.setIzquierda(borrar(nodo.getIzquierda(), dato));
    } else if (dato.compareTo(nodo.getDato()) > 0) {
        nodo.setDerecha(borrar(nodo.getDerecha(), dato));
    } else {
        // Caso 1: Nodo sin hijos o con un hijo
        if (nodo.getIzquierda() == null) {
            return nodo.getDerecha();
        } else if (nodo.getDerecha() == null) {
            return nodo.getIzquierda();
        }

        // Caso 2: Nodo con dos hijos
        Nodo sucesor = encontrarMinimo(nodo.getDerecha());
        nodo.setDato(sucesor.getDato());
        nodo.setDerecha(borrar(nodo.getDerecha(), sucesor.getDato()));
    }
    return nodo;
}

private Nodo encontrarMinimo(Nodo nodo) {
    while (nodo.getIzquierda() != null) {
        nodo = nodo.getIzquierda();
    }
    return nodo;
}
}

