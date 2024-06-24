
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

// Objetivo é fazer um jogo de batalha naval contra um computador...

public class BatalhaNaval {

    // aqui são algumas variáveis que para integrar o jogo como um todo, fazendo
    // todos os metodos conversarem
    // entre si.
    private int start;
    private int acertos;
    private int opcao1;
    private int contador;

    // aqui é o construtor, onde deve-se criar as variáveis base do jogo, o mapa de
    // barcos, o mapa tampado
    // e o Scanner
    private BatalhaNaval() {
        Scanner ler = new Scanner(System.in);
        int[][] mapa = new int[8][8];
        String[][] mapaTampado = new String[10][10];
        // após a criação da base, é feito o menu do jogo e a inicialização

        // aqui é usado um do while para aparecer o menu toda vez que desistirem ou
        // digitarem um número inválido
        do {
            System.out.println(
                    "\n\tMuito bem vindo à batalha naval\n\nDigite 1 para dar start...\nDigite 0 para Fechar o jogo...\n\n\t\tSTART\n");

            comecar(this.start, ler, mapa, mapaTampado);
            if (this.start != 0) {
                System.out.println("=======================\nessas eram as posições dos barcos");
                revelaMapa(mapa, mapaTampado);
                System.out.println("=======================");
            }
        } while (this.start != 0);
    }

    // função/método para a criação de numeros aleatórios.
    private int randomNumber(int indice) {
        Random ran = new Random();
        indice += ran.nextInt(8);
        return indice;
    }

    // aqui cria o mapa que será mostrado para o jogador, com os valores de todas as
    // colunas e linhas
    // indicadas pelos caracteres X e Y para melhorar a jogabilidade
    private void geraMapa(String[][] mapaTampado) {
        DecimalFormat deci = new DecimalFormat("0");
        for (int i = 0; i < mapaTampado.length; i++) {
            mapaTampado[i][0] = " ";
        }
        mapaTampado[1][0] = "Y";

        for (int i = 0; i < mapaTampado.length; i++) {
            mapaTampado[0][i] = " ";
        }
        mapaTampado[0][1] = "X";
        // monta as linhas de números
        for (int i = 1; i < mapaTampado.length; i++) {
            mapaTampado[i][1] = deci.format(i - 2);
        }
        for (int i = 1; i < mapaTampado.length; i++) {
            mapaTampado[1][i] = deci.format(i - 2);
        }
        mapaTampado[1][1] = " ";
        // preenche os espaços com água
        for (int i = 2; i < mapaTampado.length; i++) {
            for (int j = 2; j < mapaTampado.length; j++) {
                mapaTampado[i][j] = "~";
            }
        }
    }

    // função/método para printar o mapa usando dois "for", um para as linhas e
    // outro para as colunas
    private void printaMapa(String[][] mapaTampado) {
        for (int i = 0; i < mapaTampado.length; i++) {
            for (int j = 0; j < mapaTampado.length; j++) {
                System.out.print(mapaTampado[j][i] + " ");
            }
            System.out.println();
        }
    }

    // função/método para rodar o ataque a uma das casas
    private void atacar(Scanner ler, int[][] mapa, String[][] mapaTampado) {
        // escolha das casas com base no mapa
        System.out.println("Escolha o valor de X...");
        int xAtacado = ler.nextInt();
        System.out.println("Escolha o valor de Y...");
        int yAtacado = ler.nextInt();

        // testa se os números escolhidos é válido
        if (xAtacado > 7 || xAtacado < 0 || yAtacado > 7 || yAtacado < 0) {
            System.out.println(
                    "\n===ERRO====\nO ataque falhou pois o número inserido não está entre 0 e 7\nTente novamente...");
        }
        // testa se a casa atacada ja foi atacada antes
        else if (mapaTampado[xAtacado + 2][yAtacado + 2].equals("_")
                || mapaTampado[xAtacado + 2][yAtacado + 2].equals("O")) {
            System.out.println("Você já atacou essa casa, tente atacar novamente uma casa não atacada...");
        }
        // substiituição dos "~" pelo simbolo de casa vazia(_) ou de barco(O) com base
        // no mapa de barcos/mapa binário
        // feito com a classe random
        // revela ao jogador a casa, indicando acerto ou erro
        else {
            if (mapa[xAtacado][yAtacado] == 1) {
                mapa[xAtacado][yAtacado] += 1;
                mapaTampado[xAtacado + 2][yAtacado + 2] = "O";
                System.out.println("\nVocê achou um barco!!!");
                this.contador += 1;
                this.acertos += 1;
                //testa os acertos, caso tenha 10 acertos acaba a partida
                if (this.acertos == 10) {
                    System.out.println("Parabéns, você ganhou a partida!!");
                    this.opcao1 = 0;
                    printaMapa(mapaTampado);
                    return;
                }
            } else {
                mapaTampado[xAtacado + 2][yAtacado + 2] = "_";
                System.out.printf("O barco não estava na casa %d %d!%n", xAtacado, yAtacado);
                this.contador += 1;
            }
            printaMapa(mapaTampado);
        }
        // usado para resetar caso a pessoa acerte todos os barcos e ganhe

    }

