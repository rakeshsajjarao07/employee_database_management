package com.empmanagement.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	 @Override
//	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//	        // Serve /uploads/** from the uploads directory in the project root
//	        String uploadsPath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//	        registry.addResourceHandler("/uploads/**")
//	                .addResourceLocations(uploadsPath)
//	                .setCachePeriod(3600);
//	    }
	
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {

	        String uploadsPath = Paths.get("uploads").toAbsolutePath().toUri().toString();

	        registry.addResourceHandler("/uploads/**")
	                .addResourceLocations(uploadsPath)
	                .setCachePeriod(0);
	    }
    
}
