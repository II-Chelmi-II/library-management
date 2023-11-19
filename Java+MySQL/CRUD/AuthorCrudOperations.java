package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Author;

public class AuthorCrudOperations implements CrudOperations<Author> {

    private final Connection connection;

    public AuthorCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM author");
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("sex")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO author (id, name, sex) VALUES (?, ?, ?)"
            );
            for (Author author : toSave) {
                statement.setInt(1, author.getAuthorId());
                statement.setString(2, author.getName());
                statement.setString(3, author.getSex());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Author save(Author toSave) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO author (id, name, sex) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, toSave.getAuthorId());
            statement.setString(2, toSave.getName());
            statement.setString(3, toSave.getSex());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                toSave.setAuthorId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Author delete(Author toDelete) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM author WHERE id = ?");
            statement.setInt(1, toDelete.getAuthorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }
}
