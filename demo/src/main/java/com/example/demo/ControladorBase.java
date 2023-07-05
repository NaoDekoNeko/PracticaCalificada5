package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ControladorBase {

    @Autowired
    private ServicioVideo servicioVideo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", servicioVideo.videos);
        return "index";
    }

    @PostMapping("/nuevo-video")
    public String nuevoVideo(@ModelAttribute Video nuevoVideo) {
        servicioVideo.create(nuevoVideo);
        return "redirect:/";
    }
}

