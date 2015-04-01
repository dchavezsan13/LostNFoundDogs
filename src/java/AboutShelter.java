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
public class AboutShelter extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out;
        try {
            out = response.getWriter();
            int zoneShelter = Integer.parseInt(request.getParameter("zone_shelter"));
            String nameShelter = request.getParameter("name_shelter");
            String addressShelter = request.getParameter("address_shelter");
            String descriptionShelter = request.getParameter("description_shelter");

            Shelter miShelter = new Shelter(zoneShelter, nameShelter, addressShelter, descriptionShelter);
            miShelter.insertShelterDB(out);
        } catch (IOException ex) {
            
            Logger.getLogger(AboutShelter.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>About shelter, here you can register a new shelter</h1>");
            String form = "<form method='post' action='AboutShelter'>"
                    + "Type the shelter zone 1-5<br>"
                    + "<input type='text' name='zone_shelter'><br>"
                    + "TYpe the shelters name<br>"
                    + "<input type='text' name='name_shelter'><br>"
                    + "Type the shelter address<br>"
                    + "<input type='text' name='address_shelter'><br>"
                    + "TYpe a description<br>"
                    + "<input type='text' name='description_shelter'><br>"
                    + "<input type='submit' value='Register shelter'>"
                    + "</form>";
            out.println(form);
        } catch (IOException ex) {
            Logger.getLogger(AboutShelter.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}
