package pl.sda.jp.miniblog12.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserContextService {

    public String getLoggedAs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
//        authentication.getAuthorities().stream().map(a->((GrantedAuthority) a).getAuthority());
        return authentication.getName();
    }

    public boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
       return authentication.getAuthorities().stream().map(a -> a.getAuthority()).anyMatch(s -> s.equals(roleName));
    }

    public boolean hasAnyRole(String... roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return Arrays.stream(roleNames).anyMatch(roleName ->hasRole(roleName));
    }

    public boolean hasAllRoles(String... roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return Arrays.stream(roleNames).allMatch(roleName ->hasRole(roleName));
    }
}
