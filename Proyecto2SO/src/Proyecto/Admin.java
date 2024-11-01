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
public class Admin {
    private Cola colaNivel1; // Prioridad alta
    private Cola colaNivel2; // Prioridad media
    private Cola colaNivel3; // Prioridad baja
    private int contadorCiclos = 0;
    private Random random = new Random();

    public Admin() {
        this.colaNivel1 = new Cola();
        this.colaNivel2 = new Cola();
        this.colaNivel3 = new Cola();
    }

    public void revisarColas() {
        contadorCiclos++;

        // Cada dos ciclos de revisión se agregan nuevos personajes
        if (contadorCiclos % 2 == 0) {
            agregarPersonajesConProbabilidad();
        }

        // Obtén dos personajes para el combate
        Personajes personaje1 = obtenerPersonajeParaCombate();
        Personajes personaje2 = obtenerPersonajeParaCombate();

        // Si hay personajes disponibles para pelear, llama a la IA
        if (personaje1 != null && personaje2 != null) {
            IA inteligenciaArtificial = new IA(personaje1.getId(), personaje2.getId(), 0);
            inteligenciaArtificial.VerGanador();
        }
    }

    private void agregarPersonajesConProbabilidad() {
        // Probabilidad del 80% para agregar nuevos personajes
        if (random.nextInt(100) < 80) {
            // Se agregan personajes de diferentes niveles de prioridad
            agregarPersonaje(new Personajes(generarIdUnico())); // Personaje de Star Wars
            agregarPersonaje(new Personajes(generarIdUnico())); // Personaje de Star Trek
            System.out.println("Se han agregado nuevos personajes a las colas de prioridad.");
        }
    }

    private Personajes obtenerPersonajeParaCombate() {
        if (!colaNivel1.estaVacia()) {
            return colaNivel1.eliminar();
        } else if (!colaNivel2.estaVacia()) {
            return colaNivel2.eliminar();
        } else if (!colaNivel3.estaVacia()) {
            return colaNivel3.eliminar();
        }
        return null; // No hay personajes en ninguna cola
    }

    private void agregarPersonaje(Personajes personaje) {
        switch (personaje.getPrioridad()) {
            case 1:
                colaNivel1.agregar(personaje);
                break;
            case 2:
                colaNivel2.agregar(personaje);
                break;
            case 3:
                colaNivel3.agregar(personaje);
                break;
        }
    }

    private int generarIdUnico() {
        return random.nextInt(10000); // Método para generar un ID único
    }
}
