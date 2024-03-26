package ko.co.ljy.myresultfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ko.co.ljy.myresultfulservice.domain.User;
import ko.co.ljy.myresultfulservice.exception.UserNotFoundException;
import ko.co.ljy.myresultfulservice.service.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Tag(name="user-controller", description = "일반 사용자 서비스를 위한 controller") // tag는 클래스를 설명할때 사용
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조회 API 입니다.", description = "user ID를 이용해서 상세 정보 조회를 합니다.")// @Operation annotation : method 설명
    @ApiResponses ({    // response code 에 대한 설명 정의 가능
            @ApiResponse(responseCode = "200", description = "OK!!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "USER NOT FOUND!!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR!!")
    })
    @GetMapping("/users/{id}")
    public EntityModel<Optional<User>> retrieveUser(
            //@Parameter에 대한 설명을 할때도 사용
            @Parameter(description = "userId 입력", required = true, example = "1") @PathVariable("id") int id) {
        Optional<User> user = service.findOne(id);

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        // EntityModel 생성
        EntityModel entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); //all-users -> http://localhost:8088/users

        return entityModel;
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
