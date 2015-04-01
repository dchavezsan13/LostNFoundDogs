import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
public class ReportLostDog extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        int idZone = Integer.parseInt(request.getParameter("idZone"));
        int ageLostDog = Integer.parseInt(request.getParameter("ageLostDog"));
        int idFinderPerson = Integer.parseInt(request.getParameter("idFinderPerson"));
        String nameLostDog = request.getParameter("nameLostDog");
        String breedLostDog = request.getParameter("breedLostDog");
        String sizeLostDog = request.getParameter("sizeLostDog");

        LostDog lostDog = new LostDog(idFinderPerson, idZone, nameLostDog, ageLostDog, breedLostDog, sizeLostDog);
        lostDog.insertDogDB(response);
        writer.println("THe lost dog has been inserted at db, check it out");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //here we have the form code, this is the desfult method to execute
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String form = "<form method='post' action=ReportLostDog>"
                + "<br>IdZone (1-5)<br>"
                + "<input type=text name=idZone>"
                + "<br>idFinderPerson <br>"
                + "<input type=text name=idFinderPerson>"
                + "<br>ageDog<br>"
                + "<input type=text name=ageLostDog>"
                + "<br>Name lost dog<br>"
                + "<input type=text name=nameLostDog>"
                + "<br>Breed lost dog<br>"
                + "<input type=text name=breedLostDog>"
                + "<br>size lost dog<br>"
                + "<input type=text name=sizeLostDog>"
                + "<input type=submit text=enviar>"
                + "</form>";
        out.println(form);

    }

}
