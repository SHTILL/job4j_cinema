package ru.job4j.store;

import ru.job4j.model.Account;
import ru.job4j.model.Session;
import ru.job4j.model.Ticket;

import java.util.Collection;
import java.util.Date;

public interface Store {
    void save(Account a);
    void save(Ticket t);
    Collection<Ticket> findAllTickets();
    Collection<Session> findSessions(Date date);
    boolean validateAccount(Account a);
    int getPrice(Session s, boolean luxSeat);
}
