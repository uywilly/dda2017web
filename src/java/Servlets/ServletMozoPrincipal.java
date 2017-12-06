/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import dominio.Mozo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vistaWeb.VistaPrincipalMozoWeb;

/**
 *
 * @author william
 */
@WebServlet(name = "ServletMozoPrincipal", urlPatterns = {"/mozo"})
public class ServletMozoPrincipal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("MOZO PRINCIPAL");
        Mozo unM = (Mozo) request.getSession(true).getAttribute("mozo");
        if(unM==null){
            response.sendRedirect("/mozo/");
            return;
        }
        String accion = request.getParameter("accion");
        if(accion.equals("new")){
          request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
          AsyncContext contexto = request.startAsync(); 
          contexto.getResponse().setContentType("text/event-stream");
          contexto.getResponse().setCharacterEncoding("UTF-8");
          contexto.setTimeout(0);
          VistaPrincipalMozoWeb vista = new VistaPrincipalMozoWeb(unM,contexto);
          request.getSession().setAttribute("vistaPM", vista);
        }else{
           VistaPrincipalMozoWeb vista = (VistaPrincipalMozoWeb)request.getSession().getAttribute("vistaPM");
            switch (accion){
                case "abrirMesa" : vista.abrirMesa(request); break;
                case "cerrarMesa" : vista.cerrarMesa(request);break;
                //case "agregarPedido" : vista.cerrarMesa(request);break;
                case "transferir" : vista.transferir(request);break;
                case "asignarCliente" : vista.asignarCliente(request);break;
                case "aceptarTransferencia" : vista.aceptarTransferencia(request);break;
            }
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
