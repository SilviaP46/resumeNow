package com.app.demo;

import com.app.demo.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@SpringBootApplication
public class ResumeNowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeNowApplication.class, args);
    }
    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/register").permitAll()
                    .antMatchers(HttpMethod.GET, "/getJobs").permitAll()
//                    .antMatchers(HttpMethod.POST, "/users").permitAll()
                    .anyRequest().authenticated();



            /*http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()

                    .antMatchers(HttpMethod.GET,"/notification/**").permitAll()
                    .antMatchers(HttpMethod.PUT,"/notification/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/update/**").hasAuthority("USER_MANAGEMENT")
                    .antMatchers("/bugs/**").hasAuthority("BUG_MANAGEMENT")
                    .antMatchers(HttpMethod.GET, "/users").authenticated()
                    .antMatchers("/roles/**").hasAuthority("PERMISSION_MANAGEMENT")
                    .antMatchers("/users/**").hasAuthority("USER_MANAGEMENT")
                    .anyRequest().authenticated();*/
        }
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("puiacsilvia46@gmail.com");
        mailSender.setPassword("uahoasctqlhuonps");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
