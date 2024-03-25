package ko.co.ljy.myresultfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import ko.co.ljy.myresultfulservice.domain.AdminUser;
import ko.co.ljy.myresultfulservice.domain.AdminUserV2;
import ko.co.ljy.myresultfulservice.domain.User;
import ko.co.ljy.myresultfulservice.exception.UserNotFoundException;
import ko.co.ljy.myresultfulservice.service.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserControllerV3 {

    private final UserDaoService service;

    //admin/users/{id}

    @GetMapping(value = "/users/{id}", params = "version=1")
    public MappingJacksonValue retrieve4AdminV3(@PathVariable("id") int id) {
        Optional<User> user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user.get(), adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(value = "/users", params = "version=1")
    public MappingJacksonValue retrieveAll4AdminV3() {

        List<User> users = service.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;

        for (User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }


    @GetMapping(value = "/users/{id}", params = "version=2")
    public MappingJacksonValue retrieve4AdminV4(@PathVariable("id") int id) {
        Optional<User> user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            //BeanUtils.copyProperties : 스프링에서 기본으로 제공해주는 method 객체를 쉽고 간결하게 복사할 수 있다.
            //copyProperties(Object source, Object target, String...ignoreProperties)
            // source : 원본 객체 | target : 복사 객체 | ignoreProperties : 복사하지 않을 필드명
            BeanUtils.copyProperties(user.get(), adminUser);
            adminUser.setGrade("ADMIN");
        }
        //SimpleBeanPropertyFilter 는 serializeAllExcept() / filterOutAllExcept 를 통해서 필드를 필터링 할 수 있음
        // serializeAllExcept() : 지정한 데이터 제회한 모든 데이터가 직렬화되어 보여짐
        // filterOutAllExcept() : 지정한 데이터만 직렬화 되어 보여짐
        // serializeAll() : 모든 속성이 직렬화
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(value = "/users", params = "version=2")
    public MappingJacksonValue retrieveAll4AdminV4() {

        List<User> users = service.findAll();
        List<AdminUserV2> adminUsers = new ArrayList<>();
        AdminUserV2 adminUser = null;

        for (User user : users) {
            adminUser = new AdminUserV2();
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("ADMIN");
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

}
