<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<html>
    <head>
        <title>Welcome to lost & found dogs</title>
    </head>
    <body>
        <h1>Hi, This is the main page of lost & found dogs</h1>
        <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
                           url="jdbc:mysql://localhost/perros"
                           user="root"  password=""/>

        <sql:query dataSource="${snapshot}" var="result">
            SELECT * from lost_dog;
        </sql:query>

        <table border="1" width="50%">
            <tr><td colspan="4">Last lost dogs</td></tr>
            <tr>
                <th> ID</th>
                <th> Name</th>
                <th>Breed</th>
                <th>Size</th>
            </tr>
            <%
                out.println("Todays date is; ");
            %>      
            <%= (new java.util.Date()).toLocaleString()%>                                   

            <c:forEach var="row" items="${result.rows}">
                <tr>
                    <td><c:out value="${row.id_lost_dog}"/></td>
                    <td><c:out value="${row.name_lost_dog}"/></td>
                    <td><c:out value="${row.breed}"/></td>
                    <td><c:out value="${row.size}"/></td>
                </tr>
            </c:forEach>                
        </table>

        <h2>Sections:</h2>
        <br>
        <a href="./Person">Im a person, I want a register </a>
        <br>
        <a href="./ReportLostDog">My dog is lost</a>
        <br>
        <a href="./ReportFoundDog">I found a dog, Who is the owner?</a>
        <br>
        <a href="./AboutShelter">I`m a shelter, register me please</a>

        <br>
        <a href="./AboutZone">Create a zone of the city</a>

    </body>
</html>