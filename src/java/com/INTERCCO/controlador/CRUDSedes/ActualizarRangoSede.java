package com.INTERCCO.controlador.CRUDSedes;

import com.INTERCCO.controlador.General.CifradoASCII;
import com.INTERCCO.modelo.Conexion.ConectaDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActualizarRangoSede extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String id = request.getParameter("idSede");
            String rangoSede = request.getParameter("rgo");
            
            
            ConectaDB cdb = new ConectaDB();
            Connection con = cdb.conectar();
            PreparedStatement ps;
            
            ps = con.prepareStatement("UPDATE sedes SET rango=? WHERE idSedes=?;");
            if (rangoSede.equals("S")){
                ps.setString(1, "Secundaria");
                rangoSede = "Secundaria";
            } else {
                ps.setString(1, "Principal");
                rangoSede = "Principal";
            }
            ps.setInt(2, Integer.parseInt(id));
            int res = ps.executeUpdate();

            if (res > 0){
                request.getRequestDispatcher("Sedes?mensaje=YActualizarRango&rgoSed="+rangoSede).forward(request, response);
            } else {
                request.getRequestDispatcher("Sedes?mensaje=Ne").forward(request, response);
                System.out.println("ERROR de ACTUALIZAR RANGO el dato de SEDE.");
            }
            cdb.cierraConexion();
            
        } catch (SQLException sql) {
            request.getRequestDispatcher("Sedes?mensaje=Ne").forward(request, response);
            System.out.println("ERROR en MySQL ACTUALIZANDO RANGO los datos de SEDES.");
            sql.getStackTrace();
        } catch (Exception alle){
            request.getRequestDispatcher("Error404.jsp?mensaje="+alle.toString()).forward(request, response);
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
