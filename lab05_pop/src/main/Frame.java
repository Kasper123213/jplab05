package main;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {


    private JPanel contentPane;
    private static MyPanel mp;
    private static SliderPanel sp;


    private final int width = 900;
    private final int height = 600;
    private final double proportions = 0.6;

    public Frame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("LAB 05");
        setAlwaysOnTop(true);


        mp = new MyPanel((int) (width * proportions), height);
        mp.setBounds(0, 0, (int) (width * proportions), height);

        sp = new SliderPanel();
        sp.setBounds((int) (width * proportions), 0, (int) (width * (1 - proportions)), height);

        contentPane.add(mp);
        contentPane.add(sp);

    }

    public static void repaintMP() {
        if (mp != null) {
            mp.repaint();
        }
    }
}