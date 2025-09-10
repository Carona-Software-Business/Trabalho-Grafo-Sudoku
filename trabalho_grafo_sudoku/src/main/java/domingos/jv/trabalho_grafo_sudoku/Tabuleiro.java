package domingos.jv.trabalho_grafo_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tabuleiro {

    private String caminho = "inst1.txt";

    private int[][] tabuleiro = new int[9][9];
    
    private Grafo grafo;

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
    }

    public Grafo getGrafo() {
        return grafo;
    }
}
