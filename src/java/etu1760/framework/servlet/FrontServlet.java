/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1760.framework.servlet;

import etu.framework.Mapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.codegen.CompilerConstants.className;
import modelView.ModelView;
import util.Fonction;

/**
 *
 * @author P15A-13-Hendry
 */
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> mappingUrls;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String url = request.getRequestURI();
            int last = url.lastIndexOf("/");
            String annotation = url.substring(last+1);
            
            ServletContext servletContext = getServletContext();
            
            Fonction fonction = new Fonction();
            
            String[] allPack = fonction.getAllPackages(servletContext);
            HashMap<String,Mapping> hashmap = fonction.insertMapping(allPack, annotation, mappingUrls,out);
            
            try{
            for(Map.Entry<String,Mapping> mapp:hashmap.entrySet()){
                 if(mapp.getKey().equals(annotation)){
                        String className = mapp.getValue().getClassName();
                        Class<?> clazz = Class.forName(className);
                        Object instance = clazz.newInstance();
            
                        Class<?> myClass = instance.getClass();
                        Method m = clazz.getMethod(mapp.getValue().getMethod());
                    
                        Class<?> typeRetour = m.getReturnType();
                    
                        String retour = typeRetour.getName();
                    
                        if(retour.equals("modelView.ModelView")){
                            ModelView mv = (ModelView)m.invoke(instance);
                            
                            for(Map.Entry<String,Object> me:mv.getData().entrySet()){
                                request.setAttribute("nom", me.getValue());
                            }
                            
                            RequestDispatcher rd = request.getRequestDispatcher(mv.getUrl());
                            rd.forward(request, response);
                        }
                     
                    
                 }
            }
            }catch(Exception ex){
                Throwable cause = ex.getCause();
                out.print(cause);
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
