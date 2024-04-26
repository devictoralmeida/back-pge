package br.gov.ce.pge.mspgeoauth.security;

import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private final SecurityFilter securityFilter;

	private final CustomAuthenticationProviderService customAuthenticationProvider;
	
	public SecurityConfiguration(SecurityFilter securityFilter, CustomAuthenticationProviderService customAuthenticationProvider) {
		this.securityFilter = securityFilter;
		this.customAuthenticationProvider = customAuthenticationProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
					  .antMatchers(HttpMethod.POST, "/login/portal-divida").permitAll()	
				      .anyRequest().authenticated()		
				)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(customAuthenticationProvider)
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
