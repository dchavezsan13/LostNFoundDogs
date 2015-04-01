import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LostDog extends Dog {

    private int idOwner;
    private int idZone;

    public LostDog(int idOwner, int idZone, String nameLostDog, int age, String breed, String size) {
        this.setIdOwner(idOwner);
        this.setIdZone(idZone);
        this.setName(nameLostDog);
        this.setAge(age);
        this.setBreed(breed);
        this.setSize(size);

    }

    public void insertDogDB(HttpServletResponse response) throws IOException {
        Connection myConection = MyConection.getConectionToDB();

        try {
            PrintWriter out = response.getWriter();
            
             //myConection = DriverManager.getConnection("jdbc:mysql://localhost/perros", "root", "");
            out.println("here i am");
            Statement statement = myConection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultset = statement.executeQuery("select * from lost_dog");
            resultset.moveToInsertRow();
            
            resultset.updateString("name_lost_dog", getName());
            resultset.updateInt("age", getAge());
            resultset.updateString("breed", getBreed());
            resultset.updateString("size", getSize());
            resultset.updateInt("id_person", this.getIdOwner());
            resultset.updateInt("id_zone", this.getIdZone());

            resultset.insertRow();
        } catch (SQLException e) {
             PrintWriter out = response.getWriter();
            out.println("here i am at exception");
        } 

    }

    /**
     * @return the idOwner
     */
    public int getIdOwner() {
        return idOwner;
    }

    /**
     * @param idOwner the idOwner to set
     */
    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    /**
     * @return the idZone
     */
    public int getIdZone() {
        return idZone;
    }

    /**
     * @param idZone the idZone to set
     */
    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }
}
