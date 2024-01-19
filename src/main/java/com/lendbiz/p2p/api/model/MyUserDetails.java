//package com.lendbiz.p2p.api.model;
//
//import com.lendbiz.p2p.api.entity.User3GEntity;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//@Setter
//@Getter
//public class MyUserDetails implements UserDetails {
//    private String username;
//    private String password;
//    private int active;
//    private List<GrantedAuthority> authorities;
//    public MyUserDetails(User3GEntity user) {
//        this.username = user.getUserName();
//        this.password = user.getPassword();
//        this.active = user.getEnabled();
//        this.authorities = Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//    }
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
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        if (active==1){
//            return true;
//        }
//        return false;
//    }
//}
