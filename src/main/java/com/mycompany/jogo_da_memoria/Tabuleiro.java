/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jogo_da_memoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author arthu
 */
public class Tabuleiro {
    
    public int[] board;
    private final int SIZE = 16;

    public Tabuleiro() {
        board = new int[SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        List<Integer> numbers = new ArrayList<>();
        // Adiciona pares de 1 a 8
        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        // Embaralha os números
        Collections.shuffle(numbers);
        
        // Preenche a matriz com os números embaralhados
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            board[i] = numbers.get(index++);       
        }
    }

    //public int getNumberAt(int row, int col) {
    //    return board[row][col];
    //}
}
