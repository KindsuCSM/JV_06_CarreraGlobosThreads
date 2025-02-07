package View;

import javax.swing.*;
import java.awt.*;

public class PanelFondo extends JPanel {
    private Image imagenFondo;

    public PanelFondo() {
        try {
            // Cargar la imagen de fondo desde la ruta proporcionada
            imagenFondo = new ImageIcon(getClass().getResource("/Assets/Fondo/background.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + "/Assets/Fondo/Background.png");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen de fondo si está cargada
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (imagenFondo != null) {
            return new Dimension(imagenFondo.getWidth(null), imagenFondo.getHeight(null));
        }
        return new Dimension(450, 700); // Tamaño predeterminado si no hay imagen
    }
}