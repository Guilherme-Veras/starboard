import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements NativeKeyListener {

    private static JLabel texto1, texto2, texto3;

    ArrayList<String> possibilidades = new ArrayList<>();

    private ArrayList<Integer> palavra = new ArrayList<>();
    //ArrayList<ArrayList<Integer>> historico = new ArrayList<>();
    ArrayList<Integer> apertados = new ArrayList<>();

    static Node nodes = new Node();

    private void escrever(String s){
        try {
            s = Normalizer.normalize(s, Normalizer.Form.NFD);
            s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            s = s.toUpperCase();
            System.out.println(s);
            GlobalScreen.unregisterNativeHook();
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            for (int i = 1; i < s.length(); i++) {
                robot.keyPress(s.charAt(i));
                robot.keyRelease(s.charAt(i));
            }
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String listToString(ArrayList<Integer> list){
        StringBuilder builder = new StringBuilder(list.size());
        for(Integer ch: list){
            builder.append(NativeKeyEvent.getKeyText(ch));
        }
        return builder.toString();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println(e.getKeyCode());
        if((e.getKeyCode() >= 16 && e.getKeyCode() <= 25) || (e.getKeyCode() >= 30 && e.getKeyCode() <= 38) || (e.getKeyCode() >= 44 && e.getKeyCode() <= 50)){//Apenas letras

            palavra.add(e.getKeyCode());
        }else if(e.getKeyCode() == 14) {
            if (palavra.size()>0) {
                palavra.remove(palavra.size() - 1);
            }
        }else{
            switch (e.getKeyCode()){
                case 15:
                    if (!possibilidades.get(0).equals("erro") && !possibilidades.get(0).equals("")){
                        System.out.println(possibilidades.get(0).replaceAll("[\\p{InCombiningDiacriticalMarks}]", ""));
                        escrever(possibilidades.get(0).substring(palavra.size()));
                    }
                    break;

            }


            //historico.add(palavra);
            palavra = new ArrayList<>();
        }

        System.out.println(listToString(palavra));

        possibilidades = nodes.getWords(listToString(palavra).toLowerCase());
        for (int i = 0; i < possibilidades.size(); i++) {//tirando # do fim de cada palavra
            possibilidades.set(i, possibilidades.get(i).substring(0, possibilidades.get(i).length()-1));
        }
        possibilidades.sort(Comparator.comparing(String::length));
        //System.out.println(possibilidades);
        if (!possibilidades.get(0).equals("erro")){
            texto1.setText(possibilidades.get(0));
            if (possibilidades.size()>1){
                texto2.setText(possibilidades.get(1));
            }else {
                texto2.setText("");
            }
            if (possibilidades.size()>2){
                texto3.setText(possibilidades.get(2));
            }else {
                texto3.setText("");
            }
        }else {
            texto1.setText("");
            texto2.setText("");
            texto3.setText("");
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + e.getKeyCode());
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public static Node setNodes(){
        Node nodes = new Node();
        Double tam = 0.0;
        Double i = 0.0;

        File file = new File("C:\\Users\\Guilherme\\Desktop\\Brilha Brilha Estrelinha\\dic.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            tam = Double.valueOf(sc.nextLine());
            while (sc.hasNextLine()) {
                nodes.setNodes(sc.nextLine().split("/")[0]);
                //System.out.println(sc.nextLine().split("/")[0]);
                i++;
                System.out.println(((int)((i/tam)*100))+"%");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        nodes.setNodes("abelha");
        nodes.setNodes("abacaxi");
        nodes.setNodes("abobrinha");
        nodes.setNodes("ababelar");
        nodes.setNodes("ababone");
        nodes.setNodes("ababoni");
        return nodes;
    }

    public static void main(String[] args) {

        nodes = setNodes();


        texto1 = new JLabel("abcde");
        texto2 = new JLabel("eqgqwg");
        texto3 = new JLabel("abqwgcde");


        JFrame frame = new JFrame();


        frame.setLayout(new GridLayout(3,1));
        frame.add(texto1);
        frame.add(texto2);
        frame.add(texto3);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centralizado
        frame.setSize(300, 100);
        frame.setAlwaysOnTop(true);// Sempre aparente na tela
        frame.setUndecorated(true);// Sem a title bar
        frame.setVisible(true);

        // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);


        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Main());
    }
}
