import javax.swing.*;
import java.awt.*;

public class FrmPrincipal extends JFrame {
    public FrmPrincipal() {
        setTitle("Carrera de Bolas");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel personalizado
        Carrera panel = new Carrera();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER); // Agregar el panel al JFrame


        JButton startButton = new JButton("Iniciar Carrera");
        startButton.addActionListener(e -> panel.iniciarGlobos());
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(startButton);
        add(panelBoton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmPrincipal frame = new FrmPrincipal();
            frame.setVisible(true);
        });
    }
}
