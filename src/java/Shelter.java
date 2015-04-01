import java.io.PrintWriter;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniChavez
 */
public class Shelter {

    private int idShelter;
    private int zoneShelter;
    private String nameShelter;
    private String addressShelter;
    private String descriptionShelter;

    public Shelter(int zoneShelter, String nameShelter, String addressShelter, String descriptionShelter) {
        this.zoneShelter = zoneShelter;
        this.nameShelter = nameShelter;
        this.addressShelter = addressShelter;
        this.descriptionShelter = descriptionShelter;
    }

    public void insertShelterDB(PrintWriter out) {
        try {
            Connection myCon = MyConection.getConectionToDB();
            Statement statement = myCon.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultset = statement.executeQuery("select * from shelter");
            resultset.moveToInsertRow();
            resultset.updateInt("zone_shelter", this.getZoneShelter());
            resultset.updateString("name_shelter", this.getNameShelter());
            resultset.updateString("address_shelter", this.getAddressShelter());
            resultset.updateString("description_shelter", this.getDescriptionShelter());
            resultset.insertRow();
            out.println("The shelter has been inserted correctly at DB");
        } catch (SQLException e) {
            out.println("sql exception at inserShelterDB:Shelter method, message: " + e.getMessage() + " sqlState: " + e.getSQLState());
        }
    }

    /**
     * @return the idShelter
     */
    public int getIdShelter() {
        return idShelter;
    }

    /**
     * @param idShelter the idShelter to set
     */
    public void setIdShelter(int idShelter) {
        this.idShelter = idShelter;
    }

    /**
     * @return the zoneShelter
     */
    public int getZoneShelter() {
        return zoneShelter;
    }

    /**
     * @param zoneShelter the zoneShelter to set
     */
    public void setZoneShelter(int zoneShelter) {
        this.zoneShelter = zoneShelter;
    }

    /**
     * @return the nameShelter
     */
    public String getNameShelter() {
        return nameShelter;
    }

    /**
     * @param nameShelter the nameShelter to set
     */
    public void setNameShelter(String nameShelter) {
        this.nameShelter = nameShelter;
    }

    /**
     * @return the addressShelter
     */
    public String getAddressShelter() {
        return addressShelter;
    }

    /**
     * @param addressShelter the addressShelter to set
     */
    public void setAddressShelter(String addressShelter) {
        this.addressShelter = addressShelter;
    }

    /**
     * @return the descriptionShelter
     */
    public String getDescriptionShelter() {
        return descriptionShelter;
    }

    /**
     * @param descriptionShelter the descriptionShelter to set
     */
    public void setDescriptionShelter(String descriptionShelter) {
        this.descriptionShelter = descriptionShelter;
    }

}
