package ru.job4j.model;

public class Ticket {
    private Session session;
    private Account account;
    private int row;
    private int seat;

    public Ticket(Session session, Account account, int row, int seat) {
        this.session = session;
        this.account = account;
        this.row = row;
        this.seat = seat;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
