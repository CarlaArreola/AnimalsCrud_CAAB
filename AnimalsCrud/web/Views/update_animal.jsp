<%-- 
    Document   : update_animal
    Created on : 30/09/2024, 01:23:20 AM
    Author     : carla
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="configuration.ConnectionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Animal</title> 
    </head> 
    <body> 
        <h2>Actualizar Animal</h2> 
        <%
            String id = request.getParameter("id");
            String color = "";
            String especie = "";
            String tipo = "";  
            String alimentacion = "";  
            double peso = 0.0;
            String habitat = "";  
            int altura = 0;  
            ConnectionBD conexion = new ConnectionBD();
            Connection connection = conexion.getConnectionBD();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                // Consulta para obtener los datos del animal por ID 
                String sql = "SELECT color, especie,tipo, alimentacion, peso, habitat, altura FROM animales WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    color = resultSet.getString("color");
                    especie = resultSet.getString("especie");
                    tipo = resultSet.getString("tipo");  
                    alimentacion = resultSet.getString("alimentacion");  
                    peso = resultSet.getDouble("peso");
                    habitat = resultSet.getString("habitat");  
                    altura = resultSet.getInt("altura");  
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %> 

        <!-- Formulario con los datos del animal para actualizar --> 
        <form id="formActualizarAnimal"> 
            COLOR: <br>
            <input type="text" id="txt_color" value="<%= color%>" ><br>
            ESPECIE: <br>
            <input type="text" id="txt_especie" value="<%= especie%>" ><br>
            TIPO: <br>  <!-- Cambiado -->
            <input type="text" id="txt_tipo" value="<%= tipo%>" ><br> 
            ALIMENTACIÓN: <br>  
            <input type="text" id="txt_alimentacion" value="<%= alimentacion%>" ><br> 
            PESO: <br>
            <input type="number" id="txt_peso" value="<%= peso%>" step="0.01"><br>
            HABITAT: <br> 
            <input type="text" id="txt_habitat" value="<%= habitat%>" ><br>  
            ALTURA: <br>  
            <input type="number" id="txt_altura" value="<%= altura %>" step="1"><br> 
            <input type="button" value="Actualizar" onclick="actualizarAnimal()"> 
        </form> 
        <div id="resultado"></div> 
        <a href="${pageContext.request.contextPath}/Animal">Ver Animales</a>

        <script>
        function actualizarAnimal() {
            const color = document.getElementById("txt_color").value;
            const especie = document.getElementById("txt_especie").value;
            const tipo = document.getElementById("txt_tipo").value;  
            const alimentacion = document.getElementById("txt_alimentacion").value;  
            const peso = document.getElementById("txt_peso").value;
            const habitat = document.getElementById("txt_habitat").value; 
            const altura = parseInt(document.getElementById("txt_altura").value);  
            const id = "<%= id %>";

            const datos = {
                color: color,
                especie: especie,
                tipo: tipo, 
                alimentacion: alimentacion,  
                peso: peso,
                habitat: habitat, 
                altura: altura,  
                id: id
            };

            fetch(`${pageContext.request.contextPath}/Animal`, {
                method: "PUT",
                body: JSON.stringify(datos),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.text())
            .then(data => {
                document.getElementById("resultado").innerText = data;
            })
            .catch(error => {
                document.getElementById("resultado").innerText = "Error al actualizar el animal.";
                console.error('Error:', error);
            });
        }
        </script> 
    </body> 
</html>
