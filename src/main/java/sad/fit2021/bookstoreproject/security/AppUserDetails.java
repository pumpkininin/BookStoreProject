package sad.fit2021.bookstoreproject.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sad.fit2021.bookstoreproject.model.entity.Cart;
import sad.fit2021.bookstoreproject.model.entity.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AppUserDetails implements UserDetails {

    private Integer id;

    private String username;

    private String password;

    private String fullname;

    private String phonenNumber;

    private String email;

    private String address;

    private Character gender;

    private Date dob;


    private Boolean enabled;

    private List<? extends GrantedAuthority> authorities;

    public AppUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullname = user.getFullname();
        this.phonenNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.dob = user.getDob();
        this.enabled = user.getEnabled();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
