/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dominio.Gestor;
import dominio.UnidadProcesadora;
import java.util.ArrayList;

/**
 *
 * @author william
 */
public interface VistaSeleccionUPP {

    public void ingresarUPP(Gestor g);

    public void cargarLista(ArrayList<UnidadProcesadora> lista);
    
}
