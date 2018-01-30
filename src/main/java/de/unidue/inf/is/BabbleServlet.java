package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.stores.BabbleStore;
import de.unidue.inf.is.stores.BabbleUserStore;
import de.unidue.inf.is.utils.DBUtil;



/**
 * Das könnte die Eintrittsseite sein.
 */
public final class BabbleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String username;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
  
	    final String databaseToCheck = "babble";
        //boolean databaseExists = DBUtil.checkDatabaseExists(databaseToCheck);

        boolean databaseExists = DBUtil.checkDatabaseExistsExternal(databaseToCheck);
        request.setAttribute("db2name", databaseToCheck);

        if (databaseExists) {
            request.setAttribute("db2exists", "vorhanden! Supi!");
        }
        else {
            request.setAttribute("db2exists", "nicht vorhanden :-(");
        }

        request.getRequestDispatcher("babble_start.ftl").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("dopost methode von BabbleServlet.java");


        //Get user input from website url: localhost:9072/babble
        username =  request.getParameter("username");
        System.out.println("input parameter from babble page is: " + username);


        if (username !=null  && !username.isEmpty() ) {
            //For session
            BabbleUserStore store = new BabbleUserStore(username);
            BabbleUser user = store.getBabbleUser();
            System.out.println("Did getBabbleUser method work: " );
            System.out.println(user.getUsername());

            //Sets the current session id as dbuser with a BabbleUser object as value
            HttpSession session = request.getSession();
            session.setAttribute("babbler", user);
            //System.out.println("What is the user's name (BabbleServlet)" + user.getUsername());

            doGet(request, response);
        }
    }

}
