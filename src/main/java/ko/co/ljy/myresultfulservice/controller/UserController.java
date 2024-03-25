package ko.co.ljy.myresultfulservice.controller;

import jakarta.validation.Valid;
import ko.co.ljy.myresultfulservice.domain.User;
import ko.co.ljy.myresultfulservice.exception.UserNotFoundException;
import ko.co.ljy.myresultfulservice.service.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<User> retrieveUser(@PathVariable("id") int id) {
        Optional<User> user = service.findOne(id);

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        Optional<User> deletedUser = service.deleteById(id);

        if(deletedUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/users")
//    public void createUser2(@RequestBody Map<String, Object> user) {
//        Set<String> keySet = user.keySet();
//
//        User mappingUser = new User();
//        for (String key : keySet) {
//            if(key.equals("name")) {
//                mappingUser.setName(user.get(key).toString());
//            } else if(key.equals("id")) {
//                mappingUser.setId(Integer.parseInt(user.get(key).toString()));
//            } else if(key.equals("joinDate")) {
//                mappingUser.setJoinDate(new Date(user.get(key).toString()));
//            }
//        }
//        User savedUser = service.save(mappingUser);
//
//    }
}
