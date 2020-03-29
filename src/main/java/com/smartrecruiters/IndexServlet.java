package com.smartrecruiters;

import com.smartrecruiters.access.AccessController;
import com.smartrecruiters.contents.HomePageContent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "IndexServlet", value = "/index.html")
public class IndexServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log("index: verifying token:");
        AccessController accessController = new AccessController();

        if(!accessController.hasAccess(request)) {
            log("No access: redirect...");
            response.sendRedirect(Constants.PAGE_SMARTTOKEN);
            return;
        }

        log("index: Preparing page content:");
        HomePageContent homePageContent = new HomePageContent(request);
        response.setContentType("text/html");
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        Template t = ve.getTemplate("templates/index.vm");
        VelocityContext context = new VelocityContext();
        context.put("content", homePageContent);
        t.merge( context, response.getWriter() );
    }
}
