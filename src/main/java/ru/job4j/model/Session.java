package ru.job4j.model;

import java.util.Objects;

public class Session {
    private String description;

    public Session(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return description.equals(session.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
