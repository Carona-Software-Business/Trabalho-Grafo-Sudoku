package domingos.jv.trabalho_grafo_sudoku;

import java.awt.Toolkit;

public class Trabalho_grafo_sudoku {

    public static void main(String[] args) {
        // João Vitor C. Domingos e Kauan Gomes
        
        Tabuleiro tabuleiro = new Tabuleiro("dificil.txt");
        
        Grafo resultadoL = tabuleiro.buscaLargura(100000000L);
        Grafo resultadoP = tabuleiro.buscaProfundidade(100000000L);
        
        System.out.println("Resposta Largura: ");
        if(resultadoL == null) System.out.println("Nao deu :(");
        else System.out.println(resultadoL.printarTabuleiro());
        
        System.out.println("Resposta Profundo: ");
        if(resultadoP == null) System.out.println("Nao deu :(");
        else System.out.println(resultadoP.printarTabuleiro());
        
        Toolkit.getDefaultToolkit().beep();
    }
}
