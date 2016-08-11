package com.panasonic.forcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner{
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
    public static void main(String[] args) throws Exception {

        SpringApplication.run(Application.class);
    }
    
    @Override
    public void run(String... strings) throws Exception {
    	ScheduledTasks st = new ScheduledTasks();
    	st.getWeatherForcast(jdbcTemplate);
    }
}
