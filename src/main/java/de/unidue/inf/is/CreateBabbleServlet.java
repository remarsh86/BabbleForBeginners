package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
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

        HttpSession session=request.getSession();

        //Get user input from website url: localhost:9072/createbabble
        String text = request.getParameter("comment");
        //String creator = request.getParameter("creator");

        if (null != text  && !text.isEmpty() ) {
            //Add new babble to database babble
            BabbleUser bs = (BabbleUser) session.getAttribute("babbler");
            System.out.println(bs.getUsername());
            Babble babble = new Babble(text, bs.getUsername()); //creator is current BabbleUser's username
            System.out.println("adding babble: " + babble.getText() + " "+ babble.getCreator());
            boolean added = b.addBabble(babble);


            b.test();
            b.close();

            doGet(request, response);
        }
    }


}
