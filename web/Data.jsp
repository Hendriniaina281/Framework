<%-- 
    Document   : Data
    Created on : 7 juil. 2023, 19:12:33
    Author     : P15A-13-Hendry
--%>

<%@page import="modelView.ModelView"%>

<%
    ModelView m = (ModelView)request.getAttribute("modelView");
    String nom = (String)request.getAttribute("nom");
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="find.do">
            <h1>Nom:<input type="text" name="nom"></h1>
            <input type="submit" value="Valider">
        </form>
        
        <p>nom:<% out.print(nom);%></p>
    </body>
</html>
