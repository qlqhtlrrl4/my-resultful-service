package ko.co.ljy.myresultfulservice;

import jakarta.annotation.PostConstruct;
import ko.co.ljy.myresultfulservice.domain.Post;
import ko.co.ljy.myresultfulservice.domain.User;
import ko.co.ljy.myresultfulservice.repository.PostRepository;
import ko.co.ljy.myresultfulservice.repository.UserRepository;
import ko.co.ljy.myresultfulservice.service.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.LocalDate.now;

@Component
@RequiredArgsConstructor
public class TestInitData {

    private final UserDaoService userDaoService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        userDaoService.save(new User(1, "Kenneth", LocalDateTime.now(),"111111-1111111","1234"));
        userDaoService.save(new User(2, "Alice", LocalDateTime.now(),"222222-2222222","5678"));
        userDaoService.save(new User(3, "Elena", LocalDateTime.now(),"333333-3333333","test3"));

    }

    @PostConstruct
    public void initJpaData() {

        User user1 = new User("user1",LocalDateTime.now(),"890202-1111111","1234");
        User user2 = new User("user2",LocalDateTime.now(),"790202-2222222","1234");
        User user3 = new User("user3",LocalDateTime.now(),"690202-3333333","1234");
        User user4 = new User("user4",LocalDateTime.now(),"990202-4444444","1234");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Post post1 = new Post();
        post1.setDescription("My first post");
        post1.change(user1);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("My second post");
        post2.change(user1);
        postRepository.save(post2);
    }


}
