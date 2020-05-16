package com.smartrecruiters.servlets;

import com.smartrecruiters.Constants;
import com.smartrecruiters.access.AccessController;
import com.smartrecruiters.api.posting.invoker.ApiException;
import com.smartrecruiters.contentbuilders.DetailPageContentBuilder;
import com.smartrecruiters.contents.DetailPageContent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DetailPageServlet", value = "/jobDetail.html")
public class DetailPageServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log("detail: verifying company identifier:");
        AccessController accessController = new AccessController();

        if(!accessController.hasCompanyIdentifierParameter(request)) {
            log("No access: redirect...");
            response.sendRedirect(Constants.ASK_COMPANY_IDENTIFIER_HTML);
            return;
        }

        log("detail: Preparing page content:");
        try {
            DetailPageContentBuilder detailPageContentBuilder = new DetailPageContentBuilder();
            DetailPageContent detailPageContent = detailPageContentBuilder.getContent(request);

            response.setContentType("text/html");
            VelocityEngine ve = new VelocityEngine();
            ve.init();
            Template t = ve.getTemplate("templates/detail.vm");
            VelocityContext context = new VelocityContext();
            context.put("content", detailPageContent);
            t.merge( context, response.getWriter() );
        } catch (ApiException e) {
            e.printStackTrace();
            response.sendRedirect(Constants.ASK_COMPANY_IDENTIFIER_HTML);
        }
    }
}
