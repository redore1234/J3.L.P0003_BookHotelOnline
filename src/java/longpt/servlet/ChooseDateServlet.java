/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.servlet.http.HttpSession;
import longpt.cart.Cart;
import longpt.cart.CheckOutError;
import longpt.cart.RoomItem;
import longpt.tblroom.TblRoomDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "ChooseDateServlet", urlPatterns = {"/ChooseDateServlet"})
public class ChooseDateServlet extends HttpServlet {

    private final String CART_PAGE = "cartpage";
    private final static Logger logger = Logger.getLogger(ChooseDateServlet.class);

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
        String url = CART_PAGE;

        boolean foundErr = false;
        CheckOutError error = new CheckOutError();

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

            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    if (cart.getCompartment() != null) {
                        if (sqlCheckInDate.after(sqlCheckOutDate)) {
                            error.setCheckInAfterCheckOut("Checkin Date must before Checkout Date");
                            foundErr = true;
                        } else if (sqlCheckInDate.before(currentDate) || sqlCheckOutDate.before(currentDate)) {
                            error.setCheckInCheckOutBeforeCurDate("Checkin Date and Checkout Date must after currentDate");
                            foundErr = true;
                        } else {
                            TblRoomDAO roomDAO = new TblRoomDAO();
                            List<Integer> listRoomId = roomDAO.searchRoomUnavailable(sqlCheckInDate, sqlCheckOutDate);
                            if (listRoomId != null) {
                                boolean unvalidInCart = false;
                                //Check trong cart có những roomId nào không hợp lệ
                                for (Integer roomId : listRoomId) {
                                    if (cart.getCompartment().containsKey(roomId)) {
                                        unvalidInCart = true;
                                    }
                                }
                                if (unvalidInCart) {
                                    foundErr = true;
                                    String errMsg = "These rooms are not available: ";
                                    for (int i = 0; i < listRoomId.size(); i++) {
                                        errMsg += listRoomId.get(i) + "";

                                        if (i != listRoomId.size() - 1) {
                                            errMsg += ", ";
                                        }

                                    }
                                    error.setRoomIdBooked(errMsg);
                                }
                            }
                        }

                        if (foundErr == true) {
                            request.setAttribute("CHECKOUT_ERROR", error);
                            url = CART_PAGE;
                        } else {
                            Map<Integer, RoomItem> item = cart.getCompartment();
                            for (Integer roomId : item.keySet()) {
                                if (cart.getCompartment().containsKey(roomId)) {
                                    item.get(roomId).setCheckinDate(checkin);
                                    item.get(roomId).setCheckoutDate(checkout);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("ChooseDateServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("ChooseDateServlet _ NamingException: " + ex.getMessage());
        } catch (ParseException ex) {
            logger.error("ChooseDateServlet _ ParseException: " + ex.getMessage());
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
