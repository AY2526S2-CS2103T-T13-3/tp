package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

public class ApplicationCardTest {

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void constructor_setsDisplayedFieldsCorrectly() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompany("Google")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        assertEquals(application, applicationCard.application);
        assertEquals("1. ", getLabelText(applicationCard, "id"));
        assertEquals("Google", getLabelText(applicationCard, "company"));
        assertEquals("Intern", getLabelText(applicationCard, "role"));
        assertEquals("91234567", getLabelText(applicationCard, "phone"));
        assertEquals("hr@google.com", getLabelText(applicationCard, "hrEmail"));
    }

    private String getLabelText(ApplicationCard card, String fieldName) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Label label = (Label) field.get(card);
        return label.getText();
    }
}
