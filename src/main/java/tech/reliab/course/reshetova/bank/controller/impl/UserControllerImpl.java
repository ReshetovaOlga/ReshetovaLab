package tech.reliab.course.reshetova.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.reshetova.bank.controller.UserController;
import tech.reliab.course.reshetova.bank.entity.User;
import tech.reliab.course.reshetova.bank.model.UserModel;
import tech.reliab.course.reshetova.bank.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<User> createUser(UserModel userModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userModel));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(int id, String name) {
        return ResponseEntity.ok(userService.updateUser(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(int id) {
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
