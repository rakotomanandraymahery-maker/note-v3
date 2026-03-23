package com.example.forage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Spring Boot pour le système de forage.
 * 
 * @SpringBootApplication combine :
 * - @Configuration : classe de configuration Spring
 * - @EnableAutoConfiguration : configuration automatique Spring Boot
 * - @ComponentScan : scan des composants Spring dans les packages
 */
@SpringBootApplication
public class ForageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForageApplication.class, args);
        
        System.out.println("=================================");
        System.out.println("🚀 Système de Forage démarré !");
        System.out.println("📡 API Clients : http://localhost:8080/api/clients");
        System.out.println("📡 API Demandes : http://localhost:8080/api/demandes");
        System.out.println("🗄️  Console H2 : http://localhost:8080/h2-console");
        System.out.println("=================================");
    }
}
