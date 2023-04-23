package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class Player extends Thread {
    private final int x;
    private int y;
    private final int a;
    private final int teamnumber;
    private static int speed;

    public Player(int teamnumber) {
        this.teamnumber = teamnumber;
        x = teamnumber == 1 ? MyPanel.getField() : MyPanel.getField() * (MyPanel.getColumns() - 2);
        a = MyPanel.getField();
        Random random = new Random();
        int yToTry;
        do {
            yToTry = MyPanel.getField() * random.nextInt(MyPanel.getRows());
        } while (!MyPanel.isAavailable(yToTry, x));
        y = yToTry;
    }

    public static void setSpeed(int value) {
        speed = value;
    }


    private void move() {
        int min = Integer.MAX_VALUE;
        int value, yBall = -1;


        for (int i = 0; i < BallsThrower.getBalls().size(); i++) {
            try {
                value = (int) Math.sqrt(Math.pow(BallsThrower.getBalls().get(i).getY() - y, 2) + Math.pow(BallsThrower.getBalls().get(i).getX() - x, 2));
                if (value < min && findBallDirection(BallsThrower.getBalls().get(i)) && !otherPlayersAreProblem(BallsThrower.getBalls().get(i))) {
                    min = value;
                    yBall = BallsThrower.getBalls().get(i).getY();
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
            }
        }

        if (y < yBall) y++;
        else if (y > yBall && yBall != -1) y--;

    }

    private boolean otherPlayersAreProblem(Ball ball) {
        ArrayList<Integer> listBigger = new ArrayList<>();
        ArrayList<Integer> listLower = new ArrayList<>();

        for (int i = 0; i < MyPanel.getPlayers().size(); i++) {
            if (MyPanel.getPlayers().get(i) == this) continue;

            if ((ball.getDirection() < 0 && MyPanel.getPlayers().get(i).getTeam() == 1) || (ball.getDirection() > 0 && MyPanel.getPlayers().get(i).getTeam() == 2)) {
                if (MyPanel.getPlayers().get(i).getY() < y) listLower.add(MyPanel.getPlayers().get(i).getY());
                else listBigger.add(MyPanel.getPlayers().get(i).getY());

                if (MyPanel.getPlayers().get(i).getY() <= y + a + 1 && MyPanel.getPlayers().get(i).getY() + MyPanel.getPlayers().get(i).getA() >= y + a + 1 && ball.getY() > y)
                    return true;

                if (MyPanel.getPlayers().get(i).getY() + MyPanel.getPlayers().get(i).getA() >= y - 1 && MyPanel.getPlayers().get(i).getY() <= y - 1 && ball.getY() < y)
                    return true;

            }
        }

        if (!listBigger.isEmpty()) {
            if (ball.getY() >= Collections.min(listBigger)) return true;
        }

        if (!listLower.isEmpty()) {
            if (ball.getY() - ball.getD() <= Collections.max(listLower) + a) return true;
        }

        return false;
    }

    public int getTeam() {
        return teamnumber;
    }

    private boolean findBallDirection(Ball ball) {
        if ((teamnumber == 1 && ball.getDirection() < 0)) {
            if (x < ball.getX()) return true;
            else return false;
        } else if (teamnumber == 2 && ball.getDirection() > 0) {
            if (x > ball.getX()) return true;
            else return false;
        } else return false;
    }


    @Override
    public void run() {
        while (true) {
            move();
            Frame.repaintMP();

            try {
                sleep((int) (100 / speed + Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, a, a);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getA() {
        return a;
    }


}