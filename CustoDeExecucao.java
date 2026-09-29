public class CustoDeExecucao {
    public static void main(String[] args) {
        final int n = 200_000; long inicio = System.nanoTime(); long total = 0;
        for (int i = 0; i < n; i++) { String item = "item-" + i; total += item.length(); }
        long tempoMs = (System.nanoTime() - inicio) / 1_000_000;
        System.out.printf("n=%d%ntotal-positivo=%b%ntempo-ms=%d%n", n, total > 0, tempoMs);
    }
}
