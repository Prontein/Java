package geekbrains.homework.lesson7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private final int GAME_WIDTH = 500;
    private final int GAME_HEIGHT = 550;
    private final int WINDOW_X = 650;
    private final int WINDOW_Y = 250;

    private Settings Settings;
    private GameMap GameMap;


    GameWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLocation(WINDOW_X, WINDOW_Y);
        setTitle("The Game");
        Settings = new Settings(this);
        GameMap = new GameMap();

        JButton btnStartGame = new JButton("Start New Game");
        btnStartGame.setBackground(Color.cyan);
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.setVisible(true);
            }
        });

        JButton btnExitGame = new JButton ("Exit Game");
        btnExitGame.setBackground(Color.cyan);
        btnExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 2  ));

        btnPanel.add(btnStartGame);
        btnPanel.add(btnExitGame);

        add(btnPanel, BorderLayout.SOUTH);
        add(GameMap);

        setResizable(false);
        setVisible(true);

    }

    void startGame (int gameMod, int fieldSizeX, int fieldSizeY, int winStreak) {
        GameMap.start(gameMod, fieldSizeX, fieldSizeY, winStreak);
    }



}
