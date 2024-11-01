/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author Maria Daniela
 */
public class Cola {
    private Personajes[] personajes;
    private int frente;
    private int finalCola;
    private int tamaño; // Número máximo de elementos en la cola

    public Cola(int tamaño) {
        this.tamaño = tamaño;
        this.personajes = new Personajes[tamaño];
        this.frente = 0;
        this.finalCola = 0;
    }

    public void agregar(Personajes personaje) {
        if (finalCola < tamaño) {
            personajes[finalCola] = personaje;
            finalCola++;
        } else {
            System.out.println("Cola llena. No se puede agregar el personaje: " + personaje);
        }
    }

    public Personajes eliminar() {
        if (estaVacia()) {
            return null; // Cola vacía
        }
        Personajes personaje = personajes[frente];
        frente++;
        // Si la cola está vacía después de eliminar, reiniciar la cola
        if (frente == finalCola) {
            frente = 0;
            finalCola = 0;
        }
        return personaje;
    }

    public boolean estaVacia() {
        return frente == finalCola; // Si el frente es igual al final, la cola está vacía
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = frente; i < finalCola; i++) {
            sb.append(personajes[i].toString()).append(" | ");
        }
        return sb.toString();
    }
}