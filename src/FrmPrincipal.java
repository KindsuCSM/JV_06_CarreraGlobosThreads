import javax.swing.*;
import java.awt.*;

public class FrmPrincipal extends JFrame implements CarreraTerminadaListener{
    private JButton startButton; // Botón "Iniciar Carrera"
    public JButton vientoButton; // Botón "Viento"
    private Carrera panelCarrera; // Referencia al panel de la carrera

    public FrmPrincipal() {
        setTitle("Carrera de GLOBOS");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear PanelFondo
        PanelFondo fondoPanel = new PanelFondo();

        // Crear el panel del techo con una imagen fija
        Techo techoPanel = new Techo();


        // Crear el panel personalizado
        panelCarrera = new Carrera(this);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(techoPanel, BorderLayout.NORTH); // Agregar el techo arriba
        fondoPanel.add(panelCarrera, BorderLayout.CENTER); // Agregar el panel de la carrera en el centro



        // Botón "Iniciar Carrera"
        startButton = new JButton("Iniciar Carrera");
        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            vientoButton.setVisible(true);
            panelCarrera.iniciarGlobos();
        });

        // Botón "Viento"
        vientoButton = new JButton("VIENTO!");
        vientoButton.setVisible(false);
        vientoButton.addActionListener(e -> {
            vientoButton.setEnabled(false);
            panelCarrera.iniciarViento(vientoButton);

        });

        // Panel para los botones
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelBoton.add(startButton);
        panelBoton.add(vientoButton);

        fondoPanel.add(panelBoton, BorderLayout.SOUTH);

        add(fondoPanel);

        // Establecer el listener para notificar cuando la carrera termina
        panelCarrera.setCarreraTerminadaListener(this);

    }

    @Override
    public void onCarreraTerminada() {
        vientoButton.setVisible(false); // Ocultar el botón "Viento"
        startButton.setVisible(true); // Mostrar el botón "Iniciar Carrera"
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmPrincipal frame = new FrmPrincipal();
            frame.setVisible(true);
        });
    }
}
