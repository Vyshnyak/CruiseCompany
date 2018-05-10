package ua.training.model.services;

import ua.training.model.dao.PortDao;
import ua.training.model.dao.ShipDao;
import ua.training.model.dao.cp.ConnectionPool;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entities.Ship;

import java.sql.Connection;
import java.util.List;

/**
 * Максим
 * 05.05.2018
 */
public class ShipService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    
    private final static class ShipServiceHolder {
        private static final ShipService INSTANCE = new ShipService();
    } 
    
    public static ShipService getInstance() {
        return ShipServiceHolder.INSTANCE;
    }
    
    public List<Ship> getAllCruises() {
        Connection connection = ConnectionPool.getConnection();
        try(ShipDao shipDao = daoFactory.createShipDao(connection);
            PortDao portDao = daoFactory.createPortDao(connection)) {
            List<Ship> ships = shipDao.findAll();
            for (Ship ship : ships) {
                ship.setPorts(portDao.getAllPortsByShip(ship.getId()));
            }
            return ships;
        }
    }
}
