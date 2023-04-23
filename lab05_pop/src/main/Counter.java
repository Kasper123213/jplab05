package main;

import java.awt.*;

public class Counter {
    private final int x;
    private final int y;
    private int points;

    public Counter(int i, int j) {
        points=0;
        y = (int) ((i + 0.7) * MyPanel.getField());
        x = (int) ((j * (MyPanel.getColumns() - 1) + 0.2) * MyPanel.getField());
    }


    public void draw(Graphics g) {

        g.setFont(new Font(null, Font.PLAIN, Math.min(300 / MyPanel.getRows(), 300 / MyPanel.getColumns())));
        String tekst = points > 9 ? String.valueOf(points) : "0" + points;
        g.drawString(tekst, x, y);
    }

    public void incrementPoints() {
        points += 1;
    }


}