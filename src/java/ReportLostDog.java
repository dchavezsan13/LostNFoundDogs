import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

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

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 1000 * 1024;// el numero que multiplica a 1024 son kb
    private int maxMemSize = 100 * 1024;//
    private File file;

    public void init() {
        //get the file location where it would be stored
        filePath = getServletContext().getInitParameter("file-upload");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int idZone = 0;
        int ageLostDog = 0;
        int idFinderPerson = 0;
        String nameLostDog = "";
        String breedLostDog = "";
        String sizeLostDog = "";

        //right now the form is multipart thus we put that in the form
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (!isMultipart) {//in this part we validate if effectively it is multipart
//in order to be able to upload the image, our form has to be multipart
            //if not, we send a message about the error.
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();//este es el objeto que crea el archivo en el servidor, this is like a cascaron of the object
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("/Users/daniChavez/NetBeansProjects/FoundNLostDogs/temp"));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            while (i.hasNext()) {//we iterate each element of the form.                
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {//case when it is the image
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath
                                + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath
                                + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    out.println("Uploaded Filename: " + fileName + "<br>");
                } else if (fi.isFormField()) {//case when is not the image but form element
                    //in this part we give value to each form element (not the image)
                    //as we can see, throug the switch we are able to set a value
                    //in each variable needed in order to create a lostDog object
                    switch (fi.getFieldName()) {
                        case "idZone":
                            idZone = Integer.parseInt(fi.getString());
                            break;
                        case "idFinderPerson":
                            idFinderPerson = Integer.parseInt(fi.getString());
                            break;
                        case "nameLostDog":
                            nameLostDog = fi.getString();
                            break;
                        case "ageLostDog":
                            ageLostDog = Integer.parseInt(fi.getString());
                            break;
                        case "breedLostDog":
                            breedLostDog = fi.getString();
                            break;
                        case "sizeLostDog":
                            sizeLostDog = fi.getString();
                            break;
                    }

                }

            }
//finally we create a lostDog object and we insert it in the db.
            LostDog lostDog = new LostDog(idFinderPerson, idZone, nameLostDog, ageLostDog, breedLostDog, sizeLostDog);
            lostDog.insertDogDB(response);
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            System.out.println("we are in the exception");
            System.out.println(ex);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //here we have the form code, this is the desfult method to execute
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String uploadForm = "<form action='ReportLostDog' method='post' enctype='multipart/form-data'>"
                + "<input type='file' name='dogImage' size='50' />"
                + "<br />"
                + "<input type='submit' value='Upload File' />"
                + "</form>";

        String form = "<form method='post' action=ReportLostDog enctype='multipart/form-data'>"
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
                + "<br>Upload a photography<br>"
                + "<input type='file' name='dogImage' size='50' /><br>"
                + "<input type=submit text=enviar>"
                + "</form>";
        out.println(form);

    }

}
