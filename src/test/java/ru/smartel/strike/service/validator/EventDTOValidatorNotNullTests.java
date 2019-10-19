package ru.smartel.strike.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.smartel.strike.service.BaseDTOValidator;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


@SpringBootTest
class EventDTOValidatorNotNullTests {

	@ParameterizedTest
	@MethodSource("providedArguments")
	void notNullRule(Object provided, boolean emptyErrors) {
		BaseDTOValidator validator = new BaseDTOValidator(Mockito.mock(EntityManager.class));

		Map<String, String> errors = new HashMap<>();

		validator.check(provided, "value", errors).notNull();

		Assertions.assertEquals(errors.isEmpty(), emptyErrors);
	}

	private static Stream<Arguments> providedArguments() {
		return Stream.of(
				Arguments.of("lorem ipsum", true),
				Arguments.of("", true),
				Arguments.of(123, true),
				Arguments.of(0, true),
				Arguments.of(123L, true),
				Arguments.of(Integer.valueOf(1), true),
				Arguments.of(null, false)
		);
	}
}
