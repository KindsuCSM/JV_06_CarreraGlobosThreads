import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class PanelDeCarrera extends JPanel {
    private final List<Bola> bolas; // Lista de bolas
    private boolean carreraTerminada = false; // Control de la carrera
    private BufferedImage buffer; // Imagen en memoria para el doble buffer

    public PanelDeCarrera() {
        bolas = new ArrayList<>();
        //buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        buffer = new BufferedImage(450, 700, BufferedImage.TYPE_INT_ARGB);
        iniciarBolas();
    }

    private void iniciarBolas() {
        // Crear varias bolas con posiciones y colores diferentes
        bolas.add(new Bola(50, 700, 30, Color.RED));
        bolas.add(new Bola(125, 700, 30, Color.BLUE));
        bolas.add(new Bola(200, 700, 30, Color.GREEN));
        bolas.add(new Bola(275, 700, 30, Color.YELLOW));
        bolas.add(new Bola(350, 700, 30, Color.ORANGE));


        // Iniciar cada bola como un hilo independiente
        for (Bola bola : bolas) {
            bola.start();
        }

        // Crear un hilo que actualice la pantalla periódicamente
        new Thread(() -> {
            while (!carreraTerminada) {
                repaint(); // Redibujar el panel
                verificarGanador(); // Verificar si alguna bola llegó a la meta
                try {
                    Thread.sleep(1); // Actualización periódica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Asegurarse de que el buffer está creado
        if (buffer == null) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        // Dibujar en el buffer
        Graphics2D g2d = buffer.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight()); // Limpiar el fondo

        // Suavizamos los bordes (opcional)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // Dibujar todas las bolas en el buffer
        for (Bola bola : bolas) {
            // Dibujar el contorno negro
            g2d.setColor(Color.BLACK);
            g2d.fillOval(bola.getX(), bola.getY(), bola.getTamaño(), bola.getTamaño());
            // Dibujar el círculo interior rojo
            g2d.setColor(bola.getColor());
            g2d.fillOval(bola.getX() + 3, bola.getY() + 3, bola.getTamaño() - 7 , bola.getTamaño() - 7);
            //gBuffer.fillOval(x + 5, y + 5, diameter - 10, diameter - 10)
        }


        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, null);

        g2d.dispose(); // Liberar los recursos del Graphics2D
    }

    private void verificarGanador() {
        for (Bola bola : bolas) {
            if (bola.getX() >= 760) { //getWidth() - bola.getTamaño()) { // Meta alcanzada
                carreraTerminada = true;
                detenerCarrera();
                notificarGanador(bola);
                break;
            }
        }
    }

    private void detenerCarrera() {
        for (Bola bola : bolas) {
            bola.detener();
        }
    }

    private void notificarGanador(Bola ganadora) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                    "¡La bola de color " + obtenerNombreColor(ganadora.getColor()) + " ganó la carrera!",
                    "Ganador", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        if (color.equals(Color.YELLOW)) return "Amarillo";
        if (color.equals(Color.ORANGE)) return "Naranja";
        return "Desconocido";
    }
}
