package geekbrains.homework.lesson7;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {
    private final int SETTINGS_WIDTH = 350;
    private final int SETTINGS_HEIGHT = 300;

    private final int MIN_FIELD_SIZE = 3;
    private final int MAX_FIELD_SIZE = 10;
    private final int MIN_WIN_STREAK = 3;

    private GameWindow GameWindow;

    private JRadioButton humVsHum;
    private JRadioButton humVsAi;
    private JSlider sliderWinStreak;
    private JSlider sliderFieldSIze;
    private JButton btnStart;

    private final String PREFIX_FIELD_SIZE = "Размер поля: ";
    private final String PREFIX_WIN_STREAK = "Победная серия: ";


    Settings(GameWindow GameWindow) {
        this.GameWindow = GameWindow;
        setSize(SETTINGS_WIDTH, SETTINGS_HEIGHT);

        Rectangle settingsBounds = GameWindow.getBounds();
        int settingsX = (int) settingsBounds.getCenterX() - SETTINGS_WIDTH / 2;
        int settingsY = (int) settingsBounds.getCenterY() - SETTINGS_HEIGHT / 2;
        setLocation(settingsX, settingsY);

        setResizable(false);
        setTitle("Game Settings");

        setLayout(new GridLayout(10, 1));

        gameModeRadioBtn();
        gameSliders();

        btnStart = new JButton("Start Game");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            buttonClickStart();
            }
        });
        add(btnStart);

    }

    private void gameModeRadioBtn () {
        add(new JLabel("Режим игры"));
        humVsHum = new JRadioButton("Два игрока", true);
        humVsAi = new JRadioButton("Игрок против компьютера");
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humVsHum);
        gameMode.add(humVsAi);

        add(humVsHum);
        add(humVsAi);
    }

    private void gameSliders () {
        JLabel labelFieldSIze = new JLabel(PREFIX_FIELD_SIZE + MIN_FIELD_SIZE);
        JLabel labelWinStreak = new JLabel(PREFIX_WIN_STREAK + MIN_WIN_STREAK);

        sliderFieldSIze = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        sliderFieldSIze.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = sliderFieldSIze.getValue();
                labelFieldSIze.setText(PREFIX_FIELD_SIZE + currentValue);
                sliderWinStreak.setMaximum(currentValue);

            }
        });

        sliderWinStreak = new JSlider(MIN_WIN_STREAK, MIN_FIELD_SIZE, MIN_WIN_STREAK);
        sliderWinStreak.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelWinStreak.setText(PREFIX_WIN_STREAK + sliderWinStreak.getValue());
            }
        });

        add(new JLabel(PREFIX_FIELD_SIZE));
        add(sliderFieldSIze);
        add(new JLabel(PREFIX_WIN_STREAK));
        add(sliderWinStreak);


        add(labelFieldSIze);
        add(labelWinStreak);
    }

    private void buttonClickStart() {
        int gameMode;
        if (humVsHum.isSelected()) {
            gameMode = GameMap.GAME_MODE_HVSH;
        } else if (humVsAi.isSelected()) {
            gameMode =GameMap.GAME_MODE_HVSAI;
        } else {
            throw new RuntimeException("Выберите режим игры");
        }

        int fieldSize = sliderFieldSIze.getValue();
        int winStreak = sliderWinStreak.getValue();

        GameWindow.startGame(gameMode, fieldSize, fieldSize, winStreak);
        setVisible(false);
    }

}
