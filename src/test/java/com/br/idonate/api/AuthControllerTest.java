package com.br.idonate.api;
import com.br.idonate.controller.AuthController;
import com.br.idonate.dto.LoginRequestDTO;
import com.br.idonate.dto.RegisterRequestDTO;
import com.br.idonate.dto.ResponseDTO;
import com.br.idonate.model.Role;
import com.br.idonate.model.User;
import com.br.idonate.repository.UserRepository;
import com.br.idonate.security.TokenService;
import com.br.idonate.utils.RoleName;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@TestPropertySource(properties = "server.port=8080")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository repository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenService tokenService;


    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assertNotNull(repository);
        assertNotNull(passwordEncoder);
        assertNotNull(tokenService);
    }

    @Test
    void testLoginSuccess() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@example.com", "password123");
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");
        user.setUsername("user");

        when(repository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("mockedToken");

        mockMvc.perform(post("/auth/login")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("mockedToken"));


    }

    @Test
    void testLoginUserNotFound() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("invalid@example.com", "password123");

        when(repository.findByEmail(loginRequest.email())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authController.login(loginRequest);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testRegisterSuccess() throws Exception{


        User user = new User();
        user.setEmail("eweqweq@example.com");
        user.setPassword("encodedPassword");
        user.setUsername("username");
        RegisterRequestDTO registerRequest = new RegisterRequestDTO(user.getUsername(), user.getEmail(), user.getPassword());

        when(repository.findByEmail(registerRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.password())).thenReturn("encodedPassword");
        when(tokenService.generateToken(any(User.class))).thenReturn("mockedToken");


        mockMvc.perform(post("/auth/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))  // Verificar se o JSON retornado contém o username correto
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("mockedToken"));  // Verificar se o JSON retornado contém o token correto
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("user@example.com", "password123", "username");
        User existingUser = new User();
        existingUser.setEmail("user@example.com");

        when(repository.findByEmail(registerRequest.email())).thenReturn(Optional.of(existingUser));

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(400, response.getStatusCodeValue());

        verify(repository, never()).save(any(User.class));
    }
}

