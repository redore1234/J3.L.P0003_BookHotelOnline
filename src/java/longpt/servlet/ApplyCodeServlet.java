/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
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
import longpt.tbldiscount.TblDiscountDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "ApplyCodeServlet", urlPatterns = {"/ApplyCodeServlet"})
public class ApplyCodeServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(ApplyCodeServlet.class);
    private final String VIEW_CART_CONTROLLER = "ViewCart";

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

        String url = VIEW_CART_CONTROLLER;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String disCode = request.getParameter("txtDiscount");
                int code = 0;
                if (disCode == null || disCode.isEmpty()) {
                    return;
                } else {
                    code = Integer.parseInt(disCode);
                }
                TblDiscountDAO discountDAO = new TblDiscountDAO();
                int percent = discountDAO.getDisPercentById(code);
                if (percent > 0) { //discount code is existed
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart != null) {
                        cart.setDiscountID(code);
                        cart.setDiscountPer(percent);
                    }
                } else {
                    request.setAttribute("USED_DISCOUNT", "This code doesn't exist!");
                }
            }
        } catch (SQLException ex) {
            //log("ApplyCodeServlet - SQLException: " + ex.getMessage());
            logger.error("ApplyCodeServlet - SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            //log("ApplyCodeServlet - NamingException: " + ex.getMessage());
            logger.error("ApplyCodeServlet - NamingException: " + ex.getMessage());
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
