package com.mycompany.jogo_da_memoria;

import java.awt.Image;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;

public class Jogador_vs_Maquina extends Jogo {

    private final Map<Integer, Integer> memoria = new HashMap<>();
    private final Random random = new Random();

    public Jogador_vs_Maquina() {
        super();
        placar2.setText("Máquina: 0 Pontos");
    }

    @Override
    public void rodada(int verificar, int posicao) {
        posicao -= 1;

        if (verificar == 1) {
            valor_da_casa1 = tabuleiro.board[posicao];
            posicao_da_casa1 = posicao;
            ImageIcon icone = new ImageIcon(imagens[valor_da_casa1 - 1]);
            Image imagem = icone.getImage();
            Image imagemFinal = imagem.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon iconeFinal = new ImageIcon(imagemFinal);
            botoes[posicao_da_casa1].setIcon(iconeFinal);
            //botoes[posicao_da_casa1].setText(String.valueOf(valor_da_casa1));
            memoria.put(posicao_da_casa1, valor_da_casa1);
        } else {
            valor_da_casa2 = tabuleiro.board[posicao];
            posicao_da_casa2 = posicao;
            ImageIcon icone = new ImageIcon(imagens[valor_da_casa2 - 1]);
            Image imagem = icone.getImage();
            Image imagemFinal = imagem.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon iconeFinal = new ImageIcon(imagemFinal);
            botoes[posicao_da_casa2].setIcon(iconeFinal);
            //botoes[posicao_da_casa2].setText(String.valueOf(valor_da_casa2));
            memoria.put(posicao_da_casa2, valor_da_casa2);

            if (valor_da_casa1 == valor_da_casa2) {
                vetor_verificador[posicao_da_casa1] = valor_da_casa1;
                vetor_verificador[posicao_da_casa2] = valor_da_casa2;
                botoes[posicao_da_casa1].setEnabled(false);
                botoes[posicao_da_casa2].setEnabled(false);

                if (vez_de_quem == 1) {
                    placar_jogador1 += 1;
                    placar1.setText("Jogador 1: " + placar_jogador1 + " Pontos");
                } else {
                    placar_jogador2 += 1;
                    placar2.setText("Máquina: " + placar_jogador2 + " Pontos");
                }

                if (placar_jogador1 + placar_jogador2 == 8) {
                    controle_vez.setText("JOGO ACABOU");
                    mostrarVencedor();
                } else {
                    // Máquina continua jogando se for a vez dela
                    if (vez_de_quem == 2) {
                        Timer timer = new Timer(1000, e -> {
                            maquinaJoga();
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        bloquearBotoes(true);
                    }
                }
            } else {
                // Usar um Timer para atrasar a ação de esconder as cartas
                Timer timer = new Timer(1000, e -> {
                    //botoes[posicao_da_casa1].setText("");
                    //botoes[posicao_da_casa2].setText("");
                    botoes[posicao_da_casa1].setIcon(null);
                    botoes[posicao_da_casa2].setIcon(null);

                    if (vez_de_quem == 1) {
                        vez_de_quem = 2;
                        controle_vez.setText("Vez da máquina");
                        Timer machineTurnTimer = new Timer(500, evt -> maquinaJoga());
                        machineTurnTimer.setRepeats(false);
                        machineTurnTimer.start();
                    } else {
                        vez_de_quem = 1;
                        controle_vez.setText("Vez do jogador");
                    }

                    bloquearBotoes(true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void maquinaJoga() {
        // Verifica se a máquina tem algum par na memória
        for (Map.Entry<Integer, Integer> entry : memoria.entrySet()) {
            int posicao1 = entry.getKey();
            int valor1 = entry.getValue();

            for (Map.Entry<Integer, Integer> entry2 : memoria.entrySet()) {
                int posicao2 = entry2.getKey();
                int valor2 = entry2.getValue();

                if (posicao1 != posicao2 && valor1 == valor2 && vetor_verificador[posicao1] == 0 && vetor_verificador[posicao2] == 0) {
                    // Encontrou um par
                    executarJogada(posicao1 + 1, posicao2 + 1);
                    return;
                }
            }
        }

        // Se não encontrou um par, escolhe posições aleatórias
        int posicao1 = escolherPosicaoAleatoria();
        int posicao2 = escolherPosicaoAleatoria();

        while (posicao1 == posicao2) {
            posicao2 = escolherPosicaoAleatoria();
        }

        // Armazena as posições aleatórias reveladas pela máquina na memória
        memoria.put(posicao1, tabuleiro.board[posicao1]);
        memoria.put(posicao2, tabuleiro.board[posicao2]);

        executarJogada(posicao1 + 1, posicao2 + 1);
    }

    private int escolherPosicaoAleatoria() {
        int posicao;
        do {
            posicao = random.nextInt(16);
        } while (vetor_verificador[posicao] != 0);
        return posicao;
    }

    private void executarJogada(int posicao1, int posicao2) {
        // Simula a jogada da máquina
        botoes[posicao1 - 1].doClick();
        Timer timer = new Timer(1000, e -> botoes[posicao2 - 1].doClick());
        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarVencedor() {
        if (placar_jogador1 > placar_jogador2) {
            controle_vez.setText("VENCEDOR: JOGADOR 1");
        } else if (placar_jogador1 < placar_jogador2) {
            controle_vez.setText("VENCEDOR: MÁQUINA");
        } else {
            controle_vez.setText("EMPATE");
        }
    }
}