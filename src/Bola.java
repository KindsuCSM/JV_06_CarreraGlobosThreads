import java.awt.*;

public class Bola extends Thread {
    private final int x; // Posición X de la bola
    private int y; // Posición Y de la bola
    private final int tamaño; // Tamaño de la bola
    private final Color color; // Color de la bola
    private boolean corriendo = true; // Control del movimiento
    private static String campeona;

    public Bola(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
    }

    @Override
    public void run() {
        while (corriendo && y > 0) { // Se mueve mientras no alcance el borde derecho
            y -= 3; // Mover hacia la derecha
            try {
                Thread.sleep((int) (Math.random()*25+25)); // Pausa para simular el movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x >= 500) {
                setCampeona(getColor());
                System.out.println(getCampeona());
            }
        }
        corriendo = false; // Detener la bola al llegar al borde
    }

    public void detener() {
        corriendo = false;
    }

    // Métodos para obtener los datos de la bola
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTamaño() {
        return tamaño;
    }

    public Color getColor() {
        return color;
    }

    // El acceso a la variable static campeona debe ser synchronized
    public synchronized static void setCampeona (Color color){
        if (color.equals(Color.RED))
            campeona = "xRojo";
        else
            if (color.equals(Color.BLUE))
                campeona = "xAzul";
            else
                if (color.equals(Color.GREEN))
                    campeona = "xVerde";
                else
                    if (color.equals(Color.YELLOW))
                        campeona = "xAmarillo";
                    else
                        if (color.equals(Color.ORANGE))
                            campeona = "xNaranja";
                        else
                            campeona = "xDesconocido";
    };

    public synchronized static String getCampeona () {return campeona;};

}