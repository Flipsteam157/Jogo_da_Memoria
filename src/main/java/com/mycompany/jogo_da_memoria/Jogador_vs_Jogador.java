/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jogo_da_memoria;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author arthu
 */
public class Jogador_vs_Jogador extends Jogo {

    public Jogador_vs_Jogador() {
        super();
        try{
            Font gameboy = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/pokemon/Early GameBoy.ttf"));
            Font gameboy12 = gameboy.deriveFont(12f);
            Font gameboy18 = gameboy.deriveFont(18f);
            
            Font pokemon = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/pokemon/Pokemon Hollow.ttf"));
            Font pokemon30 = pokemon.deriveFont(30f);
            
            placar1.setFont(gameboy12);
            placar2.setFont(gameboy12);
            controle_vez.setFont(gameboy12);
            
            this.getContentPane().setBackground(Color.yellow);
            this.setTitle("Jogador 1 vs Jogador 2");
        }
        catch (FontFormatException | IOException e) {
            System.out.println("Erro na Fonte");
        }
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
        } else {

            // Desabilitar todos os botões antes de qualquer ação
            bloquearBotoes(false);

            valor_da_casa2 = tabuleiro.board[posicao];
            posicao_da_casa2 = posicao;
            ImageIcon icone = new ImageIcon(imagens[valor_da_casa2 - 1]);
            Image imagem = icone.getImage();
            Image imagemFinal = imagem.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon iconeFinal = new ImageIcon(imagemFinal);
            botoes[posicao_da_casa2].setIcon(iconeFinal);
            //botoes[posicao_da_casa2].setText(String.valueOf(valor_da_casa2));

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

                if (placar_jogador1 + placar_jogador2 == 8 && placar_jogador1 > placar_jogador2) {
                    controle_vez.setText("VENCEDOR: JOGADOR 1");
                } else if (placar_jogador1 + placar_jogador2 == 8 && placar_jogador1 < placar_jogador2) {
                    controle_vez.setText("VENCEDOR: JOGADOR 2");
                } else if (placar_jogador1 + placar_jogador2 == 8 && placar_jogador1 == placar_jogador2){
                    controle_vez.setText("EMPATE");
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
                        controle_vez.setText("Vez do jogador: " + String.valueOf(vez_de_quem));
                    } else {
                        vez_de_quem = 1;
                        controle_vez.setText("Vez do jogador: " + String.valueOf(vez_de_quem));
                    }

                    bloquearBotoes(true);
                });
                timer.setRepeats(false); // Garantir que o Timer só execute uma vez
                timer.start();
            }

        }
    }
}
