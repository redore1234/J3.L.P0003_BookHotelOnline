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
import longpt.tblaccount.RegisterNewAccountError;
import longpt.tblaccount.TblAccountDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "RegisterNewAccountServlet", urlPatterns = {"/RegisterNewAccountServlet"})
public class RegisterNewAccountServlet extends HttpServlet {

    private final String REGISTER_PAGE = "registerpage";
    private final String LOGIN_PAGE = "login";
    private final static Logger logger = Logger.getLogger(RegisterNewAccountServlet.class);

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

        RegisterNewAccountError errors = new RegisterNewAccountError();
        boolean foundErr = false;

        String url = REGISTER_PAGE;
        try {
            String username = request.getParameter("txtUsername");
            String name = request.getParameter("txtName");
            String address = request.getParameter("txtAddress");
            String phone = request.getParameter("txtPhone");
            String password = request.getParameter("txtPassword");
            String confirmPassword = request.getParameter("txtConfirmPassword");

            if (!confirmPassword.equals(password)) {
                foundErr = true;
                errors.setPasswordIsNotMatch("Confirm must match password!");
            }

            if (foundErr) {
                request.setAttribute("ERRORS", errors);
            } else {
                TblAccountDAO dao = new TblAccountDAO();
                boolean result = dao.createNewAccount(username, password, name, address, phone);
                if (result) {
                    url = LOGIN_PAGE;
                }
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            logger.error("RegisterNewAccountServlet _ SQLException: " + msg);
            if (msg.contains("duplicate")) {
                request.setAttribute("ERRORS", errors);
                errors.setUsernameIsExisted("Username is existed. Please input another username!");
            }
        } catch (NamingException ex) {
            logger.error("RegisterNewAccountServlet _ NamingException: " + ex.getMessage());
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
