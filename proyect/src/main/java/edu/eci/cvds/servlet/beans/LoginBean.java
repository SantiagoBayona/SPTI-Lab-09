package edu.eci.cvds.servlet.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

//import edu.eci.cvds.servlet.services.UserService;
//import edu.eci.cvds.servlet.model.Usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.cvds.servlet.services.AdminService;
import edu.eci.cvds.servlet.model.Administrator;

@Component
@ManagedBean
@SessionScoped
public class LoginBean {

    @Autowired
    AdminService adminService;

    private String adminName;
    private String passwd;

    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    public String enter(){
        String route = "login.xhtml";
        Administrator aux = adminService.getAdmin(this.adminName);
        if(aux != null){
            if(aux.getPassword().equals(this.passwd)){
                route = "private.xhtml?faces-redirect=true";
            }
        }

        return route;
    }

    public void signOut(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
}