package ru.job4j.servlet;

import com.google.gson.Gson;
import ru.job4j.model.*;
import ru.job4j.store.PsqlStore;
import ru.job4j.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Store store = PsqlStore.getInstance();
        String accountParam = req.getParameter("account");
        Account a = new Gson().fromJson(accountParam, Account.class);
        if (!store.validateAccount(a)) {
            resp.sendError(301, "Registration is required");
        }
        String ticketParam = req.getParameter("ticket");
        String sessionParam = req.getParameter("session");
        Session s = new Gson().fromJson(sessionParam, Session.class);
        Ticket t = new Gson().fromJson(ticketParam, Ticket.class);
        t.setAccount(a);
        t.setSession(s);
        store.save(t);
    }
}
