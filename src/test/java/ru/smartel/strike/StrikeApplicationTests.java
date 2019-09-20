package ru.smartel.strike;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
    public void experiment() {
    }
}
