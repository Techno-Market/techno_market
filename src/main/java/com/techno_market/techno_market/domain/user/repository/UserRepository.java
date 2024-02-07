package com.techno_market.techno_market.domain.user.repository;

import com.techno_market.techno_market.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser,Long> {
    Optional<SiteUser> findByUsername(String username);
    SiteUser findByNickName(String nickName);

    Optional<SiteUser> findByRefreshToken(String refreshToken);
}
