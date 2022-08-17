package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.UUID;

public class Theater {

    private final int total_rows;
    private final int total_columns;
    private ArrayList<Seat> available_seats;

    private ArrayList<Ticket> soldTickets;

    public Theater(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = initializeSeats();
        this.soldTickets = new ArrayList<>();
    }

    private ArrayList<Seat> initializeSeats() {
        available_seats = new ArrayList<>();
        for (int row = 1; row <= total_rows; row++) {
            for (int column = 1; column <= total_columns; column++) {
                available_seats.add(new Seat(row, column, true));
            }
        }
        return available_seats;
    }

    public Ticket sellTicket(Seat seat) {
        Ticket ticket = new Ticket(UUID.randomUUID(), getSeat(seat));
        getSeat(seat).setAvailable(false);
        this.soldTickets.add(ticket);
        return ticket;
    }

    public Seat refundTicket(Ticket ticket) {
        UUID token = ticket.getToken();
        for (Ticket t : soldTickets) {
            if (t.getToken().equals(token)) {
                getSeat(t.getTicket()).setAvailable(true);
                this.soldTickets.remove(t);
                return t.getTicket();
            }
        }
        return null;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public ArrayList<Seat> getAvailable_seats() {
        ArrayList<Seat> availableSeats = new ArrayList<>();
        for (Seat s : available_seats) {
            if (s.isAvailable()) {
                availableSeats.add(s);
            }
        }
        return availableSeats;
    }

    public Seat getSeat(Seat seat) {
        for (Seat s : available_seats) {
            if (s.equals(seat)) {
                return s;
            }
        }
        return null;
    }

    @JsonIgnore
    public ArrayList<Seat> getSoldTickets() {
        ArrayList<Seat> soldSeats = new ArrayList<>();
        for (Seat s : available_seats) {
            if (!s.isAvailable()) {
                soldSeats.add(s);
            }
        }
        return soldSeats;
    }

    @JsonIgnore
    public int getIncome() {
        int total = 0;
        for (Seat s : getSoldTickets()) {
            total += s.getPrice();
        }
        return total;
    }
}
