/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author Maria Daniela
 */
class Lista<T> {
    private Object[] elementos;
    private int tamanio;

    public Lista() {
        this.elementos = new Object[10]; // Tamaño inicial
        this.tamanio = 0;
    }

    public void agregar(T elemento) {
        if (tamanio == elementos.length) {
            redimensionar();
        }
        elementos[tamanio++] = elemento;
    }

    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return (T) elementos[indice];
    }

    public int tamanio() {
        return this.tamanio;
    }

    private void redimensionar() {
        Object[] nuevosElementos = new Object[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            nuevosElementos[i] = elementos[i];
        }
        elementos = nuevosElementos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanio; i++) {
            sb.append(elementos[i]);
            if (i < tamanio - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
