---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same role as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command role e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

### Target user profile

Hired! is designed for university students who are applying for internships and need to manage multiple
application records, deadlines, contacts, and interview progress efficiently.

Our target users:
* need to manage a significant number of internship applications and related contacts
* prefer a desktop application over mobile notes or spreadsheets
* can type quickly
* prefer typing to mouse interactions
* are reasonably comfortable using CLI applications
* want a structured way to track internship applications, deadlines, and follow-ups

**Value proposition**: Hired! helps university students manage internship applications, company contacts, deadlines, 
                       and interview records faster and more systematically than scattered notes or spreadsheet-based
                       tracking.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a … | I want to … | So that I can… |
| --- | --- | --- | --- |
| `* * *` | student applying for internships | add a new application record | keep track of the companies and roles I applied to |
| `* * *` | student applying for internships | record the HR representative’s role | follow up professionally with companies |
| `* * *` | student applying for internships | record internship roles offered | remember the positions available |
| `* * *` | student applying for internships | record application deadlines | avoid missing submission dates |
| `* * *` | student applying for internships | update the status of an application | track whether my application is pending, interviewing, rejected, or offered |
| `* * *` | student applying for internships | view all my application records | get an overview of my internship applications |
| `* * *` | student applying for internships | sort applications by deadline | handle urgent applications first |
| `* * *` | student applying for internships | delete an application record | remove incorrect or outdated entries |
| `* *` | student applying for internships | search by company role or role | quickly find a specific application |
| `* *` | student applying for internships | categorize companies by industry | organize applications more clearly |
| `* *` | student applying for internships | tag companies by interest level | prioritize which opportunities to focus on |
| `* *` | student applying for internships | record interview dates and format | prepare appropriately for interviews |
| `* *` | student applying for internships | take notes on interview questions | reflect and improve for future interviews |
| `* *` | student applying for internships | filter applications by status | focus on active applications only |
| `*` | student applying for internships | set reminders for deadlines or follow-ups | avoid missing important next steps |
| `*` | long-term user | archive old application records | keep the application list organized |
| `*` | long-term user | export internship records | analyse my application progress outside the app |
| `*` | long-term user | view a summary of application outcomes | understand my internship search performance |

## Use cases

(For all use cases below, the **System** is `Hired!` and the **Actor** is a `student`, unless specified otherwise)

### **UC01 - Add an Application Record**

**Precondition**

* System is running
* Student is authorized.

**MSS**

1. Student requests to add a new internship application.
2. System requests for application details: company, HR role, role, deadline.
3. Student provides the required details.
4. System validates the input.
5. System saves the application and assigns a unique ID.
6. System displays the newly added application.  

   Use case ends.

**Extension**

* 3a. Student leaves some fields unknown. 
  * 3a1. Student marks fields as NA. 
  
    Use case resumes at step 4.

* 4a. System detects invalid input (empty company, wrong date format).
  * 4a1. System shows an error message.
  * 4a2. Student re‑enters valid data. 
  
    Use case resumes at step 4.

* 4b. System detects a duplicate application.
  * 4b1. System informs the student. 
  
    Use case ends.

* *a. Student cancels the add operation at any time.
  * *a1. System aborts the operation. 
  
    Use case ends.



### **UC02 – Update Application Status**

**Precondition** 

* System is running
* Student is authorized
* At least one application exists in the system.

**MSS**

1. Student requests to update an application’s status.
2. Student provides an application ID or list index.
3. System verifies the application exists.
4. Student provides a new status.
5. System validates the status value.
6. System updates the status.
7. System displays the updated application.

   Use case ends.

**Extensions**
* 3a. Application ID/index does not exist.
  * 3a1. System shows an error. 
    
    Use case ends.

* 5a. Status value is invalid.
  * 5a1. System shows valid options.
  * 5a2. Student enters a valid status. 
    
    Use case resumes at step 5.

* 6a. Status is a terminal state (e.g., Rejected/Offered).
  * 6a1. System shows a warning and requests confirmation.
  * 6a2. Student confirms. 
  
    Use case resumes at step 6.

