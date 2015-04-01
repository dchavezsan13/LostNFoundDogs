import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Person extends HttpServlet {

    private String name;
    private String lastName;
    private int age;
    private int zoneResidence;
    private String phone;
    private String email;

    public Person(){
    }
    
    public Person(String name, String lastName, int age, int zoneResidence, String phone, String email) throws ServletException {
        
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.zoneResidence = zoneResidence;
        this.phone = phone;
        this.email = email;
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Person person=new Person();
            person.setZoneResidence(Integer.parseInt(request.getParameter("zone")));
            person.setName(request.getParameter("name_person"));
            person.setLastName(request.getParameter("last_name"));
            person.setEmail(request.getParameter("email_person"));            
            person.setPhone(request.getParameter("cellphone_person"));
            person.insertPersonDB(response);
        } catch (IOException ex) {
            out=response.getWriter();
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            out.println("exception io "+ex.getMessage());
        } finally {
            out.println("hi im at finally");
            out.close();
            
        }
        
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();        
        String form="<form method=post action=Person>"
                + "Write the residence zone (1-5)<br> "
                + "<input type=text name=zone><br>"
                + "Write person's name: <br>"
                + "<input type=text name=name_person><br>"
                + "Person's last name:<br>"
                + "<input type=text name=last_name><br>"
                + "person's mail<br>"
                + "<input type=text name=email_person><br>"
                + "Cellphone<br>"
                + "<input type=text name=cellphone_person><br>"
                + "<input type=submit value='Register person'>"
                + "</form>";
        out.println(form);
    }
    
    
    public void insertPersonDB(HttpServletResponse response) throws IOException {
        Connection myCon = MyConection.getConectionToDB();

        try {
            Statement statement = myCon.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultset = statement.executeQuery("select * from person");
            resultset.moveToInsertRow();
            resultset.updateString("name_person", this.getName());
            resultset.updateInt("zone_residence", this.getZoneResidence());
            resultset.updateString("second_name_person", this.getLastName());
            resultset.updateString("email_person", this.getEmail());
            resultset.updateString("cellphone_person", String.valueOf(this.getPhone()));
            resultset.insertRow();
            resultset.close();
            statement.close();
            myCon.close();
PrintWriter out=response.getWriter();
out.println("person has been inserted");
        } catch (SQLException e) {
            PrintWriter out = response.getWriter();
            out.println("SQL Exception, the person could not be created " + e.getMessage());
        }

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the zoneResidence
     */
    public int getZoneResidence() {
        return zoneResidence;
    }

    /**
     * @param zoneResidence the zoneResidence to set
     */
    public void setZoneResidence(int zoneResidence) {
        this.zoneResidence = zoneResidence;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
}
