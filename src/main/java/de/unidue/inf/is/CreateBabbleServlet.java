package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.stores.BabbleStore;
import de.unidue.inf.is.stores.BabbleUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateBabbleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setAttribute();
        request.getRequestDispatcher("/create_babble.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("dopost methode");
        BabbleStore b = new BabbleStore();
        b.test();
        //Get user input from website url: localhost:9072/createbabble
        String text = request.getParameter("comment");
        //String creator = request.getParameter("creator");

        if (null != text  && !text.isEmpty() ) {
            //Add new babble to database babble
            Babble babble = new Babble(text, "dbuser"); //creator is current BabbleUser's username
            System.out.println("adding babble" + babble.getText() + " "+ babble.getCreator());
            //new BabbleStore().addBabble(babble);
            b.addBabble(babble);
            System.out.printf("done adding");

            doGet(request, response);
        }
    }


}
