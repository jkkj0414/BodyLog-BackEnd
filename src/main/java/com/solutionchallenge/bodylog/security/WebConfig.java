package com.solutionchallenge.bodylog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(
//                        "http://localhost:8080",
//                        "http://localhost:8443",
//                        "https://bodylog1.duckdns.org"
//                )
//                .allowedMethods(HttpMethod.GET.name(),
//                        HttpMethod.POST.name(),
//                        HttpMethod.PATCH.name(),
//                        HttpMethod.PUT.name(),
//                        HttpMethod.DELETE.name(),
//                        HttpMethod.OPTIONS.name()
//                )
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
        @Override
        public void addCorsMappings(CorsRegistry registry) {
             registry.addMapping("/**")
              .allowedOrigins("*")
                .allowedMethods(
                    HttpMethod.GET.name(),
                    HttpMethod.HEAD.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name()
            );
}


}
