/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import Mapeadores.MapeadorProductos;
import Mapeadores.MapeadorUPP;
import Persistencia.BaseDatos;
import Persistencia.Persistencia;
import java.util.ArrayList;

/**
 *
 * @author william
 */
public class SistemaProductos {
    private ArrayList<Producto> productos = new ArrayList();; 

    public ArrayList<Producto> getProductos() {
        return productos;
    }
    public void agregarProductos(Producto p){
        productos.add(p);
    }
    public void eliminarProductos(Producto p){
        productos.remove(p);
    }

    public void cargarDatosPrueba() {
        // TODO Auto-generated method stub
        Persistencia p = Persistencia.getInstancia();
        MapeadorProductos mp = new MapeadorProductos();
        productos = p.obtenerTodos(mp);
        
    }

    public Producto buscarProductoXid(String dato) {
        Producto unp = null;
        int contador = 0;
        while(contador<productos.size() && unp==null){
            if(productos.get(contador).getCodigo().equalsIgnoreCase(dato)){
                unp = productos.get(contador);
            }
            contador++;
        }
        return unp;
    }
    
    
}
