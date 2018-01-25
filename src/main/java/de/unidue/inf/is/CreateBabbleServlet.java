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

        request.getRequestDispatcher("/create_babble.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        //Get user input from website url: localhost:9072/createbabble
        String text = request.getParameter("text");
        String creator = request.getParameter("creator");

        if (null != text && null != creator && !text.isEmpty() && !creator.isEmpty()) {
            //Add new babble to database babble
            Babble babble = new Babble(text, creator);
            new BabbleStore().addBabble(babble);

            doGet(request, response);
        }
    }


}
