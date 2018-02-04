package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.LikesBabble;
import de.unidue.inf.is.domain.ReBabble;
import de.unidue.inf.is.stores.BabbleStore;
import de.unidue.inf.is.stores.LikeStore;
import de.unidue.inf.is.stores.RebabbleStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailServlet extends HttpServlet {
    private BabbleUser bs = null;
    private HttpSession session;
    private Babble b;
    private String clickedBabble;
    private  LikeStore lstore;
    private RebabbleStore rstore;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LikesBabble> likesList = new ArrayList<>();
        List<LikesBabble> dislikesList = new ArrayList<>();
        List<LikesBabble> likesbabblestableList = new ArrayList<>();
        List<Babble> rebabbleList = new ArrayList<>();

        session = request.getSession(false);
        bs = (BabbleUser) session.getAttribute("babbler");
        request.setAttribute("primaryuser", bs);



        //get Babble object from database and set attribute (ftl page) "babble" only when first directed to page
     //   if(clickedBabble == null) {
            clickedBabble = request.getParameter("clickedBabble");
            int clickedId = Integer.parseInt(clickedBabble);
            BabbleStore bstore = new BabbleStore();
            b = bstore.getBabble(clickedId);
            bstore.complete();
            bstore.close();
       // }
        request.setAttribute("babble", b);


        //for showing buttons on ftl page
        lstore = new LikeStore();
        likesList = lstore.userLikedBabble(bs.getUsername(), b);
        dislikesList = lstore.userDislikedBabble(bs.getUsername(), b);
        likesbabblestableList = lstore.userDislikedorLikedBabble(bs.getUsername(), b);
        lstore.complete();
        lstore.close();

        rstore = new RebabbleStore();
        rebabbleList = rstore.userRebabbledBabble(bs.getUsername(), b);
        rstore.complete();
        rstore.close();

        request.setAttribute("dislikedlikedlist", likesbabblestableList);
        request.setAttribute("dislikedlist", dislikesList);
        request.setAttribute("likedlist", likesList);
        request.setAttribute("rebabblelist", rebabbleList);





        request.getRequestDispatcher("/detail_seite.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String like = request.getParameter("like");
        String dislike = request.getParameter("dislike");
        String rebabble = request.getParameter("rebabble");
        String norebabble = request.getParameter("norebabble");
        String delete = request.getParameter("delete");

//        if(delete !=null && !delete.isEmpty()){
//            System.out.println("delete babble");
//            BabbleStore babbleStore = new BabbleStore();
//            babbleStore.deleteBabble(b.getId());
//            babbleStore.complete();
//            babbleStore.close();
//
//        }


        if(norebabble!=null && !norebabble.isEmpty()){
            System.out.println("no rebabble ");
            rstore = new RebabbleStore();
            rstore.deleteRebabble(bs.getUsername(), b.getId());
            //check
            List<Babble> rebabbleList = rstore.userRebabbledBabble(bs.getUsername(), b);
            for(Babble b : rebabbleList) System.out.println(b.getId()+ " is in rebabblelist");
            rstore.complete();
            rstore.close();
        }

        if(rebabble!=null && !rebabble.isEmpty()){
            System.out.println("rebabble");
            rstore = new RebabbleStore();
            rstore.insertRebabble(bs.getUsername(), b.getId());
            //test
            List<Babble> rebabbleList = rstore.userRebabbledBabble(bs.getUsername(), b);
            for(Babble b : rebabbleList) System.out.println(b.getId()+ " is in rebabblelist");
            rstore.complete();
            rstore.close();
        }

        //test
//        lstore = new LikeStore();
//        List<LikesBabble> blist = lstore.userDislikedorLikedBabble(bs.getUsername(), b);
//        for(LikesBabble l : blist) System.out.println("type is: "+ l.getType());

        if(dislike!=null && !dislike.isEmpty()){
            lstore = new LikeStore();
            //check whether there is a like or dislike already in table
            String type = "";
            List<LikesBabble> list = lstore.userDislikedorLikedBabble(bs.getUsername(), b);
            for(LikesBabble l : list){
                type = l.getType();
            }

            //if type ==null, then no entry exists => dislike this babble
            if(list.isEmpty()) {
                lstore.insertLike(bs, b.getId(), "dislike");
                System.out.println("list is empty");
            }
            else{
                System.out.println("else...");
                System.out.println( Arrays.toString( type.toCharArray()));
                if(type.equals("dislike")) {
                    System.out.println("type = dislike");
                    //lstore.updateLike(bs.getUsername(), b.getId(), "like");
                    lstore.removeLike(bs.getUsername(), b.getId());
                }
                else if(type.equals("like   ")){
                    System.out.println("type = like");
                    lstore.updateLike(bs.getUsername(), b.getId(), "dislike");
                }


                //else do nothing becasue entry is already a dislike
            }
            lstore.complete();
            lstore.close();
        }

        if(like!=null && !like.isEmpty()){
            lstore = new LikeStore();
            List<LikesBabble> list = lstore.userDislikedorLikedBabble(bs.getUsername(), b);
            String type = null;
            for(LikesBabble l : list) type = l.getType();
            //if type ==null, then no entry exists => dislike this babble
            if(type == null) lstore.insertLike(bs, b.getId(), "like");
            else{
                //type = dislike => change dislike to like
                if(type.equals("dislike"))
                    lstore.updateLike(bs.getUsername(), b.getId(), "like");
                else if(type.equals("like   "))
                    lstore.removeLike(bs.getUsername(), b.getId());

            }
            lstore.complete();
            lstore.close();
        }

//        //test
//        lstore = new LikeStore();
//        List<LikesBabble> list = lstore.userDislikedorLikedBabble(bs.getUsername(), b);
//        for(LikesBabble l : list) System.out.println("type is: "+ l.getType());
//        lstore.complete();
//        lstore.close();






        doGet(request, response);
    }
}
