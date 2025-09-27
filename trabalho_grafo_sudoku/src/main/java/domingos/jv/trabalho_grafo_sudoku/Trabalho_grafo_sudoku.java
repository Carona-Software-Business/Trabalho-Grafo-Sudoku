package domingos.jv.trabalho_grafo_sudoku;

import java.awt.Toolkit;

public class Trabalho_grafo_sudoku {

    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro("inst1.txt");
        
        //System.out.println(tabuleiro.getGrafo());
        
        Grafo resultado = tabuleiro.buscaLargura(100000000L);
        //Grafo resultado = tabuleiro.buscaProfundidade(2L);
        
        System.out.println("Resposta: ");
        if(resultado == null) System.out.println("Nao deu :(");
        else System.out.println(resultado.printarTabuleiro());
        
        Toolkit.getDefaultToolkit().beep();
    }
}
