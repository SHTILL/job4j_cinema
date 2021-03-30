package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Account;
import ru.job4j.model.Session;
import ru.job4j.model.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PsqlStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class);
    private final BasicDataSource pool = new BasicDataSource();
    private static PsqlStore instance = new PsqlStore();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("cinema.db.properties")
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
        //TODO
    }

    @Override
    public void save(Ticket t) {
        try (Connection cn = pool.getConnection();
                PreparedStatement ps =  cn.prepareStatement("INSERT INTO ticket(row, seat, session_id, account_id)" +
                        "VALUES (?, ?, " +
                        "(SELECT id from public.film_session where description = ?), " +
                        "(SELECT id from public.account where name = ? and email = ? and phone = ?))", PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, t.getRow());
            ps.setInt(2, t.getSeat());
            ps.setString(3, t.getSession().getDescription());
            ps.setString(4, t.getAccount().getName());
            ps.setString(5, t.getAccount().getEmail());
            ps.setString(6, t.getAccount().getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.warn("Exception while saving the ticket into db", e);
        }
    }

    @Override
    public Collection<Ticket> findAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT row, seat, " +
                     "film_session.description as description, " +
                     "account.name as userName, " +
                     "email, " +
                     "phone " +
                     "FROM ticket " +
                     "join account on ticket.account_id = account.id " +
                     "join film_session on ticket.session_id = film_session.id"))
        {

            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Account a = new Account(it.getString("userName"), it.getString("email"), it.getString("phone"));
                Session s = new Session(it.getString("description"));
                tickets.add(new Ticket(s, a, it.getInt("row"), it.getInt("seat")));
            }
        } catch (Exception e) {
            LOG.warn("Exception while retrieving tickets", e);
        }
        return tickets;
    }

    @Override
    public Collection<Session> findSessions(Date date) {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT description from film_session"))
        {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                sessions.add(new Session(it.getString("description")));
            }
        } catch (Exception e) {
            LOG.warn("Exception while retrieving tickets", e);
        }
        return sessions;
    }

    @Override
    public boolean validateAccount(Account a) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT id from account where " +
                     "name = ? and " +
                     "email = ? and " +
                     "phone = ?"))
        {
            ps.setString(1, a.getName());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getPhone());
            ResultSet it = ps.executeQuery();
            if (it.next()) {
                return true;
            }
        } catch (Exception e) {
            LOG.warn("Exception while retrieving tickets", e);
        }
        return false;
    }

    @Override
    public int getPrice(Session s, boolean luxSeat) {
        return luxSeat ? 700 : 500;
    }
}
