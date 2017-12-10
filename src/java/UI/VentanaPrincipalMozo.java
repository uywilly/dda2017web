/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import controlador.ControladorMozo;
import dominio.Cliente;
import dominio.IMesa;
import dominio.Mozo;
import dominio.Pedido;
import dominio.Producto;
import dominio.Sistema;
import dominio.Transferencia;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import vista.VistaMozo;

/**
 *
 * @author william
 */
public class VentanaPrincipalMozo extends javax.swing.JFrame implements VistaMozo {
    
    private PanelMesas panelMesas;
    private PanelInfoServicio panelInfoServicios;
    private VentanaMensajeAceptarTransferencia ventanaMensaje;
    private VentanaTransferencia vt;
    
    private ControladorMozo controlador;
    private Mozo elMozo;
    
    private Transferencia tr;
    /**
     * Creates new form VentanaPrincipalMozo
     */
    public VentanaPrincipalMozo() {
        initComponents();

    }
    
    public VentanaPrincipalMozo(Mozo m) {
        initComponents();
        
        controlador = new ControladorMozo(this,m);
        this.elMozo = m;
        controlador.nombreEnVentana();
        panelMesas = new PanelMesas(m, controlador);
        panelInfoServicios = new PanelInfoServicio(controlador);
        GridLayout layout = new GridLayout(2,0);
        JPanel panelVentana = (JPanel)getContentPane();
        panelVentana.setLayout(layout);
        panelVentana.add(panelMesas);
        panelVentana.add(panelInfoServicios);

    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        setBounds(0, 0, 656, 800);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        controlador.salir();
    }//GEN-LAST:event_formWindowClosing

    @Override
    public void mostrarMesa(IMesa m) {
        if(m!=null){
            panelInfoServicios.mostrarMesa(m);
        }        
    }

    @Override
    public void actualizarMesas(Mozo mozo) {
        panelMesas.mostrar(mozo.getMesas());
    }
    
    @Override
    public void limpiar() {
        panelInfoServicios.limpiar();

    }
    
    @Override
    public void agregarPedido(IMesa mesaSeleccionada) {
        new VentanaAgregarPedido(mesaSeleccionada).setVisible(true);
    }

    @Override
    public void transferirMesa(IMesa mesaSeleccionada) {
        vt = new VentanaTransferencia(mesaSeleccionada);
        vt.setVisible(true);
    }

    @Override
    public void mostrarMensajeTransferenciaPendiente(Transferencia trans) {
        // reemplazar por una nueva ventana
        ventanaMensaje = new VentanaMensajeAceptarTransferencia(controlador, trans);
        ventanaMensaje.setVisible(true); 
                
                

    }
    @Override
    public void cerrar(boolean logout, Mozo origen) {
        if(logout && origen.equals(elMozo)){
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "No puede salir! Hay mesas abiertas!");
        } 

    }
    @Override
    public void mostrarMensajeMesaAceptada() {
        JOptionPane.showMessageDialog(this, "Se ha aceptado la transferencia!");
        ventanaMensaje = null;
    }
    @Override
    public void mostrarMensajeTransferenciaRechazada() {
        //
        JOptionPane.showMessageDialog(this, "No se ha aceptado la transferencia!");
        if(ventanaMensaje != null){ ventanaMensaje.dispose();}
        ventanaMensaje = null;
        
    }

    @Override
    public void error(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void nombreEnVentana(Mozo origen) {
        this.setTitle(origen.getNombreCompleto());
    }

    @Override
    public void mostrarClientesRegistrados(ArrayList<Cliente> verClientesRegistrados, IMesa mesaSeleccionada) {
        new VentanaAsignacionCliente(verClientesRegistrados,mesaSeleccionada).setVisible(true);
    }

    @Override
    public void actualizarTimer(Transferencia trans) {
        //System.out.println("Entra mas adentro " + trans.getTiempo());
        if(ventanaMensaje!=null) 
        {
            ventanaMensaje.actualizarTimer(trans.getTiempo());
        }
    }

    @Override
    public void mostrarMozosLogueados(ArrayList<Mozo> verMozosLoguados) {
        vt.cargarMozosLogueados(verMozosLoguados);
    }

    @Override
    public void ActualizarMozosLogueados(ArrayList<Mozo> verMozosLoguados) {
        vt.cargarMozosLogueados(verMozosLoguados);
    }

    @Override
    public void mostrarProductos(ArrayList<Producto> listarProductos) {
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
