<%-- 
    Document   : register_animals
    Created on : 30/09/2024, 01:25:05 AM
    Author     : carla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Animales</title>
    </head>
    <body>
        <h1>Registrar Animal</h1>
        <form action="${pageContext.request.contextPath}/Animal" method="POST">

            COLOR: <br>
            <input type="text" name="txt_color"><br>
            ESPECIE: <br>
            <input type="text" name="txt_especie"><br>
            TIPO: <br> 
            <input type="text" name="txt_tipo"><br> 
            ALIMENTACIÓN: <br> 
            <input type="text" name="txt_alimentacion"><br> 
            PESO: <br>
            <input type="number" name="txt_peso" step="0.01"><br>
            HÁBITAT: <br> 
            <input type="text" name="txt_habitat"><br> 
            ALTURA: <br> 
            <input type="number" name="txt_altura" step="1"><br>
            <br>
            <input type="submit" value="Agregar">
        </form>
        <br>

        <a href="${pageContext.request.contextPath}/Animal">Mostrar Animales</a>
    </body>
</html>
