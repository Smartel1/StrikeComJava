package ru.smartel.strike.seeder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smartel.strike.model.ConflictReason;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictReasonSeeder implements Seeder {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void seed() {

        Session session = sessionFactory.openSession();

        Long count = (Long)session.createQuery("select count(*) from ru.smartel.strike.model.ConflictReason").getSingleResult();

        if (count > 0) return;

        List<ConflictReason> conflictReasons = new ArrayList<>();
        conflictReasons.add(
                new ConflictReason("Оплата труда","Salary","remuneración")
        );
        conflictReasons.add(
                new ConflictReason("Задержка ЗП","Delay salary","Salarios Atrasados")
        );
        conflictReasons.add(
                new ConflictReason("Сокращения","Contraction","Contracción")
        );
        conflictReasons.add(
                new ConflictReason("Ликвидация предприятия","Liquidation of an enterprise","Liquidación de la empresa")
        );
        conflictReasons.add(
                new ConflictReason("Прочее","Other","Otros")
        );
        conflictReasons.add(
                new ConflictReason("Увольнение","Dismissal","Despido")
        );
        conflictReasons.add(
                new ConflictReason("Политика руководства","Management course","Política de liderazgo")
        );
        conflictReasons.add(
                new ConflictReason("Условия труда","Working conditions","Condiciones de trabajo")
        );
        conflictReasons.add(
                new ConflictReason("Рабочее время","Working time","Horas de trabajo")
        );
        conflictReasons.add(
                new ConflictReason("Коллективный договор","Collective agreement","Convenio colectivo")
        );

        session.beginTransaction();
        conflictReasons.forEach(session::save);
        session.getTransaction().commit();
    }
}
