import javax.swing.*;
import java.awt.*;

public class Main {

    static JLabel texto1, texto2, texto3;

    public static void main(String[] args){
        texto1 = new JLabel("abcde");//Texto exemplo 1
        texto2 = new JLabel("eqgqwg");//Texto exemplo 2
        texto3 = new JLabel("abqwgcde");//Texto exemplo 3

        JFrame frame = new JFrame();//Cria a janela

        frame.setLayout(new GridLayout(3,1));//Cria layout grid de 1 coluna com 3 linhas
        frame.add(texto1);//Adiciona texto 1 no layout
        frame.add(texto2);//Adiciona texto 2 no layout
        frame.add(texto3);//Adiciona texto 3 no layout

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Define que quando a janela for fechada o programa tbm sera fechado
        frame.setLocationRelativeTo(null); // Centralizado a posicao da tela
        frame.setSize(300, 100);//Define o tamanho da tela
        frame.setAlwaysOnTop(true);// Sempre aparente na tela
        frame.setUndecorated(true);// Remove a title bar
        frame.setVisible(true);//Mostra a visibilidade
    }

}
