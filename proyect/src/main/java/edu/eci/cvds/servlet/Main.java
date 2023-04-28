package edu.eci.cvds.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import java.util.Arrays;
//import edu.eci.cvds.servlet.model.Usuario;
//import edu.eci.cvds.servlet.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import edu.eci.cvds.servlet.services.AdminService;
import edu.eci.cvds.servlet.model.Administrator;


@SpringBootApplication
public class Main {

    @Autowired
    AdminService adminService;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    ServletRegistrationBean jsfServletRegistration(ServletContext servletContext) {
        // spring boot only works if this is set
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

        // registration
        ServletRegistrationBean srb = new ServletRegistrationBean();
        srb.setServlet(new FacesServlet());
        srb.setUrlMappings(Arrays.asList("*.xhtml"));
        srb.setLoadOnStartup(1);
        return srb;
    }

    @Bean
    public CommandLineRunner addingAdmins(){
        return args -> {
            System.out.println("Adding Admins...");
            adminService.addAdmin(new Administrator("Liliana", "1234a"));
            System.out.println("the admins...");
            System.out.println(adminService.getAdmin("Liliana").toString());
        };
    }
}
