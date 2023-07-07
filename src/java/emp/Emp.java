/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emp;

import annotation.Annotation;
import modelView.ModelView;

/**
 *
 * @author P15A-13-Hendry
 */
public class Emp {
    String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    @Annotation(url = "find.do")
    public ModelView testModel(){
        ModelView m = new ModelView();
        m.setUrl("/Data.jsp");
        
        m.addItem("nom", "Hendry");
        
        return m;
    }
    
}
