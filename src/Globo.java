import javax.swing.*;
import java.awt.*;

public class Globo extends Thread {
    private int x;
    private int y;
    private final int tamaño;
    private final Color color;
    private boolean corriendo = true;
    private boolean pausado = false;
    private static String campeona;
    private boolean estaExplotado;
    private double offset = 0;
    private int velocidadSubida = 3;
    private int velocidadOriginal;


    public Globo(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
    }

    @Override
    public void run() {
        while (corriendo && y > 0) { // Se mueve mientras no alcance el borde derecho
            if (!pausado) {
                y -= velocidadSubida;
                x += (int) (Math.sin(offset) * 2);

                offset += 0.2;
            }

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

    public boolean contienePunto(int mouseX, int mouseY) {
        int globoWidth = getTamaño() * 2; // Suponemos que el ancho es proporcional al tamaño
        int globoHeight = getTamaño() * 2;

        return mouseX >= getX() && mouseX <= getX() + globoWidth &&
                mouseY >= getY() && mouseY <= getY() + globoHeight;
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

    public boolean getCorriendo() {

        return corriendo;
    }

    public synchronized static String getCampeona () {return campeona;}

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


    public synchronized void pausar() {
        this.pausado = true;
    }

    public synchronized void reanudar() {
        this.pausado = false;
    }

    public synchronized boolean isPausado() {
        return pausado;
    }

    public synchronized void aplicarViento() {
        if (velocidadSubida > 0) { // Guardar la velocidad original solo si no está ya bajo efecto de viento
            velocidadOriginal = velocidadSubida;
            velocidadSubida /= 2; // Dividir la velocidad por 2
        }
    }

    public synchronized void revertirViento() {
        if (velocidadOriginal > 0) { // Restaurar la velocidad original
            velocidadSubida = velocidadOriginal;
            velocidadOriginal = 0; // Limpiar la variable de velocidad original
        }
    }
}