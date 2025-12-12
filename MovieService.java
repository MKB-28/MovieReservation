import java.sql.*;

public class MovieService {

    public void viewMovies() {
        try (Connection con = DBConnection.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movies");

            System.out.println("\n--- Available Movies ---");
            while (rs.next()) {
                System.out.println(rs.getInt("movie_id") + ". " +
                        rs.getString("movie_name") + " | Showtime: " +
                        rs.getString("show_time") +
                        " | Available Seats: " + rs.getInt("available_seats"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean reserveSeats(int movieId, String customer, int seats) {
        try (Connection con = DBConnection.getConnection()) {
            
            PreparedStatement check = con.prepareStatement(
                "SELECT available_seats FROM movies WHERE movie_id=?");
            check.setInt(1, movieId);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("available_seats");
                if (seats > available) {
                    System.out.println("Not enough seats!");
                    return false;
                }
            }

            PreparedStatement update = con.prepareStatement(
                "UPDATE movies SET available_seats = available_seats - ? WHERE movie_id=?");
            update.setInt(1, seats);
            update.setInt(2, movieId);
            update.executeUpdate();

            PreparedStatement insert = con.prepareStatement(
                "INSERT INTO reservations(movie_id, customer_name, seats_reserved) VALUES (?,?,?)");
            insert.setInt(1, movieId);
            insert.setString(2, customer);
            insert.setInt(3, seats);
            insert.executeUpdate();

            System.out.println("Reservation successful!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
