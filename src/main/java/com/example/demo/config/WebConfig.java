package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.note-image-dir:uploads/note-images}")
    private String noteImageDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(noteImageDir).toAbsolutePath().normalize();
        String location = "file:" + uploadPath.toString() + "/";

        // /note-images/files/**  →  실제 uploads/note-images/** 폴더
        registry.addResourceHandler("/note-images/files/**")
                .addResourceLocations(location);
    }
}
