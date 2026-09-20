package org.todo.Configurations;


import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfigs {


    //HERE WE HAVE TO CREATE CONFIG AS SPRING BOOT DOES NOT KNOW TO CREATE AND MAINTAIN OBJECT OF LIST AS WE CAN NOT WRITE @Component on List class as it is inside java jar
//    @Bean
//    public List<Note>notes(){
//        return new ArrayList<>();
//    }

    //By default jetty does not show h2 ui so added this config
    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2Console() {
        ServletRegistrationBean<JakartaWebServlet> bean =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        bean.addInitParameter("webAllowOthers", "true");
        return bean;
    }
}


