package com.emailtosms;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // Pour proteger toutes les méthodes (DAO, Metier ...)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery(
						"select username as principal, password as credentials, etat from utilisateur where username = ?")
				.authoritiesByUsernameQuery("select username as principal, role as role from utilisateur where username = ?")
				.rolePrefix("ROLE_");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()

				.authorizeRequests().antMatchers("/build/**", "/dist/**", "/plugins/**", "/shared/**", "/service/refreshPassword")
				.permitAll() // Ressources à autoriser à tout le monde
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login") .failureUrl("/login").permitAll().defaultSuccessUrl("/")
				.and()
		        .exceptionHandling().accessDeniedPage("/403")
				.and().logout().invalidateHttpSession(true).logoutUrl("/logout")
				.permitAll();

	}
}
