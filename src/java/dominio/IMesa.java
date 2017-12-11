/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;

/**
 *
 * @author william
 */
public interface IMesa {
    public int verNumero();
    public boolean estaAbierta();
    public void abrirMesa()throws RestaurantException;
    public void cerrarMesa()throws RestaurantException;
    public void setMozo(Mozo unM);
    public Mozo verMozo();
    public ArrayList<Pedido> listarServicio();
    public void agregarPedido(Pedido unP)throws RestaurantException;
    public int calcularTotalServicio();
    public void asignarClienteSeleccionado(Cliente unC) throws RestaurantException;
    public int getOid();
    public void setOid(int oid);
    public Cliente getCliente();
    public int totalSinDescuento();
    public String mostrarBeneficio();
}
