package cn.baizhi.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 时间:2021.6.4
* 内容:全局跨域设置
* 作者:曾健林
*
* */
@Configuration
public class CornConfig implements WebMvcConfigurer {
    //全局跨域设置
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("跨域处理进入");
        registry.addMapping("/**") //设置跨域访问的内容 /**表示所有
                .allowedOrigins("http://127.0.0.1:8989") //设置跨域访问的url
                .allowedHeaders("/**")  //设置访问的响应头
                .allowedMethods("/**")  //设置访问的方法
                .maxAge(30*1000)  //设置探测请求间隔
                .allowCredentials(true);

    }*/

    /*
    * 跨域设置
    * */
    @Bean
    public FilterRegistrationBean corsFilter() {
        System.out.println("跨域设置进入");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
