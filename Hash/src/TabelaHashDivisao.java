class TabelaHashDivisao extends TabelaHash {
    @Override
    protected int funcaoHash(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash = (31 * hash + chave.charAt(i)) % TAMANHO_TABELA;
        }
        return Math.abs(hash % TAMANHO_TABELA);
    }
}