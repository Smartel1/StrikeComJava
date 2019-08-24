package ru.smartel.strike.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.model.reference.ConflictResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class ConflictResultSeeder implements Seeder {
    private EntityManagerFactory entityManagerFactory;

    public ConflictResultSeeder(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void seed() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Long count = (Long) entityManager
                .createQuery("select count(*) from " + ConflictResult.class.getName())
                .getSingleResult();

        if (count > 0) return;

        ConflictResult[] conflictResults = {
                new ConflictResult("Удовлетворены полностью", "Completely satisfied", "Completamente satisfecho"),
                new ConflictResult("Удовлетворены частично", "Partially satisfied", "Parcialmente satisfecho"),
                new ConflictResult("Не удовлетворены", "Not satisfied", "No satisfecho")
        };

        entityManager.getTransaction().begin();

        for (ConflictResult item : conflictResults) {
            entityManager.persist(item);
        }
        entityManager.getTransaction().commit();
    }
}
