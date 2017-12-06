/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dominio.Cliente;
import dominio.IMesa;
import dominio.Mesa;
import dominio.Mozo;
import dominio.Pedido;
import dominio.Producto;
import dominio.RestaurantException;
import dominio.Sistema;
import dominio.Transferencia;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.VistaMozo;

/**
 *
 * @author william
 */
public class ControladorMozo implements Observer {

    private Sistema modelo = Sistema.getInstancia();

    private IMesa mesaSeleccionada;
    private Mozo origen;
    Transferencia trans;

    private VistaMozo vista;

    public ControladorMozo(VistaMozo vista, Mozo unM) {
        this.vista = vista;
        this.origen = unM;
        modelo.addObserver(this);
    }

    public void mostrarMesa(IMesa m) {
        vista.mostrarMesa(m);
    }

    public void guardarSeleccionada(IMesa m) {
        mesaSeleccionada = m;
    }

    public void abrirMesa() {
        if (mesaSeleccionada != null) {
            try {
                mesaSeleccionada.abrirMesa();
            } catch (RestaurantException ex) {
                vista.error(ex.getMessage());
            }
        } else {
            vista.error("Verifique la mesa!");
        }
    }

    public void cerrarMesa() {
        if (mesaSeleccionada != null ) {
            //validar que los pedidos esten ya procesados
            try {
                mesaSeleccionada.cerrarMesa();
            } catch (RestaurantException ex) {
                vista.error(ex.getMessage());
            }
        } else {
            vista.error("Verifique la mesa!");
        }
    }

    public void agregarPedido(IMesa mesaSeleccionada) throws RestaurantException {
        if (mesaSeleccionada != null && mesaSeleccionada.estaAbierta()) {
            vista.agregarPedido(mesaSeleccionada);
        } else {
            throw new RestaurantException("Debe abrir la mesa primero!");
        }
    }

    public void comenzarTransferenciaMesa(IMesa mesaSeleccionada) throws RestaurantException {
        //Le pide a VentanaPrincipalMozo 
        //que abra la ventana de transferencia
        if (mesaSeleccionada == null) throw new RestaurantException("Debe seleccionar una mesa!"); 
        else{
                this.mesaSeleccionada = mesaSeleccionada;
                vista.transferirMesa(mesaSeleccionada);
        }
    }

    public void aceptarTransferencia(Transferencia trans) {
        try{ modelo.aceptarTransferencia(trans);this.trans = null;}
        catch (RestaurantException ex){vista.error(ex.getMessage());}
    }

    public void rechazarTransferencia(Transferencia trans) {
        modelo.rechazarTransferencia(trans);
        this.trans = null;
    }
    

    public void salir() {
        boolean b = modelo.logout(origen);
        vista.cerrar(b, origen);
    }
    public void nombreEnVentana() {
        vista.nombreEnVentana(origen);
    }
    public void asignarClienteMesa() {
        vista.mostrarClientesRegistrados(modelo.verClientesRegistrados(), mesaSeleccionada);
    }
    public void asignarClienteMesa(Cliente unC) {
        try{
            modelo.asignarClienteSeleccionadoMesa(mesaSeleccionada,unC);
        }catch (RestaurantException ex){vista.error(ex.getMessage());}
    }
    /*agregado para web*/
    public void mostrarMozosLogueados() {
        vista.mostrarMozosLogueados(modelo.verMozosLoguados());
    }
    public void mostrarProductos() {
        vista.mostrarProductos(modelo.listarProductos());
    }
    public void comenzarTransferenciaWeb(Mozo destino) {
        Transferencia trans = new Transferencia(mesaSeleccionada.verMozo(), destino, mesaSeleccionada, false);
       try{
            this.trans = trans;
            modelo.transferir(trans);
            
        }catch (RestaurantException ex){
            vista.error(ex.getMessage() );
        }
    }
    public void aceptarTransferencia(String res) {
        if(res.equalsIgnoreCase("true")){
            try{ modelo.aceptarTransferencia(trans);this.trans = null;}
            catch (RestaurantException ex){vista.error(ex.getMessage());}
        }else{
            modelo.rechazarTransferencia(trans);
            this.trans = null;
        }
    }
    public void agregarPedido(IMesa unaM, Producto unP, int cant) {
        try {
            Pedido ped = new Pedido(cant,unP);
            mesaSeleccionada.agregarPedido(ped);
        } catch (RestaurantException ex) {
            vista.error(ex.getMessage());
        }
    }

    /*fin agregado para web*/

    @Override
    public void update(Observable o, Object arg) {
        // o = origen / arg = evento
        if (o == modelo) {
            if (arg.equals(Sistema.Eventos.abrirMesa)) {
                //mandar a la vista que hacer
                vista.actualizarMesas(origen);
                vista.mostrarMesa(mesaSeleccionada);
            }
            if (arg.equals(Sistema.Eventos.clienteAgregado)) {
                //mandar a la vista que hacer
                vista.actualizarMesas(origen);
                vista.mostrarMesa(mesaSeleccionada);
            }
            if (arg.equals(Sistema.Eventos.cerrarMesa)) {
                //mandar a la vista que hacer
                vista.actualizarMesas(origen);
                vista.mostrarMesa(mesaSeleccionada);
                vista.limpiar();
            }
            if (arg.equals(Sistema.Eventos.agregarPedido)) {
                //mandar a la vista que hacer
                vista.actualizarMesas(origen);
                vista.mostrarMesa(mesaSeleccionada);
                vista.mostrarProductos(modelo.listarProductos());
            }
            if (arg.equals(Sistema.Eventos.cerrarPedido)) {
                //mandar a la vista que hacer
                vista.actualizarMesas(origen);
                vista.mostrarMesa(mesaSeleccionada);
            }
            if (arg.equals(Sistema.Eventos.comenzarTransferencia)) {
                //toma la primer transferencia pendiente 
                //y le pide a la viste que le muestra el mensaje al mozo destino
                Transferencia trans = modelo.verTransferenciasPendientes();
                if (trans != null && trans.getDestino().equals(origen)) {
                    this.trans = trans;
                    vista.mostrarMensajeTransferenciaPendiente(trans);
                }

            }
            if (arg.equals(Sistema.Eventos.tiempo)) {
                //toma la primer transferencia pendiente 
                //y le pide a la viste que le muestra el mensaje al mozo destino
                Transferencia trans = modelo.verTransferenciasPendientes();
                if (trans != null ) {
                    vista.actualizarTimer(trans);
                }
            }
            if (arg.equals(Sistema.Eventos.aceptarTransferencia)) {
                //toma la primer transferencia pendiente 
                //y le pide a la viste que le muestra el mensaje al mozo origen
                vista.actualizarMesas(origen);
                
                if (mesaSeleccionada != null && !mesaSeleccionada.verMozo().equals(origen)) {
                    vista.mostrarMensajeMesaAceptada();
                    vista.limpiar();
                    mesaSeleccionada = null;
                }
                

            }
            if (arg.equals(Sistema.Eventos.rechazarTransferencia)) {
                //toma la primer transferencia pendiente 
                //y le pide a la viste que le muestra el mensaje al mozo destino
                if (mesaSeleccionada != null && mesaSeleccionada.verMozo().equals(origen)) {
                    vista.mostrarMensajeTransferenciaRechazada();
                }
            }
            if (arg.equals(Sistema.Eventos.login)) {
                vista.ActualizarMozosLogueados(modelo.verMozosLoguados());
            }
            if (arg.equals(Sistema.Eventos.logout)) {
                vista.ActualizarMozosLogueados(modelo.verMozosLoguados());

            }

        }
    }

    
    

}
