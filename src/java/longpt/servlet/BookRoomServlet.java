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
import longpt.cart.RoomItem;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "BookRoomServlet", urlPatterns = {"/BookRoomServlet"})
public class BookRoomServlet extends HttpServlet {

    private final String HOME_CONTROLLER = "Home";
    private final static Logger logger = Logger.getLogger(BookRoomServlet.class);

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

        String url = HOME_CONTROLLER;

        try {
            //Get Room Id
            String room = request.getParameter("roomId");
            int roomId = 0;
            if (room != null) {
                roomId = Integer.parseInt(room);
            }
            System.out.println("BookRoomServlet - roomId: " + room);
            //Get price
            String price = request.getParameter("roomPrice");
            System.out.println("BookRoomServlet - price: " + price);
            double roomPrice = 0;
            if (price != null) {
                roomPrice = Double.parseDouble(price);
            }
            //Get type Id
            String typeId = request.getParameter("typeId");
            System.out.println("BookRoomServlet - typeId: " + typeId);
            //Call DAO 

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart == null) {
                cart = new Cart();
            }
            RoomItem roomItem = new RoomItem(roomId, typeId, roomPrice);
            cart.bookRoomToCart(roomId, roomItem);
            session.setAttribute("CART", cart);

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
