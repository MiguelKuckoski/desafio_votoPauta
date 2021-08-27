package com.zallpy.Desafio_unicred.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zallpy.Desafio_unicred.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET,responseMessageForGET())
                .globalResponses(HttpMethod.POST,responseMessageForPOST());

    }


    private List<Response> responseMessageForGET()
    {
        return new ArrayList<Response>() {{
            add(new ResponseBuilder()
                    .code("500")
                    .description("{Default error report}")
                    .build());
            add(new ResponseBuilder()
                    .code("404")
                    .description("{Default error report}")
                    .build());
        }};
    }

    private List<Response> responseMessageForPOST()
    {
        return new ArrayList<Response>() {{
            add(new ResponseBuilder()
                    .code("500")
                    .description("{Default error report}")
                    .build());
            add(new ResponseBuilder()
                    .code("400")
                    .description("{Default error report}")
                    .build());
            add(new ResponseBuilder()
                    .code("404")
                    .description("{Default error report}")
                    .build());
        }};
    }
}
