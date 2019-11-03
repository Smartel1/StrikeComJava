package ru.smartel.strike.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


class EventDTOValidatorMinLengthTests {

	@ParameterizedTest
	@MethodSource("providedArguments")
	void minLengthRule(Object provided, boolean emptyErrors) {
		BaseDTOValidator validator = new BaseDTOValidator();

		Map<String, String> errors = new HashMap<>();

		validator.check(provided, "value", errors).minLength(5);

		Assertions.assertEquals(errors.isEmpty(), emptyErrors);
	}

	private static Stream<Arguments> providedArguments() {
		return Stream.of(
				Arguments.of(Optional.of("lorem ipsum"), true),
				Arguments.of(Optional.of("lorem"), true),
				Arguments.of(Optional.of(""), false),
				Arguments.of(Optional.of(1), true),
				Arguments.of(Optional.of(1L), true),
				Arguments.of(Optional.of(new Object()), true),
				Arguments.of(Optional.empty(), true),
				Arguments.of(null, true),
				Arguments.of("lorem", true),
				Arguments.of("sit", false)
		);
	}
}
