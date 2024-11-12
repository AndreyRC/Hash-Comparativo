class TabelaHashMultiplicacao extends TabelaHash {
    private static final double A = 0.6180339887;

    @Override
    protected int funcaoHash(String chave) {
        int hash = 0;
        for (char c : chave.toCharArray()) {
            hash += c;
        }
        return (int) (TAMANHO_TABELA * ((hash * A) % 1));
    }
}