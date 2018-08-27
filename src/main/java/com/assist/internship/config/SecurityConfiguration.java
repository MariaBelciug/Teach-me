package com.assist.internship.config;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
                auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource);
//                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

     /*   http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()          // POST - Login User (user and pass in body)
                .antMatchers("/reset").permitAll()          // POST - Change User Password (user and pass in body)
                .antMatchers("/create/user").permitAll()    // POST - New User (data in body)
                .antMatchers("/users").permitAll()          // GET - All Users
                .antMatchers("/user").permitAll()           // PUT/GET - Change details of user by ID or email (first, last names, email)
                .antMatchers("/create/category").permitAll()        // POST - New Category
                .antMatchers("/categories").permitAll()             // GET - All Categories
                .antMatchers("/create/course").permitAll()          // POST - New User
                .antMatchers("/courses").permitAll()                // GET - Courses by Category ID
                .antMatchers("/course").permitAll()                 // GET - Courses by ID
                .antMatchers("/create/chapter").permitAll()         // POST - New Chapter
                .antMatchers("/chapters").permitAll()               // GET - Chapters bu Course ID
                .antMatchers("/chapter").permitAll()                // GET - Chapters by ID
                .antMatchers("/create/question").permitAll()        // POST - New Question
                .antMatchers("/questions").permitAll()              // GET - Questions by Chapter ID
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/admin/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");*/

        http.authorizeRequests()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(POST, "/logout").permitAll()
                .antMatchers(POST, "/create/user").permitAll()
                .antMatchers(GET, "/users").permitAll()
                .antMatchers(GET, "/user").permitAll()
                .antMatchers(PUT, "/user").permitAll()
                .antMatchers(POST, "/reset").permitAll()
                .antMatchers(GET, "/categories").permitAll()
                .antMatchers(POST, "/create/category").permitAll()
                .antMatchers(POST, "/create/course").permitAll()
                .antMatchers(GET, "/courses").permitAll()
                .antMatchers(GET, "/course").permitAll()
                .antMatchers(POST, "/create/chapter").permitAll()
                .antMatchers(GET, "/chapters").permitAll()
                .antMatchers(GET, "/chapter").permitAll()
                .antMatchers(POST, "/create/question").permitAll()
                .antMatchers(GET, "/questions").permitAll()
                .antMatchers(PUT, "/category").permitAll()
                .antMatchers(PUT, "/course").permitAll()
                .antMatchers(PUT, "/chapter").permitAll()
                .antMatchers(PUT, "/question").permitAll()
                .antMatchers(PUT, "/answer").permitAll()
                .antMatchers(DELETE, "/user").permitAll()
                .antMatchers(DELETE, "/category").permitAll()
                .antMatchers(DELETE, "/course").permitAll()
                //.antMatchers(GET, "/login").permitAll()   //.hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()//allow CORS option calls
                .antMatchers("/resources/**").permitAll()
                .anyRequest().fullyAuthenticated() //add for default logout
                .and().httpBasic()
                .and().logout().disable()
                .csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
