package org.example.view;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class View {
    private JFrame frame;
    private JTextField cpfField;
    private JTextField nomeField;
    private JTextArea displayArea;
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
        frame = new JFrame("CRUD de App.Multiversar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Ajustando o tamanho da janela
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        inputPanel.add(cpfField);
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 6)); // Ajustando para 6 botões
        JButton createButton = new JButton("Criar Tabela");
        JButton readButton = new JButton("Ler");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Deletar");
        JButton insertButton = new JButton("Inserir"); // Adicionando botão de inserir
        JButton exitButton = new JButton("Sair");

        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(insertButton); // Adicionando botão de inserir ao painel
        buttonPanel.add(exitButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Adicionando ações aos botões
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createTableIfNotExists();
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showData();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cpf = getCpf();
                String nome = getNome();
                controller.updateData(cpf, nome);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cpf = getCpf();
                controller.deleteData(cpf);
            }
        });

        insertButton.addActionListener(new ActionListener() { // Adicionando ação ao botão de inserir
            @Override
            public void actionPerformed(ActionEvent e) {
                int cpf = getCpf();
                String nome = getNome();
                controller.insertData(cpf, nome);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adicionando KeyListener ao campo de CPF
        cpfField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nomeField.requestFocus();
                }
            }
        });
    }

    public int getCpf() {
        try {
            return Integer.parseInt(cpfField.getText());
        } catch (NumberFormatException e) {
            showMessage("CPF inválido. Por favor, insira um número.");
            return -1; // Retorna um valor inválido para indicar erro
        }
    }

    public String getNome() {
        return nomeField.getText();
    }

    public void showMessage(String message) {
        displayArea.append(message + "\n");
    }

    public void showMenu() {
        // Método para exibir o menu, se necessário
    }
}
