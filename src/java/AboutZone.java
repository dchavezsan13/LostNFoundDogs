
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniChavez
 */
public class AboutZone extends HttpServlet{
    
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter out=response.getWriter();
            String description=request.getParameter("description_zone");
            Zone myZone=new Zone(description);
            myZone.insertZoneDB(out);
        } catch (IOException ex) {
            Logger.getLogger(AboutZone.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter out=response.getWriter();
            response.setContentType("text/html");
            String form="<form method='post' action='AboutZone'>"
                    + "Type a description please:<br>"
                    + "<input type='text' name='description_zone'><br>"
                    + "<input type='submit' value='Create zone'>"
                    + "</form>";
            out.println(form);
        } catch (IOException ex) {
            Logger.getLogger(AboutZone.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
