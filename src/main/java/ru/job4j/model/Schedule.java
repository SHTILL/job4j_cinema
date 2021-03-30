package ru.job4j.model;

import java.util.Collection;

public class Schedule {
    private Collection<Session> sessions;

    public Schedule(Collection<Session> sessions) {
        this.sessions = sessions;
    }

    public Collection<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Collection<Session> sessions) {
        this.sessions = sessions;
    }
}
