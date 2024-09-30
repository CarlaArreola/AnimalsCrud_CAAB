<%-- 
    Document   : result
    Created on : 30/09/2024, 01:25:19 AM
    Author     : carla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head> 
        <meta charset="UTF-8"> 
        <title>Resultado</title> 
    </head> 
    <body> 
        <h2>Resultado</h2> 
        <p><%= request.getAttribute("mensaje") %></p> 
        <a href="Views/register_animals.jsp">Volver al formulario</a> 
    </body> 
</html>
