/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import annotation.Annotation;
import etu.framework.Mapping;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author P15A-13-Hendry
 */
public class Fonction {
    public String[] getAllPackages(ServletContext context) {
        String basePath = context.getRealPath("/");
        String path = basePath.replace("\\build\\web\\", "");
        
        //String basePath = System.getProperty("user.dir");
        //String path = basePath.replace("\\build\\web\\", "");
        
        
        File rootDirectory = new File(path + "/src/java/");
        ArrayList<String> allPackage = collectPackages(rootDirectory, basePath);
        String[] st = new String[allPackage.size()];
        //String[] test = new String[allPackage.size()];
        
        for(int i = 0;i<allPackage.size();i++){
           st[i] = allPackage.get(i).substring(allPackage.get(i).indexOf(".") + 1);
        }
        
       
        return st;
    }
    
    public ArrayList<String> collectPackages(File directory, String packageName) {
        ArrayList<String> array = new ArrayList<>();
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String subPackageName = packageName + "." + file.getName();
                    array.addAll(collectPackages(file, subPackageName));
                    array.add(subPackageName);
                } 
            }
        } 
        return array;
       
    }
    
    public HashMap<String,Mapping> insertMapping(String[] packageName,String annotationName,HashMap<String,Mapping> mappingUrls,PrintWriter out){
        mappingUrls = new HashMap<String,Mapping>();
        
        File file = null;
        java.net.URL[] resource = new java.net.URL[packageName.length];
        ArrayList<Class> array = new ArrayList<>();
        ArrayList<String> finaly = new ArrayList<>();
        

        try {
            
            ClassLoader cls = Thread.currentThread().getContextClassLoader();
            
            for(int i = 0;i<packageName.length;i++){
                resource[i] = cls.getResource(packageName[i].replace(".", "/"));
                
                file = new File(resource[i].getFile());
                
                
                if(file.exists()){
                    //System.out.println(resource[i].getFile());
                
                    String[] fileName = file.list();
                    
                    for(int j = 0;j<fileName.length;j++){
                        if(fileName[j].endsWith(".class")){
                            array.add(Class.forName(packageName[i].replace("\\.", "/") + "." +fileName[j].substring(0, fileName[j].length() - 6))); 
                        }    
                    }
                }
            }
            
                
            Class[] newCl = new Class[array.size()];
            array.toArray(newCl);
                
            for(int s = 0;s<newCl.length;s++){
                Method[] methods = newCl[s].getDeclaredMethods();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(Annotation.class)) {
                        Annotation annotation = method.getAnnotation(Annotation.class);
                        String uniqueKey = annotationName + "_" + UUID.randomUUID().toString();
                        //out.print("omenao "+annotationName);
        
                        
                        if(annotationName.equals(annotation.url())){
                            //out.print("omenao "+method.getName());
                            Mapping mapping = new Mapping();
                            mapping.setClassName(newCl[s].getName());
                            mapping.setMethod(method.getName());
                            
                            mappingUrls.put(uniqueKey, mapping);
                            
                        }
                    }
                }
            }
                 
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return mappingUrls;
    }
    
}
