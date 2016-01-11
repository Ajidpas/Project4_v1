/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.DAODirector;
import model.dao.ENUMEntity;
import model.entity.DBEntity;
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // set authorization block
        if (request.getParameter("logout") != null) {
            logout(request, response);
        } else {
            setAuthorizationBlock(request, response);
        }
        // get all meal
        if (request.getParameter("menu") != null) {
            getAllMeal(request, response);
        }
        // find meal
        if (request.getParameter("findMeal") != null) {
            findMeal(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (request.getParameter("login") != null) {
            login(request, response);
        }
        if (request.getParameter("add") != null) {
            addMeal(request, response);
        }
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");    
        if (password.equals("admin123")) {
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            request.setAttribute("userName", name);
            request.getRequestDispatcher("/view/authorization/user.jsp").include(request, response);
            request.getRequestDispatcher("/index.jsp").include(request, response);
        } else {
            try (PrintWriter out = response.getWriter()) {
                request.getRequestDispatcher("/login.jsp").include(request, response);
                out.print("Sorry, user name or password error! Try again."); // input this expression to the jsp file
            }
        }
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("/view/authorization/guest.jsp").include(request, response);
        request.getRequestDispatcher("/index.jsp").include(request, response);
    }
    
    private void getAllMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAODirector director = new DAODirector(ENUMEntity.MEAL);
        List<DBEntity> meals = director.getAllEntities();
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/view/MainMenu.jsp").include(request, response);
    }
    
    private void findMeal(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
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
        request.getRequestDispatcher("/view/Meal.jsp").include(request, response);
    }
    
    private void addMeal(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //TODO: adding meal for current user
        try (PrintWriter out = response.getWriter()) {
            request.getRequestDispatcher("/view/Test.jsp").include(request, response);
                out.print("Meal with id = " + id + " was added!"); // TODO realisation
            }
    }
    
    private void setAuthorizationBlock(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        if (name != null) {
            request.setAttribute("userName", name);
            request.getRequestDispatcher("/view/authorization/user.jsp").include(request, response);
        } else {
            request.getRequestDispatcher("/view/authorization/guest.jsp").include(request, response);
        }
    }
}
