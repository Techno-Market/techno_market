package com.techno_market.techno_market.domain.user.repository;

import com.techno_market.techno_market.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
}
