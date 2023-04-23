package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SliderPanel extends JPanel {

    private JSlider columns = new JSlider(4, 15, 4);
    private JSlider rows = new JSlider(10, 20, 10);
    private JSlider minBalls = new JSlider(3, 15, 3);
    private JSlider maxBalls = new JSlider(3, 15, 6);
    private JSlider playersNumber = new JSlider(2, 10, 2);
    private JSlider playersSpeed = new JSlider(1, 100, 20);
    private JSlider ballsSpeed = new JSlider(1, 100, 15);
    private JSlider ballThrowerSpeed = new JSlider(1, 100, 20);


    private JLabel columnsLabel = new JLabel();
    private JLabel rowsLabel = new JLabel();
    private JLabel minBallsLabel = new JLabel();
    private JLabel maxBallsLabel = new JLabel();
    private JLabel playersNumberLabel = new JLabel();
    private JLabel playersSpeedLabel = new JLabel();
    private JLabel ballsSpeedLabel = new JLabel();
    private JLabel ballThrowerSpeedLabel = new JLabel();

    private String text1 = "Minimalna ilosc pilek: ";
    private String text2 = "Maksymalna ilosc pilek: ";
    private String text3 = "Liczba graczy na drozyne: ";
    private String text4 = "Predkosc graczy: ";
    private String text5 = "Predkosc pilek: ";
    private String text6 = "predkosc wyzucania pilek: ";
    private String text7 = "Liczba kolumn: ";
    private String text8 = "Liczba rzedow: ";

    private JButton start = new JButton("Start/Reset");

    public SliderPanel() {

        setLayout(new GridLayout(9, 2));

        add(columns);
        columnsLabel.setText(text7 + (columns.getValue() * 2 + 1));
        add(columnsLabel);
        columns.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                columnsLabel.setText(text7 + (columns.getValue() * 2 + 1));

            }
        });


        add(rows);
        rowsLabel.setText(text8 + rows.getValue());
        add(rowsLabel);
        rows.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rowsLabel.setText(text8 + rows.getValue());
                boolean isOK = checkMinMaxRows();

                if (playersNumber.getValue() <= rows.getValue() && isOK) {
                    start.setEnabled(true);
                    rows.setBackground(null);
                    playersNumber.setBackground(null);
                } else if (playersNumber.getValue() > rows.getValue()) {
                    start.setEnabled(false);
                    rows.setBackground(Color.red);
                    playersNumber.setBackground(Color.red);
                }
            }
        });


        add(minBalls);
        minBallsLabel.setText(text1 + minBalls.getValue());
        add(minBallsLabel);
        minBalls.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                minBallsLabel.setText(text1 + minBalls.getValue());
                checkMinMaxRows();
            }
        });


        add(maxBalls);
        maxBallsLabel.setText(text2 + maxBalls.getValue());
        add(maxBallsLabel);
        maxBalls.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                maxBallsLabel.setText(text2 + maxBalls.getValue());
                checkMinMaxRows();
            }
        });


        add(playersNumber);
        playersNumberLabel.setText(text3 + playersNumber.getValue());
        add(playersNumberLabel);
        playersNumber.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                playersNumberLabel.setText(text3 + playersNumber.getValue());
                if (playersNumber.getValue() <= rows.getValue()) {
                    start.setEnabled(true);
                    rows.setBackground(null);
                    playersNumber.setBackground(null);
                } else {
                    start.setEnabled(false);
                    rows.setBackground(Color.red);
                    playersNumber.setBackground(Color.red);
                }
            }
        });


        add(playersSpeed);
        playersSpeedLabel.setText(text4 + playersSpeed.getValue());
        add(playersSpeedLabel);
        playersSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                playersSpeedLabel.setText(text4 + playersSpeed.getValue());
            }
        });


        add(ballsSpeed);
        ballsSpeedLabel.setText(text5 + ballsSpeed.getValue());
        add(ballsSpeedLabel);
        ballsSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ballsSpeedLabel.setText(text5 + ballsSpeed.getValue());
            }
        });


        add(ballThrowerSpeed);
        ballThrowerSpeedLabel.setText(text6 + ballThrowerSpeed.getValue());
        add(ballThrowerSpeedLabel);
        ballThrowerSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ballThrowerSpeedLabel.setText(text6 + ballThrowerSpeed.getValue());
            }
        });


        add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyPanel.setColumns(columns.getValue() * 2 + 1);
                MyPanel.setRows(rows.getValue());
                BallsThrower.setMinBalls(minBalls.getValue());
                BallsThrower.setMaxBalls(maxBalls.getValue());
                MyPanel.setPlayerNumber(playersNumber.getValue());
                Player.setSpeed(playersSpeed.getValue());
                Ball.setSpeed(ballsSpeed.getValue());
                BallsThrower.setSpeed(ballThrowerSpeed.getValue());


                MyPanel.clearPlayers();
                BallsThrower.clearBalls();
                MyPanel.clearCounters();
                MyPanel.startGame();

            }
        });
    }

    private boolean checkMinMaxRows() {
        if (minBalls.getValue() <= maxBalls.getValue() && maxBalls.getValue() <= rows.getValue()) {
            start.setEnabled(true);
            minBalls.setBackground(null);
            maxBalls.setBackground(null);
            rows.setBackground(null);
            return true;
        } else if (maxBalls.getValue() <= minBalls.getValue() && minBalls.getValue() <= rows.getValue()) {
            start.setEnabled(false);
            minBalls.setBackground(Color.red);
            maxBalls.setBackground(Color.red);
            rows.setBackground(null);
            return true;
        } else if (minBalls.getValue() <= rows.getValue() && rows.getValue() <= maxBalls.getValue()) {
            start.setEnabled(false);
            minBalls.setBackground(null);
            maxBalls.setBackground(Color.red);
            rows.setBackground(Color.red);
            return false;
        } else {
            start.setEnabled(false);
            minBalls.setBackground(Color.red);
            maxBalls.setBackground(Color.red);
            rows.setBackground(Color.red);
            return false;
        }

    }
}