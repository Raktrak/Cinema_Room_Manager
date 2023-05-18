package cinema;

import java.util.Scanner;

public class Cinema {
    private static int rowNumber, buy, currentIncome, cinemaSeats, cinemaRows, seatNumber;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        Cinema.cinemaSeats = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        Cinema.cinemaRows = scanner.nextInt();
        String[][] cinema = new String[cinemaSeats][cinemaRows];

        seatArrangement(cinema);                        // allocates seats according to a * b
        menu(cinema);                                   // shows options

    }

    public static void menu(String[][] cinema) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                                                
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                                """);
        int options = scanner.nextInt();
        while (options != 0) {
            switch (options) {
                case 1 -> showTheSeats(cinema);   // prints current allocation
                case 2 -> buyTicket(cinema);      // reserves seat and prints ticket price
                case 3 -> statistics(cinema);     // show stats
            }
            options = scanner.nextInt();
        }
    }

    public static void statistics(String[][] cinema) {
        int purchasedTickets = Cinema.buy;

        int totalSeats = cinemaSeats * cinemaRows;
        double percentage = purchasedTickets / (double) totalSeats * 100;

        int totalPossibleIncome = 0;
        if (totalSeats < 60) {
            totalPossibleIncome = totalSeats * 10;
        }
        if (totalSeats >= 60) {
            int rowsWithHigherPrice = cinemaRows / 2; // liczba rzędów w wyższej cenie (10)
            int rowsWithLowerPrice = cinemaRows - rowsWithHigherPrice; // liczba rzędów w niższej cenie (8)
            int higherPriceIncome = rowsWithHigherPrice * cinemaSeats * 10; // dochód z rzędów w wyższej cenie
            int lowerPriceIncome = rowsWithLowerPrice * cinemaSeats * 8; // dochód z rzędów w niższej cenie (odjęcie 1 rzędu)
            totalPossibleIncome = higherPriceIncome + lowerPriceIncome;
        }

        String stats = """
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """
                .formatted(purchasedTickets, percentage, currentIncome, totalPossibleIncome);
        System.out.print(stats);

        menu(cinema);
    }

    public static void seatArrangement(String[][] cinema) {
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[0].length; j++) {
                cinema[i][j] = "S";
            }
        }
    }

    public static void showTheSeats(String[][] cinema) {
        System.out.println("\nCinema:");
        System.out.print("  ");

        for (int i = 1; i < cinemaRows + 1; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        int x = 1;
        for (int i = 0; i < cinemaSeats; i++) {
            for (int j = 0; j < cinemaRows; j++) {
                if (j == 0) {
                    System.out.print(x + " ");
                    x++;
                }
                System.out.print(cinema[i][j] + " ");
                if (j == cinemaRows - 1) {
                    System.out.println();
                }
            }
        }
        menu(cinema);
    }

    public static void buyTicket(String[][] cinema) {
        Scanner scanner = new Scanner(System.in);
        int ticket = 0;
        int totalSeats = cinemaSeats * cinemaRows;

        try {
            System.out.println("\nEnter a row number:");
            Cinema.rowNumber = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            Cinema.seatNumber = scanner.nextInt();

            if (!cinema[rowNumber - 1][seatNumber - 1].equals("B")) {

                if (totalSeats < 60) {
                    ticket = 10;
                }
                if (totalSeats >= 60 & cinemaRows / 2 < rowNumber) {
                    ticket = 8;
                } else if (totalSeats >= 60) {
                    ticket = 10;
                }
                int tempIncome = ticket;
                currentIncome = currentIncome + tempIncome;
                Cinema.buy++;
                System.out.println("\nTicket price: $" + ticket);
                cinema[rowNumber - 1][seatNumber - 1] = "B";
            } else {
                System.out.println("\nThat ticket has already been purchased!");
                buyTicket(cinema);
            }

        } catch (Exception e) {
            System.out.println("\nWrong input!");
            buyTicket(cinema);
        }
        menu(cinema);
    }
}

