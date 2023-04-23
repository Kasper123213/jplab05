package main;

import java.awt.*;
import java.util.Random;

public class Ball extends Thread {
    private int x;
    private final int y;
    private static int speed;
    private int direction;
    private final int d;

    public Ball(int y) {
        d = MyPanel.getField();
        x = MyPanel.getField() * (MyPanel.getColumns() - 1) / 2;
        direction = new Random().nextBoolean() ? 1 : -1;
        this.y = y;

    }

    public static void setSpeed(int value) {
        speed = value;
    }


    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.fillOval(x, y, d, d);
    }

    @Override
    public void run() {
        while (!(x <= 0 || x >= MyPanel.getColumns() * MyPanel.getField() - d)) {
            move();
            Frame.repaintMP();
            try {
                sleep((long) (100 / speed + Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (BallsThrower.notEnough()) {
                synchronized (MyPanel.board) {
                    MyPanel.board.notify();
                }
            }
        }

        try {
            if (direction < 0) {
                MyPanel.countersGet(y / MyPanel.getField()).incrementPoints();
            } else {
                MyPanel.countersGet(MyPanel.getRows() + y / MyPanel.getField()).incrementPoints();
            }
        } catch (IndexOutOfBoundsException e) {
        }


        BallsThrower.getBalls().remove(this);
    }

    private void move() {
        if (checkPlayers()) direction *= -1;
        x += direction;
    }

    private boolean checkPlayers() {
        for (Player player : MyPanel.getPlayers()) {
            if (player.getX() == x + MyPanel.getField() * direction && y + d / 2 >= player.getY() && y + d / 2 <= player.getY() + player.getA())
                return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getDirection() {
        return direction;
    }

    public int getD() {
        return d;
    }
}