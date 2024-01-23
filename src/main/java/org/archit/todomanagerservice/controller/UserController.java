package org.archit.todomanagerservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.val;
import org.archit.todomanagerservice.config.jwt.JwtUtils;
import org.archit.todomanagerservice.entity.Role;
import org.archit.todomanagerservice.entity.User;
import org.archit.todomanagerservice.entity.UserRole;
import org.archit.todomanagerservice.model.JwtResponse;
import org.archit.todomanagerservice.model.LoginRequest;
import org.archit.todomanagerservice.model.SignUpRequest;
import org.archit.todomanagerservice.repository.RoleRepository;
import org.archit.todomanagerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "TO-DO Manager", description = "TO-DO Manager APIs")
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        val authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        val jwt = this.jwtUtils.generateJwtToken(authentication);

        val user = (User) authentication.getPrincipal();
        val roles = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer",
            user.getId(),
            user.getUsername(),
            roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (this.userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body("Error: Username is already taken!");
        }

        val user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(this.encoder.encode(signUpRequest.getPassword()));

        val strRoles = signUpRequest.getRoles();
        val roles = new HashSet<Role>();

        if (strRoles == null) {
            val userRole = this.roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        val adminRole = this.roleRepository.findByName(UserRole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        val userRole = this.roleRepository.findByName(UserRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        this.userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
