package com.example.smsbe.config;

import com.example.smsbe.entity.GlobalConfig;
import com.example.smsbe.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultConfig implements ApplicationListener<ContextRefreshedEvent> {
    private final ConfigRepository configRepository;

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent contextRefreshedEvent) {
        this.createDefaultConfig();
    }

    private void createDefaultConfig() {
        if (configRepository.findByConfigKey("minAge").isEmpty()) {
            configRepository.save(new GlobalConfig()
                    .setConfigKey("minAge")
                    .setValue("17"));
        }
        if (configRepository.findByConfigKey("maxAge").isEmpty()) {
            configRepository.save(new GlobalConfig()
                    .setConfigKey("maxAge")
                    .setValue("25"));
        }
        if (configRepository.findByConfigKey("maxTotal").isEmpty()) {
            configRepository.save(new GlobalConfig()
                    .setConfigKey("maxTotal")
                    .setValue("100"));
        }
        if (configRepository.findByConfigKey("minScorePass").isEmpty()) {
            configRepository.save(new GlobalConfig()
                    .setConfigKey("minScorePass")
                    .setValue("60"));
        }
    }
}
