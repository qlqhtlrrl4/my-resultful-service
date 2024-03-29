package ko.co.ljy.myresultfulservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result<T> {
    private T data;
    private int count;
}
