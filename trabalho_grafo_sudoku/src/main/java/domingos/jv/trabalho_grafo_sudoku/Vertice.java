package domingos.jv.trabalho_grafo_sudoku;

import java.util.ArrayList;

public class Vertice {
    private int num;
    private ArrayList<Vertice> adj;

    public Vertice(int num) {
        this.num = num;
        this.adj = new ArrayList<>();
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
}
