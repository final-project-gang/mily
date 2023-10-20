package com.mily.base.initData;

import com.mily.user.MilyUserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
public class NotProd {
    @Bean
    public ApplicationRunner init(MilyUserService milyUserService) {
        return args -> {
            milyUserService.signup("admin123", "9a9a9a9a", "administrator", "administrator", "admin123@email.com", "99999999999", "1996-10-05");
            milyUserService.signup("testaccount", "qwerasdf", "testaccount", "testaccount", "testaccount@email.com", "88888888888", "1996-10-05");
        }; // 더미 데이터 넣는곳 내가 원하는 만큼 추가해서 넣어도 됨
    }
}