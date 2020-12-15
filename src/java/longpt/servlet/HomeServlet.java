/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longpt.tblroom.TblRoomDAO;
import longpt.tblroom.tblRoomDTO;
import longpt.tblroomtype.TblRoomTypeDAO;
import longpt.tblroomtype.TblRoomTypeDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    private final String HOME_PAGE = "homepage";
    private final Logger logger = Logger.getLogger(HomeServlet.class.getName());

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = HOME_PAGE;
        try {
            //Get all Room
            TblRoomDAO roomDAO = new TblRoomDAO();
            roomDAO.loadAllRooms();

            List<tblRoomDTO> listRooms = roomDAO.getListRoom();
            request.setAttribute("LIST_ROOM", listRooms);

            //Get all Room type
            TblRoomTypeDAO roomTypeDAO = new TblRoomTypeDAO();
            roomTypeDAO.loadAllRoomType();

            List<TblRoomTypeDTO> listRoomType = roomTypeDAO.getListRoomType();
            request.setAttribute("LIST_ROOM_TYPE", listRoomType);
        } catch (SQLException ex) {
            //logger.error("HomeServlet _ SQLException: " + ex.getMessage());
            log("HomeServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            //logger.error("HomeServlet _ NamingException: " + ex.getMessage());
            log("HomeServlet _ NamingException: " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> listMap = (Map<String, String>) context.getAttribute("MAP");
            
            RequestDispatcher rd = request.getRequestDispatcher(listMap.get(url));
            rd.forward(request, response);
            out.close();
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
