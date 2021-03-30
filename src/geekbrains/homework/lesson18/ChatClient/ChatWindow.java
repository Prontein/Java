package ru.geekbrains;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class ChatWindow extends JFrame implements Thread.UncaughtExceptionHandler {
    private final int CHAT_WIDTH = 600;
    private final int CHAT_HEIGHT = 600;
    private final int WINDOW_X = 650;
    private final int WINDOW_Y = 250;

    private final JTextArea messaging = new JTextArea();

    private final JList<String> contactList = new JList<>();
    private final JPanel messagingPanel = new JPanel(new BorderLayout());
    private final JPanel contactPanel = new JPanel(new BorderLayout());
    private final JLabel contacts = new JLabel("Contact List");

    private final JPanel contactListText = new JPanel();
    private final JPanel onlineAndBlacklist = new JPanel();
    private final JCheckBox contactsCheckStatus = new JCheckBox("Online");
    private final JButton btnBlacklist = new JButton("Blacklist");

    private final JPanel sendMessagePanel = new JPanel(new BorderLayout());
    private final JPanel settingsMessagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JTextField sendMessageField = new JTextField();
    private final JButton btnSendMessage = new JButton("Send");
    private final JButton btnSmile = new JButton("Smile");
    private final JButton btnFont = new JButton("Font");
    private final JButton btnAddFile = new JButton("File");
    private String[] users = new String[1000];

    private OneServerManager serverManager;

    private JButton btnStart1;
    private JButton btnStart2;
    private JButton btnStart3;

    private AuthForm AuthForm;

    ChatWindow() {

//        Установка параметров окна чата

        setLocation(WINDOW_X, WINDOW_Y);
        setSize(CHAT_WIDTH, CHAT_HEIGHT);

//        Панель верхнего меню чата

        JMenuBar mainBar = new JMenuBar();
        mainBar.setBackground(new Color(221, 243, 229, 37));
        JMenu Settings = new JMenu("Settings");

        mainBar.add(Settings);
        mainBar.add(createEditMenu());
        mainBar.add(createHelpMenu());
        setJMenuBar(mainBar);

// В настройки добавляю панель со сменой имени пользователя

        JMenuItem changeUsernameItem = new JMenuItem("Change username");
        changeUsernameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ChangeUsername = new JFrame();
                ChangeUsername.setTitle("Смена имени пользователя");
                ChangeUsername.setSize(300,300);
                ChangeUsername.setLocation(800, 400);
                ChangeUsername.setResizable(true);

                JLabel changeUsernameLabel = new JLabel("Введите новое имя пользователя ");
                JTextField changeUsernameField = new JTextField();
                JButton changeUsernameBtn = new JButton("Изменить");
                changeUsernameLabel.setBounds(40, 50, 250, 30);
                changeUsernameField.setBounds(70, 100, 150, 40);
                changeUsernameBtn.setBounds(100, 160, 100, 30);

                changeUsernameBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String changeUsername = changeUsernameField.getText();
                        if (changeUsername.equals("")) return;

                        startChangeUsername(changeUsername);

                        ChangeUsername.setVisible(false);
                    }
                });


                ChangeUsername.add(changeUsernameLabel);
                ChangeUsername.add(changeUsernameField);
                ChangeUsername.add(changeUsernameBtn);
                ChangeUsername.setLayout(null);
                ChangeUsername.setVisible(true);
            }
        });

        Settings.add(changeUsernameItem);

//      Создание нового окна авторизации

        AuthForm = new AuthForm(this);
        AuthForm.setVisible(true);
        AuthForm.setAlwaysOnTop(true);

