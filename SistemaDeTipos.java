import java.util.List;

public class SistemaDeTipos {
    static <T> T primeiro(List<T> xs) { return xs.get(0); }
    public static void main(String[] args) {
        int inteiro = 7; long ampliado = inteiro;
        List<Number> valores = List.of(1, 2.0);
        Number primeiroValor = primeiro(valores);
        Integer encaixotado = 42; Number comoReferencia = encaixotado;
        int desempacotado = encaixotado;
        System.out.printf("ampliado=%d%nprimeiro=%s%nreferencia=%s%ndesempacotado=%d%n",
                ampliado, primeiroValor, comoReferencia, desempacotado);
    }
}
