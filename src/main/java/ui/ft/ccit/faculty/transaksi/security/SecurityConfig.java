package ui.ft.ccit.faculty.transaksi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                
                // KARYAWAN dan ADMIN: akses semua
                .requestMatchers("/api/karyawan/**").hasAnyRole("ADMIN", "KARYAWAN")
                .requestMatchers("/api/pelanggan/**").hasAnyRole("ADMIN", "KARYAWAN")
                
                // PEMASOK: hanya GET pemasok
                .requestMatchers(HttpMethod.GET, "/api/pemasok/**").hasAnyRole("ADMIN", "KARYAWAN", "PEMASOK")
                .requestMatchers("/api/pemasok/**").hasAnyRole("ADMIN", "KARYAWAN")
                
                // PELANGGAN: GET jenis barang dan barang
                .requestMatchers(HttpMethod.GET, "/api/jenis-barang/**").hasAnyRole("ADMIN", "KARYAWAN", "PELANGGAN")
                .requestMatchers("/api/jenis-barang/**").hasAnyRole("ADMIN", "KARYAWAN")
                
                .requestMatchers(HttpMethod.GET, "/api/barang/**").hasAnyRole("ADMIN", "KARYAWAN", "PELANGGAN")
                .requestMatchers("/api/barang/**").hasAnyRole("ADMIN", "KARYAWAN")
                
                // TRANSAKSI: PELANGGAN bisa akses (filter di controller)
                .requestMatchers("/api/transaksi/**").hasAnyRole("ADMIN", "KARYAWAN", "PELANGGAN")
                .requestMatchers("/api/detail-transaksi/**").hasAnyRole("ADMIN", "KARYAWAN", "PELANGGAN")
                
                // Semua request lainnya harus authenticated
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
