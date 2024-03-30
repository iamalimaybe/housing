package com.legit.housing.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Objects;
import javax.sql.DataSource;

@Profile({"!test"})
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.legit.housing.entity.main", "com.legit.housing.repository.main"},
        entityManagerFactoryRef = "housingEntityManager",
        transactionManagerRef = "housingTransactionManager"
        // TODO repositoryBaseClass = CustomBaseRepositoryImpl.class
)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class DriverDataSource {
    @Bean
    @ConfigurationProperties("spring.datasource.main")
    public DataSource housingDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.jpa.main")
    public JpaProperties housingJpaProperties(JpaProperties jpaProperties) {
        var housingJpaProperties = new JpaProperties();
        var properties = new HashMap<>(jpaProperties.getProperties());
        housingJpaProperties.setProperties(properties);
        return housingJpaProperties;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean housingEntityManager(
        @Qualifier("housingDataSource") DataSource dataSource,
        EntityManagerFactoryBuilder builder,
        JpaProperties housingJpaProperties
    ) {
        return builder.
                dataSource(dataSource).
                properties(housingJpaProperties.getProperties()).
                packages("com.legit.housing.entity.main").
                persistenceUnit("housing").
                build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager housingTransactionManager(
            @Qualifier("housingEntityManager") LocalContainerEntityManagerFactoryBean houEntityManagerFactoryBean
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(houEntityManagerFactoryBean.getObject()));
    }

}
