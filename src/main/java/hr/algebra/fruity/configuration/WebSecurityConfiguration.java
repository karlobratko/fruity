package hr.algebra.fruity.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import hr.algebra.fruity.controller.AuthenticationController;
import hr.algebra.fruity.filter.CatchExceptionFilter;
import hr.algebra.fruity.filter.JwtAuthenticationFilter;
import hr.algebra.fruity.properties.JwtProperties;
import hr.algebra.fruity.properties.RegistrationTokenProperties;
import hr.algebra.fruity.properties.RsaKeyProperties;
import hr.algebra.fruity.repository.EmployeeRepository;
import java.util.List;
import lombok.val;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties({RsaKeyProperties.class, JwtProperties.class, RegistrationTokenProperties.class})
public class WebSecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(EmployeeRepository employeeRepository) {
    return username -> employeeRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Korisničko ime %s nije registrirano.".formatted(username)));
  }

  @Bean
  JwtEncoder jwtEncoder(RsaKeyProperties rsaKeyProperties) {
    val jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
  }

  @Bean
  public JwtDecoder jwtDecoder(RsaKeyProperties rsaKeyProperties) {
    return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    val authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public CorsConfiguration corsConfiguration() {
    val corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.setAllowedMethods(List.of("*"));
    corsConfiguration.setAllowCredentials(true);

    return corsConfiguration;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource(CorsConfiguration corsConfiguration) {
    val corsConfigurationSource = new UrlBasedCorsConfigurationSource();
    corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return corsConfigurationSource;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfiguration corsConfiguration, AuthenticationManager authenticationManager, AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, CatchExceptionFilter catchExceptionFilter) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers(AuthenticationController.Mappings.requestMapping + "/**").permitAll()
          .anyRequest().authenticated()
      )
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(catchExceptionFilter, LogoutFilter.class)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

}
