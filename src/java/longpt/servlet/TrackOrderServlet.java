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
import longpt.tblaccount.TblAccountDTO;
import longpt.tblorder.TblOrderDAO;
import longpt.tblorder.TblOrderDTO;
import longpt.tblorderdetail.TblOrderDetailDAO;
import longpt.tblorderdetail.TblOrderDetailDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {

    private String TRACKING_PAGE = "trackorderpage";
    private final static Logger logger = Logger.getLogger(TrackOrderServlet.class);

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

        String url = TRACKING_PAGE;
        try {
            String orderId = request.getParameter("orderId");
            //Get username to track order
            String username = null;
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ACCOUNT");
                if (accountDTO != null) {
                    username = accountDTO.getUsername();
                }
                //Get Order by OrderId and Username
                TblOrderDAO orderDAO = new TblOrderDAO();
                TblOrderDTO orderDTO = orderDAO.getOrder(orderId, username);
                request.setAttribute("ORDER_INFO", orderDTO);

                if (orderDTO != null) {
                    //Get order detail by OrderId
                    TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                    orderDetailDAO.loadOrderDetail(orderId);
                    Map<TblOrderDetailDTO, String> orderDetailMap = orderDetailDAO.getListOrderDetail();
                    request.setAttribute("ORDER_DETAIL_INFO", orderDetailMap);
                }
            }
        } catch (SQLException ex) {
            logger.error("TrackOrderServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("TrackOrderServlet _ NamingException: " + ex.getMessage());
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
