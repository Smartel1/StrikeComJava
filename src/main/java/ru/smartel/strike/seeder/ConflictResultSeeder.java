package ru.smartel.strike.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smartel.strike.model.ConflictResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictResultSeeder implements Seeder {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void seed() {

        Session session = sessionFactory.openSession();

        Long count = (Long)session.createQuery("select count(*) from ru.smartel.strike.model.ConflictResult").getSingleResult();

        if (count > 0) return;

        List<ConflictResult> conflictResults = new ArrayList<>();
        conflictResults.add(
                new ConflictResult("Удовлетворены полностью","Completely satisfied","Completamente satisfecho")
        );
        conflictResults.add(
                new ConflictResult("Удовлетворены частично","Partially satisfied","Parcialmente satisfecho")
        );
        conflictResults.add(
                new ConflictResult("Не удовлетворены","Not satisfied","No satisfecho")
        );

        session.beginTransaction();
        conflictResults.forEach(session::save);
        session.getTransaction().commit();
    }
}
