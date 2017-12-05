/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import controlador.ControladorLoginMozo;
import dominio.Mozo;
import vista.VistaLoginMozo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;

/**
 *
 * @author william
 */
public class VistaLoginWeb implements VistaLoginMozo {

    private ControladorLoginMozo controlador;
    private AsyncContext contexto;
    private PrintWriter out;
    
    public VistaLoginWeb(AsyncContext contexto) {
        this.contexto = contexto;
        try {
            out = contexto.getResponse().getWriter();
        } catch (IOException ex) { }
        controlador = new ControladorLoginMozo(this);
    }
    private void enviar(String evento, String dato) {
        if(out!=null){
            out.write("event: " + evento + "\n"); //asi se especifica el nombre del evento, con este nombre se registran los listeners en la pagina
            dato = dato.replace("\n", ""); //el dato no puede tener \n por protocolo
            out.write("data: " + dato + "\n\n"); //envio el dato
            out.flush();
        }
    }
    public void login(String nombre, String pass) {
        controlador.LoginMozo(nombre, pass);
    }
    
    @Override
    public void ingresarMozo(Mozo m) {
        enviar("ingresar", m.getNombre());
    }

    @Override
    public void error(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
