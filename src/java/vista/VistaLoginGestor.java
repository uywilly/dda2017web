/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dominio.Gestor;

/**
 *
 * @author william
 */
public interface VistaLoginGestor {
    
     public void ingresarGestor(Gestor g);

    public void error(String message);
    
}
