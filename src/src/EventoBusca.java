package src;

import src.Grafo.Vertice;

/**
 *
 * @author rodiz
 */

enum TipoEvento{
    VISITAR_VERTICE,
    PERCORRER_ARESTA
}

public class EventoBusca {
    TipoEvento tipo;
    Vertice origem;
    Vertice destino;
    
    public EventoBusca(TipoEvento tipo, Vertice origem, Vertice destino){
        this.tipo = tipo;
        this.origem = origem;
        this.destino = destino;
    }
    
}
