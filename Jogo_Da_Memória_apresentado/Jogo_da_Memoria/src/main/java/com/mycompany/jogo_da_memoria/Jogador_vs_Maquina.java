package com.mycompany.jogo_da_memoria;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Jogador_vs_Maquina extends Jogo {

    private final Map<Integer, Integer> memoria = new HashMap<>();
    private final Random random = new Random();
    private JButton voltarButton;
    

    public Jogador_vs_Maquina() {
        super();
        placar2.setText("Maquina: 0 Pontos");
        Font gameboy12 = null;
        try{
            Font gameboy = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/pokemon/Early GameBoy.ttf"));
            gameboy12 = gameboy.deriveFont(12f);
            Font gameboy18 = gameboy.deriveFont(18f);
            
            Font pokemon = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/pokemon/Pokemon Hollow.ttf"));
            Font pokemon30 = pokemon.deriveFont(30f);
            
            placar1.setFont(gameboy12);
            placar2.setFont(gameboy12);
            controle_vez.setFont(gameboy12);
            
            this.getContentPane().setBackground(Color.yellow);
            this.setTitle("Jogador vs Máquina");
        }
        catch (FontFormatException | IOException e) {
            System.out.println("Erro na Fonte");
        }
        voltarButton = new JButton("Voltar");
        voltarButton.setFont(gameboy12);  
        voltarButton.setBounds((this.getWidth() - voltarButton.getPreferredSize().width) / 2, 
        (this.getHeight() - voltarButton.getPreferredSize().height) / 2, 
        180, 30); 
        voltarButton.setVisible(false);
        voltarButton.addActionListener(e -> {
        pararMusica();
        Tela_inicial telaInicial = new Tela_inicial();
        telaInicial.setVisible(true);
        this.dispose();
    });
    this.setLayout(null); // Ajuste a posição e tamanho conforme necessário
    this.add(voltarButton);
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
                    placar2.setText("Maquina: " + placar_jogador2 + " Pontos");
                }

                if (placar_jogador1 + placar_jogador2 == 8) {
                    controle_vez.setText("JOGO ACABOU");
                    mostrarVencedor();
                    voltarButton.setVisible(true); 
                    for (JButton botoesCasa : botoes) {
                        botoesCasa.setVisible(false); // Esconde todos os botões das casas
                    }
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
                        controle_vez.setText("Vez da maquina");
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

        // Escolhe a primeira posição aleatória caso nao tenha nenhum par na memoria
        int posicao1 = escolherPosicaoAleatoria();
        memoria.put(posicao1, tabuleiro.board[posicao1]);

        // Verifica se a primeira posição forma um par com algum valor na memória
        for (Map.Entry<Integer, Integer> entry : memoria.entrySet()) {
            int posicaoMemoria = entry.getKey();
            int valorMemoria = entry.getValue();

            if (posicao1 != posicaoMemoria && tabuleiro.board[posicao1] == valorMemoria && vetor_verificador[posicaoMemoria] == 0) {
                // Jogar no par encontrado
                executarJogada(posicao1 + 1, posicaoMemoria + 1);
                return;
            }
        }

        // Caso não encontre par, escolhe a segunda posição aleatória
        int posicao2 = escolherPosicaoAleatoria();
        while (posicao1 == posicao2) {
            posicao2 = escolherPosicaoAleatoria();
        }
        memoria.put(posicao2, tabuleiro.board[posicao2]);

        // Realiza a jogada
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
            controle_vez.setText("VENCEDOR: MAQUINA");
        } else {
            controle_vez.setText("EMPATE");
        }
    }
}