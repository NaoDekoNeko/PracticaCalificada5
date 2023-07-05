package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ServicioVideo {
    List<Video> videos = List.of(
        new Video("Video de Persona 3"),
        new Video("https://www.youtube.com/watch?v=hTVeFmHqZnk"),
        new Video("no se me ocurrió qué más poner"));
    
    public Video create(Video nuevoVideo) {
        List<Video> extend = new ArrayList<>(videos);
        extend.add(nuevoVideo);
        this.videos = List.copyOf(extend);
        return nuevoVideo;
    }
    
    public List<Video> getVideos(){
        return videos;
    }
}