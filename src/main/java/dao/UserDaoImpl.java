package dao;

import am.basic.db.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM my_user_db";

    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * FROM my_user_db WHERE id = ?";

    //language=SQL
    private final String SQl_INSERT_USER = "INSERT INTO my_user_db(first_name, last_name) VALUES (?, ?)";

    //language=SQL
    private final String SQL_UPDATE_USER = "UPDATE my_user_db SET first_name = ?, last_name = ? WHERE id = ?";

    //language=SQL
    private final String SQL_DELETE_BY_ID = "DELETE FROM my_user_db WHERE id = ?";

    private Connection connection;

    public UserDaoImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return null;
    }

    @Override
    public Optional<User> find(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                return Optional.of(new User(firstName, lastName));

            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User user) {
        try {
            List<User> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(SQl_INSERT_USER);
            statement.setString(1, "first_name");
            statement.setString(2, "last_name");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                User newUser = new User(firstName, lastName);
                users.add(newUser);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.executeQuery();
            System.out.println("Updated!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Deleted!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                User user = new User(id, firstName, lastName);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
