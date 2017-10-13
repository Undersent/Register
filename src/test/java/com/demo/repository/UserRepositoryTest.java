package com.demo.repository;

import com.demo.DemoApplicationTests;
import com.demo.model.Role;
import com.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles(value = "TestWoJavaMailSender")
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User userCreate(){
        return User
                .builder()
                .password(bCryptPasswordEncoder.encode("admin"))
                .email("admin@admin.com")
                .active(1)
                .enabled(true)
                .confirmationEmailToken(UUID.randomUUID().toString())
                .roles(new HashSet<>(Collections
                        .singletonList(entityManager
                        .find(Role.class, 1))))
                .name("admin")
                .lastName("admin")
                .build();
    }


    @Test
    public void findByEmailThenReturnUser(){
        User user = userCreate();
        entityManager.merge(user);
        entityManager.flush();

        User foundUser = userRepository.findByEmail(user.getEmail());

        assertThat(foundUser.getEmail())
                .isEqualTo(user.getEmail());
    }
}
