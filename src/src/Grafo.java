package src;

/**
 * @author rodiz
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo {
    
    public class Vertice {
        
        private int valor;
        private int posX;
        private int posY;
        private int numVizinhos; 

        public Vertice(int valor, int posX, int posY) {
            this.valor = valor;
            this.posX = posX;
            this.posY = posY;
            this.numVizinhos = 0;
        }
        
        public int getValor() { return valor; }
        public int getPosX() { return posX; }
        public int getPosY() { return posY; }
        public int getNumVizinhos() { return numVizinhos; }
        public void incrementarVizinhos() { this.numVizinhos++; }
    }

    
    public ArrayList<Vertice> vertices;
    // Lista de adjacencia. O índice da lista externa corresponde ao valor do vértice
    public ArrayList<ArrayList<Integer>> listaAdj;
    public int tamanho;
    
    //construtor
    public Grafo() {
        this.vertices = new ArrayList<>();
        this.listaAdj = new ArrayList<>();
    }
    
    // Cria um novo vértice e o adiciona ao grafo
    public void adicionarVertice(int posX, int posY) {
        int novoValor = vertices.size(); // O valor será o índice atual (0, 1, 2...)
        Vertice novoV = new Vertice(novoValor, posX, posY);
        
        vertices.add(novoV);
        listaAdj.add(new ArrayList<>()); // Cria a lista de vizinhos para ele
    }
    
    // Adiciona aresta entre dois vértices pelos seus valores
    public void adicionarAresta(int valorOrigem, int valorDestino) {
        
        if (!existeVertice(valorOrigem) || !existeVertice(valorDestino)) {
            System.out.println("Erro: Vértice inválido.");
            return;
        }

        // Adiciona na lista de adjacência (Grafo não-direcionado)
        listaAdj.get(valorOrigem).add(valorDestino);
        listaAdj.get(valorDestino).add(valorOrigem);
        
        // Atualiza a contagem de vizinhos dentro do objeto Vértice
        vertices.get(valorOrigem).incrementarVizinhos();
        vertices.get(valorDestino).incrementarVizinhos();
    }
    
    public Vertice getVertice(int valor) {
        if (existeVertice(valor)) {
            return vertices.get(valor);
        }
        return null;
    }
    
    public ArrayList<Vertice> getTodosVertices() {
        return vertices;
    }
    
    public ArrayList<Integer> getVizinhos(int valor) {
        return listaAdj.get(valor);
    }
    
    private boolean existeVertice(int valor) {
        return valor >= 0 && valor < vertices.size();
    }
    
    
}
