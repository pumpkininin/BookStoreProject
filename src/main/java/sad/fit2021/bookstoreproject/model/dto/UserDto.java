package sad.fit2021.bookstoreproject.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.validation.PasswordConfirm;
import sad.fit2021.bookstoreproject.validation.ValidEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@PasswordConfirm
public class UserDto {
    @NotBlank(message = "{NotNull.userDto.username}")
    private String username;

    @NotBlank(message = "{NotNull.userDto.firstName}")
    private String firstName;

    @NotBlank(message = "{NotNull.userDto.lastName}")
    private String lastName;

    @NotBlank(message = "{NotNull.user.password}")
    private String password;

    @NotBlank(message = "{NotNull.user.password}")
    private String confirmPassword;

    private String phoneNumber;

    @ValidEmail
    @NotBlank(message = "{NotNull.user.email}")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String province;

    private String district;

    private String ward;

    private String street;
    private String address;
    private Character gender;
    private boolean enabled;
    private Date createdAt;

    private String avt;

    public UserDto() {
        this.enabled = false;
        //set default avt for each user
        this.avt = "https://bookstorev1.s3.us-east-2.amazonaws.com/avt/default.jpg";
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFullname().split("\\s+")[0];
        this.lastName = user.getFullname().replace(user.getFullname().split("\\s+")[0], " ");
        this.password = user.getPassword();
        this.confirmPassword = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.dob = user.getDob();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.enabled = user.getEnabled();
        this.createdAt = user.getCreatedAt();
        this.avt = user.getAvt();
    }
}
