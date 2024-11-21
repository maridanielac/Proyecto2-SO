package Proyecto;

import java.util.Random;

/**
 *
 * @author Maria Daniela
 */

//Falta revisar Inanición, que sean las 8 colas y que se vea la cola de repuesto
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

    // Revisar si funciona la Inanición
    public void incrementarInanicion() {
        contadorInanicion++;

        if (contadorInanicion >= 8 && prioridad > 1) {
            prioridad--; // Disminuye el valor de la prioridad (mejora de nivel)
            contadorInanicion = 0; // Reinicia el contador

            // Imprime el cambio de prioridad
            System.out.println("Personaje " + id + " de " + empresa + " mejora a prioridad " + prioridad);
        }
    }

    // Método para simular el 40% de probabilidad de pasar a prioridad 1 desde la cola de refuerzo
    public boolean intentarAscensoDesdeRefuerzo() {
        if (random.nextInt(100) < 40) {
            prioridad = 1;
            System.out.println("Personaje " + id + " de " + empresa + " asciende a prioridad 1 desde la cola de refuerzo");
            return true;
        }
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