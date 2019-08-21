package ru.smartel.strike.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.model.reference.ConflictResult;

@Service
public class ConflictResultSeeder implements Seeder {
    private SessionFactory sessionFactory;

    public ConflictResultSeeder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void seed() {

        Session session = sessionFactory.openSession();

        Long count = (Long) session
                .createQuery("select count(*) from " + ConflictResult.class.getName())
                .getSingleResult();

        if (count > 0) return;

        ConflictResult[] conflictResults = {
                new ConflictResult("Удовлетворены полностью", "Completely satisfied", "Completamente satisfecho"),
                new ConflictResult("Удовлетворены частично", "Partially satisfied", "Parcialmente satisfecho"),
                new ConflictResult("Не удовлетворены", "Not satisfied", "No satisfecho")
        };

        session.beginTransaction();

        for (ConflictResult item : conflictResults) {
            session.save(item);
        }
        session.getTransaction().commit();
    }
}
