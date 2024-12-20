package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.palla.Insta_Clone.Model.User> opt=repo.findByEmail(username);
        if(opt.isPresent()){
            com.palla.Insta_Clone.Model.User user=opt.get();
            List<GrantedAuthority> authorities=new ArrayList<>();
            return new User(user.getEmail(),user.getPassword(),authorities);

        }


        throw new BadCredentialsException("user not found with username "+username);
    }
}
