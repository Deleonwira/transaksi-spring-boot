package ui.ft.ccit.faculty.transaksi.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Cek username exists
        if (userDetailsService.existsByUsername(request.getUsername())) {
             return ResponseEntity.badRequest().body("Username already exists");
        }

        ui.ft.ccit.faculty.transaksi.pengguna.Pengguna pengguna = new ui.ft.ccit.faculty.transaksi.pengguna.Pengguna(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            "PELANGGAN" // Role for customer registration
        );
        
        userDetailsService.saveUser(pengguna, request.getIdPelanggan());

        return ResponseEntity.ok("Pelanggan registered successfully");
    }

    /**
     * Endpoint untuk generate BCrypt hash dari password plain text.
     * Gunakan ini untuk mendapatkan hash yang valid untuk disimpan di database.
     * HAPUS ENDPOINT INI DI PRODUCTION!
     */
    @GetMapping("/encode")
    public ResponseEntity<Map<String, String>> encodePassword(@RequestParam String password) {
        String encoded = passwordEncoder.encode(password);
        return ResponseEntity.ok(Map.of(
            "plainPassword", password,
            "encodedPassword", encoded
        ));
    }
}
