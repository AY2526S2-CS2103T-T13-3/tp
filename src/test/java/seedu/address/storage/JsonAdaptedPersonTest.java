package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {

    private static final String VALID_REMARK = "friend";

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        Person person = new PersonBuilder().withRemark(VALID_REMARK).build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, "98765432", "alice@example.com", "123 street", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "@lice", "98765432", "alice@example.com", "123 street", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Alice", null, "alice@example.com", "123 street", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Alice", "abc", "alice@example.com", "123 street", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Alice", "98765432", null, "123 street", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Alice", "98765432", "invalid", "123 street", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Alice", "98765432", "alice@example.com", null, VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Alice", "98765432", "alice@example.com", "", VALID_REMARK, List.of());

        assertThrows(IllegalValueException.class, person::toModelType);
    }
}