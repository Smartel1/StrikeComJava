package ru.smartel.strike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.exsio.nestedj.DelegatingNestedNodeRepository;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.config.jpa.JpaNestedNodeRepositoryConfiguration;
import pl.exsio.nestedj.delegate.control.QueryBasedNestedNodeInserter;
import pl.exsio.nestedj.delegate.control.QueryBasedNestedNodeRebuilder;
import pl.exsio.nestedj.delegate.control.QueryBasedNestedNodeRemover;
import pl.exsio.nestedj.delegate.control.QueryBasedNestedNodeRetriever;
import pl.exsio.nestedj.delegate.query.jpa.JpaNestedNodeIRemovingQueryDelegate;
import pl.exsio.nestedj.delegate.query.jpa.JpaNestedNodeInsertingQueryDelegate;
import pl.exsio.nestedj.delegate.query.jpa.JpaNestedNodeMovingQueryDelegate;
import pl.exsio.nestedj.delegate.query.jpa.JpaNestedNodeRebuildingQueryDelegate;
import pl.exsio.nestedj.delegate.query.jpa.JpaNestedNodeRetrievingQueryDelegate;
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

        // Temporary crutch
        QueryBasedNestedNodeInserter<Long, Conflict> inserter = new QueryBasedNestedNodeInserter<>(new JpaNestedNodeInsertingQueryDelegate<>(configuration));
        QueryBasedNestedNodeRetriever<Long, Conflict> retriever = new QueryBasedNestedNodeRetriever<>(new JpaNestedNodeRetrievingQueryDelegate<>(configuration));
        return new DelegatingNestedNodeRepository<>(
                new QueryBasedNestedNodeMover<>(new JpaNestedNodeMovingQueryDelegate<>(configuration)),
                new QueryBasedNestedNodeRemover<>(new JpaNestedNodeIRemovingQueryDelegate<>(configuration)),
                retriever,
                new QueryBasedNestedNodeRebuilder<>(inserter, retriever, new JpaNestedNodeRebuildingQueryDelegate<>(configuration)),
                inserter
        );
//        return JpaNestedNodeRepositoryFactory.create(configuration);
    }
}
