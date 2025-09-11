package domingos.jv.trabalho_grafo_sudoku;

import java.util.ArrayList;

public class Vertice {
    private int num;
    private ArrayList<Vertice> adj;
    
    private Cor cor;
    private Vertice pai;
    
    // Atributos busca em largura
    private int distancia;
    
    // Atributos busca em profundidade
    private int tempoDescoberto;
    private int tempoFinalizado;

    public Vertice(int num) {
        this.num = num;
        this.adj = new ArrayList<>();
        pai = null;
        cor = Cor.BRANCO;
    }
    
    public void adicionarAresta(Vertice u) {
        adj.add(u);
    }

    @Override
    public String toString() {
        String adj = "";
        
        for(Vertice v : this.adj) {
            adj += v.num + " - ";
        }
        
        return "Vertice: " + num + " - Adj: " + adj;
    }

    public ArrayList<Vertice> getAdj() {
        return adj;
    }
    
}
