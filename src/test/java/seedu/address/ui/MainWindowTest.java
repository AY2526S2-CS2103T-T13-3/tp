package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Application;

public class MainWindowTest {

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
    public void fillInnerParts_initializesPersonListPanel() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<MainWindow> mainWindowRef = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                Logic logicStub = new LogicStub();
                MainWindow mainWindow = new MainWindow(new Stage(), logicStub);
                mainWindow.fillInnerParts();
                mainWindowRef.set(mainWindow);
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);

        assertNotNull(mainWindowRef.get());
        assertNotNull(mainWindowRef.get().getPersonListPanel());
    }

    private static class LogicStub implements Logic {

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Application> getFilteredApplicationList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Path getAddressBookFilePath() {
            return Path.of("data", "addressbook.json");
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // no-op
        }
    }
}
