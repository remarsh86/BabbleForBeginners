package de.unidue.inf.is;

import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.BabbleUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BabbleUserStore store = new BabbleUserStore();
        BabbleUser user = store.getBabbleUser();


        HttpSession session = request.getSession();
        //alternative: sesssion.setAttribute("dbuser", user);
        session.setAttribute("dbuser", user.getUsername());
        System.out.println("Get attribute from session (ProfileServlet) "+session.getAttribute("dbuser"));

        request.getRequestDispatcher("/profil_seite.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String blockButton = request.getParameter("block");
        String followButton = request.getParameter("follow");



        //if blockButton clicked
        if (null != blockButton  && !blockButton.isEmpty()) {
            System.out.println("You clicked the block Button");
        }
        //if followButton clicked
        if (null != followButton  && !followButton.isEmpty()) {
            System.out.println("You clicked the follow Button");
        }

        doGet(request, response);
    }

}
