package ko.co.ljy.myresultfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "Restful Service API 명세서",
                description = "Spring Boot 3.x.x Restful API 명세서",
                version = "v1.0.0"
        )
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfigV2 {
    @Bean
    public GroupedOpenApi customOpenAPI() {
        String [] paths = {"/users/**", "/admin/**"}; //grouping 해서 공개할 api uri -> users , admin으로 시작하는 api만 공개하겠다.

        return GroupedOpenApi
                .builder()
                .group("일반 사용자 , 관리자를 위한 User 도메인에 대한 API")
                .pathsToMatch(paths)
                .build();
    }
}
