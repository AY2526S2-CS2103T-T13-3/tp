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
import seedu.address.model.application.Status;
import seedu.address.testutil.ApplicationBuilder;

@DisabledOnOs(OS.LINUX)
public class ApplicationCardTest {

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        System.setProperty("prism.order", "sw");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("The JavaFX toolkit has timed out during startup. "
                    + "Please check if the JDK architecture matches");
        }
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

        Label companyLocationLabel = getLabel(applicationCard, "companyLocation");
        assertFalse(companyLocationLabel.isVisible());
        assertFalse(companyLocationLabel.isManaged());

        Label deadlineLabel = getLabel(applicationCard, "deadline");
        assertFalse(deadlineLabel.isVisible());
        assertFalse(deadlineLabel.isManaged());
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
    public void constructor_withDeadline_setsDeadlineVisibleAndText() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .withDeadline("2026-12-31")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label deadlineLabel = getLabel(applicationCard, "deadline");
        assertTrue(deadlineLabel.isVisible());
        assertTrue(deadlineLabel.isManaged());
        assertEquals("Deadline: " + application.getDeadline().value, getLabelText(applicationCard, "deadline"));
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

    @Test
    public void constructor_statusLabelHiddenAndStatusTagAdded() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusLabel = getLabel(applicationCard, "status");
        FlowPane tagsPane = getTagsPane(applicationCard);

        assertFalse(statusLabel.isVisible());
        assertFalse(statusLabel.isManaged());

        assertTrue(tagsPane.getChildren().stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().equals("applied")));
    }

    @Test
    public void constructor_withExistingTags_addsStatusTagAsWell() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .withTags("atag", "ztag")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        FlowPane tagsPane = getTagsPane(applicationCard);

        assertEquals(3, tagsPane.getChildren().size());
        assertEquals("atag", ((Label) tagsPane.getChildren().get(0)).getText());
        assertEquals("ztag", ((Label) tagsPane.getChildren().get(1)).getText());
        assertEquals("applied", ((Label) tagsPane.getChildren().get(2)).getText());
    }

    @Test
    public void constructor_withoutUserTags_addsOnlyStatusTag() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        assertEquals(1, tagsPane.getChildren().size());
        assertEquals("applied", ((Label) tagsPane.getChildren().get(0)).getText());
    }

    @Test
    public void constructor_withDifferentStatus_statusTagMatchesLowercaseStatus() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .withStatus(Status.OFFERED)
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        assertTrue(tagsPane.getChildren().stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().equals("offered")));
    }

    @Test
    public void constructor_withRegularTags_regularTagsDoNotUseReminderRedStyle() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .withTags("atag", "ztag")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        Label firstTag = (Label) tagsPane.getChildren().get(0);
        Label secondTag = (Label) tagsPane.getChildren().get(1);

        assertEquals("atag", firstTag.getText());
        assertEquals("ztag", secondTag.getText());
        assertFalse(firstTag.getStyle().contains("#FF0000"));
        assertFalse(secondTag.getStyle().contains("#FF0000"));
    }

    @Test
    public void constructor_withUrgentTag_setsRedBackgroundStyle() throws Exception {
        // 1. 准备带有 ReminderCommand 中定义的 "Urgent" 标签的申请
        String reminderTag = seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;
        Application application = new ApplicationBuilder()
                .withTags(reminderTag)
                .build();

        // 2. 创建 Card 实例
        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        // 3. 利用反射获取生成的 Label 并验证样式
        Label urgentLabel = (Label) tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals(reminderTag))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Urgent tag label not found"));

        // 4. 验证是否应用了红色背景样式
        String style = urgentLabel.getStyle();
        assertTrue(style.contains("-fx-background-color: #FF0000"),
                "Urgent tag should be red. Current style: " + style);
    }

    @Test
    public void constructor_withUppercaseUrgentTag_stillSetsRedStyle() throws Exception {
        Application application = new ApplicationBuilder()
                .withTags("URGENT")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        Label urgentLabel = (Label) tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals("URGENT"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Uppercase urgent tag label not found"));

        assertTrue(urgentLabel.getStyle().contains("#FF0000"));
        assertTrue(tagsPane.getChildren().stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().equals("applied")));
    }

    @Test
    public void constructor_afterRedoState_displaysUpdatedInfo() throws Exception {
        // 模拟 Redo 执行后，数据被恢复到之前的状态 (例如 Deadline 和 Status 发生了变化)
        Application redoStateApp = new ApplicationBuilder()
                .withRole("Cloud Engineer")
                .withStatus(Status.OFFERED)
                .withDeadline("2026-05-20")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(redoStateApp, 2);

        // 验证 UI 是否正确展示了恢复后的数据
        assertEquals("2. ", getLabelText(applicationCard, "id"));
        assertEquals("Deadline: 2026-05-20", getLabelText(applicationCard, "deadline"));

        // 验证 Status 标签是否正确渲染为小写
        FlowPane tagsPane = getTagsPane(applicationCard);
        assertTrue(tagsPane.getChildren().stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().equals("offered")));
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
