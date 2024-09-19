package group1.auth.service;

import group1.auth.entity.Turister;
import group1.auth.repository.TuristerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private TuristerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Turister> userDataBase = repository.findByUsername(username);
        if (userDataBase.isPresent()) {
            return User.builder()
                    .username(userDataBase.get().getUsername())
                    .password(userDataBase.get().getPassword())
                    .roles(userDataBase.get().getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
