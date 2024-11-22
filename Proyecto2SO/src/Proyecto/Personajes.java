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
    private String empresa; // Nuevo atributo para la empresa
    private int prioridad; // 1 = Excepcional, 2 = Promedio, 3 = Deficiente
    private int calidadHabilidades;
    private int calidadPuntosVida;
    private int calidadFuerza;
    private int calidadAgilidad;
    private int contadorInanicion = 0;
    private Random random = new Random();

    public Personajes(int id, String empresa) {
        this.id = id;
        this.empresa = empresa; // Asignar la empresa
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

   public void incrementarInanicion(Cola colaActual, Cola colaMedia, Cola colaAlta) {
    // Incrementamos el contador de inanición
    
    contadorInanicion++; 

    // Si el contador llega a 8 y el personaje no está en prioridad 1
    if (contadorInanicion == 8 && prioridad > 1) {
        Personajes personaje = colaActual.eliminar(); // Elimina de la cola actual

        if (personaje != null) {
            // Reducimos la prioridad del personaje
            prioridad--; 
            contadorInanicion = 0; // Reinicia el contador de inanición

            // Mueve el personaje a la nueva cola según su prioridad
            switch (prioridad) {
                case 2:
                    colaMedia.agregar(personaje); // Mover a cola de prioridad 2
                    break;
                case 1:
                    colaAlta.agregar(personaje); // Mover a cola de prioridad 1
                    break;
                default:
                    break;
            }

            // Mensaje de cambio de prioridad
            System.out.println("Personaje " + personaje.getId() + " de " + personaje.getEmpresa() + 
                               " mejora a prioridad " + prioridad);
        }
    }
}

    public boolean intentarAscensoDesdeRefuerzo(Cola colaRefuerzo, Cola colaPrioridad1) {
        if (random.nextInt(100) < 40) {
            colaPrioridad1.agregar(this); // Mueve a la cola de prioridad 1
            return true; // Indica que el personaje ascendió
        }
        colaRefuerzo.agregar(this); // Si no ascendió, regresa a la cola de refuerzo
        return false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getEmpresa() {
        return empresa; // Método para obtener la empresa
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

    @Override
    public String toString() {
        return "ID: " + id + ", Empresa: " + empresa + ", Prioridad: " + prioridad +
                ", Calidad Habilidades: " + calidadHabilidades +
                ", Calidad Puntos Vida: " + calidadPuntosVida +
                ", Calidad Fuerza: " + calidadFuerza +
                ", Calidad Agilidad: " + calidadAgilidad +
                ", Inanición: " + contadorInanicion;
    }
}