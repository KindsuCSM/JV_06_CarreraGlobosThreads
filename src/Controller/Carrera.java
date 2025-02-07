package Controller;

import Model.Globo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Carrera extends JPanel {
    // Listas
    private final List<Globo> globos;
    private List<Color> ordenLlegada = new ArrayList();
    // Booleanos
    private boolean carreraTerminada = false;
    // Buffer para pintar los sprites
    private BufferedImage buffer;
    // Interfaz
    private CarreraTerminadaListener listener;
    private JLabel fpsLabel; // Nuevo JLabel para mostrar los FPS
    private long frameCount = 0; // Contador de frames
    private long lastFpsTime = System.currentTimeMillis(); // Tiempo para calcular FPS
    private Image fondo = new ImageIcon(getClass().getResource("/Assets/Fondo/background.png")).getImage();;
    // Imagenes de globos y de fondo
    private final Image globoAmarillo = new ImageIcon(getClass().getResource("/Assets/Amarillo/amarillo_01.png")).getImage();
    private final Image globoAzul = new ImageIcon(getClass().getResource("/Assets/Azul/azul_01.png")).getImage();
    private final Image globoGris = new ImageIcon(getClass().getResource("/Assets/Gris/gris_01.png")).getImage();
    private final Image globoRojo = new ImageIcon(getClass().getResource("/Assets/Rojo/rojo_01.png")).getImage();
    private final Image globoVerde = new ImageIcon(getClass().getResource("/Assets/Verde/verde_01.png")).getImage();
    // Imagenes de explosiones
    private Image spriteExplosionUno;
    private Image SpriteExplosionDos;
    private Image SpriteExplosionTres;
    private Image SpriteExplosionCuatro;
    private Image SpriteExplosionCinco;

    // Constructor de la carrera que inicializa buffer y lista de globos
    public Carrera() {
        globos = new ArrayList<>();
        buffer = new BufferedImage(450, 700, BufferedImage.TYPE_INT_ARGB);
        // Crear el JLabel para los FPS
        fpsLabel = new JLabel("FPS: 0");
        fpsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        fpsLabel.setForeground(Color.BLACK);
        fpsLabel.setBounds(10, 550, 100, 20); // Posición absoluta
        setLayout(null);
        add(fpsLabel); // Agregar el JLabel al panel
    }

    public void setCarreraTerminadaListener(CarreraTerminadaListener listener) {
        this.listener = listener;
    }

    // Funcion para iniciar la carrera de globos
    public void iniciarGlobos() {
        globos.clear(); // Limpiar los globos existentes
        ordenLlegada.clear(); // Limpiar el orden de llegada
        carreraTerminada = false;

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

            if (listener != null) {
                listener.onCarreraTerminada(); // Notificar que la carrera ha terminado
            }
        }).start();
    }

    // Función que pintará la pantalla con los sprites tanto de globo como de globo explotado
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Incrementar el contador de frames
        frameCount++;

        // Calcular los FPS cada segundo
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFpsTime >= 1000) { // Cada segundo
            int fps = (int) (frameCount * 1000 / (currentTime - lastFpsTime));
            fpsLabel.setText("FPS: " + fps); // Actualizar el texto del JLabel
            frameCount = 0; // Reiniciar el contador
            lastFpsTime = currentTime; // Actualizar el tiempo
        }

        // Asegurarse de que el buffer está creado
        if (buffer == null) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        // Dibujar en el buffer
        Graphics2D g2d = buffer.createGraphics();


        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight()); // Limpiar el fondo

        // Suavizamos los bordes (opcional)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(fondo, 0, 0, this);

        // Dibujar todas las globo en el buffer
        for (Globo globo : globos) {
            int x = globo.getX();
            int y = globo.getY();

            int width = globoRojo.getWidth(null);
            int height = globoRojo.getHeight(null);

            if (globo.isExplotado()) {
                // Si ha explotado, dibujamos explosion
                actualizarExplosion(globo);
                //g2d.drawImage(spriteExplosionUno, x, y, width, height, null);
                //g2d.drawImage(SpriteExplosionDos, x, y, width, height, null);
                g2d.drawImage(SpriteExplosionTres, x, y, width, height, null);
                //g2d.drawImage(SpriteExplosionCuatro, x, y, width, height, null);
                g2d.drawImage(SpriteExplosionCinco, x, y, width, height, null);
            } else {
                // Si no ha explotado, dibujamos el globo 1
                if (globo.getColor() == Color.RED) {
                    g2d.drawImage(globoRojo, x, y, width, height, null);
                } else if (globo.getColor() == Color.BLUE) {
                    g2d.drawImage(globoAzul, x, y, width, height, null);
                } else if (globo.getColor() == Color.GREEN) {
                    g2d.drawImage(globoVerde, x, y, width, height, null);
                } else if (globo.getColor() == Color.YELLOW) {
                    g2d.drawImage(globoAmarillo, x, y, width, height, null);
                } else if (globo.getColor() == Color.LIGHT_GRAY) {
                    g2d.drawImage(globoGris, x, y, width, height, null);
                }
            }
        }


        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, null);

        g2d.dispose(); // Liberar los recursos
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450, 700); // Asegurarse de que el panel tenga el tamaño correcto
    }

    // Funcion para parar el globo cuando cliquemos sobre el
    @Override
    public void addNotify() {
        super.addNotify();
        this.addMouseListener(new MouseAdapter() {
            // Listener para cuando pulsemos
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                for (Globo globo : globos) {
                    if (globo.getCorriendo() && globo.contienePunto(mouseX, mouseY)) {
                        globo.pausar(); // Pausar el globo clicado
                        break;
                    }
                }
            }

            // Listener para cuando dejemos de pulsarlo
            @Override
            public void mouseReleased(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                for (Globo globo : globos) {
                    if (globo.getCorriendo() && globo.contienePunto(mouseX, mouseY)) {
                        globo.reanudar(); // Reanudar el globo clicado
                        break;
                    }
                }
            }
        });
    }

    public void iniciarViento(JButton button) {
        new Thread(() -> {
            // Aplicar viento a todos los globos
            for (Globo globo : globos) {
                if (globo.getCorriendo()) {
                    globo.aplicarViento();
                }
            }

            try {
                Thread.sleep(2000); // Esperar 2 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Revertir el efecto de viento
            for (Globo globo : globos) {
                if (globo.getCorriendo()) {
                    globo.revertirViento();
                }
            }
            button.setEnabled(true);
        }).start();
    }

    // Funcion que obtiene la lista de los globos a medida que lleguen a la meta
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

    // JOptionPane para mostrar el podio
    private void podio() {
        JOptionPane.showMessageDialog(this,
                    "Medalla de oro: " + obtenerNombreColor(ordenLlegada.get(4)) + "\n" +
                    "Medalla de plata: " + obtenerNombreColor(ordenLlegada.get(3)) + "\n" +
                    "Medalla de bronce: " + obtenerNombreColor(ordenLlegada.get(2)),
                    "Podio Final", JOptionPane.INFORMATION_MESSAGE);

    }
    // Obtenemos el color del globo para mostrarlo en el podio
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

    // Funcion que detiene el avance de los globos
    private void detenerCarrera() {
        for (Globo globo : globos) {
            globo.detener();
        }
    }

    // Opcion que da valor a las rutas de las explosiones
    public void actualizarExplosion(Globo globo) {

        spriteExplosionUno = new ImageIcon(getClass().getResource("/Assets/" + globo.cadenaColor() + "/" + globo.cadenaColor().toLowerCase() + "_01.png")).getImage();
        SpriteExplosionDos = new ImageIcon(getClass().getResource("/Assets/" + globo.cadenaColor() + "/" + globo.cadenaColor().toLowerCase() + "_02.png")).getImage();
        SpriteExplosionTres = new ImageIcon(getClass().getResource("/Assets/" + globo.cadenaColor() + "/" + globo.cadenaColor().toLowerCase() + "_03.png")).getImage();
        SpriteExplosionCuatro = new ImageIcon(getClass().getResource("/Assets/" + globo.cadenaColor() + "/" + globo.cadenaColor().toLowerCase() + "_04.png")).getImage();
        SpriteExplosionCinco = new ImageIcon(getClass().getResource("/Assets/" + globo.cadenaColor() + "/" + globo.cadenaColor().toLowerCase() + "_05.png")).getImage();
        SpriteExplosionCinco = new ImageIcon(getClass().getResource("/Assets/" + globo.cadenaColor() + "/" + globo.cadenaColor().toLowerCase() + "_06.png")).getImage();

    }
}
