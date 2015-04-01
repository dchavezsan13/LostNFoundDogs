import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniChavez
 */
public class FeatureTag {

    private int idFeatureTag;
    private String descriptionTag;

    public FeatureTag(String descriptionTag) {
        this.descriptionTag = descriptionTag;
    }

    public void insertFeatureTagDB(PrintWriter out) throws SQLException {
        try {
            Connection myCon = MyConection.getConectionToDB();
            PreparedStatement thePrepared = myCon.prepareStatement("INSERT INTO features_tag(description)VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            thePrepared.setString(1, this.getDescriptionTag());
            int affectedRow = thePrepared.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("there was not any featured inserted");
            }
            ResultSet generatedKeys = thePrepared.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.setIdFeatureTag((int) generatedKeys.getLong(1));
                out.println("the featured tag was inserted at db");
            }

        } catch (SQLException e) {
            out.println("exception at inserFeatureTagDB method message: " + e.getMessage() + " sql state: " + e.getSQLState());
        }

    }

    /**
     * @return the idFeatureTag
     */
    public int getIdFeatureTag() {
        return idFeatureTag;
    }

    /**
     * @param idFeatureTag the idFeatureTag to set
     */
    public void setIdFeatureTag(int idFeatureTag) {
        this.idFeatureTag = idFeatureTag;
    }

    /**
     * @return the descriptionTag
     */
    public String getDescriptionTag() {
        return descriptionTag;
    }

    /**
     * @param descriptionTag the descriptionTag to set
     */
    public void setDescriptionTag(String descriptionTag) {
        this.descriptionTag = descriptionTag;
    }
}
