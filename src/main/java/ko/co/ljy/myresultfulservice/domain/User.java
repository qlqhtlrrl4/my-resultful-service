package ko.co.ljy.myresultfulservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@AllArgsConstructor
@JsonIgnoreProperties(value={"password", "ssn"})
@NoArgsConstructor
@Schema(description = "사용자 상세 정보를 위한 도메인 객체") // description : class 정보를 적어준다
@Entity
@Table(name ="users")
public class User {
    @Schema(title="userId" ,description = "userId 는 자동 생성") // field 단에 있으면 field 정보
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    @Schema(title="username" ,description = "username 이름을 입력합니다.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    @Schema(title="user join date" ,description = "등록일을 입력하지않으면 현재 날짜 시간으로 지정됩니다.")
    private LocalDateTime joinDate;

//    @JsonIgnore
    @Schema(title="주민번호 " ,description = "주민번호를 입력합니다.")
    private String ssn;

//    @JsonIgnore
    @Schema(title="password" ,description = "password를 입력합니다.")
    private String password;

    @OneToMany(mappedBy = "user")
//    @BatchSize(size = 100)
    private List<Post> posts = new ArrayList<>();

    public User(Integer id, String name, LocalDateTime joinDate, String ssn, String password) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.ssn = ssn;
        this.password = password;
    }

    public User(String name, LocalDateTime joinDate, String ssn, String password) {
        this.name = name;
        this.joinDate = joinDate;
        this.ssn = ssn;
        this.password = password;
    }
}
