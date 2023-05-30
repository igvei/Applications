package ru.stars.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.stars")
@EnableWebMvc //dasdas

//Реализация интерфейса WebMvcConfigurer = настраиваем под себя Spring MVC (вместо стандартного шаблонизатора мы хотим использовать Thymeleaf)
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    //Внедрение зависимости ApplicationContext
    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //Создание объекта для настройки пути к файлам представлений
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/"); //задаем папку с представлениями
        templateResolver.setSuffix(".html"); //задаем расширение представлений
        return templateResolver;
    }

    //Создание объекта для обработки файлов представлений
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();   //Создание компонента для генерации HTML страниц на основе шаблонов с динамическими данными
        templateEngine.setTemplateResolver(templateResolver());     //Устанавливаем ранее заданный templateResolver
        templateEngine.setEnableSpringELCompiler(true);     //Включение компилятора  SpringEL
        return templateEngine;
    }

    //Подключение Thymeleaf
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();    //Создаем объект шаблонизатор
        resolver.setTemplateEngine(templateEngine());   //Устанавливаем объект SpringTemplateEngine в качестве шаблонизатора
        registry.viewResolver(resolver);    //Регистрируем экземпляр resolver
    }
}
