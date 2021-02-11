package geekbrains.homework.lesson7;

import javax.swing.*;
import java.awt.*;

public class GameMap extends JPanel {
    public static final int GAME_MODE_HVSH = 0;
    public static final int GAME_MODE_HVSAI = 1;

    private int gameMod;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winStreak;
    private int[][] field;

    private int cellWidth;
    private int cellHeight;

    private boolean isMapExist;

    GameMap() {
        setBackground(Color.YELLOW);
        isMapExist = false;
    }

    void start (int gameMod, int fieldSizeX, int fieldSizeY, int winStreak) {
        this.gameMod = gameMod;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winStreak = winStreak;
        field = new int [fieldSizeX][fieldSizeY];
        isMapExist = true;
        repaint();
    }

    private void render (Graphics g) {
        if (!isMapExist) return;

        int width = getWidth();
        int height = getHeight();

        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;
        g.setColor(Color.GRAY);

        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width,y);
        }
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        render (g);
    }

}
