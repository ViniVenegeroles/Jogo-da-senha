import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class JogodaSenha {

    /**
     * Jogo da Senha (CLI)
     * - O jogador tem 10 tentativas para acertar uma senha de 4 dígitos (1 a 6).
     * - O programa informa quantos dígitos estão na posição correta e quantos estão corretos mas deslocados.
     * <p>
     * Uso:
     * java JogoDaSenha         // executa normalmente
     * java JogoDaSenha --debug // mostra a senha (útil para testar)
     */

    public static void main(String[] args) {
        boolean debug = Arrays.asList(args).contains("--debug") || Arrays.asList(args).contains("-d");

        Random rand = new Random();
        int[] senha = new int[4];
        gerarSenha(senha, rand);

        if (debug) {
            System.out.print("[DEBUG] Senha gerada: ");
            imprimirSenha(senha);
            System.out.println();
        }

        System.out.println("Jogo da Senha - Digite números de 1 a 6");

        boolean venceu = false;
        try (Scanner sc = new Scanner(System.in)) {
            for (int numeroDeJogada = 1; numeroDeJogada <= 10 && !venceu; numeroDeJogada++) {
                System.out.println("\nTentativa " + numeroDeJogada + ":");
                int[] tentativa = lerTentativa(sc);

                int corretos = contarPosicaoCorreta(senha, tentativa);
                int deslocados = contarPosicaoDeslocados(senha, tentativa);

                System.out.println("Dígitos corretos na posição correta: " + corretos);
                System.out.println("Dígitos corretos, mas deslocados: " + deslocados);

                if (corretos == 4) {
                    venceu = true;
                }
            }
        }

        if (venceu) {
            System.out.println("\nVocê acertou a senha! Triunfo!!!");
        } else {
            System.out.println("\nVocê perdeu. A senha era:");
        }
        imprimirSenha(senha);
        System.out.println();
    }

    // Gera a senha com valores entre 1 e 6
    public static void gerarSenha(int[] senha, Random rand) {
        for (int i = 0; i < senha.length; i++) {
            senha[i] = rand.nextInt(6) + 1;
        }
    }

    // Lê 4 números válidos (1 a 6) do usuário, tratando entradas inválidas
    public static int[] lerTentativa(Scanner sc) {
        int[] tentativa = new int[4];
        for (int i = 0; i < tentativa.length; i++) {
            while (true) {
                System.out.print("Digite o " + (i + 1) + "º número (1 a 6): ");
                String entrada = sc.next().trim();
                try {
                    int valor = Integer.parseInt(entrada);
                    if (valor >= 1 && valor <= 6) {
                        tentativa[i] = valor;
                        break;
                    } else {
                        System.out.println("Valor inválido! Digite apenas números de 1 a 6.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida! Digite um número inteiro entre 1 e 6.");
                }
            }
        }
        return tentativa;
    }

    // Conta quantos dígitos estão na mesma posição
    public static int contarPosicaoCorreta(int[] senha, int[] tentativa) {
        int cont = 0;
        for (int i = 0; i < senha.length; i++) {
            if (senha[i] == tentativa[i]) {
                cont++;
            }
        }
        return cont;
    }

    // Conta quantos dígitos corretos existem em posições diferentes (sem contar os já corretos)
    public static int contarPosicaoDeslocados(int[] senha, int[] tentativa) {
        // copia para não alterar os originais
        int[] s = Arrays.copyOf(senha, senha.length);
        int[] t = Arrays.copyOf(tentativa, tentativa.length);
        int deslocados = 0;

        // elimina os corretos na mesma posição
        for (int i = 0; i < s.length; i++) {
            if (s[i] == t[i]) {
                s[i] = 0;
                t[i] = 0;
            }
        }

        // para cada dígito não-zero em tentativa, verifica se existe em s
        for (int i = 0; i < t.length; i++) {
            if (t[i] != 0) {
                for (int j = 0; j < s.length; j++) {
                    if (t[i] == s[j]) {
                        deslocados++;
                        s[j] = 0; // evita contar duas vezes o mesmo dígito da senha
                        break;
                    }
                }
            }
        }
        return deslocados;
    }

    // Imprime a senha no formato "1 2 3 4" (com espaço extra no final — simples e legível)
    private static void imprimirSenha(int[] senha) {
        for (int i = 0; i < senha.length; i++) {
            System.out.print(senha[i] + " ");
        }
    }
}
