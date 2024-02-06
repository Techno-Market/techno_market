package com.techno_market.techno_market.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EnableJpaAuditing
public class SiteUser extends BaseEntity {
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickName;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    private LocalDate birthDate;

    private String refreshToken;

    public List<String> getAuthoritiesAsStringList() {
        return List.of("ROLE_MEMBER");
    }
}
