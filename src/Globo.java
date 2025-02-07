import javax.swing.*;
import java.awt.*;

public class Globo extends Thread {
    private int x;
    private int y;
    private final int tamaño;
    private final Color color;
    private boolean corriendo = true;
    private boolean estaExplotado;
    private double offset = 0;
    private final int velocidadSubida = 3;

    public Globo(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
        cadenaColor();
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

            if (y <=0 && !estaExplotado) {
                estaExplotado = true;
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

    public Color getColor() {
        return color;
    }

    public void explotar() {
        this.estaExplotado = true;
    }

    public boolean isExplotado() {
        return estaExplotado;
    }

    public String cadenaColor(){
        if(color == Color.YELLOW){
            return  "Amarillo";
        }else if(color == Color.RED){
            return "Rojo";
        }else if(color == Color.BLUE){
            return "Azul";
        }else if(color == Color.GREEN){
            return "Verde";
        }else if(color == Color.LIGHT_GRAY){
            return "Gris";
        }else{
            return "Rojo";
        }
    }

}