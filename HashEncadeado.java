import java.util.Random;

class Node {
    int chave;
    Node proximo;

    Node(int c) {
        chave = c;
        proximo = null;
    }
}

public class HashEncadeado {

    static int tam = 100;
    static int vazio = -1;
    static Node[] tabela = new Node[tam];

    static int[] ultimos = new int[3];
    static int contColisoes = 0;

    public static int hash(int chave) {
        return chave % tam;
    }

    public static void insere(int chave) {
        int i = hash(chave);

        if (tabela[i].chave == vazio) {
            tabela[i].chave = chave;
        } else {

            Node novo = new Node(chave);

            if (tabela[i].proximo == null || tabela[i].proximo.chave >= chave) {
                novo.proximo = tabela[i].proximo;
                tabela[i].proximo = novo;
            } else {
                Node atual = tabela[i].proximo;
                while (atual.proximo != null && atual.proximo.chave < chave) {
                    atual = atual.proximo;
                }
                novo.proximo = atual.proximo;
                atual.proximo = novo;
            }

            ultimos[0] = ultimos[1];
            ultimos[1] = ultimos[2];
            ultimos[2] = chave;
            contColisoes++;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < tam; i++) {
            tabela[i] = new Node(vazio);
        }

        Random random = new Random(1090);

        for (int i = 0; i < 1000; i++) {
            int n = random.nextInt(100001);
            insere(n);
        }

        int qtdListas = 0;
        int vazias = 0;
        int maiorTam = 0;
        int posMaior = -1;

        for (int i = 0; i < tam; i++) {
            if (tabela[i].chave == vazio) {
                vazias++;
            } else if (tabela[i].proximo != null) {
                qtdListas++;

                int t = 0;
                Node aux = tabela[i].proximo;
                while (aux != null) {
                    t++;
                    aux = aux.proximo;
                }
                if (t > maiorTam) {
                    maiorTam = t;
                    posMaior = i;
                }
            }
        }

        System.out.print("maior lista encadeada (posicao " + posMaior + "): ");
        if (posMaior != -1) {
            System.out.print(tabela[posMaior].chave);
            Node aux = tabela[posMaior].proximo;
            while (aux != null) {
                System.out.print(" - " + aux.chave);
                aux = aux.proximo;
            }
            System.out.println();
        }

        System.out.println("listas encadeadas: " + qtdListas);
        System.out.println("posicoes vazias: " + vazias);
        System.out.println("3 ultimos: " + ultimos[0] + ", " + ultimos[1] + ", " + ultimos[2]);
    }
}