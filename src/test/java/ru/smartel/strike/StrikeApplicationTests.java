package ru.smartel.strike;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.smartel.strike.model.User;
import ru.smartel.strike.repository.UserRepository;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrikeApplicationTests {

    @Autowired
    UserRepository userRepository;

	@Test
	public void contextLoads() {
	}

	@Test
    public void user() {
	    User user = userRepository.findFirstByUuid("yBjIs0BELQSsWvyPAHcrGZa2hJi2");
    }
}
