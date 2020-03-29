package com.smartrecruiters;

import com.smartrecruiters.access.AccessController;
import com.smartrecruiters.contents.DetailPageContent;
import com.smartrecruiters.contents.HomePageContent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "JobDetailServlet", value = "/jobDetail/*")
public class jobDetailServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log("detail: verifying token:");
        AccessController accessController = new AccessController();

        if(!accessController.hasAccess(request)) {
            log("No access: redirect...");
            response.sendRedirect(Constants.PAGE_SMARTTOKEN);
            return;
        }

        log("detail: Preparing page content:");
        DetailPageContent detailPageContent = new DetailPageContent(request);
        response.setContentType("text/html");
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        Template t = ve.getTemplate("templates/detail.vm");
        VelocityContext context = new VelocityContext();
        context.put("content", detailPageContent);
        t.merge( context, response.getWriter() );
    }
}
