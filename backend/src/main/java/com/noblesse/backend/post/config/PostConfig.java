//package com.noblesse.backend.post.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "com.noblesse.backend.post.query.infrastructure.persistence.repository")
//public class PostConfig {
//
//    /**
//     * - 포스트 관련 Spring 설정을 담당
//     *   (JPA, 트랜잭션 관리)
//     */
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean manager = new LocalContainerEntityManagerFactoryBean();
//        manager.setDataSource(dataSource);
//        manager.setPackagesToScan("com.noblesse.backend.post.common.entity");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        manager.setJpaVendorAdapter(vendorAdapter);
//
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        manager.setJpaPropertyMap(properties);
//
//        return manager;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
//
//        return transactionManager;
//    }
//}
