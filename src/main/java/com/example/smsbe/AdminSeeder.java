package com.example.smsbe;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.smsbe.entity.Manager;
import com.example.smsbe.repository.ManagerRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final ManagerRepository managerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        Optional<Manager> optionalUser = managerRepository.findByUsername("admin");

        if (optionalUser.isPresent()) {
            return;
        }

        var user = new Manager()
                .setUsername("admin")
                .setPassword(passwordEncoder.encode("admin"));

        managerRepository.save(user);
    }
}
