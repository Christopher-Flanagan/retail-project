package retail.jwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import retail.model.ApplicationUser;
import retail.repo.ApplicationUserRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    public JwtUserDetailsService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findByUsername(username);
        if(!userOptional.isPresent()) {
            throw new UsernameNotFoundException("No data associated with the following user name :" + username);
        }
        ApplicationUser returnedUser = userOptional.get();

        return new User(returnedUser.getUsername(), returnedUser.getPassword(), emptyList());
    }
}
