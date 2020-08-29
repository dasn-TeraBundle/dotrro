package com.innova.doctrro.ps.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() throws IOException {
        return new JCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public JCacheManagerFactoryBean ehCacheCacheManager() throws IOException {
        JCacheManagerFactoryBean cmfb = new JCacheManagerFactoryBean();
        cmfb.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
        return cmfb;
    }
}
