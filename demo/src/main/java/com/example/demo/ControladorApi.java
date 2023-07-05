package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorApi {
    private final ServicioVideo servicioVideo;
    
    public ControladorApi(ServicioVideo servicioVideo) {
        this.servicioVideo = servicioVideo;
    }

    @GetMapping("/api/videos")
    public List<Video> all() {
        return servicioVideo.getVideos();
    }
    
    //@RequestBody es una anotación que indica
    //que el metodo debe estar ligado al cuerpo de 
    //la solicitud web
    @PostMapping("/api/videos")
    public Video create(@RequestBody Video video) {
        return servicioVideo.create(video);
    }
}
