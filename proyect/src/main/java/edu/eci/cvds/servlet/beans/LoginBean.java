package edu.eci.cvds.servlet.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//import edu.eci.cvds.servlet.services.UserService;
//import edu.eci.cvds.servlet.model.Usuario;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
@ManagedBean
@SessionScoped
public class LoginBean {

    //@Autowired
    //UserService userService;

    private String adminName;
    private String passwd;
    public String getAdminName() {
        return this.adminName;
    }
    public void setUserName(String adminName) {
        this.adminName = adminName;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    /*public String enter(){
        String route = "welcome.xhtml";
        Usuario aux = userService.getUser(this.userName);
        if(aux != null){
            if(aux.getPassword().equals(this.passwd)){
                route = "guess.xhtml";
            }
        }

        return route;
    }
    */
}