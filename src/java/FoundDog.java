import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniChavez
 */
public class FoundDog extends Dog {

    private int idFinderPerson;
    private Date foundDate;
    private int idShelter;
    private int idZone;

    public FoundDog(int idFinderPerson, int idShelter, int idZone, Date foundDate, String breed, String size, String featuresTags) {
        this.setIdFinderPerson(idFinderPerson);
        this.setFoundDate(foundDate);
        this.setIdShelter(idShelter);
        this.setIdZone(idZone);
        this.setBreed(breed);
        this.setSize(size);
        this.setFeatures(featuresTags);
    }

    //algorithm to inser a found dog at db
    //insert at found dog
    //insert at person_found_dog
    //insert at features_tag_found_dog
    public void insertFoundDogDB(PrintWriter out) throws SQLException {
        Connection myCon = MyConection.getConectionToDB();

        //los siguientes son actualizaciones pero de la tabla person_found_dog
        PreparedStatement thePrepared = myCon.prepareStatement("insert INTO found_dog (id_shelter, id_zone, breed, size)VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        thePrepared.setInt(1, this.getIdShelter());
        thePrepared.setInt(2, this.getIdZone());
        thePrepared.setString(3, this.getBreed());
        thePrepared.setString(4, this.getSize());
        int affectedRows = thePrepared.executeUpdate();
        if (affectedRows == 0) {
            out.println("there was not affected any row");
            throw new SQLException("No one row was inserted");
        }

        try (ResultSet generatedKeys = thePrepared.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                out.println("hi, the key generated was " + generatedKeys.getLong(1));
                this.setIdDog((int) generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }

    }

    public void insertPersonFoundDogDB(PrintWriter out) throws SQLException {

        Connection myCon = MyConection.getConectionToDB();
        try {

            out.println("hi im at insert Person found dog");

            myCon.setAutoCommit(false);
            Statement statement = myCon.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultset = statement.executeQuery("select * from person_found_dog");
            resultset.moveToInsertRow();
            out.println("what we are going to insert is idFinder" + this.getIdFinderPerson() + " id dog: " + this.getIdDog());
            resultset.updateInt("id_person", this.getIdFinderPerson());
            resultset.updateInt("id_found_dog", this.getIdDog());
            resultset.updateDate("found_date", new Date(this.getFoundDate().getYear(), this.getFoundDate().getMonth(), this.getFoundDate().getDay()));

            resultset.insertRow();
            out.println("the info has been inserted at personfounddog");
            myCon.commit();
            out.println("after commit");
        } catch (SQLException e) {
            out.println("there have had an exception " + e.getMessage() + " sql: " + e.getSQLState());
            myCon.rollback();

        }

    }

    public void insertFeaturesTagsFoundDogDB(PrintWriter out) throws SQLException {

        try {
            StringTokenizer tokens = new StringTokenizer(this.getFeatures());
            String featureStr;
            while (tokens.hasMoreTokens()) {
                //for each token i have first to make a record in the table features_tag
                //then, i have to make a record at features_tag_found_dog

                featureStr = tokens.nextToken();
                out.println("my feature is " + featureStr);
                FeatureTag feature = new FeatureTag(featureStr);
                feature.insertFeatureTagDB(out);//here we get the idFeatureTag in order to be used later on.

                insertFeatureTagsFoundDogDB2(out, feature.getIdFeatureTag());

//                 ResultSet resultset = statement.executeQuery("select * from features_tag_found_dog");
//                resultset.moveToInsertRow();
//                
//                resultset.updateInt("id_feature_tag", feature.getIdFeatureTag());
//                resultset.updateInt("id_found_dog", this.getIdDog());
//
//                resultset.updateRow();
//            out.println("token " + tokens.nextToken());
            }
            out.println("the tags have been inserted as tags");

        } catch (SQLException e) {
            out.println("exception at inserFeaturesTagsFoundDogDB method message " + e.getMessage() + " state: " + e.getSQLState());
        }

    }

    public void insertFeatureTagsFoundDogDB2(PrintWriter out, int idFeature) throws SQLException {
        Connection myCon = MyConection.getConectionToDB();
        try {
            
            
            myCon.setAutoCommit(false);
            Statement statement = myCon.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultset = statement.executeQuery("select * from features_tag_found_dog");
            resultset.moveToInsertRow();
            out.println("what im goin to insert is "+idFeature+" id found dog "+this.getIdDog());
            resultset.updateInt("id_features_tag", idFeature);
            resultset.updateInt("id_found_dog", this.getIdDog());
            resultset.insertRow();
            
            out.println("it is supposed I inserted the feature tag and the iddog");
            myCon.commit();
        } catch (SQLException e) {
            out.println("there was an excpetion at insert..featureDogDB2 from FoundDog.java, message: " + e.getMessage() + " sql state: " + e.getSQLState());
            myCon.rollback();
        }

    }

    /**
     * @return the idFinderPerson
     */
    public int getIdFinderPerson() {
        return idFinderPerson;
    }

    /**
     * @param idFinderPerson the idFinderPerson to set
     */
    public void setIdFinderPerson(int idFinderPerson) {
        this.idFinderPerson = idFinderPerson;
    }

    /**
     * @return the foundDate
     */
    public Date getFoundDate() {
        return foundDate;
    }

    /**
     * @param foundDate the foundDate to set
     */
    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
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
