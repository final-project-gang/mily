package com.mily.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MilyUserRepository extends JpaRepository<MilyUser, Long> {
    Optional<MilyUser> findByUserLoginId(String userLoginId);
    Optional<MilyUser> findByUserNickName(String userNickName);
    Optional<MilyUser> findByUserEmail(String userEmail); // 이 메소드만 사용
    Optional<MilyUser> findByUserPhoneNumber(String userPhoneNumber);
}
