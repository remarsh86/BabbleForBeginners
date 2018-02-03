package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.stores.BabbleStore;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    private BabbleStore store = null;
    private List<Babble> list = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        request.setAttribute("babblelist", list);
        request.getRequestDispatcher("babble_search.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String text = request.getParameter("comment");
        if ( text !=null   && !text.isEmpty() ) {

            store = new BabbleStore();
            System.out.println("My text is: " + text);
            list = store.searchBabble(text);
            //list = store.searchBabble2(text);

            System.out.println("is there anything in my list");
            for (Babble b : list ) System.out.println(b.getId() + " " + b.getText());

            store.complete();
            store.close();


        }


        doGet(request, response);
    }


}


