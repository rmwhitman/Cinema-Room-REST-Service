package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TheaterController {

    Theater theater = new Theater(9, 9);

    @GetMapping("/seats")
    public Theater getAvailableSeats() {
        return theater;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {

        // if requested seat doesn't exist return error
        if (seat.getRow() < 1 || seat.getRow() > theater.getTotal_rows() || seat.getColumn() < 1 || seat.getColumn() > theater.getTotal_columns()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "The number of a row or a column is out of bounds!"));
        }

        // if seat is already purchased return error
        if (!theater.getAvailable_seats().contains(seat)) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "The ticket has been already purchased!"));
        } else {
            return ResponseEntity.ok(theater.sellTicket(seat));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> refundTicket(@RequestBody Ticket ticket) {
        Seat refundSeat = theater.refundTicket(ticket);
        if (refundSeat != null) {
            return ResponseEntity.ok(Map.of("returned_ticket", refundSeat));
        } else {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Wrong token!")
            );
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> showStats(@RequestParam(required=false) String password) {
        if (password != null && password.equals("super_secret")) {
            Map<String, Integer> stats = new HashMap<>();
            stats.put("current_income", theater.getIncome());
            stats.put("number_of_available_seats", theater.getAvailable_seats().size());
            stats.put("number_of_purchased_tickets", theater.getSoldTickets().size());
            return ResponseEntity.ok(stats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", "The password is wrong!")
            );
        }
    }
}