package com.lendbiz.p2p.api.configs;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public RestAuthenticationEntryPoint restServicesEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtEntryPoint entryPoint;

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new PasswordEncoder() {
    // @Override
    // public String encode(CharSequence charSequence) {
    // return getMd5(charSequence.toString());
    // }

    // @Override
    // public boolean matches(CharSequence charSequence, String s) {
    // return getMd5(charSequence.toString()).equals(s);
    // }
    // };
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers("/lendbiz/** ")
                .permitAll().antMatchers("/partner/api/**").hasAnyRole("PARTNER","ADMIN")
//                .antMatchers("/lendbiz/api/9pay/**").hasRole("PARTNER")
//                .antMatchers("/lendbiz/api/**").hasRole("ADMIN")
                .and().exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);
            return null;
        }
    }
}
