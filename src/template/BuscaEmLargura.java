package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import java.util.ArrayList;

/**
 * Busca em Grafos
 * 
 * @author Rodrigo C. Garcia e Davi Beli Rosa
 */
public class BuscaEmLargura extends EngineFrame {
    
    private Image logo;
    private Grafo grafo;
    int raio;
    
    public BuscaEmLargura() {
        
        super(
            1000,                 // largura                      / width
            600,                 // algura                       / height
            "Busca em Largura",      // título                       / title
            60,                  // quadros por segundo desejado / target FPS
            true,                // suavização                   / antialiasing
            false,               // redimensionável              / resizable
            false,               // tela cheia                   / full screen
            false,               // sem decoração                / undecorated
            false,               // sempre no topo               / always on top
            false                // fundo invisível              / invisible background
        );
        
    }
    
    @Override
    public void create() {
        //Inicializando o grafo com 5 vertices
        grafo = new Grafo(5);
        raio = 30;
        grafo.adicionarAresta(0, 1);
        grafo.adicionarAresta(0, 4);
        grafo.adicionarAresta(1, 2);
        grafo.adicionarAresta(1, 3);
        grafo.adicionarAresta(1, 4);
        grafo.adicionarAresta(2, 3);
        grafo.adicionarAresta(3, 4);
        
        
    }


    @Override
    public void update( double delta ) {
    }
    

    @Override
    public void draw() {
        
        clearBackground( WHITE );
        ArrayList<Integer> vizinhosDoUm = grafo.listaAdj.get(1);
        
        if(!vizinhosDoUm.isEmpty()){
            for(int i = 0; i < vizinhosDoUm.size(); i++){
                desenharVertice(vizinhosDoUm.get(i), 100, 100 + (i * 100));
            }
        }

    
    }
    
    public static void main( String[] args ) {
        new BuscaEmLargura();
    }
    
    public void desenharVertice(int vertice, int x, int y){
        fillCircle(x, y , raio, WHITE );
        drawCircle(x, y, raio, BLACK );
        drawText(Integer.toString(vertice),x, y, 20, BLACK);
    }
    
}
