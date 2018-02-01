package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.Follows;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.BabbleUserStore;
import de.unidue.inf.is.stores.FollowStore;
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
    HttpSession session;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //For session
//        HttpSession session = request.getSession(false);
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


        request.getRequestDispatcher("/profil_seite.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        session = request.getSession(false);

        String blockButton = request.getParameter("block");
        String followButton = request.getParameter("follow");



        //if blockButton clicked
        if (null != blockButton  && !blockButton.isEmpty()) {
            System.out.println("You clicked the block Button");
        }
        //if followButton clicked
        if (null != followButton  && !followButton.isEmpty()) {
            System.out.println("What is clickedUser " + clickedUser);
            //do you follow clickedUser?? if yes -> unfollow. If no -> follow

            System.out.println("You clicked the follow Button");
            BabbleUser bs = (BabbleUser) session.getAttribute("babbler");
            System.out.println(bs.getUsername());
            //if another user has been clicked (clickedUser parameter) and the clicked user is not oneself,
            //then session's babbleuser object can follow the clickeduser
            if(clickedUser != null && !clickedUser.equals(bs.getUsername())){

                Follows follows = new Follows(bs.getUsername(), clickedUser);
                FollowStore followStore = new FollowStore();
                boolean doesFollow = followStore.doTheyFollow(bs.getUsername(), clickedUser);

                System.out.println("Do they follow? " + doesFollow);
                if(doesFollow) //current session use does follow clkuser=> unfollow
                    followStore.deleteFromFollow(bs.getUsername(), clickedUser);
                if(!doesFollow) //current session use doesn't follow clkuser=> follow
                     followStore.addFollows(follows);
                System.out.println("Do they follow now? "+ doesFollow);
                followStore.complete();
                followStore.close();

            }else System.out.println("You clicked on yourself!");

        }

        doGet(request, response);
    }

}
