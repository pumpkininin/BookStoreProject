package sad.fit2021.bookstoreproject.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sad.fit2021.bookstoreproject.exception.DuplicatedEmailException;
import sad.fit2021.bookstoreproject.exception.DuplicatedUsernameException;
import sad.fit2021.bookstoreproject.exception.ResourceNotFoundException;
import sad.fit2021.bookstoreproject.model.dto.UserDto;
import sad.fit2021.bookstoreproject.model.entity.Role;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.model.entity.VerificationToken;
import sad.fit2021.bookstoreproject.repository.TokenRepository;
import sad.fit2021.bookstoreproject.repository.UserRepository;

import javax.mail.MessagingException;
import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private String appUrl;

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findUserByUsernameOrPassword(username, email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));
    }

    public User register(UserDto userDto) throws DuplicatedEmailException, DuplicatedUsernameException {
        if (existEmail(userDto.getEmail())) {
            throw new DuplicatedEmailException("Email: +" + userDto.getEmail() + " already be used for other account");
        }
        if (existUsername(userDto.getUsername())) {
            throw new DuplicatedUsernameException(userDto.getUsername() + " has been used");
        }
        User user = new User();
        user.setFullname(userDto.getFirstName() + " " + userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getProvince() + " - " + userDto.getDistrict() + " - " + userDto.getWard() + " - " + userDto.getStreet());
        user.setDob(userDto.getDob());
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setEnabled(userDto.isEnabled());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvt(userDto.getAvt());
        Optional<User> saveduser = Optional.of(userRepository.save(user));

        saveduser.ifPresent(u -> {
            try {
                String token = UUID.randomUUID().toString();
                System.out.println(token);
                verificationTokenService.saveToken(saveduser.get(), token);

                //send verification email
                emailService.sendVerificationEmail(u, "activation");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return saveduser.get();
    }

    private boolean existEmail(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public boolean existUsername(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public User getUser(String token) {
        VerificationToken tokenObj = tokenRepository.findByToken(token);
        if (tokenObj != null) {
            return tokenObj.getUser();
        }
        return null;
    }




    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
