import javax.swing.*;
import java.awt.*;

public class FrmPrincipal extends JFrame {
    public JButton btnStart;
    private Carrera panelCarrera;
    public FrmPrincipal() {
        setTitle("Carrera de Bolas");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel personalizado
        panelCarrera = new Carrera(this);
        setLayout(new BorderLayout());
        add(panelCarrera, BorderLayout.CENTER); // Agregar el panel al JFrame


        btnStart = new JButton("Iniciar Carrera");
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnStart);
        add(panelBoton, BorderLayout.SOUTH);

        initListeners();
        this.setVisible(true);
    }

    public void initListeners(){
        btnStart.addActionListener(e -> {
            panelCarrera.iniciarGlobos();
            btnStart.setEnabled(false);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmPrincipal frame = new FrmPrincipal();
        });
    }
}
