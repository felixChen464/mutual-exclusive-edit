package com.ringcentral.assessment.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import javax.sql.DataSource;

/**
 * @Author cxh
 * @Date 2020/7/11
 */
public class MybatisConfig {

    @Configuration
    public class MybatisConfigurer {

        @Bean
        public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
            SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
            factory.setDataSource(dataSource);
            factory.setTypeAliasesPackage("dao");

            // 添加XML目录
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return factory.getObject();
        }

        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
            mapperScannerConfigurer.setBasePackage("com.ringcentral.assessment.dao");
            return mapperScannerConfigurer;
        }
    }

}
