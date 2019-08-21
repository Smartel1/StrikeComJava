package ru.smartel.strike.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.model.reference.ConflictReason;

@Service
public class ConflictReasonSeeder implements Seeder {
    private SessionFactory sessionFactory;

    public ConflictReasonSeeder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void seed() {

        Session session = sessionFactory.openSession();

        Long count = (Long) session
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

        session.beginTransaction();

        for (ConflictReason item : array) {
            session.save(item);
        }
        session.getTransaction().commit();
    }
}
