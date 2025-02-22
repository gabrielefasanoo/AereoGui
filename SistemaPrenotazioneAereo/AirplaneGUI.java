import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AirplaneGUI extends JFrame {
    private Volo volo;
    private JButton[][] seatButtons;
    private JPanel seatsPanel;
    private JLabel statusLabel;

    public AirplaneGUI(Volo volo) {
        this.volo = volo;
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Sistema Prenotazione Aereo - " + volo.getFlightNumber());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superiore con info volo
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Volo: " + volo.getFlightNumber() + " | " +
                                volo.getDepartureCity() + " → " + volo.getDestinationCity()));
        add(infoPanel, BorderLayout.NORTH);

        // Panel centrale con i posti
        seatsPanel = new JPanel(new GridLayout(6, 6, 5, 5));
        seatsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        seatButtons = new JButton[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                seatButtons[i][j] = new JButton((i + 1) + "" + (char)('A' + j));
                seatButtons[i][j].setPreferredSize(new Dimension(60, 60));
                final int row = i + 1;
                final char col = (char)('A' + j);
                seatButtons[i][j].addActionListener(e -> bookSeat(row, col));
                seatsPanel.add(seatButtons[i][j]);
            }
        }
        add(seatsPanel, BorderLayout.CENTER);

        // Panel inferiore con statistiche
        statusLabel = new JLabel("Posti disponibili: " + volo.getAvailableSeats());
        JPanel statsPanel = new JPanel();
        statsPanel.add(statusLabel);
        add(statsPanel, BorderLayout.SOUTH);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opzioni");
        JMenuItem showStats = new JMenuItem("Mostra statistiche");
        showStats.addActionListener(e -> showStatistics());
        menu.add(showStats);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        updateSeatDisplay();
        pack();
        setLocationRelativeTo(null);
    }

    private void bookSeat(int row, char col) {
        try {
            String nome = JOptionPane.showInputDialog("Inserisci nome passeggero:");
            if (nome == null) return;
            String cognome = JOptionPane.showInputDialog("Inserisci cognome passeggero:");
            if (cognome == null) return;
            String etaStr = JOptionPane.showInputDialog("Inserisci età passeggero:");
            if (etaStr == null) return;
            int eta = Integer.parseInt(etaStr);

            Passengers passenger = new Passengers(nome, cognome, eta);
            volo.bookSeat(row, col, passenger);
            updateSeatDisplay();
            statusLabel.setText("Posti disponibili: " + volo.getAvailableSeats());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSeatDisplay() {
        boolean[][] occupiedSeats = volo.getOccupiedSeatsMap();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (occupiedSeats[i][j]) {
                    seatButtons[i][j].setBackground(Color.RED);
                    seatButtons[i][j].setEnabled(false);
                } else {
                    seatButtons[i][j].setBackground(Color.GREEN);
                    seatButtons[i][j].setEnabled(true);
                }
            }
        }
    }

    private void showStatistics() {
        String stats = String.format("""
            Statistiche volo %s:
            Posti occupati: %.1f%%
            Prezzo attuale: %.2f€
            """, 
            volo.getFlightNumber(),
            volo.getOccupiedSeatsPercentage(),
            volo.getPrezzoAggiornato());
        JOptionPane.showMessageDialog(this, stats, "Statistiche", JOptionPane.INFORMATION_MESSAGE);
    }
}
