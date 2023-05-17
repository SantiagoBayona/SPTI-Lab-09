package edu.eci.cvds.servlet.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.cvds.servlet.model.Administrator;
import edu.eci.cvds.servlet.services.AdminService;

@Component
@ManagedBean
@ApplicationScoped
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
        this.adminName = "";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
}