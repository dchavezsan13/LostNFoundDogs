
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniChavez
 */
public class Zone {
 private int idZone;
 private String descriptionZone;

    public Zone(String descriptionZone) {
        this.descriptionZone = descriptionZone;
    }

    
    public void insertZoneDB(PrintWriter out){
    Connection myCon=MyConection.getConectionToDB();
     try {
         Statement statement=myCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
         ResultSet resultset=statement.executeQuery("select * from zone");
         resultset.moveToInsertRow();
         resultset.updateString("description_zone", this.getDescriptionZone());
         resultset.insertRow();
         out.println("Good news: The new zone has been created");
     } catch (SQLException ex) {
         out.println("Houston we have problems at inserZoneDB:Zone message: "+ex.getMessage()+" sqlState: "+ex.getSQLState());
         Logger.getLogger(Zone.class.getName()).log(Level.SEVERE, null, ex);
     }
    
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

    /**
     * @return the descriptionZone
     */
    public String getDescriptionZone() {
        return descriptionZone;
    }

    /**
     * @param descriptionZone the descriptionZone to set
     */
    public void setDescriptionZone(String descriptionZone) {
        this.descriptionZone = descriptionZone;
    }
 
}
