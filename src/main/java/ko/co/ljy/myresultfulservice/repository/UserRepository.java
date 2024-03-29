package ko.co.ljy.myresultfulservice.repository;

import ko.co.ljy.myresultfulservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
