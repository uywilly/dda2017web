/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import controlador.ControladorMozo;
import dominio.Cliente;
import dominio.IMesa;
import dominio.Mesa;
import dominio.Mozo;
import dominio.RestaurantException;
import dominio.Transferencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import utilidades.Componentes;
import vista.VistaMozo;

/**
 *
 * @author william
 */
public class VistaPrincipalMozoWeb implements VistaMozo{

    private ControladorMozo controlador;
    private AsyncContext contexto;
    private PrintWriter out;
    private ArrayList<IMesa> mesas;
    
    public VistaPrincipalMozoWeb(Mozo m,AsyncContext c){
        contexto = c;
        try {
            out = contexto.getResponse().getWriter();
            controlador = new ControladorMozo(this,m);
            mesas = m.getMesas();
            mostrarMesasWeb(m.getMesas());
        } catch (IOException ex) {
            System.out.println("Error al obtener el writer");
        }
    }
    public void enviar(String evento, String dato) {
        //COMUNICACION SSE
        out.write("event: " + evento + "\n");
        dato = dato.replace("\n", "");
        out.write("data: " + dato + "\n\n");
        if (out.checkError()) {//checkError llama a flush, si da false evio bien
           // cerrar();   
            System.out.println("Error");
        } else {
            //TODO OK!
             System.out.println("Enviado");
        }
    }
    public void abrirMesa(HttpServletRequest request) {
        int pos = Integer.parseInt(request.getParameter("mesa"));
        if(pos>-1){
            IMesa unaM = mesas.get(pos);
            controlador.guardarSeleccionada(unaM);
            controlador.abrirMesa();
            
            /*try{
                controlador.guardarSeleccionada(unaM);
                controlador.abrirMesa();
                //unaM.abrirMesa();
                
            }catch(RestaurantException ex){
                System.out.println(ex.getMessage());
            }*/
        }
    }

    private void mostrarMesasWeb(ArrayList<IMesa> mesas) {
        ArrayList lista = new ArrayList();
        for(IMesa m : mesas){
            lista.add("mesa " + m.verNumero() + "abierta:" + m.estaAbierta());
        }
        enviar("mostrarMesas",Componentes.lista(true, "lstMesasMozo", lista));
        //enviar("lo que escucha el event listener en la pagina",
        //Componentes.lista(true, "lstMesasMozo", lista)
        //);
    }
    
        @Override
    public void actualizarMesas(Mozo origen) {
        this.mostrarMesasWeb(origen.getMesas());
    }

    @Override
    public void mostrarMesa(IMesa m) {
        enviar("infoMesa",m.toString());

    }

    @Override
    public void agregarPedido(IMesa mesaSeleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transferirMesa(IMesa mesaSeleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarMensajeTransferenciaPendiente(Transferencia trans) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    @Override
    public void cerrar(boolean b, Mozo origen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void error(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarMensajeMesaAceptada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarMensajeTransferenciaRechazada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nombreEnVentana(Mozo origen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarClientesRegistrados(ArrayList<Cliente> verClientesRegistrados, IMesa mesaSeleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarTimer(Transferencia trans) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}