package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

@DisabledOnOs(OS.LINUX)
public class ApplicationCardTest {

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void constructor_setsDisplayedFieldsCorrectly() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        assertEquals(application, applicationCard.application);
        assertEquals("1. ", getLabelText(applicationCard, "id"));
        assertEquals("Google", getLabelText(applicationCard, "companyName"));
        assertEquals("Intern", getLabelText(applicationCard, "role"));
        assertEquals("91234567", getLabelText(applicationCard, "phone"));
        assertEquals("hr@google.com", getLabelText(applicationCard, "hrEmail"));
        assertFalse(getLabel(applicationCard, "status").isVisible());
        assertFalse(getLabel(applicationCard, "status").isManaged());
        // companyLocation is hidden when empty; its text is not guaranteed.
    }

    @Test
    public void constructor_withCompanyLocation_setsLocationVisibleAndText() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        assertEquals("Google", getLabelText(applicationCard, "companyName"));
        assertEquals("Singapore", getLabelText(applicationCard, "companyLocation"));
    }

    @Test
    public void constructor_withTags_displaysAllTagsSorted() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .withTags("ztag", "atag")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        FlowPane tagsPane = getTagsPane(applicationCard);
        assertEquals(3, tagsPane.getChildren().size());
        assertEquals("atag", ((Label) tagsPane.getChildren().get(0)).getText());
        assertEquals("ztag", ((Label) tagsPane.getChildren().get(1)).getText());
        assertEquals("applied", ((Label) tagsPane.getChildren().get(2)).getText());
    }

    private String getLabelText(ApplicationCard card, String fieldName) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Label label = (Label) field.get(card);
        return label.getText();
    }

    private Label getLabel(ApplicationCard card, String fieldName) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Label) field.get(card);
    }

    private FlowPane getTagsPane(ApplicationCard card) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField("tags");
        field.setAccessible(true);
        return (FlowPane) field.get(card);
    }
}
