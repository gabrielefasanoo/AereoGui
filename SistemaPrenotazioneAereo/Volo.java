import java.util.ArrayList;
import java.util.List;

public class Volo {
    private final String flightNumber;  // cambiato da planeID
    private final int capacity;
    private final String departureCity;  // cambiato da Origin
    private final String destinationCity;  // cambiato da Destination
    private final boolean[][] seats;
    private static final int ROWS = 6;
    private static final int SEATS_PER_ROW = 6;
    private final List<Prenotazione> prenotazioni;

    public Volo(String flightNumber, int capacity, String departureCity, String destinationCity) {
        this.flightNumber = flightNumber;
        this.capacity = capacity;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.seats = new boolean[ROWS][SEATS_PER_ROW];
        this.prenotazioni = new ArrayList<>();
    }

    public void displaySeats() {
        System.out.println("Mappa dei posti (O = libero, X = occupato):");
        // Stampa intestazione
        System.out.println("  A B C D E F");
        // Stampa posti
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                System.out.print(seats[i][j] ? "X " : "O ");
            }
            System.out.println(); // Aggiungiamo un a capo alla fine di ogni riga
        }
    }

    public boolean bookSeat(int row, char column, Passengers passenger) {
        int col = column - 'A';
        if (row < 1 || row > ROWS || col < 0 || col >= SEATS_PER_ROW) {
            System.out.println("Posto non valido");
            return false;
        }
        if (seats[row - 1][col]) {
            System.out.println("Il posto è già occupato");
            return false;
        }
        seats[row - 1][col] = true;
        prenotazioni.add(new Prenotazione(passenger, row, column));
        System.out.println("Posto prenotato con successo");
        return true;
    }

    public void showBookingHistory() {
        if (prenotazioni.isEmpty()) {
            System.out.println("Nessuna prenotazione presente");
            return;
        }
        System.out.println("\nStorico prenotazioni:");
        for (Prenotazione p : prenotazioni) {
            System.out.println(p);
        }
    }

    public int getAvailableSeats() {
        int available = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                if (!seats[i][j]) {
                    available++;
                }
            }
        }
        return available;
    }

    //calcoliamo la percentuale di posti occupati
    public double getOccupiedSeatsPercentage() {
        int occupiedSeats = capacity - getAvailableSeats();
        // Calcoliamo la percentuale
        // proporzione:  capacità : 100% = posti occupati : x
        return (occupiedSeats * 100.0) / capacity;
    }

    public double getPrezzoAggiornato() {
        double price = 100.00;
        // se il tasso di occupazione posti è superiori al 50% il prezzo del volo aumenta del 10%
        if (getOccupiedSeatsPercentage() > 50) {
            price *= 1.10;
        }
        return price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public boolean[][] getOccupiedSeatsMap() {
        boolean[][] map = new boolean[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                map[i][j] = seats[i][j];  // rimosso il controllo null
            }
        }
        return map;
    }
}
