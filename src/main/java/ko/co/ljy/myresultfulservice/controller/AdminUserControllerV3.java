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

    @GetMapping(value = "/v3/users/{id}", headers = "X-API-VERSION=1")
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

    @GetMapping(value = "/v3/users", headers = "X-API-VERSION=1")
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


    @GetMapping(value = "/v3/users/{id}", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieve4AdminV3_1(@PathVariable("id") int id) {
        Optional<User> user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user.get(), adminUser);
            adminUser.setGrade("ADMIN");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(value = "/users", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveAll4AdminV3_1() {

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
