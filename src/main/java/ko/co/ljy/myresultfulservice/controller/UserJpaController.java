package ko.co.ljy.myresultfulservice.controller;

import ko.co.ljy.myresultfulservice.domain.Post;
import ko.co.ljy.myresultfulservice.domain.PostDto;
import ko.co.ljy.myresultfulservice.domain.Result;
import ko.co.ljy.myresultfulservice.domain.User;
import ko.co.ljy.myresultfulservice.exception.UserNotFoundException;
import ko.co.ljy.myresultfulservice.repository.PostRepository;
import ko.co.ljy.myresultfulservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpa")
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //jpa/users

    @GetMapping("users")
    public Result<?> retrieveAllUsers() {
        List<User> data = userRepository.findAll();
        return new Result<>(data, data.size());
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> retrieveUsersById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new UserNotFoundException("id- "+ id);
        }

        EntityModel entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("jpa-all-users")); // all-users -> http://localhost:8088/jpa/users

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Validated @RequestBody User user) {

        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public Result<?> retrieveAllPostsByUser(@PathVariable("id") int id) {

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new UserNotFoundException("id ="+id);
        }

        List<Post> posts = postRepository.findByUserPost(id);

        List<PostDto> result = posts.stream()
                .map(PostDto::new)
                .toList();

        return new Result<>(result, result.size());
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<?> createPost(@PathVariable("id") int id, @RequestBody Post post) {

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new UserNotFoundException("id : "+id);
        }

        post.change(user.get());
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
