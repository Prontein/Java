package ru.geekbrains;

public interface AuthService {
    void start();
    void stop();
    String getUsernameAndPass (String login, String password);
}
