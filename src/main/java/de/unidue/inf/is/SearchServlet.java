package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.stores.BabbleStore;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setAttribute();
        request.getRequestDispatcher("babble_search.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("dopost methode");



        //Get user input from website url: localhost:9072/createbabble
        String text = request.getParameter("comment");
        //String creator = request.getParameter("creator");

        if (null != text  && !text.isEmpty() ) {
            //todo find babble with text
            System.out.println("find babble with text: " + text);

            doGet(request, response);
        }
    }


}


