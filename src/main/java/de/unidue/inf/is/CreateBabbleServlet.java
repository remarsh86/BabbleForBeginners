package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.stores.BabbleStore;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateBabbleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession();

        //request.setAttribute();
        request.getRequestDispatcher("/create_babble.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("dopost methode von CreateBabbletServlet.java");


        BabbleStore b = new BabbleStore();

        HttpSession session=request.getSession(false);

        //Get user input from website url: localhost:9072/createbabble
        String text = request.getParameter("comment");
        //String creator = request.getParameter("creator");

        if (null != text  && !text.isEmpty() ) {
            //Add new babble to database babble
            //System.out.println("session attribute: " );


            Babble babble = new Babble(text, (String) session.getAttribute("dbuser")); //creator is current BabbleUser's username
            System.out.println("adding babble: " + babble.getText() + " "+ babble.getCreator());
            //new BabbleStore().addBabble(babble);
            boolean added = b.addBabble(babble);
            //System.out.printf("babble added? "+ String.valueOf(added));
            b.test();
            b.close();

            doGet(request, response);
        }
    }


}
