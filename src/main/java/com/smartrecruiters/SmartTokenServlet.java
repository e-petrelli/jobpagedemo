package com.smartrecruiters;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

@WebServlet(name = "HelloAppEngine", value = "/smartToken.html")
public class SmartTokenServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("text/html");
    VelocityEngine ve = new VelocityEngine();
    ve.init();
    Template t = ve.getTemplate("templates/smartToken.vm");
    VelocityContext context = new VelocityContext();
    t.merge( context, response.getWriter() );
  }
}
