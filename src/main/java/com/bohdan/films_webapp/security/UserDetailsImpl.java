//package com.bohdan.films_webapp.security;
//
//import com.bohdan.films_webapp.DAO.User;
//import com.bohdan.films_webapp.DAO.enums.UserStatus;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//public class UserDetailsImpl implements UserDetails {
//
//    private final String username;
//    private final String password;
//    private final List<SimpleGrantedAuthority> authorities;
//    private final boolean isActive;
//
//    public static UserDetails fromUser(User user){
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(),
//                user.getStatus().equals(UserStatus.ACTIVE),
//                user.getStatus().equals(UserStatus.ACTIVE),
//                user.getStatus().equals(UserStatus.ACTIVE),
//                user.getStatus().equals(UserStatus.ACTIVE),
//                Collections.singletonList(new SimpleGrantedAuthority(
//                        user.getRole().toString())
//                )
//        );
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return isActive;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return isActive;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return isActive;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isActive;
//    }
//}
