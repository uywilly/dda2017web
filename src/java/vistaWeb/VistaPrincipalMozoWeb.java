/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import com.mysql.fabric.Response;
import controlador.ControladorMozo;
import controlador.ControladorTransferencia;
import dominio.Cliente;
import dominio.IMesa;
import dominio.Mesa;
import dominio.Mozo;
import dominio.Pedido;
import dominio.Producto;
import dominio.RestaurantException;
import dominio.Sistema;
import dominio.Transferencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            controlador.asignarClienteMesa();
            controlador.mostrarMozosLogueados();
            controlador.mostrarProductos();
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
        }
    }
    public void cerrarMesa(HttpServletRequest request) {
        int pos = Integer.parseInt(request.getParameter("mesa"));
        if(pos>-1){
            IMesa unaM = mesas.get(pos);
            controlador.guardarSeleccionada(unaM);
            controlador.cerrarMesa();
        }
    }
    public void asignarCliente(HttpServletRequest request) {
        int pos = Integer.parseInt(request.getParameter("cliente"));
        if(pos>-1){
            Cliente unC;
            try {
                unC = Sistema.getInstancia().buscarClienteXid(pos);
                controlador.asignarClienteMesa(unC);
            } catch (RestaurantException ex) {
                Logger.getLogger(VistaPrincipalMozoWeb.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public void transferir(HttpServletRequest request) {
        int posMesa = Integer.parseInt(request.getParameter("mesa"));
        int posMozo = Integer.parseInt(request.getParameter("mozo"));
        if(posMesa>-1&&posMozo>-1){
            IMesa unaM = mesas.get(posMesa);
            Mozo destino = Sistema.getInstancia().verMozosLoguados().get(posMozo);
            controlador.guardarSeleccionada(unaM);
            controlador.comenzarTransferenciaWeb(destino);
            
        }
    }
    public void aceptarTransferencia(HttpServletRequest request) {
        String res = request.getParameter("respuesta");
        controlador.aceptarTransferencia(res);
    }
    public void agregarPedidoWeb(HttpServletRequest request) {
        int posMesa = Integer.parseInt(request.getParameter("mesa"));
        int posProd = Integer.parseInt(request.getParameter("prod"));
        int cant = Integer.parseInt(request.getParameter("cant"));
        if(posMesa>-1&&posProd>-1){
            IMesa unaM = mesas.get(posMesa);
            Producto unP = Sistema.getInstancia().listarProductos().get(posProd);
            controlador.guardarSeleccionada(unaM);
            controlador.agregarPedido(unaM,unP,cant);
            
        }
        
    }
    @Override
    public void mostrarProductos(ArrayList<Producto> listarProductos) {
        enviar("listarPedidos",Componentes.lista(true, "lstPedidos", listarProductos));
    }
    
    
    
    private void mostrarMesasWeb(ArrayList<IMesa> mesas) {
        ArrayList lista = new ArrayList();
        ArrayList listaPedidosMesa = new ArrayList();
        for(IMesa m : mesas){
            lista.add("mesa " + m.verNumero() + "abierta:" + m.estaAbierta());
            if(m.listarServicio()!=null && m.listarServicio().size()>0){
                for(Pedido p : m.listarServicio()){
                    listaPedidosMesa.add(p.toString() + m.getCliente());
                }
            }
        }
        enviar("mostrarMesas",Componentes.lista(true, "lstMesasMozo", lista));
        enviar("mostrarPedidosMesas",Componentes.lista(true, "lstPedidosMesa", listaPedidosMesa));
        
        //enviar("lo que escucha el event listener en la pagina",
        //Componentes.lista(true, "lstMesasMozo", lista)
        //);
    }
    @Override
    public void ActualizarMozosLogueados(ArrayList<Mozo> verMozosLoguados) {
        enviar("listarMozosLogueados",Componentes.lista(true, "lstMozos", verMozosLoguados));
    }

    @Override
    public void mostrarClientesRegistrados(ArrayList<Cliente> verClientesRegistrados, IMesa mesaSeleccionada) {
        enviar("listaClientes",Componentes.lista(true, "lstClientes", verClientesRegistrados));
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
    public void limpiar() {
        enviar("infoMesa","");
    }
    @Override
    public void mostrarMozosLogueados(ArrayList<Mozo> verMozosLoguados) {
        enviar("listarMozosLogueados",Componentes.lista(true, "lstMozos", verMozosLoguados));
    }
    
    @Override
    public void actualizarTimer(Transferencia trans) {
        enviar("timer",trans.getTiempo()+"");
    }
    @Override
    public void mostrarMensajeMesaAceptada() {
        enviar("mensajeTrans","Transferencia Aceptada");
    }
    @Override
    public void mostrarMensajeTransferenciaRechazada() {
        enviar("mensajeTrans","Transferencia Rechazada");
    }
    @Override
    public void mostrarMensajeTransferenciaPendiente(Transferencia trans) {
        enviar("mensajeTrans","Transferencia Pendiente: " + trans.toString()+" Aceptar? ");
    }

    @Override
    public void agregarPedido(IMesa mesaSeleccionada) {
        //request.getSession(true).setAttribute("mozo", m);
        //response.sendRedirect("VentanaPrincipalMozoWeb.html");
    }

    @Override
    public void transferirMesa(IMesa mesaSeleccionada) {
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
    public void nombreEnVentana(Mozo origen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    

    

    


    



    









    

    
    
}
