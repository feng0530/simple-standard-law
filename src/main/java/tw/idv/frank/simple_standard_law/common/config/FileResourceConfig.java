package tw.idv.frank.simple_standard_law.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class FileResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/IR01/**")
//                .addResourceLocations("file:///C:/Users/Frank/Desktop/Projects/simple-standard-law/pdf/IR01/");
        String projectPath = Paths.get("").toAbsolutePath().toString();
        registry.addResourceHandler("/IR01/**")
                .addResourceLocations("file:/" + projectPath + "/pdf/IR01/");
    }
}
