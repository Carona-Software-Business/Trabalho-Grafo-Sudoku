package domingos.jv.trabalho_grafo_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Tabuleiro {

    private String caminho = "inst1.txt";

    private int[][] tabuleiro = new int[9][9];
    
    private Grafo grafo;
    
    private ArrayDeque<Vertice> fila;
    private Deque<Vertice> pilha;

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
        
        fila = new ArrayDeque<>();
        this.pilha = new ArrayDeque<>();
    }

    public Grafo getGrafo() {
        return grafo;
    }
    
    private Vertice escolherVertice(Grafo g) {
        Random rand = new Random();
        int numeroInt = rand.nextInt(g.getVertices().size());
        Vertice v = g.getVertices().get(numeroInt);
        if(v.getNum() == -1){
            return v;
        }else{
            for(Vertice u : v.getAdj()){
                if(u.getNum() == -1){
                    return u;
                }
            }
            for(Vertice u: g.getVertices()){
                if(u.getNum() == -1){
                    return u;
                }
            }
        }
        return null;
    }
    
    private int escolherNumero(Vertice v) {
        int num = 1;      
        while(num < 9) {
            for(Vertice u : v.getAdj()) {
                if(u.getNum() == num) {
                    num++;
                    break;
                } else return num;
            }
        }       
        return -1;
    }
    
    private boolean ehValido() {
        return false;
    }
    
    public Grafo buscaLargura(int maxInteracao) {
        for(int i = 1; i <= maxInteracao; i++) {
            Grafo grafoRetorno = new Grafo(tabuleiro);
            
            Vertice no = escolherVertice(grafoRetorno);
            fila.add(no);
            no.setNum(escolherNumero(no));
            
            while(!fila.isEmpty()) {
                Vertice v = fila.remove();
                for(Vertice w : v.getAdj()) {
                    if(w.getNum() == -1) {
                        w.setNum(escolherNumero(w));
                        fila.add(w);
                    }
                }
            }
            
            if(ehValido()) return grafoRetorno;
            
            fila.clear();
        }
        
        return null;
    }
    
    public Grafo buscaProfundidade(int maxInteracao) {
        for(int i = 1; i <= maxInteracao; i++){
            Grafo grafoRetorno = new Grafo(tabuleiro);
            
            Vertice no = escolherVertice(grafoRetorno);
            no.setNum(escolherNumero(no));
            pilha.push(no);
            
            while(!pilha.isEmpty()){
                Vertice v = pilha.pop();
                
                for(Vertice w : v.getAdj()){
                    if (w.getNum() == -1){
                       w.setNum(escolherNumero(w));
                       pilha.push(v);
                       pilha.push(w);                    
                    } 
                }
            }
            
            if(ehValido()) return grafoRetorno;
            
            pilha.clear();
        }
        return null;
    }
}
