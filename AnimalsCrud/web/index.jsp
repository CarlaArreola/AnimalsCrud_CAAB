<%-- 
    Document   : index
    Created on : 30/09/2024, 02:57:16 AM
    Author     : carla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de Inicio</title>
    </head>
    <body>
        <h1>Animales</h1>
        <a href="${pageContext.request.contextPath}/Views/register_animals.jsp">Registrar Animal</a>
        <a href="${pageContext.request.contextPath}/Animal">Mostrar Animales</a>
    </body>
</html>
