package sad.fit2021.bookstoreproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.service.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        //Login with username or email
        User user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username or email: " + usernameOrEmail);
        }
        //return custom UserDetail
        return new AppUserDetails(user);
    }

}
