package domingos.jv.trabalho_grafo_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Tabuleiro {
    private int linhaColuna;
    
    private int[][] tabuleiro;
    
    private Grafo grafo;
    
    private ArrayDeque<Vertice> fila;
    private Deque<Vertice> pilha;

    public Tabuleiro(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;
            
            linha = br.readLine();
            String[] partes = linha.split(",");
            linhaColuna = partes.length;
            tabuleiro = new int[linhaColuna][linhaColuna];
            
            do {
                partes = linha.trim().split(",");

                for (int j = 0; j < linhaColuna && j < partes.length; j++) {
                    tabuleiro[i][j] = Integer.parseInt(partes[j].trim());
                }
                i++;
                
            } while((linha = br.readLine()) != null && i < linhaColuna);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tabuleiro Inicial:");
        for (int i = 0; i < linhaColuna; i++) {
            for (int j = 0; j < linhaColuna; j++) {
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
        System.out.println("Vertice escolhido: " + v.getNum());
        if(v.getNum() == -1){
            return v;
        }else{
            for(Vertice u : v.getAdj()){
                if(u.getNum() == -1){
                    System.out.println("Vertice adj escolhido: " + u.getNum());
                    return u;
                }
            }
            for(Vertice u: g.getVertices()){
                if(u.getNum() == -1){
                    System.out.println("Vertice matriz escolhido: " + u.getNum());
                    return u;
                }
            }
        }
        System.out.println("Erro null");
        return null;
    }
    
    private int escolherNumero(Vertice v) {
        int num = 1;   
        boolean quebra = false;
        
        while(num <= linhaColuna) {
            for(Vertice u : v.getAdj()) {
                if(u.getNum() == num) {
                    System.out.println("Numero com o mesmo numero, trocar!");
                    num++;
                    quebra = true;
                    break;
                }
            }
            if(!quebra){
                System.out.println("Numero escolhido: " + num);
                return num;
            }
            
            quebra = false;
        }      
        System.out.println("Erro escolher numero!");
        return -1;
    }
    
    private boolean ehValido(Grafo g) {
        System.out.println("Testando: ");
        System.out.println(g.printarTabuleiro());
        
        for(Vertice v : g.getVertices()) {
            for(Vertice u : v.getAdj()) {
                if(v.getNum() == u.getNum() || v.getNum() == -1) {
                    System.out.println("Tabuleiro incorreto");
                    return false;
                }
            }
        }
        System.out.println("Valido");
        return true;
    }
    
    public Grafo buscaLargura(long maxInteracao) {
        Grafo grafoRetorno = new Grafo(tabuleiro);
        
        for(long i = 1L; i <= maxInteracao; i++) {
            Vertice no = escolherVertice(grafoRetorno);
            fila.add(no);
            no.setNum(escolherNumero(no));
            System.out.println(grafoRetorno.printarTabuleiro());
            
            while(!fila.isEmpty()) {
                Vertice v = fila.remove();
                System.out.println("Vertice removido: " + v.getNum());
                for(Vertice w : v.getAdj()) {
                    if(w.getNum() == -1) {
                        System.out.println("Vertice adj: " + w.getNum());
                        w.setNum(escolherNumero(w));
                        System.out.println(grafoRetorno.printarTabuleiro());
                        fila.add(w);
                    }
                }
            }
            
            if(ehValido(grafoRetorno)) return grafoRetorno;
            
            fila.clear();
            copiarGrafo(grafoRetorno);
        }
        
        return null;
    }
    
    public Grafo buscaProfundidade(long maxInteracao) {
        Grafo grafoRetorno = new Grafo(tabuleiro);
        
        for(long i = 1L; i <= maxInteracao; i++){
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
            
            if(ehValido(grafoRetorno)) return grafoRetorno;
            
            pilha.clear();
            copiarGrafo(grafoRetorno);
        }
        return null;
    }
    
    private void copiarGrafo(Grafo g) {
        for(int i = 0; i < this.grafo.getVertices().size(); i++) {
            g.getVertices().get(i).setNum(this.grafo.getVertices().get(i).getNum());
        }
    }
}