* *a. Student cancels at any time.
  * *a1. System cancels the update. 
  
    Use case ends.


### **UC03 – List and Sort Applications**

**Precondition** 

* System is running.

**MSS**

1. Student requests to view the application list.
2. System retrieves all stored applications.
3. System displays all applications in a structured list.

   Use case ends.

**Extensions**

* 2a. There are no applications.
  * 2a1. System informs the student. 
  * Use case ends.

* 3a. Student requests sorting by deadline.
  *  3a1. System sorts applications by deadline.
  * 3a2. System displays the sorted list. 
  * Use case ends.

* 3b. Student requests filtering by status.
  * 3b1. System filters applications.
  * 3b2. System displays filtered results. 
  
    Use case ends.

### **UC04: Delete an application**

**Precondition**

* System is running
* Student is authorized.
* At least one application exists in the system

**MSS**

1.  Student requests to list applications
2.  Hired! shows a list of applications
3.  Student requests to delete a specific application in the list
4.  Hired! deletes the person

    Use case ends.

**Extensions**

* 1a. The list is empty.
  * 1a1. Hired! informs user the list is empty

    Use case ends.

* 3a. The given index is invalid.

    * 3a1. Hired! shows an error message.

      Use case resumes at step 2.

*{More to be added}*

### Non-Functional Requirements

**Technical Requirements**
1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  The system should operate as a standalone application without requiring an active internet connection.
3.  The system must store all internship and contact data in a _local text file_ (JSON format) and must _not_ require the installation or use of any external Database Management System (e.g., MySQL, PostgreSQL).
4.  The system must be developed using only standard Java `17` libraries and the JavaFX framework. Any third-party libraries must be less than `10MB` in total and must not require separate installation by the user.
5.  The entire application must be packaged into a single executable JAR file not exceeding `100MB`. The user should be able to run it by simply double-clicking the file, provided Java `17` is installed.

**Performance Requirements**
1. Should be able to hold up to `1000` applications without a noticeable sluggishness in performance for typical usage.
2. The system should respond to any valid user command (e.g., adding a company or filtering contacts) within `100` milliseconds under normal usage conditions.

**Usability Requirements**
1.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
2.  All critical features (creating an application, linking a contact) must be _executable entirely_ via keyboard without requiring mouse interaction.
3.  A tech-savvy user should be able to master the basic commands (Add, Find, List, Edit) within `10` minutes of reading the User Guide.

**Reliability & Data Integrity**
1. The system must _automatically_ save all changes to the local storage after every successful state-changing command (e.g., add, edit, delete) to prevent data loss in case of a crash or accidental exit.
2. If a command involves multiple steps, the operation must be atomic. Either both changes are made correctly, or no changes are saved at all.
3. A tech-savvy user should be able to open the data file in a standard text editor and understand the relationships between companies and contacts.
4. The system must be able to read and migrate data files produced by earlier versions of the same product within the same major version.
5. The system should prevent the entry of _logically inconsistent_ data, such as an internship "Deadline" that predates the "Application Date."

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Role**: The position/title applied for.
* **Status**: The current progress state of an application (e.g. SAVED, APPLIED, OA, INTERVIEW, OFFER, REJECTED, WITHDRAWN).
* **Company**: The organisation that offers the opportunity.
* **Record**: The complete collection of information stored in the system about users submitting internship applications to enterprises.
* **Industry Categorization**: The industry labels marked by users for enterprises (such as Internet, finance, manufacturing, etc.)
* **Archive**: Move the internship application records that have completed the process (such as those that have been rejected, hired, and have completed onboarding) from the active list to the archive area.
* **Reminder**: Time reminders for the deadline of internship applications, interview times, and follow-up nodes.
* **Deadline**: The time by which a submission is due for an opportunity record.
* **MSS**: Main Success Scenario. Scenario)The primary, happy-path flow of a use case, describing what happens when everything goes as expected with no errors or exceptions
* **JSON Data File**: A JSON format text file used for local storage of all internship data.
* **Executable JAR**: Package it as a single executable file and double-click to run it.
* **Data Integrity**: Data is automatically saved without loss, logically legal and free of contradictions.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
