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
import longpt.tblaccount.TblAccountDTO;
import longpt.tbldiscount.TblDiscountDAO;
import longpt.tblorder.TblOrderDAO;
import longpt.tblorderdetail.TblOrderDetailDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String VIEW_CART_CONTROLLER = "ViewCart";
    private final String HOME_CONTROLLER = "Home";
    private final static Logger logger = Logger.getLogger(CheckOutServlet.class);

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
            String name = request.getParameter("txtCustomerName");
            String address = request.getParameter("txtAddress");
            String phone = request.getParameter("txtPhone");

            String checkin = request.getParameter("dtCheckin");
            System.out.println("CheckOutServlet - checkin: " + checkin);
            String checkout = request.getParameter("dtCheckout");
            System.out.println("CheckOutServlet - checkout: " + checkout);


            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    if (cart.getCompartment() != null) {
                        //INSERT ORDER
                        TblOrderDAO orderDAO = new TblOrderDAO();
                        //Get username 
                        String username = null;
                        TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ACCOUNT");
                        if (accountDTO != null) {
                            username = accountDTO.getUsername();
                        }
                        int discountId = cart.getDiscountID();
                        double totalPrice = cart.getTotalPrice();
                        double discountPrice = cart.getPriceAfterDiscount();
                        String orderId = orderDAO.createOrder(username, name, address, phone, totalPrice, discountPrice);
                        if (orderId != null) {
                            //---UPDATE DISCOUNT STATUS---//
                            TblDiscountDAO discountDAO = new TblDiscountDAO();
                            if (discountDAO.deleteDiscount(discountId) == true) {
                                //INSERT ORDER DETAIL
                                TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                                boolean result = orderDetailDAO.addOrderDetail(cart, orderId, checkin, checkout);
                                if (result == true) {
                                    session.removeAttribute("CART");

                                    request.setAttribute("TRACKING", orderId);
                                }
                            }
                        }
                    }
                } else {
                    url = HOME_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            //logger.error("CheckOutServlet _ SQLException: " + ex.getMessage());
            log("CheckOutServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            //logger.error("CheckOutServlet _ NamingException: " + ex.getMessage());
            log("CheckOutServlet _ NamingException: " + ex.getMessage());
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
