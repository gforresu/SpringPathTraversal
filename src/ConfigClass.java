import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

/**
 * Created by giacomo on 13/10/16.
 */
@Configuration
@EnableWebMvc
public class ConfigClass extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // String rootPath = System.getProperty("user.home");
        //String resourcePath = "file:/"+rootPath + File.separator + "Desktop/";
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("(/resources/");
    }
}