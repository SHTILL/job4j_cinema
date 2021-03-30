package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Account;
import ru.job4j.model.Session;
import ru.job4j.model.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PsqlStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class);
    private final BasicDataSource pool = new BasicDataSource();
    private static PsqlStore instance = new PsqlStore();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    public static PsqlStore getInstance() {
        return instance;
    }

    @Override
    public void save(Account a) {

    }

    @Override
    public void save(Ticket t) {

    }

    @Override
    public Collection<Ticket> findAllTickets() {
        List<Ticket> l = new ArrayList<>();
        l.add(new Ticket(new Session("12:00"), new Account(), 1, 2));
        l.add(new Ticket(new Session("12:00"), new Account(), 2, 3));
        return l;
    }

    @Override
    public Collection<Session> findSessions(Date date) {
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("12:00"));
        sessions.add(new Session("14:00"));
        return sessions;
    }

    @Override
    public boolean validateAccount(Account a) {
        return true;
    }

    @Override
    public int getPrice(Session s, boolean luxSeat) {
        return luxSeat ? 700 : 500;
    }
}
