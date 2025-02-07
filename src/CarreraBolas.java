import javax.swing.*;

public class CarreraBolas extends JFrame {
    public CarreraBolas() {
        setTitle("Carrera de Bolas");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel personalizado
        PanelDeCarrera panel = new PanelDeCarrera();
        add(panel); // Agregar el panel al JFrame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarreraBolas frame = new CarreraBolas();
            frame.setVisible(true);
        });
    }
}
