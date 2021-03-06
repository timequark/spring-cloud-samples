package com.example.demoserviceprovider.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    public static final String BASE_PACKAGE = "com.example.demoserviceprovider.controller";

    @Value("${app.swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        // Add head parameters
        /*ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("ACCESS_TOKEN").description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        pars.add(tokenPar.build());*/

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enableSwagger) // 生产环境的时候关闭 swagger 比较安全
                .directModelSubstitute(Timestamp.class, Long.class) //将Timestamp类型全部转为Long类型
                .directModelSubstitute(Date.class, Long.class) //将Date类型全部转为Long类型
                .select()        // 扫描接口的包路径，不要忘记改成自己的
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
                //.globalOperationParameters(pars);

    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger RESTful API")
                .description("Powered by Hong Que")
                .termsOfServiceUrl("http://swagger.io/")
                //.contact(contact)
                .version("1.0")
                .build();
    }
}
