package com.smartrecruiters.servlets;

import com.smartrecruiters.Constants;
import com.smartrecruiters.access.AccessController;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.contentbuilders.HomePageContentBuilder;
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
public class HomePageServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log("index: verifying companyIdentifier parameter:");
        AccessController accessController = new AccessController();

        if(!accessController.hasCompanyIdentifierParameter(request)) {
            log("No companyIdentifier parameter: redirect...");
            response.sendRedirect(Constants.ASK_COMPANY_IDENTIFIER_HTML);
            return;
        }

        try {
            log("index: Preparing page content:");
            HomePageContentBuilder homePageContentBuilder = new HomePageContentBuilder();
            HomePageContent homePageContent = homePageContentBuilder.getContent(request);

            response.setContentType("text/html");
            VelocityEngine ve = new VelocityEngine();
            ve.init();
            Template t = ve.getTemplate("templates/index.vm");
            VelocityContext context = new VelocityContext();
            context.put("content", homePageContent);
            t.merge( context, response.getWriter() );
        } catch (ApiException e) {
            e.printStackTrace();
            response.sendRedirect(Constants.ASK_COMPANY_IDENTIFIER_HTML);
        }

    }
}
