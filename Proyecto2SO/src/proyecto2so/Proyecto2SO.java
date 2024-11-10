/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2so;
import Interfaces.Home;
import Proyecto.Admin;
import Proyecto.Cola;
import Proyecto.IA;
import Proyecto.Nodo;
import Proyecto.Personajes;

/**
 *
 * @author Maria Daniela
 */
public class Proyecto2SO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Home home = new Home();
        home.setVisible(true);
        
        // Crea una instancia de Admin, que controla la simulación
        Admin admin = new Admin();
        
        // Inicia la simulación
        admin.iniciarSimulacion();
    }
    
}
