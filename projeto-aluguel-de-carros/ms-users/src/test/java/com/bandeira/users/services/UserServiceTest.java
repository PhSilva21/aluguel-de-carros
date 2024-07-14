package com.bandeira.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bandeira.users.exceptions.EmailAlreadyExistsException;
import com.bandeira.users.util.RandomString;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bandeira.users.dto.UpdateUserDTO;
import com.bandeira.users.dto.UserRequest;
import com.bandeira.users.exceptions.UserNotFoundException;
import com.bandeira.users.model.Gender;
import com.bandeira.users.model.User;
import com.bandeira.users.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RandomString randomString;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    User user = new User(
            "Mateus",
            "mateus@gmail.com",
            "12142151dsd",
            "2128172211",
            "sws626222",
            LocalDate.of(2003, 9,11),
            Gender.MASCULINE
    );

    UserRequest userRequest = new UserRequest(
            "Carolina",
            "carol@Gmail.com",
            "121261g2g61",
            "2891828182",
            "727187281811",
            LocalDate.of(1998,8,17),
            Gender.FEMININE
    );

    UpdateUserDTO updateUserDTO = new UpdateUserDTO(
            "Julio",
            "julio@gmail.com",
            "w918w1hsqs",
            LocalDate.of(2001,5,27),
            Gender.MASCULINE
    );


    @Nested
    class createUser {

        @Test
        @DisplayName("Must create user successfully")
        void MustCreateUserSuccessfully() throws JsonProcessingException {

            var random = randomString.generateRandomString(44);

            doReturn(null)
                    .when(userRepository)
                    .findByEmail(userRequest.email());
            doReturn(userRequest.password())
                    .when(passwordEncoder)
                    .encode(userRequest.password());
            doReturn(random)
                    .when(randomString)
                    .generateRandomString(64);
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var response = userService.createUser(userRequest);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getId(), userCaptured.getId());
            assertEquals(userRequest.name(), userCaptured.getName());
            assertEquals(userRequest.email(), userCaptured.getEmail());
            assertEquals(userRequest.password(), userCaptured.getPassword());
            assertEquals(userRequest.cel(), userCaptured.getCel());
            assertEquals(userRequest.cpf(), userCaptured.getCpf());
            assertEquals(userRequest.dateOfBirth(), userCaptured.getDateOfBirth());
            assertEquals(userRequest.gender(), userCaptured.getGender());
        }

        @Test
        @DisplayName("Must return exception when the email already exists")
        void MustReturnExceptionWhenTheEmailAlreadyExists(){
            doReturn(user)
                    .when(userRepository)
                    .findByEmail(userRequest.email());

            assertThrows(EmailAlreadyExistsException.class, () ->
                    userService.createUser(userRequest));
        }
    }

    @Nested
    class verify {

        @Test
        @DisplayName("Must return unverified user")
        void MustReturnUnverifiedUser() {

                doReturn(user)
                        .when(userRepository)
                        .findByVerificationCode(user.getVerificationCode());
                doReturn(user)
                        .when(userRepository)
                        .save(userArgumentCaptor.capture());


                var response = userService.verify(user.getVerificationCode());

                assertTrue(response);
                assertNull(user.getVerificationCode());
        }
        @Test
        @DisplayName("Must return the verified user")
        void MustReturnTheVerifiedUser() {
            doReturn(user)
                    .when(userRepository)
                    .findByVerificationCode(user.getVerificationCode());

            user.setEnabled(true);

            var response = userService.verify(user.getVerificationCode());

            assertTrue(user.isEnabled());
            assertFalse(response);
            assertNull(user.getVerificationCode());
        }

        @Test
        @DisplayName("Should throw exception when not finding the user")
        void ShouldThrowExceptionWhenNotFindingTheUser() {
            doReturn(null)
                    .when(userRepository)
                    .findByVerificationCode(user.getVerificationCode());

            var response = userService.verify(user.getVerificationCode());

            assertFalse(response);
        }
    }
    @Nested
    class findByEmail{

        @Test
        @DisplayName("The user must be returned successfully")
        void TheUserMustBeReturnedSuccessfully() {
            doReturn(user)
                    .when(userRepository)
                    .findByEmail(user.getEmail());

            var response = userService.findByEmail(user.getEmail());

            assertNotNull(response);
            assertEquals(response.getName(), user.getName());
        }
        @Test
        @DisplayName("Should throw exception when not finding the user")
        void ShouldThrowExceptionWhenNotFindingTheUser() {
            doReturn(null)
                    .when(userRepository)
                    .findByEmail(user.getEmail());

            assertThrows(UserNotFoundException.class,
                    () -> userService.findByEmail(user.getEmail()));
        }
    }

    @Nested
    class updateUser {

        @Test
        @DisplayName("Must update basic user information")
        void MustUpdateBasicUserInformation() {
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(user.getId());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());


            var response = userService.updateUser(user.getId(), updateUserDTO);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getId(), userCaptured.getId());
            assertEquals(updateUserDTO.name(), userCaptured.getName());
            assertEquals(updateUserDTO.cel(), userCaptured.getCel());
            assertEquals(updateUserDTO.cpf(), userCaptured.getCpf());
            assertEquals(updateUserDTO.dateOfBirth(), userCaptured.getDateOfBirth());
            assertEquals(updateUserDTO.gender(), userCaptured.getGender());

            verify(userRepository, times(1))
                    .findById(user.getId());
            verify(userRepository, times(1))
                    .save(user);
        }
        @Test
        @DisplayName("Should throw exception when not finding the user")
        void ShouldThrowExceptionWhenNotFindingTheUser() {
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(user.getId());

            assertThrows(UserNotFoundException.class,
                    () -> userService.updateUser(user.getId(), updateUserDTO));

            verify(userRepository, times(1))
                    .findById(user.getId());
            verify(userRepository, times(0))
                    .save(user);
        }
    }
    @Nested
    class updateEmail{

        @Test
        @DisplayName("Must update email successfully")
        void MustUpdateEmailSuccessfully() {
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(user.getId());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var response = userService.updateEmail(user.getId(), user.getPassword()
                    , user.getEmail());

            assertNotNull(response);
            assertEquals("Your email has been updated successfully", response);
        }
        @Test
        @DisplayName("Should throw exception when not finding the user")
        void ShouldThrowExceptionWhenNotFindingTheUser() {
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(user.getId());

            assertThrows(UserNotFoundException.class,
                    () -> userService.updatePassword(user.getId(), user.getPassword(),
                            "1371g6df11", "7yw1t6wt16tw661"));

            verify(userRepository, times(1))
                    .findById(user.getId());
            verify(userRepository, times(0))
                    .save(user);
        }
    }


    @Nested
    class updatePassword {

        @Test
        @DisplayName("must update password successfully")
        void MustUpdatePasswordSuccessfully() {
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(user.getId());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var response = userService.updatePassword(user.getId(),
                    user.getPassword(), "12345678", "12345678");

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getPassword(), userCaptured.getPassword());
            assertEquals("Your password has been updated successfully", response);
        }

        @Test
        @DisplayName("Should throw exception when not finding the user")
            void ShouldThrowExceptionWhenNotFindingTheUser () {
                doReturn(Optional.empty())
                        .when(userRepository)
                        .findById(user.getId());

                assertThrows(UserNotFoundException.class,
                        () -> userService.updatePassword(user.getId(), user.getPassword(),
                                "1371g6df11", "7yw1t6wt16tw661"));

                verify(userRepository, times(1))
                        .findById(user.getId());
                verify(userRepository, times(0))
                        .save(user);
            }
    }

        @Nested
        class updateUsername {

            @Test
            @DisplayName("Must update the username successfully")
            void MustUpdateTheUsernameSuccessfully() {
                doReturn(Optional.of(user))
                        .when(userRepository)
                        .findById(user.getId());
                doReturn(user)
                        .when(userRepository)
                        .save(userArgumentCaptor.capture());

                var response = userService.updateUserName(user.getId(), "Augustinho Carrara");

                assertEquals(user.getName(), "Augustinho Carrara");
                assertEquals("Username has been updated successfully", response);
            }


            @Test
            @DisplayName("Should throw exception when not finding the user")
                void ShouldThrowExceptionWhenNotFindingTheUser () {
                    doReturn(Optional.empty())
                            .when(userRepository)
                            .findById(user.getId());

                    assertThrows(UserNotFoundException.class,
                            () -> userService.updateUserName(user.getId(), "ffad)"));

                    verify(userRepository, times(1))
                            .findById(user.getId());
                    verify(userRepository, times(0))
                            .save(user);
                }
            }

            @Nested
            class findAll {

                @Test
                @DisplayName("Must return users successfully")
                void MustReturnUsersSuccessfully() {
                    var usersList = List.of(user);
                    doReturn(usersList)
                            .when(userRepository)
                            .findAll();

                    var response = userService.findAll();

                    assertNotNull(response);
                    assertEquals(usersList.size(), response.size());
                }
            }

            @Nested
            class deleteById {

                @Test
                @DisplayName("Must delete user successfully")
                void MustDeleteUserSuccessfully() {
                    doReturn(Optional.of(user))
                            .when(userRepository)
                            .findById(user.getId());
                    doNothing()
                            .when(userRepository)
                            .deleteById(user.getId());

                    userService.deleteById(user.getId());

                    verify(userRepository, times(1))
                            .findById(user.getId());
                    verify(userRepository, times(1))
                            .deleteById(user.getId());
                }

                @Test
                @DisplayName("Should throw exception when not finding the user")
                    void ShouldThrowExceptionWhenNotFindingTheUser () {
                        doReturn(Optional.empty())
                                .when(userRepository)
                                .findById(user.getId());

                        assertThrows(UserNotFoundException.class,
                                () -> userService.updateUserName(user.getId(), "ffad)"));

                        verify(userRepository, times(1))
                                .findById(user.getId());
                        verify(userRepository, times(0))
                                .save(user);
                    }
                }
            }
