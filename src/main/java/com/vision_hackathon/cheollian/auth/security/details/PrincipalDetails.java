package com.vision_hackathon.cheollian.auth.security.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.vision_hackathon.cheollian.member.entity.Member;

import lombok.Builder;

@Builder
public record PrincipalDetails(Member member, Map<String, Object> attributes) implements OAuth2User {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (member.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(member.getRole().name()));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        }
        return authorities;
    }

    @Override
    public String getName() {
        return member.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public static PrincipalDetails buildPrincipalDetails(Member member, OAuth2User oAuth2User) {
        return PrincipalDetails.builder()
            .member(member)
            .attributes(oAuth2User.getAttributes())
            .build();
    }

    public static PrincipalDetails buildPrincipalDetails(Member member) {
        return PrincipalDetails.builder()
            .member(member)
            .build();
    }
}
