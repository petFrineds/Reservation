package petfriends.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/**/**")
		.allowedOrigins("http://localhost:8084")
				.allowedOrigins("http://localhost:8081")
		.allowedMethods(HttpMethod.GET.name(),
				        HttpMethod.POST.name(),
				        HttpMethod.PUT.name(),
				        HttpMethod.DELETE.name()
				       );
	}
	
}
