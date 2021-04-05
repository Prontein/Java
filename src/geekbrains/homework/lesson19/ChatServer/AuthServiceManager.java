package ru.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthServiceManager implements AuthService{
    private List<Client> clients;

    public AuthServiceManager () {
        clients = new ArrayList<>(Arrays.asList(
            new Client("user1", "log1", "pass1"),
            new Client("user2", "log2", "pass2"),
            new Client("user3", "log3", "pass3")
        ));
    }
    @Override
    public void start() {

    }

    @Override
    public void stop() {
        System.out.println("Авторизация остановлена");
    }

    @Override
    public String getUsernameAndPass(String login, String password) {
        for (Client c : clients) {
            if (c.getLogin().equals(login) && c.getPassword().equals(password))
                return c.getUsername();
        }
        return null;
    }
}
