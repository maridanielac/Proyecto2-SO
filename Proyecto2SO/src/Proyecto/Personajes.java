/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

import java.util.Random;

/**
 *
 * @author Maria Daniela
 */
public class Personajes {
    private int id;
    private int prioridad; // 1 = Excepcional, 2 = Promedio, 3 = Deficiente
    private int calidadHabilidades;
    private int calidadPuntosVida;
    private int calidadFuerza;
    private int calidadAgilidad;
    private int contadorInanicion = 0;
    private Random random = new Random();

    public Personajes(int id) {
        this.id = id;
        this.calidadHabilidades = random.nextInt(100) < 60 ? 1 : 0; // 60% probabilidad de calidad
        this.calidadPuntosVida = random.nextInt(100) < 70 ? 1 : 0; // 70% probabilidad de calidad
        this.calidadFuerza = random.nextInt(100) < 50 ? 1 : 0; // 50% probabilidad de calidad
        this.calidadAgilidad = random.nextInt(100) < 40 ? 1 : 0; // 40% probabilidad de calidad
        this.prioridad = determinarPrioridad();
    }

    private int determinarPrioridad() {
        int atributosDeCalidad = calidadHabilidades + calidadPuntosVida + calidadFuerza + calidadAgilidad;

        if (atributosDeCalidad >= 3) {
            return 1; // Excepcional
        } else if (atributosDeCalidad == 2) {
            return 2; // Promedio
        } else {
            return 3; // Deficiente
        }
    }

    public void incrementarInanicion() {
        contadorInanicion++;
        if (contadorInanicion >= 8 && prioridad > 1) {
            prioridad--; // Subir de nivel de prioridad
            contadorInanicion = 0; // Reiniciar el contador
            System.out.println("Personaje " + id + " sube a prioridad " + prioridad);
        }
    }

    // Método para simular el 40% de probabilidad de pasar a prioridad 1 desde la cola de refuerzo
    public boolean intentarAscensoDesdeRefuerzo() {
        if (random.nextInt(100) < 40) {
            prioridad = 1;
            System.out.println("Personaje " + id + " asciende a prioridad 1 desde la cola de refuerzo");
            return true;
        }
        return false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getContadorInanicion() {
        return contadorInanicion;
    }

    public void resetContadorInanicion() {
        contadorInanicion = 0;
    }
}
