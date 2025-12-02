package src;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import src.Grafo.Vertice;

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
    
    //Essa variavel serve para avisar o metodo draw de algo que aconteceu no update
    private boolean desenhar;
    private boolean desenharCaminho;
    private boolean buscaIniciada = false;
    
    int valorInicio;
    int raio;
    Color corPadrao = BLACK;
    
    //Fila para armazenar o caminho da busca
    private Queue<EventoBusca> filaEventos;
    private EventoBusca eventoAtual;
    private List<EventoBusca> historicoEventos;
    
    //variaveis de animacao ( nao usei :( )
    private Vertice animOrigem = null; 
    private Vector2 animPonta = null;
    private boolean animando = false;
    private Line linha;
    
   
    

    
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
        
        filaEventos = new LinkedList<>();
        historicoEventos = new ArrayList<>();
        
        btnGrafoPequeno = new GuiButton(150, 30, 200, 50, "Grafo Pequeno", this);
        btnGrafoGrande = new GuiButton(650, 30, 200, 50, "Grafo Grande", this);
             
        
        //Inicializando o grafo pequeno com 5 vertices
        grafoPequeno = new Grafo();
        grafoPequeno.tamanho = 1;
        grafoPequeno.adicionarVertice(200, 150);
        grafoPequeno.adicionarVertice(300, 350);
        grafoPequeno.adicionarVertice(400, 150);
        grafoPequeno.adicionarVertice(500, 300);
        grafoPequeno.adicionarVertice(400, 450);
        
        grafoPequeno.adicionarAresta(0, 1);
        grafoPequeno.adicionarAresta(0, 2);
        grafoPequeno.adicionarAresta(1, 2);
        grafoPequeno.adicionarAresta(2, 3);
        grafoPequeno.adicionarAresta(4, 3);
        grafoPequeno.adicionarAresta(4, 1);
        
        //Inicializando grafo grande com 20 vertices
        grafoGrande = new Grafo();
        grafoGrande.tamanho = 2;

        grafoGrande.adicionarVertice(50, 150);  
        grafoGrande.adicionarVertice(180, 120); 
        grafoGrande.adicionarVertice(300, 180);  
        grafoGrande.adicionarVertice(450, 130); 
        grafoGrande.adicionarVertice(580, 160); 
        grafoGrande.adicionarVertice(680, 200); 

        grafoGrande.adicionarVertice(80, 300);   
        grafoGrande.adicionarVertice(220, 350);  
        grafoGrande.adicionarVertice(350, 320); 
        grafoGrande.adicionarVertice(480, 380); 
        grafoGrande.adicionarVertice(600, 340); 
        grafoGrande.adicionarVertice(690, 400); 


        grafoGrande.adicionarVertice(60, 450); 
        grafoGrande.adicionarVertice(150, 550); 
        grafoGrande.adicionarVertice(280, 500);  
        grafoGrande.adicionarVertice(400, 580); 
        grafoGrande.adicionarVertice(520, 520); 
        grafoGrande.adicionarVertice(650, 550); 
        grafoGrande.adicionarVertice(320, 440); 
        grafoGrande.adicionarVertice(500, 250);  

        grafoGrande.adicionarAresta(0, 1);
        grafoGrande.adicionarAresta(1, 2);
        grafoGrande.adicionarAresta(2, 3);
        grafoGrande.adicionarAresta(3, 4);
        grafoGrande.adicionarAresta(4, 5);


        grafoGrande.adicionarAresta(0, 6);
        grafoGrande.adicionarAresta(1, 7);
        grafoGrande.adicionarAresta(2, 8);
        grafoGrande.adicionarAresta(3, 9);
        grafoGrande.adicionarAresta(4, 10);
        grafoGrande.adicionarAresta(5, 11);


        grafoGrande.adicionarAresta(6, 12);
        grafoGrande.adicionarAresta(7, 13);
        grafoGrande.adicionarAresta(8, 14);
        grafoGrande.adicionarAresta(9, 15);
        grafoGrande.adicionarAresta(10, 16);
        grafoGrande.adicionarAresta(11, 17);

        grafoGrande.adicionarAresta(12, 13);
        grafoGrande.adicionarAresta(14, 18);
        grafoGrande.adicionarAresta(18, 8);
        grafoGrande.adicionarAresta(19, 3);
        grafoGrande.adicionarAresta(19, 9);
        
        
    }


    @Override
    public void update( double delta ) {
        btnGrafoPequeno.update(delta);
        btnGrafoGrande.update(delta);
        
        if(btnGrafoPequeno.isMousePressed()){
            raio = 30;
            grafoAtual = grafoPequeno;
            desenhar = true;
            buscaIniciada = false;
            
            //Reseta tudo quando aperta
            historicoEventos.clear();
            filaEventos.clear();
            eventoAtual = null;
            
        }
        
        if(btnGrafoGrande.isMousePressed()){
            raio = 10;
            grafoAtual = grafoGrande;
            desenhar = true;
            buscaIniciada = false;
            
            //Reseta tudo quando aperta
            historicoEventos.clear();
            filaEventos.clear();
            eventoAtual = null;
        }
        
        if(isMouseButtonPressed(MOUSE_BUTTON_LEFT)){
            if(verticeClicado(grafoAtual) != null){
                //reseta tudo quando clica em outro vertice
                historicoEventos.clear(); 
                filaEventos.clear();
                
                valorInicio = verticeClicado(grafoAtual).getValor();
                buscaEmLargura(grafoAtual, valorInicio);
                System.out.println("Vertice: " + verticeClicado(grafoAtual).getValor());
                
                
            }
            
            
        }
        
        if(isKeyPressed(KEY_SPACE)){
            
            if(eventoAtual != null){
                historicoEventos.add(eventoAtual);
                
                //debug
                if(eventoAtual.tipo == TipoEvento.PERCORRER_ARESTA){
                    System.out.println("Origem = " + eventoAtual.origem.getValor());
                    System.out.println("Destino = " + eventoAtual.destino.getValor());  
                }else{
                    System.out.println("Visitado = " + eventoAtual.origem.getValor());
                }
            }
            
            if(!filaEventos.isEmpty()){
                eventoAtual = filaEventos.poll();  
                buscaIniciada = true;
                System.out.println(eventoAtual.tipo.toString());
                desenharCaminho = true;
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
        
        if(desenharCaminho){
            
            //esse loop garante que os eventos q ja aconteceram continuem sendo desenhados      
            for(EventoBusca evento : historicoEventos){
                desenharEvento(evento);   
            }
            
            
            if(eventoAtual != null){
                desenharEvento(eventoAtual);
            }
            
            //isso aqui desenha apenas o atual (metodo diferente por causa da cor)
            desenharEventoAtual(eventoAtual);
        }
        
        if(buscaIniciada){
            desenharTextoEvento(eventoAtual);
        }
          
    }
    
    public static void main( String[] args ) {
        new BuscaEmLargura();
    }
    
    public void desenharVertice(Vertice v, int raio, Color cor, int tamanhoGrafo){
        int x = v.getPosX();
        int y = v.getPosY();
        
        fillCircle( x, y, raio, WHITE );
        drawCircle( x, y,  raio, cor );
        
        if(tamanhoGrafo == 1){
            drawText( Integer.toString(v.getValor()), x - 5, y - 5, raio - 5, BLACK );
        } else if(tamanhoGrafo == 2){
            drawText( Integer.toString(v.getValor()), x - 3, y - 2, raio, BLACK );
        }
    }
    
    public void desenharAresta(Vertice vOrigem, Vertice vDestino, int raio, Color cor){
        //Código da internet, mt complicado nao compensava perder tempo
        int x1 = vOrigem.getPosX();
        int y1 = vOrigem.getPosY();
        int x2 = vDestino.getPosX();
        int y2 = vDestino.getPosY();
        
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
            desenharVertice(v, raio, BLACK, g.tamanho);
            
            //desenha as arestas
            for(int valorVizinho : g.getVizinhos(v.getValor())){
                Vertice vizinho = g.getVertice(valorVizinho);
                
                desenharAresta(v, vizinho, raio, BLACK);
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
        
        //Valor inicio visitado, adicionar na fila de eventos
        filaEventos.add(new EventoBusca(TipoEvento.VISITAR_VERTICE, g.getVertice(valorInicio), null));
        
        
        System.out.println(valorInicio + " visitado"); //debug
        
        while(!fila.isEmpty()){
            int v = fila.poll();
            System.out.println("Olhando vizinhos de " + v);
            
            for(int w : g.listaAdj.get(v)){
                //busca os vizinhos
                filaEventos.add(new EventoBusca(TipoEvento.PERCORRER_ARESTA, g.getVertice(v), g.getVertice(w)));
                
                posX = g.getVertice(w).getPosX();
                posY = g.getVertice(w).getPosY();
                
                if( !marked[w] ){
                    
                    fila.add(w);
                    marked[w] = true;
                    
                    System.out.println(w + ", vizinho de " + v + " ainda nao foi visitado");
                    
                    //visita o vizinho
                    filaEventos.add(new EventoBusca(TipoEvento.VISITAR_VERTICE, g.getVertice(w), null));
                    
                } else{
                    System.out.println(w + " ja foi visitado");
                }
            }
        }       
    }
    
        
    
    
    //Retorna o vertice clicado
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
    
    private void desenharEvento(EventoBusca evento) {
        int offset = 4;
        
        if(grafoAtual.tamanho == 1){
            offset = 4;
            setStrokeLineWidth(2);
        } else if (grafoAtual.tamanho == 2){
            offset = 2;
            setStrokeLineWidth(2);
        }
        
        if(evento.tipo == TipoEvento.PERCORRER_ARESTA){
            desenharAresta(evento.origem, evento.destino, raio, DARKGREEN);
        } else {
            desenharVertice(evento.origem, raio - offset, DARKGREEN, grafoAtual.tamanho);
        }
    }
    
    //Igual ao metodo de cima só muda a cor e um pouco do raio para diferenciar
    private void desenharEventoAtual(EventoBusca evento) {
        int offset = 4;
        if(grafoAtual.tamanho == 1){
            offset = 4;
            setStrokeLineWidth(5);
        } else if (grafoAtual.tamanho == 2){
            offset = 1;
            setStrokeLineWidth(3);
        }
        
        
        
        if(evento.tipo == TipoEvento.PERCORRER_ARESTA){
            desenharAresta(evento.origem, evento.destino, raio, BLUE);
        } else {
            desenharVertice(evento.origem, raio + offset, BLUE, grafoAtual.tamanho);
        }
    }
    
    private void desenharTextoEvento(EventoBusca evento){
        
        int valorO = evento.origem.getValor();
        
        drawText("Passo atual: ", 700, 100, 20, BLACK);
        
        if(evento.tipo == TipoEvento.VISITAR_VERTICE){
            drawText("Vizitando Vértice " + valorO, 
                    700, 120, 15, BLACK);  
        }
        
        if(evento.tipo == TipoEvento.PERCORRER_ARESTA){
            drawText("Percorrendo a aresta" + "(" + valorO + ", " + evento.destino.getValor() + ")", 
                    700, 120, 15, BLACK);
        }
    }
}

