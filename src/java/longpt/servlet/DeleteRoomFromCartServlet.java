/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.cart.Cart;
import longpt.tblaccount.TblAccountDTO;

/**
 *
 * @author phamt
 */
@WebServlet(name = "DeleteRoomFromCartServlet", urlPatterns = {"/DeleteRoomFromCartServlet"})
public class DeleteRoomFromCartServlet extends HttpServlet {

    private final String VIEW_CART_CONTROLLER = "ViewCart";
    private final String HOME_CONTROLLER = "Home";

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
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ACCOUNT");
                if (accountDTO != null) {
                    if (accountDTO.getRoleId().equals("user")) {
                        //Get room id
                        String room = request.getParameter("txtRoomId");
                        int roomId = 0;
                        if (room != null) {
                            roomId = Integer.parseInt(room);
                        }

                        Cart cart = (Cart) session.getAttribute("CART");
                        if (cart != null) {
                            cart.removeRoomFromCart(roomId);
                            if (cart.getCompartment() == null) {
                                cart = null;
                            }
                            session.setAttribute("CART", cart);
                            url = VIEW_CART_CONTROLLER;
                        }
                    } else {
                        url = HOME_CONTROLLER;
                    }
                } else {
                    url = HOME_CONTROLLER;
                }
            } else {
                url = HOME_CONTROLLER;
            }
        } finally {
            response.sendRedirect(url);
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
