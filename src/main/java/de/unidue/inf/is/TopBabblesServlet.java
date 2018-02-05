package de.unidue.inf.is;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.stores.BabbleStore;
import de.unidue.inf.is.stores.LikeStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopBabblesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BabbleStore store = null;
    private List<Babble> list = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("get top 5 most liked babbles from babblestore");
        store = new BabbleStore();
        list = store.searchTopBabbles();
        store.complete();
        store.close();

        //count likes
        LikeStore lstore = new LikeStore();
        lstore.complete();
        lstore.close();


        request.setAttribute("babblelist", list);

        request.getRequestDispatcher("top_babbles.ftl").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {



        doGet(request, response);
    }


}
