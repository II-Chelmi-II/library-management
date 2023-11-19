import CRUD.*;
import Model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);

        Properties properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading config.properties file", e);
            return;
        }

        String databaseUrl = properties.getProperty("db.url");
        String databaseUser = properties.getProperty("db.user");
        String databasePassword = properties.getProperty("db.password");

        try (Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword)) {

            BookCrudOperations bookCrudOperations = new BookCrudOperations(connection);

            logger.info("List of books:");
            List<Book> books = bookCrudOperations.findAll();
            books.forEach(book -> logger.info(book.getBookId() + " - " + book.getBookName() + " - " + book.getPageNumbers() + " pages - " + book.getReleaseDate()));

            logger.info("\nAdding new books:");
            Book newBook1 = new Book(4, "La peste", 200, Book.Topic.OTHER, new Date(), 1);
            Book newBook2 = new Book(5, "Le Tartuffe", 300, Book.Topic.COMEDY, new Date(), 2);
            bookCrudOperations.saveAll(Arrays.asList(newBook1, newBook2));

            logger.info("\nUpdated list of books:");
            List<Book> updatedBooks = bookCrudOperations.findAll();
            updatedBooks.forEach(book -> logger.info(book.getBookId() + " - " + book.getBookName() + " - " + book.getPageNumbers() + " pages - " + book.getReleaseDate()));

            SubscribersCrudOperations subscribersCrudOperations = new SubscribersCrudOperations(connection);

            logger.info("\nList of subscribers:");
            List<Subscribers> subscribersList = subscribersCrudOperations.findAll();
            subscribersList.forEach(subscribers -> logger.info(subscribers.getSubscriberId() + " - " + subscribers.getName() + " - " + subscribers.getSubscriptionDate()));

            logger.info("\nAdding a new subscriber:");
            Subscribers newSubscriber = new Subscribers(4, "GothamChess", new Date());
            subscribersCrudOperations.save(newSubscriber);

            logger.info("\nUpdated list of subscribers:");
            List<Subscribers> updatedSubscribers = subscribersCrudOperations.findAll();
            updatedSubscribers.forEach(subscribers -> logger.info(subscribers.getSubscriberId() + " - " + subscribers.getName() + " - " + subscribers.getSubscriptionDate()));

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection error", e);
        }
    }
}
