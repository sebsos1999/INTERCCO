package com.INTERCCO.controlador.CRUDParametros;

import com.INTERCCO.modelo.Conexion.ConectaDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActualizarIVA extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String porcentaje = request.getParameter("actPorcentajeIVA");
            
            ConectaDB cdb = new ConectaDB();
            Connection con = cdb.conectar();
            PreparedStatement ps;
            
            int numPuntos = 0;
            for (int i = 0; i < porcentaje.length(); i++) {
                char cp = porcentaje.charAt(i);
                if (cp == '.'){
                    numPuntos ++;
                }
            }
            
            if (numPuntos > 1){
                request.getRequestDispatcher("Parametros?mensaje=NptMP").forward(request, response);
                System.out.println("ERROR de ACTUALIZAR el dato de IVA.");
            } else {
                ps = con.prepareStatement("UPDATE iva SET porcentaje=?;");
                ps.setFloat(1, Float.parseFloat(porcentaje));
                int res = ps.executeUpdate();

                if (res > 0){
                    request.getRequestDispatcher("Parametros?mensaje=YEditarIVA").forward(request, response);
                } else {
                    request.getRequestDispatcher("Parametros?mensaje=Ne&nomMod=El IVA&accMod=actualizar").forward(request, response);
                    System.out.println("ERROR de ACTUALIZAR el dato de IVA.");
                }
            }
            
            cdb.cierraConexion();
        } catch (SQLException sql) {
            request.getRequestDispatcher("Parametros?mensaje=Ne&nomMod=El IVA&accMod=actualizar").forward(request, response);
            System.out.println("ERROR en MySQL ACTUALIZANDO los datos de IVA.");
            sql.getStackTrace();
        } catch (NullPointerException npe){
            request.getRequestDispatcher("Error404.jsp?mensaje="+npe.toString()).forward(request, response);
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