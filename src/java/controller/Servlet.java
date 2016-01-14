/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        if (request.getParameter("select") != null) {
            dispatchers.addAll(setLanguage(request, response));
        } else {
            // set language combo box and authorization block
            dispatchers.addAll(setHead(request, response));
            
            // get all meal
            if (request.getParameter("menu") != null) {
                dispatchers.addAll(getAllMeal(request, response));
            }
            // find meal
//            if (request.getParameter("findMeal") != null) {
//                dispatchers.addAll(findMeal(request, response));
//            }
            if (dispatchers.size() <= 2) {
                dispatchers.add(request.getRequestDispatcher("/view/home.jsp"));
            }
        }
        executeAll(dispatchers, request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        
        // set language combo box and authorization block
        
        if (request.getParameter("login") != null) {
            dispatchers.addAll(login(request, response));
        } else if (request.getParameter("authorization") != null) {
            dispatchers.addAll(loginRequest(request, response));
        } else {
            dispatchers.addAll(setHead(request, response));
        }
//        if (request.getParameter("add") != null) {
//            addMeal(request, response);
//        }
        
        executeAll(dispatchers, request, response);
    }
    
    private List<RequestDispatcher> login(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        String name = request.getParameter("name");
        String password = request.getParameter("password");    
        if (password.equals("admin123")) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", name);
            dispatchers.addAll((List<RequestDispatcher>) session.getAttribute("dispatchersbeforelogin"));
        } else {
            try (PrintWriter out = response.getWriter()) {
                dispatchers.add(request.getRequestDispatcher("/login.jsp"));
                out.print("Sorry, user name or password error! Try again."); // input this expression to the jsp file
            }
        }
        return dispatchers;
    }
    
    private List<RequestDispatcher> loginRequest(HttpServletRequest request, HttpServletResponse response) {
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        HttpSession session = request.getSession();
        session.setAttribute("dispatchersbeforelogin", session.getAttribute("dispatchers"));
        dispatchers.add(request.getRequestDispatcher("/login.jsp"));
        return dispatchers;
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("/view/language.jsp").include(request, response);
        request.getRequestDispatcher("/view/guest/authorization.jsp").include(request, response);
        request.getRequestDispatcher("/view/home.jsp").include(request, response);
    }
    
    private List<RequestDispatcher> getAllMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        DAODirector director = new DAODirector(ENUMEntity.MEAL);
        List<DBEntity> meals = director.getAllEntities();
        request.getSession().setAttribute("meals", meals);
        dispatchers.add(request.getRequestDispatcher("/view/user/MainMenu.jsp"));
        return dispatchers;
    }
    
    private List<RequestDispatcher> findMeal(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        List<RequestDispatcher> dispatchers = new ArrayList<>();
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
        dispatchers.add(request.getRequestDispatcher("/view/Meal.jsp"));
        return dispatchers;
    }
    
    private RequestDispatcher addMeal(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        int id = Integer.parseInt(request.getParameter("id"));
        //TODO: adding meal for current user
        try (PrintWriter out = response.getWriter()) {
            dispatcher = request.getRequestDispatcher("/view/Test.jsp");
            dispatcher.include(request, response);
            out.print("Meal with id = " + id + " was added!"); // TODO realisation
        }
        return dispatcher;
    }
    
    private List<RequestDispatcher> setLanguage(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        return (List<RequestDispatcher>) request.getSession().getAttribute("dispatchers");
    }
    
    private List<RequestDispatcher> setAuthorizationBlock(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("userName");
        if (name != null) {
            dispatchers.add(request.getRequestDispatcher("/view/user/authorization.jsp"));
        } else {
            dispatchers.add(request.getRequestDispatcher("/view/guest/authorization.jsp"));
        }
        return dispatchers;
    }
    
    private void executeAll(List<RequestDispatcher> dispatchers, 
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
//        /* 
//         * check size, if (dispatchers.size() <= 2) - becouse after setHead() 
//         * method there will be 2 dispatchers: language and authorization block
//         */
//        if (dispatchers.size() <= 2) { 
//            dispatchers.add(request.getRequestDispatcher("/view/home.jsp"));
//        }
        
        // save last query and execute
        request.getSession().setAttribute("dispatchers", dispatchers);
        for (RequestDispatcher dispatcher : dispatchers) {
            dispatcher.include(request, response);
        }
    }
    
    private List<RequestDispatcher> setHead(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        List<RequestDispatcher> dispatchers = new ArrayList<>();
        dispatchers.add(request.getRequestDispatcher("/view/language.jsp"));
        // set authorization block
        if (request.getParameter("logout") != null) {
            logout(request, response);
        } else {
            dispatchers.addAll(setAuthorizationBlock(request, response));
        }
        return dispatchers;
    }
}
