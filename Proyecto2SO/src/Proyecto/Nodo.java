/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author Maria Daniela
 */
public class Nodo {
    Personajes personajes;
    Nodo siguiente;

    public Nodo(Personajes personaje) {
        this.personajes = personaje;
        this.siguiente = null;
    }
}
