import java.util.function.IntSupplier;

public class EscopoEClosure {
    static int x = 10;
    static IntSupplier criaLeitura() { int base = 2; return () -> base + x; }
    public static void main(String[] args) {
        int x = 3;
        { int bloco = 4; System.out.println("local=" + x + ", bloco=" + bloco); }
        System.out.println("campo=" + EscopoEClosure.x);
        System.out.println("closure=" + criaLeitura().getAsInt());
    }
}
