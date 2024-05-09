package com.WFHS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.WFHS.entity.Role;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.service.impl.OurUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private OurUserDetailService ourUserDetailService;
	
	@Autowired
    private UserLoginRepository userLoginRepo;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
        		.formLogin(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(
                                ((request, response, authentication) -> {
                                    response.sendRedirect("/users");
                                })
                        )
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(
                        logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/login?logout");
                        })
                        .invalidateHttpSession(true)
                        .permitAll()
                );
                
        return http.build();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Bean
    public UserDetailsService userDetailsService() {
        return staff_id -> {
            UserLogin user = userLoginRepo.findByStaffId(staff_id);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Role userRole = user.getRole();
            System.out.println("User staff-id: " + user.getStaffId());
            System.out.println("User email: " + user.getEmail());
            System.out.println("User roles: " + userRole.getName());
            return org.springframework.security.core.userdetails.User.withUsername(user.getStaffId())
                    .password(user.getPassword())
                    .roles(userRole.getName())
                    .build();
        };
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendRedirect("/login");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("error", "true");
            request.getSession().setAttribute("errorMessage", exception.getMessage());
            response.sendRedirect("/login");
        };
    }
    

}
