package ko.co.ljy.myresultfulservice;

import jakarta.annotation.PostConstruct;
import ko.co.ljy.myresultfulservice.domain.User;
import ko.co.ljy.myresultfulservice.service.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TestInitData {

    @Autowired
    private final UserDaoService userDaoService;

    @PostConstruct
    public void init() {
        userDaoService.save(new User(1, "Kenneth", new Date(),"111111-1111111","1234"));
        userDaoService.save(new User(2, "Alice", new Date(),"222222-2222222","5678"));
        userDaoService.save(new User(3, "Elena", new Date(),"333333-3333333","test3"));

    }


}
