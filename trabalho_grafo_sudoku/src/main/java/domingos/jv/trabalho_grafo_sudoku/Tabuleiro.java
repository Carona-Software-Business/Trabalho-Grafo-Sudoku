package domingos.jv.trabalho_grafo_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
<<<<<<< Updated upstream
=======
import java.util.Deque;
>>>>>>> Stashed changes

public class Tabuleiro {

    private String caminho = "inst1.txt";

    private int[][] tabuleiro = new int[9][9];
    
    private Grafo grafo;
    private Grafo grafoOriginal;
<<<<<<< Updated upstream
    
    private ArrayDeque<Vertice> fila;
=======
    private Deque<Vertice> pilha;
>>>>>>> Stashed changes

    public Tabuleiro() {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null && i < 9) {
                String[] partes = linha.trim().split(",");

                for (int j = 0; j < 9 && j < partes.length; j++) {
                    tabuleiro[i][j] = Integer.parseInt(partes[j].trim());
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
        
        this.grafo = new Grafo(tabuleiro);
        this.grafoOriginal = new Grafo(tabuleiro);
<<<<<<< Updated upstream
        
        fila = new ArrayDeque<>();
=======
        this.pilha = new ArrayDeque<>();
>>>>>>> Stashed changes
    }

    public Grafo getGrafo() {
        return grafo;
    }
    
    private Vertice escolherVertice() {
        return null;
    }
    
    private int escolherNumero(Vertice v) {
        return -1;
    }
    
    private boolean ehValido() {
        return false;
    }
    
    private void reinicializar() {
        
    }
    
    public Grafo buscaLargura(int maxInteracao) {
        for(int i = 1; i <= maxInteracao; i++) {
            Vertice no = escolherVertice();
            fila.add(no);
            escolherNumero(no);
            
            while(!fila.isEmpty()) {
                Vertice v = fila.remove();
                for(Vertice w : v.getAdj()) {
                    if(w.getNum() == -1) {
                        escolherNumero(w);
                        fila.add(w);
                    }
                }
            }
            
            if(ehValido()) return grafo;
            
            reinicializar();
        }
        
        return null;
    }
    
    public Grafo buscaProfundidade(int maxInteracao) {
        for(int i = 1; i <= maxInteracao; i++){
            Vertice no = escolherVertice();
            escolherNumero(no);
            pilha.push(no);
            
            while(!pilha.isEmpty()){
                Vertice v = pilha.pop();
                
                for(Vertice w : v.getAdj()){
                    if (w.getNum() == -1){
                       escolherNumero(w);
                       pilha.push(v);
                       pilha.push(w);                    
                    } 
                }
            }
            if(ehValido()){
                return grafo;
            }else{
                reinicializar();
            }
        }
        return null;
    }
}
