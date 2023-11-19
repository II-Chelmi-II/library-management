package CRUD;

import Model.Subscribers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscribersCrudOperations implements CrudOperations<Subscribers> {

    private final Connection connection;

    public SubscribersCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subscribers> findAll() {
        List<Subscribers> subscribersList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM subscribers");
            while (resultSet.next()) {
                Subscribers subscriber = new Subscribers(
                        resultSet.getInt("subscriber_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("subscription_date")
                );
                subscribersList.add(subscriber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscribersList;
    }

    @Override
    public List<Subscribers> saveAll(List<Subscribers> toSave) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO subscribers (subscriber_id, name, subscription_date) VALUES (?, ?, ?)"
            );
            for (Subscribers subscriber : toSave) {
                statement.setInt(1, subscriber.getSubscriberId());
                statement.setString(2, subscriber.getName());
                statement.setDate(3, new java.sql.Date(subscriber.getSubscriptionDate().getTime()));
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Subscribers save(Subscribers toSave) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO subscribers (subscriber_id, name, subscription_date) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, toSave.getSubscriberId());
            statement.setString(2, toSave.getName());
            statement.setDate(3, new java.sql.Date(toSave.getSubscriptionDate().getTime()));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                toSave.setSubscriberId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Subscribers delete(Subscribers toDelete) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM subscribers WHERE subscriber_id = ?");
            statement.setInt(1, toDelete.getSubscriberId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }
}