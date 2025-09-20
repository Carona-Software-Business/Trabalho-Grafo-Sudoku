package domingos.jv.trabalho_grafo_sudoku;

import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices;
    
    private int tamanho;

    public Grafo(int[][] tabuleiro) {
        this.vertices = new ArrayList<>();
        this.tamanho = tabuleiro.length;
        criarGrafo(tabuleiro);
        criarArestas(tabuleiro);
    }
    
    private void criarGrafo(int[][] tabuleiro) {
        for(int i = 0; i < tamanho; i++) {
            for(int j = 0; j < tamanho; j++) {
                vertices.add(new Vertice(tabuleiro[i][j]));
            }
        }
    }
    
    private void criarArestas(int[][] tabuleiro) {
        for(int i = 0; i < tamanho; i++) {
            for(int j = 0; j < tamanho; j++) {
                int posV = i*tamanho+j;
                Vertice v = vertices.get(posV);
                
                // Linha
                for(int k = 0; k < tamanho; k++) {
                    int posU = i*tamanho+k;
                    
                    if(posU != posV) {
                        Vertice u = vertices.get(posU);
                        v.adicionarAresta(u);
                    }
                }
                
                // Coluna
                for(int k = 0; k < tamanho; k++) {
                    int posU = k*tamanho+j;
                    
                    if(posU != posV) {
                        Vertice u = vertices.get(posU);
                        v.adicionarAresta(u);
                    }
                }
                
                // Quadrante
                int q = (int) Math.sqrt(tamanho);
                int qi = (i / q) * q;
                int qj = (j / q) * q;
                for(int k = qi; k < (qi + q); k++) {
                    for(int l = qj; l < (qj + q); l++) {
                        int posU = k*tamanho+l;
                        
                        if(posU != posV && k != i && l != j) {
                            Vertice u = vertices.get(posU);
                            v.adicionarAresta(u);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String retorno = "";
        for(Vertice v : this.vertices) {
            retorno += v.toString() + "\n";
        }
        
        return retorno;
    }
    
    public String printarTabuleiro() {
        String retorno = "";
        
        int i = 1;
        for(Vertice v : vertices) {
            retorno += v.getNum();
            
            if(i % tamanho == 0) retorno += "\n";
            else retorno += " ";
            
            i++;
        }
        
        return retorno;
    }
    
    public String printarTabuleiro(int pos) {
        String retorno = "";
        
        int i = 1;
        for(Vertice v : vertices) {
            if((i-1) == pos)
                retorno += "\\033[1m" + v.getNum() + "\\033[0m";
            else
                retorno += v.getNum();
            
            if(i % tamanho == 0) retorno += "\n";
            else retorno += " ";
            
            i++;
        }
        
        return retorno;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }
    
    
}
