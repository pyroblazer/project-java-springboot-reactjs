package com.onlinestore.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.onlinestore.payload.UserDto;
import com.onlinestore.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
//@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    @Autowired
    private UserRepo userRepo;

    private User user;
    private Role role;
    private List<Role> roles;
//    private UserDto userDto;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setPassword("password");
        // Add role if needed (assuming Role object exists)
        // user.setRole(Collections.singletonList(new Role("USER")));

        // Inject your persistence layer (e.g., userRepository)
        User savedUser = userRepo.save(this.modelMapper.map(userDto, User.class));

        User retrievedSavedUser = userRepo.getReferenceById(savedUser.getId());

        assertNotNull(retrievedSavedUser);
        assertEquals("John Doe", retrievedSavedUser.getName());
        assertEquals("johndoe@example.com", retrievedSavedUser.getEmail());
    }

    @Test
    public void testDuplicateEmail() {
        // Create a user and save it
        User user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("johndoe@example.com");
        user1.setPassword("password");
        userRepo.save(user1);

        // Create another user with the same email
        User user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("johndoe@example.com"); // Duplicate email
        user2.setPassword("password2");

        // Use expectedException to specify the expected exception
        assertThrows(DataIntegrityViolationException.class, () -> userRepo.save(user2));
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");

        assertEquals("John Doe", user.getName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testGetAuthorities() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        user.setRoles(roles);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}