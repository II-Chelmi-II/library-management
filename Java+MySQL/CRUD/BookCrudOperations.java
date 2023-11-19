package CRUD;

import java.util.ArrayList;
import java.util.List;
import Model.Book;
import java.sql.*;

public class BookCrudOperations implements CrudOperations<Book> {

    private final Connection connection;

    public BookCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("book_name"),
                        resultSet.getInt("page_numbers"),
                        Book.Topic.valueOf(resultSet.getString("topic")),
                        resultSet.getDate("release_date"),
                        resultSet.getInt("author_id")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> saveAll(List<Book> toSave) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO book (book_id, book_name, page_numbers, topic, release_date, author_id) VALUES (?, ?, ?, ?, ?, ?)"
            );
            for (Book book : toSave) {
                statement.setInt(1, book.getBookId());
                statement.setString(2, book.getBookName());
                statement.setInt(3, book.getPageNumbers());
                statement.setString(4, book.getTopic().name());
                statement.setDate(5, new java.sql.Date(book.getReleaseDate().getTime()));
                statement.setInt(6, book.getAuthorId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Book save(Book toSave) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO book (book_id, book_name, page_numbers, topic, release_date, author_id) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, toSave.getBookId());
            statement.setString(2, toSave.getBookName());
            statement.setInt(3, toSave.getPageNumbers());
            statement.setString(4, toSave.getTopic().name());
            statement.setDate(5, new java.sql.Date(toSave.getReleaseDate().getTime()));
            statement.setInt(6, toSave.getAuthorId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                toSave.setBookId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Book delete(Book toDelete) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM book WHERE book_id = ?");
            statement.setInt(1, toDelete.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }
}
