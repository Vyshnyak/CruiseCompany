package ua.training.controller.commands.admin;

import ua.training.constants.Attributes;
import ua.training.constants.Pages;
import ua.training.constants.Parameters;
import ua.training.constants.URLs;
import ua.training.controller.commands.AccessRequired;
import ua.training.controller.commands.Command;
import ua.training.controller.servlets.actions.Forward;
import ua.training.controller.servlets.actions.Redirect;
import ua.training.controller.servlets.actions.ServletAction;
import ua.training.model.entities.Ship;
import ua.training.model.entities.User;
import ua.training.model.services.ShipService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Максим
 * 05.05.2018
 */
@AccessRequired(roles = {User.Role.ADMIN})
public class ShowShipsCommand implements Command {
    private ShipService shipService = ShipService.getInstance();
    @Override
    public ServletAction execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute(Attributes.LOCALE);
        int numberOfPages = shipService.getNumberOfPages();
        try {
            int pageNumber = Integer.parseInt(request.getParameter(Parameters.PAGE));
            if (pageNumber > 0 && numberOfPages >= pageNumber) {
                List<Ship> ships = shipService.getAllShipsPerPage(pageNumber, locale);
                request.setAttribute(Attributes.NUMBER_OF_PAGES, numberOfPages);
                request.setAttribute(Attributes.SHIPS, ships);
                return new Forward(Pages.SHIP_LIST);
            } else {
                return new Redirect(URLs.SHIP_LIST +
                        (pageNumber > 0 ? numberOfPages : 1));
            }
        } catch (NullPointerException | NumberFormatException e) {
            return new Redirect(URLs.SHIP_LIST + 1);
        }
    }
}
