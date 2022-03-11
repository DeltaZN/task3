package ru.itmo.task3.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SpringFoxConfig {
    private val API_VERSION = "0.0.1"
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.itmo.task3.controller"))
                .paths(PathSelectors.any())
                .build()
            .apiInfo(apiInfo())
    }

    private fun apiInfo() = ApiInfo(
            "Task 3 Backend",
            "Backend description of API.",
            API_VERSION,
            "Terms of service",
            Contact("", "", ""),
            "License of API",
            "API license URL",
            Collections.emptyList())

}