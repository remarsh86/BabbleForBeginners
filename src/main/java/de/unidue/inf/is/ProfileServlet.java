package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.BabbleUserStore;
import de.unidue.inf.is.stores.TimelineStore;

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
    String clickedUser;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //For session
        HttpSession session = request.getSession(false);
        bs = (BabbleUser) session.getAttribute("babbler");
        System.out.println(" session username is (on profile page):" + bs.getUsername() );
        request.setAttribute("primaryuser", bs.getUsername());

        clickedUser = request.getParameter("clickedUser");
        System.out.println("clicked user is: "+ clickedUser);

        if(clickedUser !=null){
            BabbleUserStore store = new BabbleUserStore(clickedUser);
            BabbleUser user = store.getBabbleUser();
            request.setAttribute("user", user);
            TimelineStore tstore = new TimelineStore(clickedUser);
            //for(Babble b : timelineList) System.out.println(b.getId());
            timelineList = tstore.createTimeline();
            request.setAttribute("users", timelineList);

        }else{
            request.setAttribute("user", bs);
            TimelineStore store = new TimelineStore(bs.getUsername());
            timelineList = store.createTimeline(); //maybe return
            //for(Babble b : timelineList) System.out.println(b.getId());
            request.setAttribute("users", timelineList);
        }

//        //For Personal Information
//        request.setAttribute("user", bs);
//
//
////        //For timeline: Pass list of babbles to profile page
//        TimelineStore store = new TimelineStore(bs.getUsername());
//        timelineList = store.createTimeline(); //maybe return
//        for(Babble b : timelineList) System.out.println(b.getId());
//        request.setAttribute("users", timelineList);

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
