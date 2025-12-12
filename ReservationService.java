import java.sql.*;

public class ReservationService {

    public void cancelReservation(int id) {
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement get = con.prepareStatement(
                "SELECT movie_id, seats_reserved FROM reservations WHERE reservation_id=?");
            get.setInt(1, id);
            ResultSet rs = get.executeQuery();

            if (!rs.next()) {
                System.out.println("Reservation not found!");
                return;
            }

            int movieId = rs.getInt("movie_id");
            int seats = rs.getInt("seats_reserved");

            PreparedStatement updateSeats = con.prepareStatement(
                "UPDATE movies SET available_seats = available_seats + ? WHERE movie_id=?");
            updateSeats.setInt(1, seats);
            updateSeats.setInt(2, movieId);
            updateSeats.executeUpdate();

            PreparedStatement delete = con.prepareStatement(
                "DELETE FROM reservations WHERE reservation_id=?");
            delete.setInt(1, id);
            delete.executeUpdate();

            System.out.println("Reservation cancelled!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
