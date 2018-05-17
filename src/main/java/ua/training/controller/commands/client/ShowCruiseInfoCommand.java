package ua.training.controller.commands.client;

import ua.training.constants.Attributes;
import ua.training.constants.CommandPaths;
import ua.training.constants.Pages;
import ua.training.constants.URLs;
import ua.training.controller.commands.AccessRequired;
import ua.training.controller.commands.Command;
import ua.training.controller.servlets.actions.Forward;
import ua.training.controller.servlets.actions.Redirect;
import ua.training.controller.servlets.actions.ServletAction;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.entities.Excursion;
import ua.training.model.entities.Ship;
import ua.training.model.entities.User;
import ua.training.model.services.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Максим
 * 08.05.2018
 */

@SuppressWarnings("unchecked")
@AccessRequired(roles = {User.Role.CLIENT}, path = CommandPaths.SHOW_CRUISE_INFO)
public class ShowCruiseInfoCommand implements Command {
    CruiseService cruiseService = CruiseService.getInstance();
    @Override
    public ServletAction execute(HttpServletRequest request) {
        String cruiseName = request.getParameter("cruise");
        HttpSession session = request.getSession();

        boolean freePlacesAvailable = cruiseService.freePlacesAvailable(cruiseName);
        if (!freePlacesAvailable) {
            return new Redirect(URLs.CRUISE_LIST);
        }

        String locale = (String)session.getAttribute(Attributes.LANGUAGE);
        Optional<Ship> ship = cruiseService.getCruiseByName(cruiseName, locale);

        if (ship.isPresent()) {
            Map<String, List<Excursion>> basket = ControllerUtil.getCart(session);
            List<Excursion> excursions = basket.get(ship.get().getNameEn());
            int price = calculatePrice(ship.get().getPrice(), excursions);
            session.setAttribute("cruise", ship.get());
            request.setAttribute("price", price);
            return new Forward(Pages.CRUISE_INFO);
        }
        return new Redirect(URLs.CRUISE_LIST);
    }

    private int calculatePrice(int cruisePrice, List<Excursion> excursions) {
        return excursions == null ? cruisePrice :
                cruisePrice + excursions.stream().mapToInt(Excursion::getPrice).sum();
    }
}
