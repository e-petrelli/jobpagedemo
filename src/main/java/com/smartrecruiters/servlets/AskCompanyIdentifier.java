package com.smartrecruiters.servlets;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

@WebServlet(name = "askCompanyIdentifier", value = "/askCompanyIdentifier.html")
public class AskCompanyIdentifier extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("text/html");
    VelocityEngine ve = new VelocityEngine();
    ve.init();
    Template t = ve.getTemplate("templates/askCompanyIdentifier.vm");
    VelocityContext context = new VelocityContext();
    t.merge( context, response.getWriter() );
  }
}
