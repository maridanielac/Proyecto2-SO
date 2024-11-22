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
public class IA {
    private int personaje1; // Star Wars
    private int personaje2; // Star Trek
    private int ganador;
    private String estado;
    private int time = 10000;
    private Random random = new Random();

    // Modificado: solo 2 parámetros
    public IA(int personaje1, int personaje2) {
        this.personaje1 = personaje1;
        this.personaje2 = personaje2;
        this.ganador = -1; // Inicializa ganador como -1 (sin ganador)
        this.estado = "Esperando";
    }
    
    public void VerGanador(Cola colaRefuerzo){
        estado = "Decidiendo"; // Cambia el estado a "Decidiendo"
        System.out.println("Estado de la IA: " + estado);
       int resultado = random.nextInt(100); // Genera un número entre 0 y 99

        if (resultado < 40) {
            // 40% de probabilidad de que haya un ganador
            determinarGanador();
            estado = "Anunciando Resultado"; // Cambia el estado a "Anunciando Resultado"
            System.out.println("Estado de la IA: " + estado);
            System.out.println("Hay un ganador: Personaje " + ganador);
        } else if (resultado < 67) {
             estado = "Anunciando Resultado"; // Cambia el estado a "Anunciando Resultado"
            System.out.println("Estado de la IA: " + estado);
            // 27% de probabilidad de que ocurra un empate
            System.out.println("Resultado: Empate. Ambos personajes vuelven a la cola de nivel 1.");
        } else {
             estado = "Esperando"; // Cambia el estado a "Anunciando Resultado"
             System.out.println("Estado de la IA: " + estado);
            // 33% de probabilidad de que el combate no pueda llevarse a cabo
            System.out.println("Resultado: Combate no puede llevarse a cabo. Ambos personajes van a la cola de refuerzo.");
            colaRefuerzo.agregar(new Personajes(personaje1, "StarWars"));
            colaRefuerzo.agregar(new Personajes(personaje2, "StarTrek"));
        } 
    }
    
    private void determinarGanador(){
        int score1 = 0;
        int score2 = 0;

        while (score1 < 2 && score2 < 2) {
            int eleccion1 = random.nextInt(3); // 0: Piedra, 1: Papel, 2: Tijera
            int eleccion2 = random.nextInt(3);

            // Determina el ganador de la ronda
            if (eleccion1 != eleccion2) {
                if ((eleccion1 == 0 && eleccion2 == 2) || 
                    (eleccion1 == 1 && eleccion2 == 0) || 
                    (eleccion1 == 2 && eleccion2 == 1)) {
                    score1++; // Personaje1 gana la ronda
                } else {
                    score2++; // Personaje2 gana la ronda
                }
            }
        }

        // Asigna el ganador a 1 o 2 dependiendo de la franquicia
        ganador = score1 == 2 ? 1 : 2;
    }

    public int getPersonaje1() {
        return personaje1;
    }

    public void setPersonaje1(int personaje1) {
        this.personaje1 = personaje1;
    }

    public int getPersonaje2() {
        return personaje2;
    }

    public void setPersonaje2(int personaje2) {
        this.personaje2 = personaje2;
    }

    public int getGanador() {
        return ganador;
    }

    public void setGanador(int ganador) {
        this.ganador = ganador;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public String getEstado() {
        return estado;
}
    
    
    
}
