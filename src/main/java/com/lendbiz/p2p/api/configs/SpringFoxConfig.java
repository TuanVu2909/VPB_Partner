//package com.lendbiz.p2p.api.configs;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///***********************************************************************
// *
// *   @package：com.example.service.auth，@class-name：SpringFoxConfig.java
// *
// *   @copyright       Copyright:   2021-2022
// *   @creator         Hoang Thanh Tu <br/>
// *   @create-time   Apr 19, 2021   9:07:24 AM
// *
// ***********************************************************************/
//@Configuration
//@EnableSwagger2
//public class SpringFoxConfig {
//    @Bean
//    public Docket api() {
//        //Adding Header
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        parameterBuilder.name("RequestId")
//                         .modelRef(new ModelRef("string"))
//                         .parameterType("header")
//                         .defaultValue("TEST")
//                         .required(false)
//                         .build();
//        List<Parameter> lstParameters = new ArrayList<>();
//        lstParameters.add(parameterBuilder.build());             // add parameter
//
//        return new Docket(DocumentationType.SWAGGER_2)
//          .apiInfo(getApiInfo())
//          .securityContexts(Arrays.asList(securityContext()))
//          .securitySchemes(Arrays.asList(apiKey()))
//          .select()
//          .apis(RequestHandlerSelectors.any())
//          .paths(PathSelectors.any())
//          .build()
//          .globalOperationParameters(lstParameters);
//    }
//
//    private ApiInfo getApiInfo() {
//        Contact contact = new Contact("lendbiz.vn", "https://lendbiz.vn", "nguyenlh@lendbiz.vn");
//        return new ApiInfoBuilder()
//                .title("LendBiz Swagger")
//                .description("Service Interface Description")
//                .version("1.0.0")
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
//                .contact(contact)
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//}
