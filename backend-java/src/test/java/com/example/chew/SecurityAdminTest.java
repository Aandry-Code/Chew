package com.example.chew;

import org.springframework.security.test.context.support.WithMockUser;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "CHEW_ADMIN_USER=admin_prueba",
    "CHEW_ADMIN_PASSWORD=ClaveSoloParaPruebas123"
})
@AutoConfigureMockMvc
class SecurityAdminTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void permitirConsultarMenuSinAutenticacion()
            throws Exception {

        mockMvc.perform(
                get("/api/platos/activos")
        )
                .andExpect(status().isOk());
    }

    @Test
    void rechazarRegistroSinAutenticacion()
            throws Exception {

        mockMvc.perform(
                post("/api/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void permitirAccesoDelAdministrador()
            throws Exception {

        String credenciales
                = "admin_prueba:ClaveSoloParaPruebas123";

        String autorizacion = "Basic "
                + Base64.getEncoder().encodeToString(
                        credenciales.getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        mockMvc.perform(
                post("/api/platos")
                        .header("Authorization", autorizacion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "LECTOR")
    void rechazarUsuarioSinRolAdministrador()
            throws Exception {

        mockMvc.perform(
                post("/api/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{")
        )
                .andExpect(status().isForbidden());
    }

}
