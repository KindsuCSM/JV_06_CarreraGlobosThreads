import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Techo extends JPanel implements Runnable {
    private BufferedImage imagen; // Imagen fija para el techo
    private Thread hiloVacio; // Hilo vacío
    private Image techo;
    private Image fondo;

    public Techo() {
        try {
            techo = new ImageIcon(getClass().getResource("/Assets/Techo/techo.png")).getImage();
            fondo = new ImageIcon(getClass().getResource("/Assets/Fondo/background.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del techo: " + "/Assets/Techo/techo.png");
            e.printStackTrace();
        }

        // Iniciar el hilo vacío
        hiloVacio = new Thread(this);
        hiloVacio.start();
    }

    @Override
    public void run() {
        // Hilo vacío: No realiza ninguna acción
        while (true) {
            // El hilo no hace nada, simplemente duerme indefinidamente
            try {
                Thread.sleep(Long.MAX_VALUE); // Dormir durante mucho tiempo
            } catch (InterruptedException e) {
                break; // Salir si el hilo es interrumpido
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen fija si está cargada
        if (techo != null) {
            g.drawImage(fondo, 0, 0, null);
            g.drawImage(techo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (imagen != null) {
            return new Dimension(imagen.getWidth(null), imagen.getHeight(null));
        }
        return new Dimension(450, 50); // Tamaño predeterminado si no hay imagen
    }
}