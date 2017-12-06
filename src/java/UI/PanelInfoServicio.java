/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import controlador.ControladorMozo;
import dominio.IMesa;
import dominio.Pedido;
import dominio.RestaurantException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author william
 */
public class PanelInfoServicio extends javax.swing.JPanel {

    private ControladorMozo controlador;
    private IMesa mesaSeleccionada;

    /**
     * Creates new form PanelInfoServicio
     */
    public PanelInfoServicio() {
        initComponents();
    }

    public PanelInfoServicio(ControladorMozo c) {
        controlador = c;
        initComponents();
        
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
        lstServiciosMesa = new javax.swing.JList();
        btnAbrir = new javax.swing.JButton();
        btnAgregarPedido = new javax.swing.JButton();
        btnTransferir = new javax.swing.JButton();
        btnCerrarMesa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtMesaSeleccionada = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setLayout(null);

        jScrollPane1.setViewportView(lstServiciosMesa);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 80, 570, 130);

        btnAbrir.setText("Abrir Mesa");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });
        add(btnAbrir);
        btnAbrir.setBounds(20, 240, 100, 23);

        btnAgregarPedido.setText("Agregar Pedido");
        btnAgregarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPedidoActionPerformed(evt);
            }
        });
        add(btnAgregarPedido);
        btnAgregarPedido.setBounds(120, 240, 130, 23);

        btnTransferir.setText("Transferir");
        btnTransferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferirActionPerformed(evt);
            }
        });
        add(btnTransferir);
        btnTransferir.setBounds(250, 240, 110, 23);

        btnCerrarMesa.setText("Cerrar Mesa");
        btnCerrarMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarMesaActionPerformed(evt);
            }
        });
        add(btnCerrarMesa);
        btnCerrarMesa.setBounds(480, 240, 120, 23);

        jLabel1.setText("Se ha seleccionado la mesa:");
        add(jLabel1);
        jLabel1.setBounds(20, 30, 180, 14);

        txtMesaSeleccionada.setEnabled(false);
        add(txtMesaSeleccionada);
        txtMesaSeleccionada.setBounds(230, 30, 360, 20);

        jButton1.setText("Asignar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(360, 240, 120, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        this.abrirMesa();
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnCerrarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarMesaActionPerformed
        this.cerrarMesa();
    }//GEN-LAST:event_btnCerrarMesaActionPerformed

    private void btnAgregarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPedidoActionPerformed
        this.agregarPedido();
    }//GEN-LAST:event_btnAgregarPedidoActionPerformed

    private void btnTransferirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferirActionPerformed
        this.comenzarTransferenciaMesa();
    }//GEN-LAST:event_btnTransferirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.asignarClienteMesa();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnAgregarPedido;
    private javax.swing.JButton btnCerrarMesa;
    private javax.swing.JButton btnTransferir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstServiciosMesa;
    private javax.swing.JTextField txtMesaSeleccionada;
    // End of variables declaration//GEN-END:variables

    public void mostrarMesa(IMesa m) {
        if(m!=null){
            mesaSeleccionada = m;
            txtMesaSeleccionada.setText(" # " +m.verNumero() + " estado: " + m.estaAbierta() + " total= " + m.calcularTotalServicio());
            lstServiciosMesa.setListData(m.listarServicio().toArray());
        }
    }
    public void limpiar() {
            mesaSeleccionada = null;
            txtMesaSeleccionada.setText("");
            //lstServiciosMesa.removeAll();
            lstServiciosMesa.setListData(new ArrayList<Object>().toArray());

    }

    private void abrirMesa() {
        controlador.abrirMesa();
    }

    private void cerrarMesa() {
        controlador.cerrarMesa();
    }

    private void agregarPedido() {
        try{
            controlador.agregarPedido(mesaSeleccionada);
        }catch(RestaurantException ex){
            JOptionPane.showMessageDialog(this, "La mesa esta cerrada!");
        }
    }

    private void comenzarTransferenciaMesa() {
        try{
            controlador.comenzarTransferenciaMesa(mesaSeleccionada);
        }catch (RestaurantException ex){
            JOptionPane.showMessageDialog(this, "debe seleccionar una mesa!");
        }
    }

    private void asignarClienteMesa() {
        controlador.asignarClienteMesa();
    }
}
