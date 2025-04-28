package com.uniquindio.sebas.guia5.controllers;

import com.uniquindio.sebas.guia5.dtos.UserRegistration;
import com.uniquindio.sebas.guia5.dtos.UserResponse;
import com.uniquindio.sebas.guia5.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;

    // CURD User

    //Create
    @PostMapping
    public ResponseEntity<UserResponse>createUser(@RequestBody UserRegistration request){
        return ResponseEntity.ok(userServices.createUser(request));
    }

    //READ
    @GetMapping
    public ResponseEntity<UserResponse>getUser(@PathVariable String id) {
        Optional<UserResponse> user = userServices.getUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody UserRegistration request) {
        // Buscar el usuario por ID
        Optional<UserResponse> user = userServices.getUser(id);

        // Si el usuario existe, lo actualizamos
        if (user.isPresent()) {
            UserResponse updatedUser = userServices.updateUser(id, request);
            return ResponseEntity.ok(updatedUser); // Retornamos el usuario actualizado con el estado 200 OK
        } else {
            // Si el usuario no existe, retornamos un 404 Not Found
            return ResponseEntity.notFound().build();
        }


    }


    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        // Buscar el usuario por ID
        Optional<UserResponse> user = userServices.getUser(id);

        // Si el usuario existe, lo eliminamos
        if (user.isPresent()) {
            userServices.deleteUser(id);
            return ResponseEntity.noContent().build(); // Retornamos un 204 No Content después de eliminar
        } else {
            // Si el usuario no existe, retornamos un 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }


}