//        Часть с текстовым полем и списком контактов

        contactPanel.setPreferredSize(new Dimension(150, 0));
        JScrollPane scrollMessages = new JScrollPane(messaging);
        JScrollPane scrollContacts = new JScrollPane();
        scrollContacts.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        contactList.setListData(users);
        contactList.addListSelectionListener(new ListSelectionListener() { // Позволяет выделять элемент в списке
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
        contactList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { // При двойном нажатии в списке убирает выделение
                if (e.getClickCount() == 2)
                contactList.clearSelection();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        scrollContacts.setViewportView(contactList);
        contactList.setLayoutOrientation(JList.VERTICAL);

        scrollContacts.setPreferredSize(new Dimension(100, 100));
        messaging.setBackground(new Color(183, 248, 235));
        messaging.setEditable(false);

        contactPanel.add(scrollContacts);
        messagingPanel.add(scrollMessages);
        messagingPanel.add(contactPanel, BorderLayout.EAST);
        contactListText.setBorder(BorderFactory.createEtchedBorder());

        contacts.setFont(new Font("Arial", Font.BOLD, 14));
        contactListText.add(contacts);
        contactPanel.add(contactListText, BorderLayout.NORTH);

        onlineAndBlacklist.add(contactsCheckStatus);
        onlineAndBlacklist.add(btnBlacklist);
        onlineAndBlacklist.setPreferredSize(new Dimension(150, 70));
        onlineAndBlacklist.setBorder(BorderFactory.createEtchedBorder());

        contactPanel.add(onlineAndBlacklist, BorderLayout.SOUTH);

        add(messagingPanel, BorderLayout.CENTER);


//        Нижняя часть отправки сообщений

        try {
            serverManager = new OneServerManager("localhost", 65500, messaging, AuthForm, contactList);
        } catch (IOException  e) {
            e.printStackTrace();
        }

        sendMessagePanel.setPreferredSize(new Dimension(350, 80));
        settingsMessagePanel.setBackground(new Color(245, 248, 148));
        settingsMessagePanel.add(btnSmile);
        settingsMessagePanel.add(btnFont);
        settingsMessagePanel.add(btnAddFile);

        sendMessagePanel.add(settingsMessagePanel, BorderLayout.NORTH);

        sendMessageField.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sendMessageField.getText().equals("")) {/* Проверка на пустое сообщение*/
                    MessageDTO dto = new MessageDTO();

                    if (!(contactList.getSelectedValue() == null)) {
                        dto.setTo(contactList.getSelectedValue());
                        dto.setMessageType(MessageType.PRIVATE_MESSAGE);
                    } else {
                        dto.setMessageType(MessageType.PUBLIC_MESSAGE);
                    }
                    dto.setBody(sendMessageField.getText());
                    serverManager.writeMsg(dto.convertToJson());
                    sendMessageField.setText(null);
                }

            }


        });

        sendMessagePanel.add(sendMessageField);
        btnSendMessage.setPreferredSize(new Dimension(120, 40));

        btnSendMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!sendMessageField.getText().equals("")) {/* Проверка на пустое сообщение*/
                    MessageDTO dto = new MessageDTO();

                    if (!(contactList.getSelectedValue() == null)) { // Проверяем выбран ли элемент в списке контактов
                        dto.setTo(contactList.getSelectedValue());
                        dto.setMessageType(MessageType.PRIVATE_MESSAGE);
                    } else {
                        dto.setMessageType(MessageType.PUBLIC_MESSAGE);
                    }
                    dto.setBody(sendMessageField.getText());
                    serverManager.writeMsg(dto.convertToJson());
                    sendMessageField.setText(null);
                }

            }
        });

        sendMessagePanel.add(btnSendMessage, BorderLayout.EAST);

        add(sendMessagePanel, BorderLayout.SOUTH);

        setTitle("ICQ 2021");


//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {  // Закрытие окга чата черех " Х "
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane
                        .showOptionDialog(e.getWindow(), "Закрыть окно?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    MessageDTO dto = new MessageDTO();
                    dto.setMessageType(MessageType.ONLINE_STATUS);

                    serverManager.writeMsg(dto.convertToJson());
                    try {
                        serverManager.socketClose();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    e.getWindow().setVisible(false);
                    System.exit(0);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setResizable(false);
        setVisible(true);

        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    private JMenu createSettingsMenu() {
        JMenu Settings = new JMenu("Settings");
        return Settings;
    }

    private JMenu createEditMenu() {
        JMenu Edit = new JMenu("Edit");
        return Edit;
    }

    private JMenu createHelpMenu() {
        JMenu Help = new JMenu("Help");
        return Help;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] ste = e.getStackTrace();
        JOptionPane.showMessageDialog(this, ste[0].toString(), "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    public synchronized void startAuth(String login, String password){  // Отправляет полученные с авторизации данные на сервер
        System.out.println(login);
        System.out.println(password);
        MessageDTO dto = new MessageDTO();
        dto.setLogin(login);
        dto.setPassword(password);
        dto.setMessageType(MessageType.SEND_AUTH_MESSAGE);
        serverManager.writeMsg(dto.convertToJson());
    }

    public synchronized void startChangeUsername(String Username){  // Отправляет полученные с авторизации данные на сервер
        MessageDTO dto = new MessageDTO();
        dto.setUsername(Username);
        dto.setMessageType(MessageType.CHANGE_NAME);
        serverManager.writeMsg(dto.convertToJson());
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
