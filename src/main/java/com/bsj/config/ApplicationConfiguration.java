package com.bsj.config;

import com.bsj.interceptor.BannerContentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/jChan.properties")
public class ApplicationConfiguration implements WebMvcConfigurer {
    @Value("${sqlite.db.path}")
    private String sqliteDatabaseLocation;

    @Value("${uploaded.images.directory}")
    private String uploadedImageDirectory;

    @Autowired
    private BannerContentInterceptor bannerContentInterceptor;

    @Bean
    public DataSource sqliteDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + sqliteDatabaseLocation);
        ds.setDatabaseName("jChan");
        return ds;
    }

    @Bean
    public JdbcTemplate sqliteTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(sqliteDataSource());
        return template;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadedImageDirectory + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bannerContentInterceptor);
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }
}
