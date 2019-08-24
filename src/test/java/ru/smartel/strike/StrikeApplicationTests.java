package ru.smartel.strike;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.smartel.strike.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StrikeApplicationTests {

	@PersistenceContext
	EntityManager entityManager;
	@Test
	public void contextLoads() {
	}

	@Test
    public void experiment() {
		long relatedConflictsCount =  entityManager
				.createQuery("select c.id " +
						"from ru.smartel.strike.model.Conflict c " +
						"where c.parentEvent = :event")
				.setMaxResults(1)
				.setParameter("event", entityManager.getReference(Event.class, 21))
				.getResultList().size();
		System.out.println(relatedConflictsCount);
    }
}
