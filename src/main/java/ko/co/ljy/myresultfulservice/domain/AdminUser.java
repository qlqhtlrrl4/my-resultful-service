package ko.co.ljy.myresultfulservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo")
//@JsonIgnoreProperties("sss", "password) <- jackson 이 무시하는 property를 표시하는 클래스 레벨의 어노테이션
public class AdminUser {

    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

//    @JsonIgnore <- jackson 이 무시하는 field 레벨의 어노베이션
    private String ssn;

//    @JsonIgnore
    private String password;
}
