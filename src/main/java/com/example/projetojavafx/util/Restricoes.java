package com.example.projetojavafx.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class Restricoes {
    public static void CPF(TextField textField) {
        UnaryOperator<TextFormatter.Change> mascaraCPF = change -> {
            String texto = change.getControlNewText().replaceAll("[^\\d]", "");


            if (texto.length() > 11) {
                return null;
            }

            StringBuilder textoFormatado = new StringBuilder(texto);


            if (texto.length() > 3) {
                textoFormatado.insert(3, '.');
            }
            if (texto.length() > 6) {
                textoFormatado.insert(7, '.');
            }
            if (texto.length() > 9) {
                textoFormatado.insert(11, '-');
            }

            int posicaoOriginalCursor = change.getCaretPosition();
            change.setText(textoFormatado.toString());
            change.setRange(0, change.getControlText().length()); // Substitui todo o texto
            change.selectRange(Math.min(posicaoOriginalCursor + 1, textoFormatado.length()), Math.min(posicaoOriginalCursor + 1, textoFormatado.length()));

            return change;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(mascaraCPF);
        textField.setTextFormatter(textFormatter);
    }
}