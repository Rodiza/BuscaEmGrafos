package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import java.util.ArrayList;
import template.Grafo.Vertice;

/**
 * Busca em Grafos
 * 
 * @author Rodrigo C. Garcia e Davi Beli Rosa
 */
public class BuscaEmLargura extends EngineFrame {
    
    private Image logo;
    private Grafo grafoPequeno;
    private Grafo grafoGrande;
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
        grafoPequeno = new Grafo();
        raio = 30;
        
        grafoPequeno.adicionarVertice(100, 100);
        grafoPequeno.adicionarVertice(200, 150);
        grafoPequeno.adicionarVertice(300, 200);
        grafoPequeno.adicionarVertice(400, 250);
        
        grafoPequeno.adicionarAresta(0, 1);
        grafoPequeno.adicionarAresta(0, 2);
        grafoPequeno.adicionarAresta(1, 2);
        grafoPequeno.adicionarAresta(2, 3);
        
        
        
    }


    @Override
    public void update( double delta ) {
    }
    

    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        desenharGrafo(grafoPequeno);

    
    }
    
    public static void main( String[] args ) {
        new BuscaEmLargura();
    }
    
    public void desenharVertice(int valor, int x, int y){
        fillCircle( x, y, raio, WHITE );
        drawCircle( x, y,  raio, BLACK );
        drawText( Integer.toString(valor), x - 5, y - 5, BLACK );
    }
    
    public void desenharGrafo(Grafo g){
        
        //desenha todos os vértices com base nas posições dadas lá no create
        for(Vertice v : g.getTodosVertices()){
            desenharVertice(v.getValor(), v.getPosX(), v.getPosY());
        }
    }
}
