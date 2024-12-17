package tech.reliab.course.reshetova.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.UserModel;
import tech.reliab.course.reshetova.bank.repository.*;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("John Doe", LocalDate.of(1999, 1, 1), "Engineer");
        testUser.setId(1);
        testUser.setMonthlyIncome(5000);
        testUser.setCreditRating(500);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserModel Model = new UserModel("John Doe", LocalDate.of(1999, 1, 1), "Engineer");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1);
            return user;
        });

        // Act
        User createdUser = userService.createUser(Model);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(1);
        assertThat(createdUser.getFullName()).isEqualTo("John Doe");
        assertThat(createdUser.getJob()).isEqualTo("Engineer");
        assertThat(createdUser.getMonthlyIncome()).isBetween(0.0, 10000.0);
        assertThat(createdUser.getCreditRating()).isEqualTo((int) Math.ceil(createdUser.getMonthlyIncome() / 1000.0) * 100);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.getUserDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFullName()).isEqualTo("John Doe");

        verify(userRepository).findById(1);
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.getUserDtoById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User was not found");

        verify(userRepository).findById(1);
    }

    @Test
    void testGetUserDtoById() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.getUserDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(userRepository).findById(1);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullName()).isEqualTo("John Doe");

        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User updatedUser = userService.updateUser(1, "Jane Doe");

        // Assert
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getFullName()).isEqualTo("Jane Doe");

        verify(userRepository).findById(1);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(1, "Jane Doe"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User was not found");

        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository).deleteById(1);
    }
}