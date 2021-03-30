package ru.job4j.model;

import ru.job4j.store.PsqlStore;

import java.util.Collection;

public class HallManager {
    private static final int HALL_ROW = 3;
    private static final int HALL_SEATS_IN_A_ROW = 4;
    private static final int PRIME_ROW = 1;
    private static final HallManager instance = new HallManager();

    private HallManager() {
    }

    private boolean isLuxSeat(int row, int seat) {
        boolean even = HALL_SEATS_IN_A_ROW % 2 == 0;
        int center = HALL_SEATS_IN_A_ROW / 2;
        return (row == PRIME_ROW && (center == seat || (even && (center - 1) == seat)));
    }

    private void loadPrices(Hall h, Session s) {
        for (int row = 0; row < HALL_ROW; row++) {
            for (int seat = 0; seat < HALL_SEATS_IN_A_ROW; seat++) {
                h.setPrice(row, seat, PsqlStore.getInstance().getPrice(s, isLuxSeat(row, seat)));
            }
        }
    }

    public static HallManager getInstance() {
        return instance;
    }

    public Hall loadHall(Session session) {
        Hall hall = new Hall(HALL_ROW, HALL_SEATS_IN_A_ROW);
        loadPrices(hall, session);
        Collection<Ticket> tickets = PsqlStore.getInstance().findAllTickets();
        for (Ticket t: tickets) {
            if (t.getSession().equals(session)) {
                hall.reserve(t.getRow(), t.getSeat());
            }
        }
        return hall;
    }
}
