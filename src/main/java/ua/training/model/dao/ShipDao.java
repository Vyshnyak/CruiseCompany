package ua.training.model.dao;

import ua.training.controller.exceptions.CruiseAlreadyBoughtException;
import ua.training.model.entities.Ship;
import ua.training.model.entities.enums.Bonus;

import java.util.List;
import java.util.Optional;

/**
 * Максим
 * 06.05.2018
 */
public interface ShipDao extends GenericDao<Ship> {
    int countAllShips();
    int getFreePlacesAmount(String shipName);
    Optional<Ship> getShipByName(String shipName, String locale);
    List<Ship> getAllShipsPerPage(int pageNumber, String locale);
    List<Ship> getAllShipsByUser(int userId, String locale);
    List<Bonus> getAllBonusesByShip(int shipId);
    void addShipToUser(int shipId, int userId) throws CruiseAlreadyBoughtException;
    void addBonusToShip(int shipId, String bonus);
    void deleteBonusFromShip(int shipId, String bonus);
}
