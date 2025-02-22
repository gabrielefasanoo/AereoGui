import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Volo volo1 = new Volo("AZ2024", 36, "Roma", "Milano");
        AirplaneGUI gui = new AirplaneGUI(volo1);
        gui.setVisible(true);
    }
}
