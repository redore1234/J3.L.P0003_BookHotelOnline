/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.dbulti.ReCaptchaVerify;
import longpt.tblaccount.TblAccountDAO;
import longpt.tblaccount.TblAccountDTO;
import longpt.tblrole.TblRoleDAO;

/**
 *
 * @author phamt
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalid";
    private final String HOME_CONTROLLER = "Home";
    private final String LOGIN_PAGE = "login";

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
                    request.setAttribute("CAPTCHA_ERROR", "Captcha invalid!");
                    url = LOGIN_PAGE;
                } else {
                    //Check status in DB
                    if (accountDTO.getStatusId() == 1) {  //1 means Active in DB
                        HttpSession session = request.getSession();

                        //put the account to session
                        session.setAttribute("ACCOUNT", accountDTO);

                        //put the role to session
                        TblRoleDAO roleDAO = new TblRoleDAO();
                        String role = roleDAO.getRole(accountDTO.getRoleId());
                        session.setAttribute("ACCOUNT_ROLE", role);

                        url = HOME_CONTROLLER;
                    } else {
                        url = INVALID_PAGE;
                    }
                }
            } else {
                url = LOGIN_PAGE;
            }
        } catch (SQLException ex) {
            //logger.error("LoginServlet _ SQLException: " + ex.getMessage());
            log("LoginServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            //logger.error("LoginServlet _ NamingException: " + ex.getMessage());
            log("LoginServlet _ NamingException: " + ex.getMessage());
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
