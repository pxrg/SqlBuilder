/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author paulo.gomes
 */
public abstract class StringUtils {

    public static String capitalize(String string) {
        StringBuilder aux = new StringBuilder();
        String[] palavras = string.toLowerCase().split(" ");
         for (int i = 0; i < palavras.length; i++) {
            char letra = palavras[i].charAt(0);
            aux.append(Character.toUpperCase(letra)).append(palavras[i].substring(1)).append(" ");
        }
        return String.valueOf(aux.toString().trim());
    }
    
     public static String capitalizeCaseSensitive(String string) {
        StringBuilder aux = new StringBuilder();
        String[] palavras = string.split(" ");
         for (int i = 0; i < palavras.length; i++) {
            char letra = palavras[i].charAt(0);
            aux.append(Character.toUpperCase(letra)).append(palavras[i].substring(1)).append(" ");
        }
        return String.valueOf(aux.toString().trim());
    }
}
