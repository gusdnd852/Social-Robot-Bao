package com.welfarerobotics.welfareapplcation.api.chat.crawler;

import com.welfarerobotics.welfareapplcation.api.chat.tools.Encoder;
import com.welfarerobotics.welfareapplcation.entity.ServerCache;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @Author : Hyunwoong
 * @When : 3/22/2019 2:16 PM
 * @Homepage : https://github.com/gusdnd852
 */
public final class PreprocessorApi {
    /**
     * 토크나이저 (형태소 분석기) API
     * 문장을 형태소 단위로 자른뒤, 특수문자 등을 제거함
     *
     * @param text 형태소를 분석할 문장
     * @return 조사, 특수문자등이 잘린 문장
     */
    public static String tokenize(String text) throws IOException {
        return Jsoup.connect(ServerCache.getInstance().getUrl() + "/tokenize/" + Encoder
                .utf8(text))
                .timeout(20000)
                .get()
                .body()
                .text();
    }

    /**
     * 네이버 맞춤법 검사기 API
     * 문장의 맞춤법을 교정함
     *
     * @param text 맞춤법을 교정할 문장
     * @return 맞춤법이 교정된 문장
     */
    public static String fix(String text) throws IOException {
        String fix = Jsoup.connect(ServerCache.getInstance().getUrl() + "/fix/" + Encoder
                .utf8(text))
                .timeout(20000)
                .get()
                .body()
                .text();

        // 예외처리
        if (fix.contains("빈") && fix.contains("지노")) {
            fix = fix.replace("빈", "");
            fix = fix.replace("지노", "빈지노");
        }
        return fix;
    }
}