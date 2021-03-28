package store;

import model.Account;
import model.Ticket;

public interface Store {
    void save(Account a);
    void save(Ticket t);
}
