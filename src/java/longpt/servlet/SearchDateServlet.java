/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import longpt.tblroom.TblRoomDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "SearchDateServlet", urlPatterns = {"/SearchDateServlet"})
public class SearchDateServlet extends HttpServlet {

    private final String HOME_PAGE = "homepage";
    private final String HOME_CONTROLLER = "Home";
    private final static Logger logger = Logger.getLogger(SearchDateServlet.class);

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

        String url = "";

        try {
            String checkin = request.getParameter("dtCheckin");
            String checkout = request.getParameter("dtCheckout");

            long millis = System.currentTimeMillis();
            java.sql.Date currentDate = new java.sql.Date(millis);

            //Change java.util to java.sql
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate = format.parse(checkin);
            java.sql.Date sqlCheckInDate = new java.sql.Date(checkInDate.getTime());

            Date checkOutDate = format.parse(checkout);
            java.sql.Date sqlCheckOutDate = new java.sql.Date(checkOutDate.getTime());

            //Call DAO
            TblRoomDAO roomDAO = new TblRoomDAO();

            if (sqlCheckInDate.after(sqlCheckOutDate)) { // checkin date sau checkout date
                // false => load again
                request.setAttribute("SEARCH_ERROR", "Checkin Date must before Checkout Date");
                url = HOME_CONTROLLER;
            } else if (sqlCheckInDate.compareTo(currentDate) < 0 || sqlCheckOutDate.compareTo(currentDate) < 0) { // checkin va checkout khong o truoc currentDate
                // false => load again
                request.setAttribute("SEARCH_ERROR", "Checkin Date and Checkout Date must after currentDate");
                url = HOME_CONTROLLER;
            } else {
                //Search ROOM
                roomDAO.searchRoomUnavailable(sqlCheckInDate, sqlCheckOutDate);
                List<TblRoomDTO> listRoom = roomDAO.getListRoom();
                request.setAttribute("LIST_ROOM", listRoom);
                request.setAttribute("UNAVAILABLE_ROOM", "UNAVAILABLE");
                
                url = HOME_PAGE;
                
            }
        } catch (ParseException ex) {
            //logger.error("SearchDateServlet _ ParseException: " + ex.getMessage());
            log("SearchDateServlet _ ParseException: " + ex.getMessage());
        } catch (SQLException ex) {
            //logger.error("SearchDateServlet _ SQLException: " + ex.getMessage());
            log("SearchDateServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            //logger.error("SearchDateServlet _ NamingException: " + ex.getMessage());
            log("SearchDateServlet _ NamingException: " + ex.getMessage());
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
