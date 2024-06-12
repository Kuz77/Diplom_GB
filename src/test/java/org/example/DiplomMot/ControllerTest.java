package org.example.DiplomMot;

import org.example.DiplomMot.Model.Motorcycle;
import org.example.DiplomMot.Service.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service service;

    @Test
    public void testGetViewAsGuest() throws Exception {
        List<Motorcycle> motorcycles = Arrays.asList(new Motorcycle("Honda", "CBR", 2000000.0, "Sport", "image.png"));
        Mockito.when(service.getAllMotorcycles()).thenReturn(motorcycles);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("showroom"))
                .andExpect(model().attributeExists("motorcycles"))
                .andExpect(model().attribute("motorcycles", motorcycles));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetUserProfile() throws Exception {
        List<Motorcycle> motorcycles = Arrays.asList(new Motorcycle("Suzuki", "GSX-R1000", 18000.0, "Sport", "image.png"));
        Mockito.when(service.getAllMotorcycles()).thenReturn(motorcycles);

        mockMvc.perform(get("/user-profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("motorcycles"))
                .andExpect(model().attribute("motorcycles", motorcycles));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddMotorcycle() throws Exception {
        Motorcycle motorcycle = new Motorcycle("BMW", "S1000RR", 3220000.0, "Sport", "image.png");
        Mockito.when(service.createMotorcycle(motorcycle)).thenReturn(motorcycle);

        mockMvc.perform(post("/admin-profile")
                        .param("brand", "BMW")
                        .param("model", "S1000RR")
                        .param("price", "3220000.0")
                        .param("category", "Sport")
                        .param("image", "image.png"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-profile"));
    }
}
