import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

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
public class ReportFoundDog extends HttpServlet {

    //algorithm to inser a found dog at db
    //insert at found dog
    //insert at person_found_dog
    //insert at features_tag_found_dog
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out;
        try {
            out = response.getWriter();
            int idShelter = Integer.parseInt(request.getParameter("id_shelter"));
            int idZone = Integer.parseInt(request.getParameter("id_zone"));
            String breed = request.getParameter("breed");
            String size = request.getParameter("size");
            int idFinderPerson = Integer.parseInt(request.getParameter("id_person"));
            String featuresTags = request.getParameter("tags");
            
            FoundDog somebodyDog = new FoundDog(idFinderPerson, idShelter, idZone, new Date(1), breed, size, featuresTags);
            somebodyDog.insertFoundDogDB(out);
            somebodyDog.insertPersonFoundDogDB(out);
            somebodyDog.insertFeaturesTagsFoundDogDB(out);
        } catch (IOException | SQLException ex) {
            out=response.getWriter();
            out.println("exception here sql "+ex.getMessage());
            Logger.getLogger(ReportFoundDog.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //int idFinderPerson, int idShelter, int idZone, Date foundDate, String breed, String size,String featuresTags

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println("<h1>Hi, here you can report a dog you found</h1>");
            String form = "<form method=post action=ReportFoundDog>"
                    + "Insert a shelter (1 - 5)<br>"
                    + "<input type=text name=id_shelter><br>"
                    + "Insert a zone (1-5)<br>"
                    + "<input type='text' name='id_zone'><br>"
                    + "Insert its breed <br>"
                    + "<input type='text' name='breed'><br>"
                    + "Insert its size <br>"
                    + "<input type='text' name='size'><br>"
                    + "Insert id of the finder person<br>"
                    + "<input type='text' name='id_person'><br>"
                    + "Here write with words adjetives o features tags<br>"
                    + "<input type='text' name='tags'><br>"
                    + "<input type='submit' value='Insert found dog'>";
            response.setContentType("text/html");

            out.println(form);
        } catch (IOException ex) {
            Logger.getLogger(ReportFoundDog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }

    }

}
