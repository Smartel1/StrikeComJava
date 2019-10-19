### Optional<T> fields may be null, Optional.empty or non-empty.

1. If dto field equals to null - client request body doesnt have such field;

2. If dto field equals to Optional.empty - request have field = null.

3. Otherwise dto field equals to non-empty Optional

Use Optional if business logic assumes that null/non specified are different states of field.