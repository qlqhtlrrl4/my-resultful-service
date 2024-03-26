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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserControllerV1 {

    private final UserDaoService service;

    //admin/users/{id}
    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieve4Admin(@PathVariable("id") int id) {
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

    @GetMapping("/v1/users")
    public MappingJacksonValue retrieveAll4Admin() {

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




}
