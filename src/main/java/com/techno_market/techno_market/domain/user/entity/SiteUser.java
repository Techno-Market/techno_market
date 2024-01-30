package com.techno_market.techno_market.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class SiteUser extends BaseEntity {
    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String nickName;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    private LocalDate birthDate;
}
