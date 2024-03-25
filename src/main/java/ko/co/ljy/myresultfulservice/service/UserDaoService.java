package ko.co.ljy.myresultfulservice.service;

import ko.co.ljy.myresultfulservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {

    private static final List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }

        if(user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }
        users.add(user);

        return user;
    }

    public Optional<User> findOne(int id) {
        for (User user : users) {
            if(user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> deleteById(int id) {

        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
