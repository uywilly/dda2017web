/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import controlador.ControladorUnidadProcesadora;
import dominio.Gestor;
import dominio.Pedido;
import dominio.UnidadProcesadora;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import vista.VistaGestionPedidos;

/**
 *
 * @author william
 */
public class VentanaPrincipalGestor extends javax.swing.JFrame implements VistaGestionPedidos {

    private ControladorUnidadProcesadora controlador;
    /**
     * Creates new form VentanaPrincipalGestor
     */
    public VentanaPrincipalGestor() {
        initComponents();
    }
    public VentanaPrincipalGestor(Gestor g) {
        initComponents();
        controlador = new ControladorUnidadProcesadora(this,g);
        controlador.nombreEnVentana();
        
        this.cargarDatosIniciales();

    }
    private void cargarDatosIniciales() {
        controlador.cargarPedidosPendientes();
        controlador.cargarPedidosEnProcesoPorGestor();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstPedidosPendientesSistema = new javax.swing.JList();
        btnProcesar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstPedidosEnProcesoGestor = new javax.swing.JList();
        btnFinalizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jScrollPane1.setViewportView(lstPedidosPendientesSistema);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 40, 580, 130);

        btnProcesar.setText("Procesar Pedido");
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });
        getContentPane().add(btnProcesar);
        btnProcesar.setBounds(10, 190, 140, 23);

        jScrollPane2.setViewportView(lstPedidosEnProcesoGestor);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 240, 580, 130);

        btnFinalizar.setText("Finalizar Pedido");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnFinalizar);
        btnFinalizar.setBounds(10, 390, 140, 23);

        setBounds(0, 0, 630, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarActionPerformed
        Pedido p = (Pedido)lstPedidosPendientesSistema.getSelectedValue();
        if(p!= null) this.procesarPedido(p);
        else JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido");
        
    }//GEN-LAST:event_btnProcesarActionPerformed

    private void procesarPedido(Pedido p) {
        controlador.procesarPedido(p);
    }
    
    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        Pedido p = (Pedido)lstPedidosEnProcesoGestor.getSelectedValue();
        if(p!=null)this.finalizarPedido(p);
        else JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido");
        
    }//GEN-LAST:event_btnFinalizarActionPerformed
    private void finalizarPedido(Pedido p) {
        controlador.finalizarPedido(p);
    }   
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        controlador.logout();
        dispose();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstPedidosEnProcesoGestor;
    private javax.swing.JList lstPedidosPendientesSistema;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void cargarPedidosPendientes(ArrayList<Pedido> verPedidosPorUnidadProcesadora) {
        lstPedidosPendientesSistema.setListData(verPedidosPorUnidadProcesadora.toArray());
    }

    @Override
    public void cargarPedidosEnProcesoPorGestor(ArrayList<Pedido> verPedidosPorGestorEnUnidadProcesadora) {
        lstPedidosEnProcesoGestor.setListData(verPedidosPorGestorEnUnidadProcesadora.toArray());
    }

    @Override
    public void refrescarPantalla() {
        this.cargarDatosIniciales();
    }

    @Override
    public void nombreEnVentana(Gestor g) {
        this.setTitle(g.getNombreCompleto());
    }

    

    

    
}
