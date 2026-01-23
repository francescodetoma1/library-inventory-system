package com.library.inventory;

import com.library.inventory.model.User;
import com.library.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibraryInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryInventoryApplication.class, args);
    }


    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        "ADMIN"
                );
                userRepository.save(admin);
            }


            if (userRepository.findByUsername("reader").isEmpty()) {
                User reader = new User(
                        "reader",
                        passwordEncoder.encode("reader123"),
                        "READER"
                );
                userRepository.save(reader);
            }
        };
    }
}



