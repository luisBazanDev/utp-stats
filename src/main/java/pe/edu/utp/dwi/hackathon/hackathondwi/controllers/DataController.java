package pe.edu.utp.dwi.hackathon.hackathondwi.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value="/data/*")
public class DataController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        if (pathInfo != null && pathInfo.length() > 1) {
            String sectionId = pathInfo.substring(1);
            req.setAttribute("sectionId", sectionId);
        } else {
            // Redirect
            resp.sendRedirect("/home");
            return;
        }

        req.getRequestDispatcher("/data.xhtml").forward(req, resp);
    }
}
