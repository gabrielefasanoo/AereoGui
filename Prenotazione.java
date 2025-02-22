public class Prenotazione {
    private Passengers passenger;
    private int row;
    private char column;
    private String timestamp;

    public Prenotazione(Passengers passenger, int row, char column) {
        this.passenger = passenger;
        this.row = row;
        this.column = column;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return String.format("Posto: %d%c - Passeggero: %s %s - Data: %s",
            row, column, passenger.getName(), passenger.getSurname(), timestamp);
    }
}
