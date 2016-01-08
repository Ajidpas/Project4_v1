/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.DAODirector;
import model.dao.ENUMEntity;
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        DAODirector director = new DAODirector(ENUMEntity.MEAL);
        int id = Integer.parseInt(request.getParameter("mealId"));
        Meal meal = (Meal) director.getEntityById(id);
        String name;
        String price;
        String description;
        if (meal == null || id < 1) {
            name = "No information!";
            price = "No information!";
            id = 0;
            description = "No information!";
        } else {
            name = meal.getName();
            price = meal.getPrice().toString();
            id = meal.getId();
            description = meal.getDescription();
        }
        request.setAttribute("id", id);
        request.setAttribute("name", name);
        request.setAttribute("price", price);
        request.setAttribute("description", description);
        request.getRequestDispatcher("/Meal.jsp").forward(request, response);
    }
}
