package ua.training.controller.listeners;

import ua.training.constants.Attributes;
import ua.training.constants.Parameters;
import ua.training.model.entities.Excursion;
import ua.training.model.entities.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Set;

/**
 * Максим
 * 02.05.2018
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();
        event.getSession().setAttribute(Attributes.CART, new HashMap<String, Set<Excursion>>());
        event.getSession().setAttribute(Attributes.LOCALE, context.getInitParameter(Parameters.LOCALE));
        event.getSession().setAttribute(Attributes.ROLE, User.Role.GUEST);
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        event.getSession().removeAttribute(Attributes.ACTIVE_USER);
    }
}
