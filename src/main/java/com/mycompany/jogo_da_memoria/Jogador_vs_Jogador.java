/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jogo_da_memoria;

import javax.swing.Timer;

/**
 *
 * @author arthu
 */
public class Jogador_vs_Jogador extends Jogo {

    public Jogador_vs_Jogador() {
        super();
    }
    
    @Override
    public void rodada(int verificar, int posicao) {

        posicao -= 1;

        if (verificar == 1) {
            valor_da_casa1 = tabuleiro.board[posicao];
            posicao_da_casa1 = posicao;
            botoes[posicao_da_casa1].setText(String.valueOf(valor_da_casa1));
        } else {

            // Desabilitar todos os botões antes de qualquer ação
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
                }

            } else {
                // Usar um Timer para atrasar a ação de esconder as cartas
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
                });
                timer.setRepeats(false); // Garantir que o Timer só execute uma vez
                timer.start();
            }

        }
    }
}
