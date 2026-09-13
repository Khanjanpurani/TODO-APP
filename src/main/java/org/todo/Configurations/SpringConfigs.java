package org.todo.Configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.todo.pojos.Note;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringConfigs {


    //HERE WE HAVE TO CREATE CONFIG AS SPRING BOOT DOES NOT KNOW TO CREATE AND MAINTAIN OBJECT OF LIST AS WE CAN NOT WRITE @Component on List class as it is inside java jar
    @Bean
    public List<Note>notes(){
        return new ArrayList<>();
    }
}


