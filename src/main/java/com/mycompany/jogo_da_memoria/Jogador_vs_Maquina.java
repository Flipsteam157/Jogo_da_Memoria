package com.mycompany.jogo_da_memoria;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.Timer;

public class Jogador_vs_Maquina extends Jogo {

    private final Map<Integer, Integer> memoriaCartas;
    private final Random random;

    public Jogador_vs_Maquina() {
        super();
        memoriaCartas = new HashMap<>();
        random = new Random();
    }

    @Override
    public void rodada(int verificar, int posicao) {
        posicao -= 1;

        if (verificar == 1) {
            valor_da_casa1 = tabuleiro.board[posicao];
            posicao_da_casa1 = posicao;
            botoes[posicao_da_casa1].setText(String.valueOf(valor_da_casa1));
        } else {
            bloquearBotoes(false);

            valor_da_casa2 = tabuleiro.board[posicao];
            posicao_da_casa2 = posicao;
            botoes[posicao_da_casa2].setText(String.valueOf(valor_da_casa2));

            if (valor_da_casa1 == valor_da_casa2) {
                vetor_verificador[posicao_da_casa1] = valor_da_casa1;
                vetor_verificador[posicao_da_casa2] = valor_da_casa2;
                botoes[posicao_da_casa1].setEnabled(false);
                botoes[posicao_da_casa2].setEnabled(false);

                if (vez_de_quem == 1) {
                    placar_jogador1 += 1;
                    placar1.setText("Jogador 1: " + String.valueOf(placar_jogador1) + " Pontos");
                } else {
                    placar_jogador2 += 1;
                    placar2.setText("Jogador 2: " + String.valueOf(placar_jogador2) + " Pontos");
                }

                if (placar_jogador1 + placar_jogador2 == 8) {
                    controle_vez.setText("JOGO ACABOU");
                } else {
                    bloquearBotoes(true);
                    if (vez_de_quem == 2) {
                        maquinaFazJogada();
                    }
                }
            } else {
             
                Timer timer = new Timer(1000, e -> {
                    botoes[posicao_da_casa1].setText("");
                    botoes[posicao_da_casa2].setText("");

                    if (vez_de_quem == 1) {
                        vez_de_quem = 2;
                        controle_vez.setText("Vez do: " + String.valueOf(vez_de_quem));
                    } else {
                        vez_de_quem = 1;
                        controle_vez.setText("Vez do: " + String.valueOf(vez_de_quem));
                    }

                    bloquearBotoes(true);
                    if (vez_de_quem == 2) {
                        maquinaFazJogada();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void maquinaFazJogada() {
            realizarJogadaAleatoria();
    }

    private void realizarJogada(int pos1, int pos2) {
        // Realiza a jogada (revela as cartas)
        botoes[pos1].setText(String.valueOf(tabuleiro.board[pos1]));
        botoes[pos2].setText(String.valueOf(tabuleiro.board[pos2]));

        Timer timer = new Timer(1000, e -> {
            if (tabuleiro.board[pos1] == tabuleiro.board[pos2]) {
                vetor_verificador[pos1] = tabuleiro.board[pos1];
                vetor_verificador[pos2] = tabuleiro.board[pos2];
                botoes[pos1].setEnabled(false);
                botoes[pos2].setEnabled(false);

                memoriaCartas.remove(tabuleiro.board[pos1]);
                memoriaCartas.remove(tabuleiro.board[pos2]);

                if (placar_jogador1 + placar_jogador2 == 8) {
                    controle_vez.setText("JOGO ACABOU");
                }
            } else {
                botoes[pos1].setText("");
                botoes[pos2].setText("");
                vez_de_quem = 1;
                controle_vez.setText("Vez do: " + String.valueOf(vez_de_quem));
            }

            bloquearBotoes(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void realizarJogadaAleatoria() {
        int pos1 = random.nextInt(16);
        int pos2;

        do {
            pos2 = random.nextInt(16);
        } while (pos1 == pos2 || vetor_verificador[pos2] != 0);

        realizarJogada(pos1, pos2);
    }
}
