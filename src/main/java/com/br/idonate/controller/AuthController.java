package com.br.idonate.controller;

import com.br.idonate.dto.LoginRequestDTO;
import com.br.idonate.dto.RegisterRequestDTO;
import com.br.idonate.dto.ResponseDTO;
import com.br.idonate.model.Role;
import com.br.idonate.model.User;
import com.br.idonate.repository.RoleRepository;
import com.br.idonate.repository.UserRepository;
import com.br.idonate.security.TokenService;
import com.br.idonate.utils.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){

            User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
            if(passwordEncoder.matches(body.password(), user.getPassword())) {
                String token = this.tokenService.generateToken(user);
                ArrayList<RoleName> roles = getRolesToString(user.getRoles());

                kafkaTemplate.send("fromAuthController", roles.iterator().toString());

                return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token, roles));
            }
            return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUsername(body.name());

            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            newUser.getRoles().add(userRole);

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            List<RoleName> roles = getRolesToString(newUser.getRoles());

            return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token, roles));
        }
        return ResponseEntity.badRequest().build();
    }

    private ArrayList<RoleName> getRolesToString(Set<Role> roles) {
        ArrayList<RoleName> roleNames = new ArrayList<>();
        roles.forEach(role -> roleNames.add(role.getName()));
        return roleNames;
    }

//    @KafkaListener(topics = "fromAuthController", groupId = "grupo1")
//    public void listenGroupFoo(String message) {
//        System.out.println("Mensagem recebida=========\n" + message);
//    }


}
