package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import template.Grafo.Vertice;

/**
 * Busca em Grafos
 * 
 * @author Rodrigo C. Garcia e Davi Beli Rosa
 */
public class BuscaEmLargura extends EngineFrame {
     
    private Grafo grafoPequeno;
    private Grafo grafoGrande;
    private Grafo grafoAtual;
    
    private GuiButton btnGrafoPequeno;
    private GuiButton btnGrafoGrande;
    
    //Essas variaveis servem para avisar o metodo draw so de algo que aconteceu no update
    private boolean desenhar;
    private boolean buscar;
    
    int valorInicio;
    int raio;
    int raioGrande;
    
    private ArrayList<Vertice> visitados;

    
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
        btnGrafoPequeno = new GuiButton(150, 30, 200, 50, "Grafo Pequeno", this);
        btnGrafoGrande = new GuiButton(650, 30, 200, 50, "Grafo Grande", this);
        
        
        //Inicializando o grafo pequeno com 5 vertices
        grafoPequeno = new Grafo();
        
        grafoPequeno.adicionarVertice(300, 150);
        grafoPequeno.adicionarVertice(400, 350);
        grafoPequeno.adicionarVertice(500, 150);
        grafoPequeno.adicionarVertice(600, 300);
        grafoPequeno.adicionarVertice(500, 450);
        
        grafoPequeno.adicionarAresta(0, 1);
        grafoPequeno.adicionarAresta(0, 2);
        grafoPequeno.adicionarAresta(1, 2);
        grafoPequeno.adicionarAresta(2, 3);
        grafoPequeno.adicionarAresta(4, 3);
        grafoPequeno.adicionarAresta(4, 1);
        
        
        
    }


    @Override
    public void update( double delta ) {
        btnGrafoPequeno.update(delta);
        btnGrafoGrande.update(delta);
        
        if(btnGrafoPequeno.isMousePressed()){
            raio = 30;
            grafoAtual = grafoPequeno;
            desenhar = true;
        }
        
        if(isMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            if(verticeClicado(grafoAtual) != null){
                valorInicio = verticeClicado(grafoAtual).getValor();
                buscaEmLargura(grafoAtual, valorInicio);
                System.out.println("Vertice: " + verticeClicado(grafoAtual).getValor());
            }
        }
    }
    

    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        btnGrafoPequeno.draw();
        btnGrafoGrande.draw();
        
        drawText("Clique em um vértice para iniciar a busca", 320, 10, 15, BLACK);
        
        if(desenhar){
            desenharGrafo(grafoAtual, raio);
        }
        
        if(buscar){
            
        }
        
       
        

    
    }
    
    public static void main( String[] args ) {
        new BuscaEmLargura();
    }
    
    public void desenharVertice(int valor, int x, int y, int raio, Color cor){
        fillCircle( x, y, raio, WHITE );
        drawCircle( x, y,  raio, cor );
        drawText( Integer.toString(valor), x - 5, y - 5, raio - 5, BLACK );
    }
    
    public void desenharAresta(int x1, int y1, int x2, int y2, int raio, Color cor){
        //Código da internet, mt complicado nao compensava perder tempo
        
        //calcula a hipotenusa
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        
        //evita divisao por 0 ou distancia mt perto
        if(distancia == 0 || distancia <= 2 * raio) return;
        
        //calculo de deslocamento
        double deslocamentoX = (dx / distancia) * raio;
        double deslocamentoY = (dy / distancia) * raio;
               
        //calcula novos pontos
        // Ponto inicial: avança a partir do centro 1
        int novoX1 = (int) (x1 + deslocamentoX);
        int novoY1 = (int) (y1 + deslocamentoY);

        // Ponto final: recua a partir do centro 2
        int novoX2 = (int) (x2 - deslocamentoX);
        int novoY2 = (int) (y2 - deslocamentoY);
        
        //desenha a linha ajustada
        drawLine(novoX1, novoY1, novoX2, novoY2, cor);
        
    }    
    
    public void desenharGrafo(Grafo g, int raio){
        
        //Itera por todos os vértices
        for(Vertice v : g.getTodosVertices()){
            //desenha os vértices
            desenharVertice(v.getValor(), v.getPosX(), v.getPosY(), raio, BLACK);
            
            //desenha as arestas
            for(int valorVizinho : g.getVizinhos(v.getValor())){
                Vertice vizinho = g.getVertice(valorVizinho);
                
                desenharAresta(v.getPosX(), v.getPosY(), 
                        vizinho.getPosX(), vizinho.getPosY(), raio, BLACK);
            }
        }
        
    }
    

    //fazer uma lista de vertices visitados para desenhar depois
    public void buscaEmLargura(Grafo g, int valorInicio){
        int posX = g.getVertice(valorInicio).getPosX();
        int posY = g.getVertice(valorInicio).getPosY();
        boolean[] marked = new boolean[g.getTodosVertices().size()];
        
        
        Queue<Integer> fila = new LinkedList<>(); //Mudar para linked blocking queue se nao funfar
        fila.add(valorInicio);
        marked[valorInicio] = true;
        desenharVertice(valorInicio, posX, posY, raio - 5, GREEN);
        System.out.println(valorInicio + " visitado"); //debug
        
        while(!fila.isEmpty()){
            int v = fila.poll();
            System.out.println("Olhando vizinhos de " + v);
            
            for(int w : g.listaAdj.get(v)){
                //animarCaminho(g.getVertice(v), g.getVertice(w));
                posX = g.getVertice(w).getPosX();
                posY = g.getVertice(w).getPosY();
                
                if( !marked[w] ){
                    System.out.println(w + ", vizinho de " + v + " ainda nao foi visitado");
                    
                    fila.add(w);
                    marked[w] = true;
                    
                    desenharVertice(w, posX, posY, raio - 5, GREEN);
                    

                } else{
                    System.out.println(w + " ja foi visitado");
                }
            }
        }       
    }
    
    public void animarCaminho(Vertice origem, Vertice destino){
        
        
    }
    
    
    //Retorna se um vertice foi clicado
    public Vertice verticeClicado(Grafo g){
        //Esse metodo vai trapacear, vai ser um quadrado em volta do vertice
        int mouseX = getMouseX();
        int mouseY = getMouseY();
        
        for(Vertice v : g.getTodosVertices()){
            int x = v.getPosX() - raio;
            int y = v.getPosY() - raio;
            int largura = raio * 2;
            int altura = raio * 2;
            
            if(
                mouseX >= x &&
                mouseX <= x + largura &&
                mouseY >= y &&
                mouseY <= y + altura  
            ){ 
                return v; 
            }          
        }
        return null;
    }
    

}

