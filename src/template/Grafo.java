package template;

/**
 * @author rodiz
 */

import java.util.ArrayList;

public class Grafo {
    
    private int numVertices;
    //Array list de array list: lista de adjacencias
    public ArrayList<ArrayList<Integer>> listaAdj;
    
    //Construtor
    public Grafo(int numVertices){
        this.numVertices = numVertices;
        this.listaAdj = new ArrayList<>(numVertices);
        
        //Lista vazia para cada vertice
        for(int i = 0; i < numVertices; i++){
            this.listaAdj.add(new ArrayList<>());
        }
    }
    
    public void adicionarAresta(int origem, int destino){
        listaAdj.get(origem).add(destino);
        listaAdj.get(destino).add(origem);
        
    }
}
