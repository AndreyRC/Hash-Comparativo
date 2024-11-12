abstract class TabelaHash {
    protected String[] tabela;
    protected int tamanho;
    protected int colisoes;
    protected static final int TAMANHO_TABELA = 10000;

    public TabelaHash() {
        tabela = new String[TAMANHO_TABELA];
        tamanho = 0;
        colisoes = 0;
    }

    protected abstract int funcaoHash(String chave);

    public void inserir(String chave) {
        int indice = funcaoHash(chave);
        boolean primeiraColisao = true;

        while (tabela[indice] != null) {
            if (primeiraColisao) {
                colisoes++;
                primeiraColisao = false;
            }
            indice = (indice + 1) % TAMANHO_TABELA;
        }

        tabela[indice] = chave;
        tamanho++;
    }

    public int[] getClusterizacao() {
        int[] clusters = new int[TAMANHO_TABELA];
        for (int i = 0; i < TAMANHO_TABELA; i++) {
            if (tabela[i] != null) {
                int sequencia = 0;
                int j = i;
                while (j < TAMANHO_TABELA && tabela[j] != null) {
                    sequencia++;
                    j++;
                }
                clusters[i] = sequencia;
            }
        }
        return clusters;
    }

    public boolean buscar(String chave) {
        int indice = funcaoHash(chave);
        int indiceOriginal = indice;

        while (tabela[indice] != null) {
            if (tabela[indice].equals(chave)) {
                return true;
            }
            indice = (indice + 1) % TAMANHO_TABELA;
            if (indice == indiceOriginal) break;
        }
        return false;
    }

    public int getColisoes() {
        return colisoes;
    }

    public int[] getDistribuicao() {
        int[] distribuicao = new int[TAMANHO_TABELA];
        for (int i = 0; i < TAMANHO_TABELA; i++) {
            if (tabela[i] != null) {
                distribuicao[i] = 1;
            }
        }
        return distribuicao;
    }

    public static int getTamanhoTabela() {
        return TAMANHO_TABELA;
    }
}
