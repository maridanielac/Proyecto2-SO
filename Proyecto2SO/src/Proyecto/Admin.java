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
    private int victoriasStarWars = 0;
    private int victoriasStarTrek = 0;
    private int velocidadIA = 1000; // Velocidad por defecto en milisegundos

    public Admin() {
        this.colaNivel1 = new Cola(100); // Inicializa con capacidad para 100 personajes
        this.colaNivel2 = new Cola(100);
        this.colaNivel3 = new Cola(100);
    }

    public void iniciarSimulacion() {
        for (int i = 0; i < 20; i++) {
            agregarPersonaje(new Personajes(generarIdUnico())); // Personaje de Star Wars
            agregarPersonaje(new Personajes(generarIdUnico())); // Personaje de Star Trek
        }
        System.out.println("20 personajes han sido creados.");
        iniciarCiclo(); // Comienza el ciclo de combate
    }

    public void iniciarCiclo() {
        while (!colaNivel1.estaVacia() || !colaNivel2.estaVacia() || !colaNivel3.estaVacia()) {
            revisarColas();
            mostrarColas();
            try {
                Thread.sleep(1000); // Pausa entre ciclos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
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
        IA inteligenciaArtificial = new IA(personaje1.getId(), personaje2.getId()); // Cambia aquí
        inteligenciaArtificial.VerGanador();
        actualizarMarcador(inteligenciaArtificial.getGanador());
    }
}

    private void agregarPersonajesConProbabilidad() {
        // Probabilidad del 80% para agregar nuevos personajes
        if (random.nextInt(100) < 80) {
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

    private void actualizarMarcador(int ganador) {
        if (ganador == 1) {
            victoriasStarWars++;
        } else if (ganador == 2) {
            victoriasStarTrek++;
        }
        System.out.println("Marcador: Star Wars " + victoriasStarWars + " - Star Trek " + victoriasStarTrek);
    }

    public void setVelocidad(int milisegundos) {
        this.velocidadIA = milisegundos; // Cambiar velocidad de IA
    }

    public void mostrarColas() {
        System.out.println("Cola Nivel 1: " + colaNivel1);
        System.out.println("Cola Nivel 2: " + colaNivel2);
        System.out.println("Cola Nivel 3: " + colaNivel3);
    }
    
    
}
