/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

import Proyecto.Cola;
import Proyecto.IA;
import Proyecto.Lista;
import Proyecto.Personajes;
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
    private int velocidadIA = 10000; // Velocidad por defecto en milisegundos
    private Cola colaRefuerzo; // Cola de refuerzo para combates no realizados
    IA inteligenciaArtificial;
    
     public Admin() {
        this.colaNivel1 = new Cola(100); // Inicializa con capacidad para 100 personajes
        this.colaNivel2 = new Cola(100);
        this.colaNivel3 = new Cola(100);
        this.ganadores = new Lista<>();
        this.colaRefuerzo = new Cola(100); // Capacidad inicial para la cola de refuerzo
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
                Thread.sleep(velocidadIA); // Pausa entre ciclos
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

        // Mueve personajes desde la cola de refuerzo con probabilidad de ascenso
        if (!colaRefuerzo.estaVacia()) {
            Personajes personaje = colaRefuerzo.eliminar();
            if (personaje.intentarAscensoDesdeRefuerzo(colaRefuerzo, colaNivel1)) {
                System.out.println("Personaje " + personaje.getId() + " asciende desde la cola de refuerzo a prioridad 1.");
            }
        }

        // Combate
        Personajes personaje1 = obtenerPersonajeParaCombate();
        Personajes personaje2 = obtenerPersonajeParaCombate();
        
        // Incrementar inanición para los personajes no seleccionados
    if (personaje1 != null) {
        personaje1.incrementarInanicion(colaNivel1, colaNivel2, colaNivel3);
    }
    if (personaje2 != null) {
        personaje2.incrementarInanicion(colaNivel1, colaNivel2, colaNivel3);
    }

    // Incrementar inanición para todos los personajes en cola 2 y cola 3
    incrementarInanicionCola(colaNivel2);
    incrementarInanicionCola(colaNivel3);

        if (personaje1 != null && personaje2 != null) {
            inteligenciaArtificial = new IA(personaje1.getId(), personaje2.getId());
            
            // Simula el tiempo que toma la IA para decidir el ganador
            try {
                Thread.sleep(velocidadIA); // Pausa por la velocidad de la IA
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            inteligenciaArtificial.VerGanador(colaRefuerzo);
            int ganadorId = inteligenciaArtificial.getGanador();
            
             String estadoIA = inteligenciaArtificial.getEstado();

            if (ganadorId != -1) {
                Personajes ganador = (ganadorId == personaje1.getId()) ? personaje1 : personaje2;
                actualizarMarcador(ganador.getEmpresa());
                manejarGanadorYReinsercion(ganador, personaje1, personaje2);
            }
        }
    }
    
    private void incrementarInanicionCola(Cola cola) {
    for (int i = cola.frente; i < cola.finalCola; i++) {
        Personajes personaje = cola.personajes[i];
        if (personaje != null) {
            personaje.incrementarInanicion(colaNivel1, colaNivel2, colaNivel3);
        }
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
        System.out.println("Cola de Refuerzo: " + colaRefuerzo);
    }

    public void mostrarGanadores() {
        System.out.println("Lista de ganadores:");
        for (int i = 0; i < ganadores.tamanio(); i++) {
            System.out.println(ganadores.obtener(i));
        }
    }
    
    public String getColaRefuerzosSW() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaRefuerzo.frente; i < colaRefuerzo.finalCola; i++) {
            String empresa = colaRefuerzo.personajes[i].getEmpresa();
  
             if (empresa.equals("StarWars")) {
                sb.append("ID: ").append(colaRefuerzo.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }

    public String getColaRefuerzosST() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaRefuerzo.frente; i < colaRefuerzo.finalCola; i++) {
            String empresa = colaRefuerzo.personajes[i].getEmpresa();
  
             if (empresa.equals("StarTrek")) {
                sb.append("ID: ").append(colaRefuerzo.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }


     public String getColaNivel1SW() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaNivel1.frente; i < colaNivel1.finalCola; i++) {
            // Asumiendo que getEmpresa() devuelve un String que indica la empresa del personaje
            String empresa = colaNivel1.personajes[i].getEmpresa();
        
        // Si es de Star Trek, lo agrega a la otra cola
             if (empresa.equals("StarWars")) {
                sb.append("ID: ").append(colaNivel1.personajes[i].getId()).append(", ");
        }
//              .append(", Prioridad: ").append(colaNivel1.personajes[i].getPrioridad())
//              .append("\n");
        }
        return sb.toString();
    }

     public String getColaNivel1ST() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaNivel1.frente; i < colaNivel1.finalCola; i++) {
            String empresa = colaNivel1.personajes[i].getEmpresa();
  
             if (empresa.equals("StarTrek")) {
                sb.append("ID: ").append(colaNivel1.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }

    public void setColaNivel1(Cola colaNivel1) {
        this.colaNivel1 = colaNivel1;
    }

   public String getColaNivel2SW() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaNivel2.frente; i < colaNivel2.finalCola; i++) {
            String empresa = colaNivel2.personajes[i].getEmpresa();
  
             if (empresa.equals("StarWars")) {
                sb.append("ID: ").append(colaNivel2.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }
   
   public String getColaNivel2ST() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaNivel2.frente; i < colaNivel2.finalCola; i++) {
            String empresa = colaNivel2.personajes[i].getEmpresa();
  
             if (empresa.equals("StarTrek")) {
                sb.append("ID: ").append(colaNivel2.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }

    public void setColaNivel2(Cola colaNivel2) {
        this.colaNivel2 = colaNivel2;
    }

      public String getColaNivel3SW() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaNivel3.frente; i < colaNivel3.finalCola; i++) {
            String empresa = colaNivel3.personajes[i].getEmpresa();
  
             if (empresa.equals("StarWars")) {
                sb.append("ID: ").append(colaNivel3.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }
      
      public String getColaNivel3ST() {
        StringBuilder sb = new StringBuilder();
        for (int i = colaNivel3.frente; i < colaNivel3.finalCola; i++) {
            String empresa = colaNivel3.personajes[i].getEmpresa();
  
             if (empresa.equals("StarTrek")) {
                sb.append("ID: ").append(colaNivel3.personajes[i].getId()).append(", ");
        }
        }
        return sb.toString();
    }

    public void setColaNivel3(Cola colaNivel3) {
        this.colaNivel3 = colaNivel3;
    }

    public int getContadorCiclos() {
        return contadorCiclos;
    }

    public void setContadorCiclos(int contadorCiclos) {
        this.contadorCiclos = contadorCiclos;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getVictoriasStarWars() {
        return victoriasStarWars;
    }

    public void setVictoriasStarWars(int victoriasStarWars) {
        this.victoriasStarWars = victoriasStarWars;
    }

    public int getVictoriasStarTrek() {
        return victoriasStarTrek;
    }

    public void setVictoriasStarTrek(int victoriasStarTrek) {
        this.victoriasStarTrek = victoriasStarTrek;
    }

    public int getVelocidadIA() {
        return velocidadIA;
    }

    public void setVelocidadIA(int velocidadIA) {
        this.velocidadIA = velocidadIA;
    }
 
    public String getEstadoIA(){
        return inteligenciaArtificial.getEstado();
    }
    
    public int getIDpersonaje1(){
        return inteligenciaArtificial.getPersonaje1();
    }
    public int getIDpersonaje2(){
        return inteligenciaArtificial.getPersonaje2();
    }

     public String obtenerInfoPersonajesEnCombate() {
    Personajes personaje1 = obtenerPersonajeParaCombate();
    Personajes personaje2 = obtenerPersonajeParaCombate();

    StringBuilder sb = new StringBuilder();

    if (personaje1 != null) {
        sb.append("Personaje 1: ").append(personaje1.toString()).append("\n");
    }
    if (personaje2 != null) {
        sb.append("\nPersonaje 2: ").append(personaje2.toString()).append("\n");
    }

    return sb.toString();
}
}
