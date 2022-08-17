package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {

    private final int row;
    private final int column;
    private int price;
    private boolean isAvailable;

    public Seat(int row, int column, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        price = row <= 4 ? 10 : 8;
    }

    public Seat(@JsonProperty("row") int row, @JsonProperty("column") int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Seat)) {
            return false;
        }
        Seat seat = (Seat) o;
        return this.row == seat.getRow() && this.column == seat.getColumn();
    }
}
