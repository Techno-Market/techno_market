package com.techno_market.techno_market.global.initData;

import com.techno_market.techno_market.domain.photo.entity.Photo;
import com.techno_market.techno_market.domain.photo.repository.PhotoRepository;
import com.techno_market.techno_market.domain.photo.service.FileHandler;
import com.techno_market.techno_market.domain.sellArticle.service.SellArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final SellArticleService sellArticleService;
    private final FileHandler fileHandler;
    private final PhotoRepository photoRepository;
    @Bean
    CommandLineRunner initData(SellArticleService sellArticleService) {
        return args -> {
//            Random random = new Random();
//
//            for (int i = 1; i <= 25; i++) {
//                String subject = String.format("삼성갤럭시:[%03d]", i);
//                String content = "내용무";
//                int price = random.nextInt(300000);
//                String area = "서울";
//                String category = "mobile";
//                Boolean directly = true;
//                Boolean parcel = false;
//
//                this.sellArticleService.create(subject, content, price, area, category, directly, parcel, null);
//            }
//            for (int i = 1; i <= 25; i++) {
//                String subject = String.format("노트북:[%03d]", i);
//                String content = "내용무";
//                int price = random.nextInt(300000);
//                String area = "대전";
//                String category = "laptop";
//                Boolean directly = true;
//                Boolean parcel = true;
//
//                this.sellArticleService.create(subject, content, price, area, category, directly, parcel, null);
//            }
//            for (int i = 1; i <= 25; i++) {
//                String subject = String.format("모니터:[%03d]", i);
//                String content = "내용무";
//                int price = random.nextInt(300000);
//                String area = "부산";
//                String category = "monitor";
//                Boolean directly = false;
//                Boolean parcel = true;
//
//                this.sellArticleService.create(subject, content, price, area, category, directly, parcel, null);
//            }
//            for (int i = 1; i <= 25; i++) {
//                String subject = String.format("스마트워치:[%03d]", i);
//                String content = "내용무";
//                int price = random.nextInt(300000);
//                String area = "인천";
//                String category = "smartwatch";
//                Boolean directly = false;
//                Boolean parcel = true;
//
//                this.sellArticleService.create(subject, content, price, area, category, directly, parcel, null);
//            }
//            for (int i = 1; i <= 25; i++) {
//                String subject = String.format("태블릿:[%03d]", i);
//                String content = "내용무";
//                int price = random.nextInt(300000);
//                String area = "대전";
//                String category = "tablet";
//                Boolean directly = true;
//                Boolean parcel = false;
//
//                this.sellArticleService.create(subject, content, price, area, category, directly, parcel, null);
//            }
        };
    }
}
