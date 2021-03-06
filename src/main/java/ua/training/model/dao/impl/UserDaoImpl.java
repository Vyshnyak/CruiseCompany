package ua.training.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.constants.TableColumns;
import ua.training.controller.exceptions.LoginAlreadyExistsException;
import ua.training.model.dao.UserDao;
import ua.training.constants.Queries;
import ua.training.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Максим
 * 06.05.2018
 */
public class UserDaoImpl implements UserDao {
    private final static Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private Connection connection;

    /**
     * Instantiates a new User dao.
     *
     * @param connection the connection
     */
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) throws LoginAlreadyExistsException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.USER_CREATE)) {
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getRole().name());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LoginAlreadyExistsException(e.getMessage(), entity.getLogin());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(int id, String locale) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.USER_FIND_BY_ID)) {
            ps.setInt(1, id);
            User user = extractEntityFromResultSet(ps.executeQuery());
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.USER_FIND_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = extractEntityFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll(String locale) {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.USER_FIND_ALL);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = extractEntityFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.USER_UPDATE)) {
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getRole().name());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.USER_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract entity from result set user.
     *
     * @param resultSet the result set
     * @return the user
     * @throws SQLException the sql exception
     */
    public static User extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder()
                .setId(resultSet.getInt(TableColumns.USER_ID))
                .setLogin(resultSet.getString(TableColumns.USER_LOGIN))
                .setPassword(resultSet.getString(TableColumns.USER_PASSWORD))
                .setRole(User.Role.valueOf(resultSet.getString(TableColumns.USER_ROLE)))
                .build();
    }
}
