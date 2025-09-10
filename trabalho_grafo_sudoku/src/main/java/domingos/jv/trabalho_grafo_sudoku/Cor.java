package domingos.jv.trabalho_grafo_sudoku;

public enum Cor {
    BRANCO("Branco"), CINZA("Cinza"), PRETO("Preto");
    
    private String descricao;
    
    Cor(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
