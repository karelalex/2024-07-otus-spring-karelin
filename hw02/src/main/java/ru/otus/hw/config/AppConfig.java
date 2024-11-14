package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("megatest.properties")
public class AppConfig {
    @Bean
    AppProperties appProperties(
            @Value("${test.minRightAnswerCount: 0}") int rightAnswerCount,
            @Value("${test.questionsFileName}") String questionsFilename
    ) {
        var props = new AppProperties();
        props.setRightAnswersCountToPass(rightAnswerCount);
        props.setTestFileName(questionsFilename);
        return props;
    }
}
