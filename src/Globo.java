import javax.swing.*;
import java.awt.*;

public class Globo extends Thread {
    private int x;
    private int y;
    private final int tamaño;
    private final Color color;
    private boolean corriendo = true;
    private static String campeona;
    private double offset = 0;
    private final int velocidadSubida = 3;


    public Globo(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
    }

    @Override
    public void run() {
        while (corriendo && y > 0) { // Se mueve mientras no alcance el borde derecho
            y -= velocidadSubida;
            x += (int) (Math.sin(offset) * 2);;

            offset += 0.2;

            try {
                Thread.sleep((int) (Math.random()*25+25)); // Pausa para simular el movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (y <=0 ) {
                System.out.println(getCampeona());
                corriendo = false;
            }
        }
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

    public synchronized static String getCampeona () {return campeona;}

}