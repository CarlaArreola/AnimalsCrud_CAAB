/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import configuration.ConnectionBD;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AnimalModel;

/**
 *
 * @author carla
 */
@WebServlet(name = "Animal", urlPatterns = {"/Animal"})
public class Animal extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Animal</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Animal at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Se ejecuta el doGet");
        ConnectionBD conexion = new ConnectionBD();
        List<AnimalModel> listaAnimales = new ArrayList<>();
        String sql = "SELECT id, color, especie, tipo, alimentacion, peso, habitat, altura FROM animales";

        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AnimalModel animal = new AnimalModel();
                animal.setId(rs.getInt("id"));
                animal.setColor(rs.getString("color"));
                animal.setEspecie(rs.getString("especie"));
                animal.setTipo(rs.getString("tipo"));
                animal.setAlimentacion(rs.getString("alimentacion"));
                animal.setPeso(rs.getDouble("peso"));
                animal.setHabitat(rs.getString("habitat"));
                animal.setAltura(rs.getInt("altura")); 
                listaAnimales.add(animal);
            }
            request.setAttribute("animales", listaAnimales);
            request.getRequestDispatcher("/Views/show_animals.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los animales: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        String color = request.getParameter("txt_color");
        String especie = request.getParameter("txt_especie");
        String tipo = request.getParameter("txt_tipo"); 
        String alimentacion = request.getParameter("txt_alimentacion"); 
        String pesoStr = request.getParameter("txt_peso");
        String habitat = request.getParameter("txt_habitat"); 
        String alturaStr = request.getParameter("txt_altura");

        double peso = Double.parseDouble(pesoStr);
        int altura = Integer.parseInt(alturaStr); 

        try {
            String sql = "INSERT INTO animales (color, especie, tipo, alimentacion, peso, habitat, altura) VALUES (?, ?, ?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, color);
            ps.setString(2, especie);
            ps.setString(3, tipo); 
            ps.setString(4, alimentacion);
            ps.setDouble(5, peso);
            ps.setString(6, habitat); 
            ps.setInt(7, altura); 

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                request.setAttribute("mensaje", "Animal registrado con éxito!");
                request.getRequestDispatcher("/Views/result.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "Error al registrar el animal.");
                request.getRequestDispatcher("/Views/result.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("/Views/result.jsp").forward(request, response);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        String sql = "DELETE FROM animales WHERE id = ?";

        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();
        Gson gson = new Gson();
        AnimalModel animal = gson.fromJson(json, AnimalModel.class);

        String sql = "UPDATE animales SET color = ?, especie = ?, tipo = ?, alimentacion = ?, peso = ?, habitat = ?, altura = ? WHERE id = ?";
        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, animal.getColor());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getTipo()); 
            ps.setString(4, animal.getAlimentacion()); 
            ps.setDouble(5, animal.getPeso());
            ps.setString(6, animal.getHabitat());
            ps.setInt(7, animal.getAltura()); 
            ps.setInt(8, animal.getId());

            int filasActualizadas = ps.executeUpdate();
            response.setContentType("text/plain");
            if (filasActualizadas > 0) {
                response.getWriter().write("Animal actualizado exitosamente.");
            } else {
                response.getWriter().write("No se encontró el animal para actualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error al actualizar el animal.");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