    // função/método que libera a decisão de atacar ou desistir ao jogador
    private void leAtaque(Scanner ler, int[][] mapa, String[][] mapaTampado) {
        this.contador = 0;
        do {    
            //roda enquanto ainda houver barcos não atingidos
            if (this.acertos != 10) {
                System.out.println("\nDigite 1 para atacar...");
                System.out.println("Digite 0 para desistir...");
                System.out.printf("Você possui %d jogadas...%n", 30 - this.contador);
                if (this.contador > 0) {
                    System.out.printf("você tem %d acertos%n%n", this.acertos);
                }
                this.opcao1 = ler.nextInt();
                switch (this.opcao1) {
                    case 0:
                        System.out.println("Você desistiu, voltando para o menu...");
                        System.out.printf("você teve %d acertos%n", this.acertos);
                        return;
                    case 1:
                        atacar(ler, mapa, mapaTampado);
                        break;
                    default:
                        System.out.println("Número inválido, tente novamente...");
                        break;
                }
            } else {
                return;
            }

        } while (this.contador < 30);
        if (this.contador == 30) {
            System.out.println("Suas jogadas acabaram...");
            System.out.printf("Você abateu um total de %d barcos%n", this.acertos);
            this.opcao1 = 0;
        }
        // retorna os acertos caso o jogador use todas suas jogadas
        if (this.contador > 29) {
            System.out.printf("você teve %d acertos%n", this.acertos);
        }
    }

    // função/método que starta o jogo, dando a opção de fechar ou jogar
    // importante citar que o start serve para criar um mapa aleatório novo toda vez
    // que o jogador decide
    // jogar.
    private void comecar(int start, Scanner ler, int[][] mapa, String[][] mapaTampado) {
        this.start = ler.nextInt();
        do {
            switch (this.start) {
                case 0:
                    System.out.println("Saindo do programa...");
                    return;
                case 1:
                    criaMapaBinario(mapa);
                    geraMapa(mapaTampado);
                    System.out.println("\n\n   Esse é o campo de batalha!!\n");
                    printaMapa(mapaTampado);
                    leAtaque(ler, mapa, mapaTampado);
                    break;
                default:
                    System.out.println("Número inválido, tente novamente...");
                    this.opcao1 = start;
                    return;
            }
        } while (this.opcao1 != 0);

    }

    // cria o mapa de barcos, garantindo que tenha 10 barcos
    private void criaMapaBinario(int[][] mapa) {
        int posicao = 0;
        for (int i = 0; i < 10; i++) {
            int randomX = randomNumber(posicao);
            int randomY = randomNumber(posicao);
            if (mapa[randomX][randomY] != 1) {
                mapa[randomX][randomY] = 1;
            } else {
                i -= 1;
            }

        }
    }

    // revela onde todos os barcos estavam posicionados
    private void revelaMapa(int[][] mapa, String[][] mapaTampado) {
        for (int i = 2; i < mapaTampado.length; i++) {
            for (int j = 2; j < mapaTampado.length; j++) {
                if (mapa[j - 2][i - 2] == 0) {
                    mapaTampado[j][i] = "_";
                } else {
                    mapaTampado[j][i] = "O";
                }
            }
        }
        printaMapa(mapaTampado);
    }

    // método main, que roda todo o código
    public static void main(String[] args) {
        new BatalhaNaval();
    }
}
// a funcionalidade desistir não estava nos requesitos, porém ele não dificulta
// nenhum pouco a jogabilidade
// por conta disso acabamos mantendo essa função que já havia sido criada antes
// de revisarmos os requisitos
