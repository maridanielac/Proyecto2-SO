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
    private Lista<Personajes> ganadores; // Lista personalizada de personajes ganadores
    private int contadorCiclos = 0;
    private Random random = new Random();
    private int victoriasStarWars = 0;
    private int victoriasStarTrek = 0;
    private int velocidadIA = 1000; // Velocidad por defecto en milisegundos

    public Admin() {
        this.colaNivel1 = new Cola(100); // Inicializa con capacidad para 100 personajes
        this.colaNivel2 = new Cola(100);
        this.colaNivel3 = new Cola(100);
        this.ganadores = new Lista<>();
    }

    private int generarIdUnico(String empresa) {
        int baseId = random.nextInt(100); // Genera un ID base entre 0 y 99
        if (empresa.equals("StarWars")) {
            return baseId; // Para StarWars, devuelve el ID base
        } else {
            return baseId + 100; // Para otras empresas, añade 100
        }
    }

    public void iniciarSimulacion() {
        // Generar 20 personajes para Star Wars
        for (int i = 0; i < 20; i++) {
            agregarPersonaje(new Personajes(generarIdUnico("StarWars"), "StarWars")); // Personaje de Star Wars
        }

        // Generar 20 personajes para Star Trek
        for (int i = 0; i < 20; i++) {
            agregarPersonaje(new Personajes(generarIdUnico("StarTrek"), "StarTrek")); // Personaje de Star Trek
        }
        System.out.println("20 personajes de Star Wars y 20 de Star Trek han sido creados.");
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

        if (contadorCiclos % 2 == 0) {
            agregarPersonajesConProbabilidad();
        }

        Personajes personaje1 = obtenerPersonajeParaCombate();
        Personajes personaje2 = obtenerPersonajeParaCombate();

        if (personaje1 != null && personaje2 != null) {
            IA inteligenciaArtificial = new IA(personaje1.getId(), personaje2.getId());
            int ganadorId = inteligenciaArtificial.getGanador();

            Personajes ganador = (ganadorId == personaje1.getId()) ? personaje1 : personaje2;

            actualizarMarcador(ganador.getEmpresa());
            manejarGanadorYReinsercion(ganador, personaje1, personaje2);
        }
    }

    private void manejarGanadorYReinsercion(Personajes ganador, Personajes personaje1, Personajes personaje2) {
        ganadores.agregar(ganador);
        System.out.println("Ganador registrado: " + ganador);
        reinsercionCola(personaje1);
        reinsercionCola(personaje2);
    }

    private void reinsercionCola(Personajes personaje) {
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
        System.out.println("Personaje reintegrado a su cola: " + personaje);
    }

    private void agregarPersonajesConProbabilidad() {
        if (random.nextInt(100) < 80) {
            agregarPersonaje(new Personajes(generarIdUnico("StarWars"), "StarWars"));
            agregarPersonaje(new Personajes(generarIdUnico("StarTrek"), "StarTrek"));
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
        return null;
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

    private void actualizarMarcador(String empresa) {
        if (empresa.equals("StarWars")) {
            victoriasStarWars++;
        } else if (empresa.equals("StarTrek")) {
            victoriasStarTrek++;
        }
        System.out.println("Marcador: Star Wars " + victoriasStarWars + " - Star Trek " + victoriasStarTrek);
    }

    public void mostrarColas() {
        System.out.println("Cola Nivel 1: " + colaNivel1);
        System.out.println("Cola Nivel 2: " + colaNivel2);
        System.out.println("Cola Nivel 3: " + colaNivel3);
    }

    public void mostrarGanadores() {
        System.out.println("Lista de ganadores:");
        for (int i = 0; i < ganadores.tamanio(); i++) {
            System.out.println(ganadores.obtener(i));
        }
    }
}