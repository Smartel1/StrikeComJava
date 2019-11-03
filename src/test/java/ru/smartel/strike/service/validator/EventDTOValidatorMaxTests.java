package ru.smartel.strike.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


class EventDTOValidatorMaxTests {

	@ParameterizedTest
	@MethodSource("providedArguments")
	void maxRule(Object provided, boolean emptyErrors) {
		BaseDTOValidator validator = new BaseDTOValidator();

		Map<String, String> errors = new HashMap<>();

		validator.check(provided, "value", errors).max(5);

		Assertions.assertEquals(errors.isEmpty(), emptyErrors);
	}

	private static Stream<Arguments> providedArguments() {
		return Stream.of(
				Arguments.of(Optional.of(10), false),
				Arguments.of(Optional.of(5), true),
				Arguments.of(Optional.of(3), true),
				Arguments.of(Optional.of(3L), true),
				Arguments.of(Optional.of(0), true),
				Arguments.of(Optional.empty(), true),
				Arguments.of(null, true),
				Arguments.of(10, false),
				Arguments.of(5, true),
				Arguments.of(-10, true),
				Arguments.of("sit", true)
		);
	}
}
