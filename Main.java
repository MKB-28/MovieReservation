import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieService movieService = new MovieService();
        ReservationService resService = new ReservationService();

        while (true) {
            System.out.println("\n===== MKB's Theatre =====");
            System.out.println("1. View Movies");
            System.out.println("2. Reserve Ticket");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    movieService.viewMovies();
                    break;

                case 2:
                    System.out.print("Enter Movie ID: ");
                    int movieId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Your Name: ");
                    String name = sc.nextLine();
                    System.out.print("Seats to reserve: ");
                    int seats = sc.nextInt();
                    movieService.reserveSeats(movieId, name, seats);
                    break;

                case 3:
                    System.out.print("Enter Reservation ID: ");
                    resService.cancelReservation(sc.nextInt());
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    return;
            }
        }
    }
}
