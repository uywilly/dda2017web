/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import controlador.ControladorLoginMozo;
import dominio.Mozo;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vista.VistaLoginMozo;

/**
 *
 * @author william
 */
public class VistaLoginMozoWeb implements VistaLoginMozo{

    private ControladorLoginMozo controlador;
    private HttpServletResponse response; 
    private HttpServletRequest request; 
    
    public VistaLoginMozoWeb(){
        controlador = new ControladorLoginMozo(this);
    }
    
    @Override
    public void ingresarMozo(Mozo m) {
        try {
            request.getSession(true).setAttribute("mozo", m);
            response.sendRedirect("newhtml.html");
        } catch (IOException ex) {
            System.out.println("Error al redirect:" + ex.getMessage());
        }
    }

    @Override
    public void error(String message) {
         try {
            response.sendRedirect("index.jsp?msg=" + message);
        } catch (IOException ex) {
            System.out.println("Error al redirect:" + ex.getMessage());
        }
    }
    
    public void login(HttpServletRequest req,HttpServletResponse res) {
        String usr = req.getParameter("usuario");
        String pass = req.getParameter("pass");
        response = res;
        request = req;
        controlador.LoginMozo(usr, pass);
    }
    
}
