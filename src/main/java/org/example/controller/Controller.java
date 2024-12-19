package org.example.controller;

import org.example.model.Model;
import org.example.view.View;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Controller {
    private View view;
    private SessionFactory sessionFactory;

    public Controller(String url, String user, String password) {
        this.view = new View(this);
        // Configura o Hibernate
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", url);
            configuration.setProperty("hibernate.connection.username", user);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.addAnnotatedClass(Model.class); // Adiciona a classe anotada
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Erro ao configurar o Hibernate: " + e.getMessage());
        }
    }

    public void start() {
        view.showMenu();
    }

    public void createTableIfNotExists() {
        // É pra estar vazio mesmo. O Hibernate já cuida dessa parte.
    }

    public void insertData(int cpf, String nome) {
        if (sessionFactory == null) {
            view.showMessage("SessionFactory não está inicializado.");
            return;
        }
        Model pessoa = new Model(nome, cpf);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(pessoa);
            session.getTransaction().commit();
        } catch (Exception e) {
            view.showMessage("Erro ao inserir dados: " + e.getMessage());
        }
    }

    public void showData() {
        if (sessionFactory == null) {
            view.showMessage("SessionFactory não está inicializado.");
            return;
        }
        try (Session session = sessionFactory.openSession()) {
            List<Model> pessoas = session.createQuery("FROM Model", Model.class).list();
            for (Model pessoa : pessoas) {
                view.showMessage("CPF: " + pessoa.getCpf() + ", Nome: " + pessoa.getNome());
            }
        } catch (Exception e) {
            view.showMessage("Erro ao mostrar dados: " + e.getMessage());
        }
    }

    public void updateData(int cpf, String novoNome) {
        if (sessionFactory == null) {
            view.showMessage("SessionFactory não está inicializado.");
            return;
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Model pessoa = session.get(Model.class, cpf);
            if (pessoa != null) {
                pessoa.setNome(novoNome);
                session.update(pessoa);
                session.getTransaction().commit();
            } else {
                view.showMessage("Pessoa não encontrada.");
            }
        } catch (Exception e) {
            view.showMessage("Erro ao atualizar dados: " + e.getMessage());
        }
    }

    public void deleteData(int cpf) {
        if (sessionFactory == null) {
            view.showMessage("SessionFactory não está inicializado.");
            return;
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Model pessoa = session.get(Model.class, cpf);
            if (pessoa != null) {
                session.delete(pessoa);
                session.getTransaction().commit();
            } else {
                view.showMessage("Pessoa não encontrada.");
            }
        } catch (Exception e) {
            view.showMessage("Erro ao excluir dados: " + e.getMessage());
        }
    }
}
