import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TabelaHash tabelaMultiplicacao = new TabelaHashMultiplicacao();
        TabelaHash tabelaDivisao = new TabelaHashDivisao();

        List<String> nomes = lerNomesDoArquivo("female_names.txt");

        long tempoInicial = System.nanoTime();
        for (String nome : nomes) {
            tabelaMultiplicacao.inserir(nome);
        }
        long tempoInsercaoMultiplicacao = System.nanoTime() - tempoInicial;

        tempoInicial = System.nanoTime();
        for (String nome : nomes) {
            tabelaDivisao.inserir(nome);
        }
        long tempoInsercaoDivisao = System.nanoTime() - tempoInicial;

        tempoInicial = System.nanoTime();
        for (String nome : nomes) {
            tabelaMultiplicacao.buscar(nome);
        }
        long tempoBuscaMultiplicacao = System.nanoTime() - tempoInicial;

        tempoInicial = System.nanoTime();
        for (String nome : nomes) {
            tabelaDivisao.buscar(nome);
        }
        long tempoBuscaDivisao = System.nanoTime() - tempoInicial;

        System.out.println("=== Resultados da Comparação das Tabelas Hash ===\n");

        System.out.println("Método de Multiplicação:");
        System.out.println("Colisões: " + tabelaMultiplicacao.getColisoes());
        System.out.println("Tempo de inserção: " + tempoInsercaoMultiplicacao / 1_000_000.0 + " ms");
        System.out.println("Tempo de busca: " + tempoBuscaMultiplicacao / 1_000_000.0 + " ms\n");

        System.out.println("Método de Divisão:");
        System.out.println("Colisões: " + tabelaDivisao.getColisoes());
        System.out.println("Tempo de inserção: " + tempoInsercaoDivisao / 1_000_000.0 + " ms");
        System.out.println("Tempo de busca: " + tempoBuscaDivisao / 1_000_000.0 + " ms\n");

        System.out.println("Análise da Distribuição:");
        analisarDistribuicao("Multiplicação", tabelaMultiplicacao.getDistribuicao());
        analisarDistribuicao("Divisão", tabelaDivisao.getDistribuicao());

        System.out.println("\nAnálise de Clusterização:");
        analisarClusterizacao("Multiplicação", tabelaMultiplicacao);
        analisarClusterizacao("Divisão", tabelaDivisao);
    }

    private static List<String> lerNomesDoArquivo(String nomeArquivo) {
        List<String> nomes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    nomes.add(linha.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return nomes;
    }

    private static void analisarDistribuicao(String metodo, int[] distribuicao) {
        int elementosOcupados = 0;
        int maiorSequencia = 0;
        int sequenciaAtual = 0;

        for (int valor : distribuicao) {
            if (valor == 1) {
                elementosOcupados++;
                sequenciaAtual++;
                maiorSequencia = Math.max(maiorSequencia, sequenciaAtual);
            } else {
                sequenciaAtual = 0;
            }
        }

        System.out.println("\nMétodo " + metodo + ":");
        System.out.println("Posições ocupadas: " + elementosOcupados);
        System.out.println("Maior sequência de elementos consecutivos: " + maiorSequencia);
        System.out.println("Fator de carga: " + String.format("%.2f", (double) elementosOcupados / TabelaHash.getTamanhoTabela()));
    }

    private static void analisarClusterizacao(String metodo, TabelaHash tabela) {
        int[] clusters = tabela.getClusterizacao();
        System.out.println("\nClusterização para " + metodo + ":");

        int[] frequenciaCluster = new int[1000];
        int maiorCluster = 0;

        for (int tamanhoCluster : clusters) {
            if (tamanhoCluster > 0) {
                frequenciaCluster[tamanhoCluster - 1]++;
                maiorCluster = Math.max(maiorCluster, tamanhoCluster);
            }
        }

        System.out.println("Distribuição de clusters:");
        for (int i = 0; i < maiorCluster; i++) {
            if (frequenciaCluster[i] > 0) {
                System.out.println("Clusters de tamanho " + (i + 1) + ": " + frequenciaCluster[i]);
            }
        }
    }
}