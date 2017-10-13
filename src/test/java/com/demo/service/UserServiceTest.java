package com.demo.service;

import com.demo.model.User;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import com.demo.service.User.UserService;
import com.demo.service.User.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @TestConfiguration
    static class UserServiceTestContextConfiguration{
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        User user = User
                .builder()
                .email("admin@admin.com")
                .build();
        Mockito.when(userRepository
                .findByEmail(user.getEmail()))
                .thenReturn(user);
    }

    @Test
    public void whenValidEmailUserShouldBeFound(){
        String email = "admin@admin.com";
        /*User foundUser = userService.findUserByEmail(email);

        assertThat(foundUser.getEmail())
                .isEqualToIgnoringCase(email);*/
    }
}
