package ua.training.model.dao.impl;

import ua.training.constants.Queries;
import ua.training.constants.TableColumns;
import ua.training.model.dao.PortDao;
import ua.training.model.entities.Port;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Максим
 * 06.05.2018
 */
public class PortDaoImpl implements PortDao {
    private Connection connection;

    public PortDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Port entity) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.PORT_CREATE)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getCountry());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPortToShip(int portId, int shipId, int number) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.PORT_ADD_TO_SHIP)) {
            ps.setInt(1, portId);
            ps.setInt(2, shipId);
            ps.setInt(3, number);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Port> getAllPortsByShip(int shipId) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.PORT_FIND_ALL_BY_SHIP)) {
            ps.setInt(1, shipId);
            ResultSet resultSet = ps.executeQuery();
            List<Port> ports = new ArrayList<>();
            while (resultSet.next()) {
                Port port = extractEntityFromResultSet(resultSet);
                ports.add(port);
            }
            return ports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Port> findById(int id) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.PORT_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            Port port = null;
            while (resultSet.next()) {
                port = extractEntityFromResultSet(resultSet);
            }
            return Optional.ofNullable(port);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Port> findAll() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.PORT_FIND_ALL);
            List<Port> ports = new ArrayList<>();
            while (resultSet.next()) {
                Port port = extractEntityFromResultSet(resultSet);
                ports.add(port);
            }
            return ports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Port entity) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.PORT_UPDATE)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getCountry());
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try(PreparedStatement ps = connection.prepareStatement(Queries.PORT_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Port extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new Port.PortBuilder()
                .setId(resultSet.getInt(TableColumns.ID))
                .setName(resultSet.getString(TableColumns.PORT_NAME))
                .setCountry(resultSet.getString(TableColumns.PORT_COUNTRY))
                .build();
    }
}
