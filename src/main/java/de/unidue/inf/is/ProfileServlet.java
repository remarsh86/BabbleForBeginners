package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.BabbleUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BabbleUser bs = null;
    private List<Babble> timelineList = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        //For session
//        BabbleUserStore store = new BabbleUserStore();
//        user = store.getBabbleUser();
//        //Sets the current session id as dbuser with a BabbleUser object as value
  //      HttpSession session = request.getSession();
//        session.setAttribute("dbuser", user);
//        //Set profile personal information to match session user
//        request.setAttribute("user", user);


        HttpSession session = request.getSession(false);
        if(session.getAttribute("babbler") != null) {
            bs = (BabbleUser) session.getAttribute("babbler");
        }

        //For Personal Information
        request.setAttribute("user", bs);

        //For timeline: Pass list of babbles to profile page
        //request.setAttribute("datatable", timelineList);

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
