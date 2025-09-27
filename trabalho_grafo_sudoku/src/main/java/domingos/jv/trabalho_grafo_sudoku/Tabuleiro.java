package domingos.jv.trabalho_grafo_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Tabuleiro {

    private int linhaColuna;

    private int[][] tabuleiro;

    private Grafo grafo;

    private ArrayDeque<Vertice> fila;
    private Deque<Vertice> pilha;

    int pos;

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

            } while ((linha = br.readLine()) != null && i < linhaColuna);

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        System.out.println("Tabuleiro Inicial:");
        for (int i = 0; i < linhaColuna; i++) {
            for (int j = 0; j < linhaColuna; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
         */
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
        //System.out.println("Vertice escolhido: " + v.getNum());
        if (v.getNum() == -1) {
            return v;
        } else {
            for (Vertice u : v.getAdj()) {
                if (u.getNum() == -1) {
                    //System.out.println("Vertice adj escolhido: " + u.getNum());
                    return u;
                }
            }
            for (Vertice u : g.getVertices()) {
                if (u.getNum() == -1) {
                    //System.out.println("Vertice matriz escolhido: " + u.getNum());
                    return u;
                }
            }
        }
        //System.out.println("Erro null");
        return null;
    }

    private Vertice escolherVerticeRandom(Grafo g) {
        Vertice v;

        do {
            pos = new Random().nextInt(g.getVertices().size());

            v = g.getVertices().get(pos);

        } while (v.getNum() != -1);

        return v;
    }

    private int escolherNumero(Vertice v) {
        Set<Integer> numeros = new HashSet<>();

        for (Vertice u : v.getAdj()) {
            if (u.getNum() != -1) {
                numeros.add(u.getNum());
            }
        }

        List<Integer> numerosEsc = new ArrayList<>();
        int num = 1;
        while (num <= linhaColuna) {
            if (!numeros.contains(num)) {
                numerosEsc.add(num);
            }
            
            num++;
        }
        
        if(numerosEsc.isEmpty())
            return -1;

        return numerosEsc.get(new Random().nextInt(numerosEsc.size()));

    }

    private boolean ehValido(Grafo g) {
        //System.out.println("Testando: ");
        //System.out.println(g.printarTabuleiro());

        if (g == null) {
            return false;
        }

        for (Vertice v : g.getVertices()) {
            for (Vertice u : v.getAdj()) {
                if (v.getNum() == u.getNum() || v.getNum() == -1) {
                    //System.out.println("Tabuleiro incorreto");
                    return false;
                }
            }
        }
        //System.out.println("Valido");
        return true;
    }

    public Grafo buscaLargura(long maxInteracao) {
        Grafo grafoRetorno = new Grafo(tabuleiro);

        for (long i = 1L; i <= maxInteracao; i++) {
            //System.out.println("Tentativa " + i);
            
            Vertice no = escolherVerticeRandom(grafoRetorno);
            fila.add(no);
        
            no.setNum(escolherNumero(no));
            
            preecherSudokuLargura();

            if (ehValido(grafoRetorno)) {
                return grafoRetorno;
            } 

            //System.out.println("Nao deu, tentando de novo...");
            fila.clear();
            copiarGrafo(grafoRetorno);
        }
        
        return null;
    }

    private void preecherSudokuLargura() {
        while (!fila.isEmpty()) {
            Vertice v = fila.remove();

            for (Vertice w : v.getAdj()) {
                if (w.getNum() == -1) {
                    int num = escolherNumero(w);
                    
                    if (num == -1) {
                        return;
                    }
                    
                    w.setNum(escolherNumero(w));
                    fila.add(w);
                }
            }
        }
    }

    public Grafo buscaProfundidade(long maxInteracao) {
        Grafo grafoRetorno = new Grafo(tabuleiro);

        for (long i = 1L; i <= maxInteracao; i++) {
            Vertice no = escolherVerticeRandom(grafoRetorno);

            System.out.println("Vertice escolhido: " + no.getNum());
            System.out.println(grafoRetorno.printarTabuleiro(pos));

            no.setNum(escolherNumero(no));

            System.out.println(grafoRetorno.printarTabuleiro(pos));

            pilha.push(no);

            System.out.println("Pilha:");
            for (Vertice v : pilha) {
                System.out.print(v.getNum() + " - ");
            }

            while (!pilha.isEmpty()) {
                Vertice v = pilha.pop();

                System.out.println("Vertice removido: " + v.getNum());

                System.out.println("Pilha:");
                for (Vertice u : pilha) {
                    System.out.print(u.getNum() + " - ");
                }

                for (Vertice w : v.getAdj()) {
                    if (w.getNum() == -1) {
                        System.out.println("Vertice adj: " + w.getNum());
                        w.setNum(escolherNumero(w));
                        System.out.println(grafoRetorno.printarTabuleiro());

                        pilha.push(v);
                        pilha.push(w);

                        System.out.println("Pilha:");
                        for (Vertice u : pilha) {
                            System.out.print(u.getNum() + " - ");
                        }

                        break;
                    }
                }
            }

            if (ehValido(grafoRetorno)) {
                return grafoRetorno;
            }

            pilha.clear();
            copiarGrafo(grafoRetorno);
        }
        return null;
    }

    private void copiarGrafo(Grafo g) {
        for (int i = 0; i < this.grafo.getVertices().size(); i++) {
            g.getVertices().get(i).setNum(this.grafo.getVertices().get(i).getNum());
        }
    }
}
