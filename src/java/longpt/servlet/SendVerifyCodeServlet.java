/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.dbulti.SendMail;
import longpt.tblaccount.TblAccountDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "SendVerifyCodeServlet", urlPatterns = {"/SendVerifyCodeServlet"})
public class SendVerifyCodeServlet extends HttpServlet {

    private final String VERIFY_BOOKING_PAGE = "verifybookingpage";
    private Logger log = Logger.getLogger(SendVerifyCodeServlet.class);

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

        String url = VERIFY_BOOKING_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ACCOUNT");
                String username = null;
                if (accountDTO != null) {
                    username = accountDTO.getUsername();
                }

                //Get name, address and phone and put them in session scope
                String name = request.getParameter("txtCustomerName");
                session.setAttribute("NAME_ACCOUNT", name);

                String address = request.getParameter("txtAddress");
                session.setAttribute("ADDRESS", address);

                String phone = request.getParameter("txtPhone");
                session.setAttribute("PHONE", phone);

                //Generate random code with 4 digit
                String code = generateRandomCode(4);

                //put confirmation code to request scope
                session.setAttribute("CONFIRMATION_CODE", code);

                //send activation mail
                SendMail.sendAccountActivationCode(code, username);
            }
        } catch (MessagingException ex) {
            log.error("SendVerifyCodeServlet _ MessagingException: " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> listMap = (Map<String, String>) context.getAttribute("MAP");

            RequestDispatcher rd = request.getRequestDispatcher(listMap.get(url));
            rd.forward(request, response);
            out.close();
        }
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        String code = "";

        for (int i = 0; i < length; i++) {
            code += random.nextInt(10);
        }
        return code;
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
