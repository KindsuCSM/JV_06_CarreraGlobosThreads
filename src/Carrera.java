import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Carrera extends JPanel {
    private final List<Globo> globos;
    private List<Color> ordenLlegada = new ArrayList();
    private boolean carreraTerminada = false;
    private BufferedImage buffer;
    private final Image globoAmarillo = new ImageIcon(getClass().getResource("/Assets/Amarillo/amarillo_01.png")).getImage();
    private final Image globoAzul = new ImageIcon(getClass().getResource("/Assets/Azul/azul_01.png")).getImage();
    private final Image globoGris = new ImageIcon(getClass().getResource("/Assets/Gris/gris_01.png")).getImage();
    private final Image globoRojo = new ImageIcon(getClass().getResource("/Assets/Rojo/rojo_01.png")).getImage();
    private final Image globoVerde = new ImageIcon(getClass().getResource("/Assets/Verde/verde_01.png")).getImage();

    public Carrera() {
        globos = new ArrayList<>();
        //buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        buffer = new BufferedImage(450, 700, BufferedImage.TYPE_INT_ARGB);
    }

    public void iniciarGlobos() {
        // Crear varias globos con posiciones y colores diferentes
        globos.add(new Globo(50, 700, 30, Color.RED));
        globos.add(new Globo(125, 700, 30, Color.BLUE));
        globos.add(new Globo(200, 700, 30, Color.GREEN));
        globos.add(new Globo(275, 700, 30, Color.YELLOW));
        globos.add(new Globo(350, 700, 30, Color.LIGHT_GRAY));


        // Iniciar cada globo como un hilo independiente
        for (Globo globo : globos) {
            globo.start();
        }

        // Crear un hilo que actualice la pantalla periódicamente
        new Thread(() -> {
            while (!carreraTerminada) {
                repaint(); // Redibujar el panel
                verificarGanador();
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


        // Dibujar todas las globo en el buffer
        for (Globo globo : globos) {
            int x = globo.getX();
            int y = globo.getY();

            int width = globoRojo.getWidth(null);
            int height = globoRojo.getHeight(null);

            if(globo.getColor() == Color.RED) {
                g2d.drawImage(globoRojo, x, y, width, height, null);
            }else if(globo.getColor() == Color.BLUE) {
                g2d.drawImage(globoAzul, x, y, width, height, null);
            }else if(globo.getColor() == Color.GREEN) {
                g2d.drawImage(globoVerde, x, y, width, height, null);
            }else if (globo.getColor() == Color.YELLOW) {
                g2d.drawImage(globoAmarillo, x, y, width, height, null);
            }else if(globo.getColor() == Color.LIGHT_GRAY) {
                g2d.drawImage(globoGris, x, y, width, height, null);
            }
        }


        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, null);

        g2d.dispose(); // Liberar los recursos del Graphics2D
    }

    private void verificarGanador() {
        for (Globo globo : globos) {
            if(globo.getY() <= 0 && !ordenLlegada.contains(globo.getColor())) {
                ordenLlegada.add(globo.getColor());

                if(ordenLlegada.size() == globos.size()) {
                    carreraTerminada = true;
                    detenerCarrera();
                    podio();
                }
            }
        }
    }

    private void podio() {
        JOptionPane.showMessageDialog(this,
                    "Primero: " + obtenerNombreColor(ordenLlegada.get(0)) + "\n" +
                    "Segundo: " + obtenerNombreColor(ordenLlegada.get(1)) + "\n" +
                    "Tercero: " + obtenerNombreColor(ordenLlegada.get(2)),
                    "Podio Final", JOptionPane.INFORMATION_MESSAGE);

    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) {
            return "Rojo";
        } else if (color.equals(Color.BLUE)) {
            return "Azul";
        } else if (color.equals(Color.GREEN)) {
            return "Verde";
        } else if (color.equals(Color.YELLOW)) {
            return "Amarillo";
        } else {
            return "Gris";
        }

    }

    private void detenerCarrera() {
        for (Globo globo : globos) {
            globo.detener();
        }
    }
}
