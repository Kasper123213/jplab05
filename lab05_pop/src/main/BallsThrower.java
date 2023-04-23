package main;

import java.util.ArrayList;
import java.util.Random;

public class BallsThrower extends Thread {
    private static int maxBalls;
    private static int minBalls;
    private static int speed;
    private static ArrayList<Ball> balls = new ArrayList<>();

    public static boolean isAvailableY(int yToTry) {
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i) == null) continue;
            if (balls.get(i).getY() == yToTry) return false;
        }
        return true;
    }

    public static ArrayList<Ball> getBalls() {
        return balls;
    }

    public static boolean notEnough() {
        return balls.size() < minBalls;
    }

    public static void setMinBalls(int value) {
        minBalls = value;
    }

    public static void setMaxBalls(int value) {
        maxBalls = value;
    }

    public static void setSpeed(int value) {
        speed = value;
    }

    public static void clearBalls() {
        for (Ball ball : balls) ball.stop();

        balls.clear();
    }


    private void throwBall() {
        Random random = new Random();
        int y;
        do {
            y = MyPanel.getField() * random.nextInt(MyPanel.getRows());
        } while (!isAvailableY(y));
        balls.add(new Ball(y));
        try {
            balls.get(balls.size() - 1).start();
        } catch (IllegalThreadStateException | NullPointerException e) {
        }

    }

    @Override
    public void run() {
        while (true) {

            if (balls.size() < maxBalls) {
                throwBall();
            }
            synchronized (MyPanel.board) {
                try {
                    MyPanel.board.wait((long) (10000 / speed + Math.random() * 10000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}