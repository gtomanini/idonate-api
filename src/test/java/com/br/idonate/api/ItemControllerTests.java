package com.br.idonate.api;

import com.br.idonate.controller.AuthController;
import com.br.idonate.controller.ItemController;
import com.br.idonate.dto.ItemDTO;
import com.br.idonate.dto.RegisterRequestDTO;
import com.br.idonate.model.Item;
import com.br.idonate.model.User;
import com.br.idonate.repository.UserRepository;
import com.br.idonate.security.TokenService;
import com.br.idonate.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "server.port=8080")
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthController authController;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testListItems() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ItemDTO> items = new PageImpl<>(Collections.emptyList(), pageable, 0);
        Mockito.when(itemService.findItems(Mockito.any(), Mockito.eq(0), Mockito.eq(10))).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateUnauthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"} )
    public void testCreateAuthenticated() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("password");
        mockUser.setUsername("Gustavo");
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(mockUser.getUsername(), mockUser.getEmail(), mockUser.getPassword());
        authController.register(registerRequestDTO);

        String token = tokenService.generateToken(mockUser);

        mockMvc.perform(post("/items")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("{\"name\":\"Test Item teste\"}"))
                        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"} )
    public void testUpdate() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setName("Updated Item");

        Mockito.when(itemService.save(Mockito.any(Item.class))).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.put("/items")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Item\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"Updated Item\"}"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"} )
    public void testDelete() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");

        Mockito.when(itemService.delete(Mockito.any(Item.class))).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.delete("/items")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Test Item\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"Test Item\"}"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"} )
    public void testeRetorna() throws Exception{
        Mockito.when(itemService.retorna(Mockito.anyString())).thenReturn(Mockito.anyString());

        mockMvc.perform(MockMvcRequestBuilders.get("/testa")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());


    }
}
