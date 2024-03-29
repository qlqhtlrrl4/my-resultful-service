package ko.co.ljy.myresultfulservice.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private Integer postId;
    private String description;
    private Integer userId;
    private String userName;
    private LocalDateTime joinDate;

    public PostDto(Post post) {
        postId = post.getId();
        description = post.getDescription();
        userId = post.getUser().getId();
        userName = post.getUser().getName();
        joinDate = post.getUser().getJoinDate();
    }

}
