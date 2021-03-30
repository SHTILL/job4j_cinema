package ru.job4j.model;

public class Hall {
    private final int rows;
    private final int seatsInRow;
    private final Seat[][] seats;

    public Hall(int rows, int seatsInRow) {
        this.rows = rows;
        this.seatsInRow = seatsInRow;
        seats = new Seat[rows][seatsInRow];
        for (int i = 0; i< rows; i++) {
            for (int j=0; j<seatsInRow; j++ ) {
                seats[i][j] = new Seat();
            }
        }
    }

    private void validateRowAndSeat(int row, int seat) throws IllegalArgumentException {
        if (row < 0 || row >= rows || seat < 0 || seat >= seatsInRow) {
            throw new IllegalArgumentException();
        }
    }

    public void reserve(int row, int seat) {
        validateRowAndSeat(row, seat);
        seats[row][seat].setBusy();
    }

    public void setPrice(int row, int seat, int price) {
        validateRowAndSeat(row, seat);
        seats[row][seat].setPrice(price);
    }
}
