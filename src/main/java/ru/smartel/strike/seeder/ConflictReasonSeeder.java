package ru.smartel.strike.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.model.reference.ConflictReason;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class ConflictReasonSeeder implements Seeder {
    private EntityManagerFactory entityManagerFactory;

    public ConflictReasonSeeder(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void seed() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Long count = (Long) entityManager
                .createQuery("select count(*) from " + ConflictReason.class.getName())
                .getSingleResult();

        if (count > 0) return;

        ConflictReason[] array = {
                new ConflictReason("Оплата труда", "Salary", "remuneración"),
                new ConflictReason("Задержка ЗП", "Delay salary", "Salarios Atrasados"),
                new ConflictReason("Сокращения", "Contraction", "Contracción"),
                new ConflictReason("Ликвидация предприятия", "Liquidation of an enterprise", "Liquidación de la empresa"),
                new ConflictReason("Прочее", "Other", "Otros"),
                new ConflictReason("Увольнение", "Dismissal", "Despido"),
                new ConflictReason("Политика руководства", "Management course", "Política de liderazgo"),
                new ConflictReason("Условия труда", "Working conditions", "Condiciones de trabajo"),
                new ConflictReason("Рабочее время", "Working time", "Horas de trabajo"),
                new ConflictReason("Коллективный договор", "Collective agreement", "Convenio colectivo")
        };

        entityManager.getTransaction().begin();

        for (ConflictReason item : array) {
            entityManager.persist(item);
        }
        entityManager.getTransaction().commit();
    }
}
