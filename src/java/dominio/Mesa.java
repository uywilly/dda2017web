/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import Mapeadores.MapeadorProductos;
import Mapeadores.MapeadorServicio;
import Persistencia.Persistencia;
import java.util.ArrayList;

/**
 *
 * @author william
 */
public class Mesa implements IMesa {

    private int numero;
    private boolean abierta;
    private Mozo mozo;
    private Cliente cliente;
    private ArrayList<Pedido> servicio;
    private int oid;
    


    public Mesa(int numero, boolean abierta, Mozo mozo) {
        this.numero = numero;
        this.abierta = abierta;
        this.mozo = mozo;
        servicio = new ArrayList<Pedido>();
        //this.oid = numero;
        
    }

    public Cliente getCliente() {
        return cliente;
    }

    
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setMozo(Mozo mozo) {
        this.mozo = mozo;
    }
    

    @Override
    public int verNumero() {
        return this.numero;
    }

    @Override
    public boolean estaAbierta() {
        return this.abierta;
    }

    @Override
    public Mozo verMozo() {
        return this.mozo;
    }
    
    @Override
    public int totalSinDescuento() {
        int salida = 0;
        for (Pedido unP : this.servicio) {
            salida += (unP.getCantidad() * unP.getProducto().getPrecioUni());
        }
        return salida;
    }
    @Override
    public String mostrarBeneficio() {
        String salida = "";
        if(this.getCliente()!=null) salida = this.getCliente().mostrarBeneficio();
        return salida;
    }

    @Override
    public int calcularTotalServicio() {
        int salida = 0;
        int descuentoTotal = 0;
        for (Pedido unP : this.servicio) {
            if(cliente!=null){
               salida += (unP.getCantidad() * unP.getProducto().getPrecioUni()) - cliente.calcularDescuentoProducto(unP);

            }else{
                salida += (unP.getCantidad() * unP.getProducto().getPrecioUni());
            }
        }
        if(cliente!=null){
            descuentoTotal = cliente.calcularDescuentoTotal(salida);
        }
        salida -= descuentoTotal;
        return salida;
    }

    @Override
    public ArrayList<Pedido> listarServicio() {
        return this.servicio;
    }

    @Override
    public void abrirMesa() throws RestaurantException {
        if (this.abierta) {
            throw new RestaurantException("Mesa ya abierta!");
        } else {
            abierta = true;
            Sistema.getInstancia().avisar(Sistema.Eventos.abrirMesa);
        }
    }

    @Override
    public void cerrarMesa() throws RestaurantException {
        if (!this.abierta) {
            throw new RestaurantException("La mesa no esta abierta!");
        }
        if (!this.hayPedidosSinFinalizar()) {
            abierta = false;
            this.guardarServicio();
            this.servicio.clear();
            this.cliente = null;
            Sistema.getInstancia().avisar(Sistema.Eventos.cerrarMesa);
        } else {
            throw new RestaurantException("La mesa tiene pedidos abiertos!");
        }
    }
    private void guardarServicio(){
        Persistencia p = Persistencia.getInstancia();
        MapeadorServicio ms = new MapeadorServicio();
        ms.setM(this);
        p.guardar(ms);
    }

    @Override
    public String toString() {
        return "# " + numero + " , abierta= " + abierta + " , mozo= " + mozo.getNombre() + '}';
    }

    @Override
    public void agregarPedido(Pedido unP) throws RestaurantException{
        if (this.estaAbierta()) {
            if (unP.isValido()) {
                this.servicio.add(unP);
                int stock = unP.getProducto().getStock();
                int cantidad = unP.getCantidad();
                unP.getProducto().getUpp().agregarPedido(unP);
                unP.getProducto().setStock(stock - cantidad);
                this.actualizarStockDB(unP.getProducto());
                Sistema.getInstancia().avisar(Sistema.Eventos.agregarPedido);
            }else throw new RestaurantException("Pedido con errores");
        }else throw new RestaurantException("La mesa esta cerrada");
    }
    
    private void actualizarStockDB(Producto prod){
        Persistencia p = Persistencia.getInstancia();
        MapeadorProductos mp = new MapeadorProductos();
        mp.setUnP(prod);
        p.guardar(mp);
    }

    private boolean hayPedidosSinFinalizar() {
        for (Pedido unP : this.servicio) {
            if (!unP.isFinalizado()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void asignarClienteSeleccionado(Cliente unC) throws RestaurantException {
        if(unC!=null) {
            this.cliente = unC;
            Sistema.getInstancia().avisar(Sistema.Eventos.clienteAgregado);
        }
        else throw new RestaurantException("cliente no valido");
    }

    @Override
    public int getOid() {
        return oid;
    }

    @Override
    public void setOid(int oid) {
        this.oid = oid;
    }



    

}
