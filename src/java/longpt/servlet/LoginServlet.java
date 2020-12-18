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
import longpt.dbulti.ReCaptchaVerify;
import longpt.tblaccount.TblAccountDAO;
import longpt.tblaccount.TblAccountDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalid";
    private final String HOME_CONTROLLER = "Home";
    private final String LOGIN_PAGE = "login";
    private final Logger logger = Logger.getLogger(LoginServlet.class);

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
        String url = INVALID_PAGE;
        boolean loginSuccessful = false;
        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            //Call DAO
            TblAccountDAO accountDAO = new TblAccountDAO();

            TblAccountDTO accountDTO = accountDAO.checkLogin(username, password);
            if (accountDTO != null) {
                String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                //Verify Recaptcha
                loginSuccessful = ReCaptchaVerify.verify(gRecaptchaResponse);
                if (loginSuccessful == false) {
                    request.setAttribute("CAPTCHA_ERROR", "INVALID CAPTCHA");
                    url = LOGIN_PAGE;
                } else {
                    //Check status in DB
                    if (accountDTO.getStatusId() == 1) {  //1 means Active in DB
                        HttpSession session = request.getSession();

                        //put the account to session
                        session.setAttribute("ACCOUNT", accountDTO);
                        url = HOME_CONTROLLER;
                    } else {
                        url = INVALID_PAGE;
                    }
                }
            } else {
                url = LOGIN_PAGE;
            }
        } catch (SQLException ex) {
            logger.error("LoginServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("LoginServlet _ NamingException: " + ex.getMessage());
        } finally {
            if (loginSuccessful == true) {
                response.sendRedirect(url);
            } else {
                ServletContext context = request.getServletContext();
                Map<String, String> listMap = (Map<String, String>) context.getAttribute("MAP");

                RequestDispatcher rd = request.getRequestDispatcher(listMap.get(url));
                rd.forward(request, response);
            }
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
