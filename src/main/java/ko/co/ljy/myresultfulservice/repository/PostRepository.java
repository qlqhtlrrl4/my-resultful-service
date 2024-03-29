package ko.co.ljy.myresultfulservice.repository;

import ko.co.ljy.myresultfulservice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("select p from Post p join p.user where p.user.id = :userId")
    List<Post> findByUserPost(@Param("userId") int userId);
}
