package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyPanel extends JPanel {
    private static int playersInTeam;

    private static ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<Counter> counters = new ArrayList<Counter>();
    private static BallsThrower ballThrower;
    public static final Object board = new Object();


    private static int rows;
    private static int columns;
    private static int field;
    private static int width;
    private static int height;


    public MyPanel(int width, int height) {

        setBackground(Color.gray);
        this.width = width;
        this.height = height;

    }

    public static void startGame() {

        field = Math.min(width / columns, height / rows);

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < rows; i++) {
                counters.add(new Counter(i, j));
            }


            for (int i = 0; i < playersInTeam; i++) {
                players.add(new Player(j + 1));
                players.get(players.size() - 1).start();
            }
        }

        ballThrower = null;
        ballThrower = new BallsThrower();
        ballThrower.start();
    }

    public static boolean isAavailable(int y, int x) {
        for (Player player : players) {
            if (player.getY() == y && player.getX() == x) return false;
        }
        return true;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static Counter countersGet(int i) {
        return counters.get(i);
    }

    public static int getField() {
        return field;
    }

    public static int getRows() {
        return rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static void setColumns(int value) {
        columns = value;
    }

    public static void clearCounters() {
        counters.clear();
    }

    public static void setRows(int value) {
        rows = value;
    }

    public static void setPlayerNumber(int value) {
        playersInTeam = value;
    }

    public static void clearPlayers() {
        for (Player player : players) player.stop();
        players.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        //pionowe osie
        for (int i = 1; i <= columns; i++) {
            g.drawLine(i * field, 0, i * field, field * rows);
        }

        //poziome osie
        for (int i = 1; i <= rows; i++) {
            g.drawLine(0, i * field, columns * field, i * field);
        }


        for (int i = 0; i < BallsThrower.getBalls().size(); i++) {
            try {
                BallsThrower.getBalls().get(i).draw(g);
            } catch (NullPointerException e) {
            }
        }
        for (Player player : players) {
            player.draw(g);
        }
        for (Counter counter : counters) {
            counter.draw(g);
        }
    }
}