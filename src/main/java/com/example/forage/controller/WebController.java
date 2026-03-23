package com.example.forage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller pour servir les pages web statiques
 */
@Controller
public class WebController {
    
    /**
     * Page d'accueil - sert le fichier index.html
     * @return le nom de la vue
     */
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
