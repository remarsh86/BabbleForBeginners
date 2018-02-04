package de.unidue.inf.is;

import de.unidue.inf.is.domain.*;
import de.unidue.inf.is.stores.*;

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
    TimelineStore tstore;
    RebabbleStore rstore;
    LikeStore lstore;
    BabbleStore bstore;
    private List<LikesBabble> likesList = new ArrayList<>();
    private List<Babble> rebabbledList = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //For session
//        HttpSession session = request.getSession(false);
        bs = (BabbleUser) session.getAttribute("babbler");
        System.out.println(" session username is (on profile page):" + bs.getUsername() );
        request.setAttribute("primaryuser", bs.getUsername());

        clickedUser = request.getParameter("clickedUser");
        //System.out.println("clicked user is: "+ clickedUser);

        if(clickedUser !=null){ //create profile for clicked user
            BabbleUserStore store = new BabbleUserStore(clickedUser);
            BabbleUser user = store.getBabbleUser();

            request = createTimeline(request, clickedUser, user);


        }else{ //create profile for session user

            request = createTimeline(request, bs.getUsername(), bs);
        }

        String clickedBabble = request.getParameter( "clickedBabble");
        if(clickedBabble !=null){
            int clickedId = Integer.parseInt(clickedBabble);
            bstore = new BabbleStore();
            Babble b = bstore.getBabble(clickedId);
            session.setAttribute("babble", b);

        }

        tstore.complete();
        tstore.close();
        rstore.complete();
        rstore.close();

        request.getRequestDispatcher("/profil_seite.ftl").forward(request, response);

    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        session = request.getSession(false);
        BabbleUser bs = (BabbleUser) session.getAttribute("babbler");
        System.out.println(bs.getUsername());

        String blockButton = request.getParameter("block");
        String followButton = request.getParameter("follow");


        //delete any babbles
        Babble deleteBab = (Babble) session.getAttribute("babble");
        if(deleteBab != null) {
            bstore = new BabbleStore();
            bstore.deleteBabble(deleteBab.getId());
        }

        //if blockButton clicked
        if (null != blockButton  && !blockButton.isEmpty()) {
            System.out.println("You clicked the block Button");
            System.out.println("What is clickedUser " + clickedUser);

            //don't block yourself
            if(clickedUser != null && !clickedUser.equals(bs.getUsername())){
                System.out.println("What is clickedUser " + clickedUser);
                BlockStore blockStore = new BlockStore();
                if (blockStore.isBlockingClickedUser(bs.getUsername(), clickedUser)){
                    //then unblock
                    blockStore.deleteBlock(bs.getUsername(), clickedUser);

                }else{
                    //Add block
                    blockStore.insertBlock(bs.getUsername(), clickedUser);

                }
                blockStore.complete();
                blockStore.close();

                //check result
                blockStore = new BlockStore();
                ArrayList<Blocks> list = blockStore.getBlockTable();
                for(Blocks b : list) System.out.println("Blocks table: " + b.getBlocker() + "blocks" + b.getBlockee());
                blockStore.complete();
                blockStore.close();
            }
        }
        //if followButton clicked
        if (null != followButton  && !followButton.isEmpty()) {
            //System.out.println("What is clickedUser " + clickedUser);
            //do you follow clickedUser?? if yes -> unfollow. If no -> follow

            //System.out.println("You clicked the follow Button");

            //if another user has been clicked (clickedUser parameter) and the clicked user is not oneself,
            //then session's babbleuser object can follow the clickeduser
            if(clickedUser != null && !clickedUser.equals(bs.getUsername())){

                Follows follows = new Follows(bs.getUsername(), clickedUser);
                FollowStore followStore = new FollowStore();
                boolean doesFollow = followStore.doTheyFollow(bs.getUsername(), clickedUser);

                //System.out.println("Do they follow? " + doesFollow);
                if(doesFollow) //current session use does follow clkuser=> unfollow
                    followStore.deleteFromFollow(bs.getUsername(), clickedUser);
                if(!doesFollow) //current session use doesn't follow clkuser=> follow
                     followStore.addFollows(follows);
                System.out.println("Do they follow now? "+ followStore.doTheyFollow(bs.getUsername(),clickedUser));
                followStore.complete();
                followStore.close();

            }else System.out.println("You clicked on yourself!");

        }

        doGet(request, response);
    }

    private HttpServletRequest createTimeline(HttpServletRequest request,  String personName, BabbleUser personObject) {
        lstore = new LikeStore();
        likesList =lstore.userLiked(personName);
//        System.out.println("printing elements in likeslist");
//        for( LikesBabble l : likesList) System.out.println(  l.getUsername() +" "+ l.getBabble()+" "+ l.getType()+" "+ l.getCreated());

        tstore = new TimelineStore(personName);
        timelineList = tstore.createTimeline2();

        rstore = new RebabbleStore();
        rebabbledList = rstore.userRebabbled(personName);

        request.setAttribute("user", personObject);
        request.setAttribute("users", timelineList);
        request.setAttribute("rebabbles", rebabbledList);
        request.setAttribute("likes", likesList);


        return request;
    }

}
