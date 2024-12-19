package org.example;

import org.example.controller.Controller;

public class Main {
    public static void main(String[] args) {

        // Dados do PostgreSQL para fazer a conexão
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "root";

        Controller controller = new Controller(url, user, password);
        controller.start();
    }
}
