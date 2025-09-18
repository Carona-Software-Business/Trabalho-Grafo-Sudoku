package domingos.jv.trabalho_grafo_sudoku;

import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices;

    public Grafo(int[][] tabuleiro) {
        this.vertices = new ArrayList<>();
        criarGrafo(tabuleiro);
        criarArestas(tabuleiro);
    }
    
    private void criarGrafo(int[][] tabuleiro) {
        for(int i = 0; i < tabuleiro.length; i++) {
            for(int j = 0; j < tabuleiro[0].length; j++) {
                vertices.add(new Vertice(tabuleiro[i][j]));
            }
        }
    }
    
    private void criarArestas(int[][] tabuleiro) {
        for(int i = 0; i < tabuleiro.length; i++) {
            for(int j = 0; j < tabuleiro[0].length; j++) {
                int posV = i*9+j;
                Vertice v = vertices.get(posV);
                
                // Linha
                for(int k = 0; k < 9; k++) {
                    int posU = i*9+k;
                    
                    if(posU != posV) {
                        Vertice u = vertices.get(posU);
                        v.adicionarAresta(u);
                    }
                }
                
                // Coluna
                for(int k = 0; k < 9; k++) {
                    int posU = k*9+j;
                    
                    if(posU != posV) {
                        Vertice u = vertices.get(posU);
                        v.adicionarAresta(u);
                    }
                }
                
                // Quadrante
                int qi = (i / 3) * 3;
                int qj = (j / 3) * 3;
                for(int k = qi; k < (qi + 3); k++) {
                    for(int l = qj; l < (qj + 3); l++) {
                        int posU = k*9+l;
                        
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
            
            if(i % 9 == 0) retorno += "\n";
            else retorno += " ";
            
            i++;
        }
        
        return retorno;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }
    
    
}
