/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.ArrayList;

/**
 *
 * @author Dario
 */
public class Componentes {
    
    public static String lista(boolean multiple,String id,ArrayList opciones){
        String lista = "<select " + (multiple?" multiple ":"") +  " id='" + id+ "'>";
        int x=0;
        for(Object obj:opciones){
            lista+= "<option id='" + x + "'>" + obj + "</option>";
            x++;
        }
        lista+="</select>";
        return lista;
    }
  
}
