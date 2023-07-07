/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelView;

import java.util.HashMap;

/**
 *
 * @author P15A-13-Hendry
 */
public class ModelView {
    String url;
    HashMap<String,Object> data;

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void addItem(String key,Object value){
        this.data = new HashMap<String,Object>();
        this.data.put(key, value);
    }
    
    
}
