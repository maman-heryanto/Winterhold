package com.winterhold;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver(){
        var session = new SessionLocaleResolver();
        var indonesia =  new Locale("id","ID");
        session.setDefaultLocale(indonesia);
        return session;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/landingPage/index");
        registry.addViewController("/author").setViewName("forward:/author/index");
        registry.addViewController("/book").setViewName("forward:/book/index");
        registry.addViewController("/customer").setViewName("forward:/customer/index");
        registry.addViewController("/loan").setViewName("forward:/loan/index");
    }

}
