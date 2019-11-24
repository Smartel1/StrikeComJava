package ru.smartel.strike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.config.jpa.JpaNestedNodeRepositoryConfiguration;
import pl.exsio.nestedj.jpa.repository.factory.JpaNestedNodeRepositoryFactory;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class NestedTreeConf {
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public NestedNodeRepository<Long, Conflict> conflictNestedNodeRepository() {
        JpaNestedNodeRepositoryConfiguration<Long, Conflict> configuration = new JpaNestedNodeRepositoryConfiguration<>(
                entityManager, Conflict.class, Long.class
        );
        return JpaNestedNodeRepositoryFactory.create(configuration);
    }
}
