---
layout: page
title: User Guide
---

Hired! is a **desktop app for managing internship applications, optimized for use via a Command Line Interface** (CLI) while still providing the benefits of a Graphical User Interface (GUI). If you can type fast, Hired! can help you manage your internship applications more efficiently than traditional GUI apps.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from the [project release page](https://github.com/AY2526S2-CS2103T-T13-3/tp/releases/tag/v1.0).

1. Copy the file to the folder you want to use as the _home folder_ for Hired!.

1. Open a command terminal, `cd` into the folder you put the jar file in, and run the application using `java -jar hired.jar`.<br>
   A GUI similar to the one below should appear in a few seconds. Note that the app contains some sample application records.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. For example, typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all application records.
    * `add r/Software Engineer p/98765432 e/hr@google.com c/Google t/interview note/Met recruiter at career fair` : Adds an application record for a Software Engineer role at Google with a note.
    * `findnote recruiter` : Finds applications whose notes contain `recruiter`.
    * `delete 3` : Deletes the 3rd application shown in the current list.
    * `clear` : Deletes all application records.
    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are parameters to be supplied by the user.<br>
  e.g. in `add r/ROLE`, `ROLE` is a parameter which can be used as `add r/Software Engineer`.

* Items in square brackets are optional.<br>
  e.g. `r/ROLE [t/TAG]` can be used as `r/Software Engineer t/interview` or as `r/Software Engineer`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]...` can be used as ` ` (i.e. 0 times), `t/interview`, `t/interview t/priority` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `r/ROLE p/PHONE`, `p/PHONE r/ROLE` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding an application: `add`

Adds an application record to Hired!.

Format: `add r/ROLE p/PHONE e/EMAIL c/COMPANY_NAME [l/COMPANY_LOCATION] [t/TAG]... [note/NOTE]`

**Required prefixes:** `r/`, `p/`, `e/`, and `c/` must be provided for a valid `add` command.

**Optional prefixes:** `l/`, `t/`, and `note/` are optional. If provided, they may appear in any order after the required fields.

> **Note:** In Hired!, `r/` is used for the internship role, and `c/` is used for the company name. Applications with the same role and company name are considered duplicates and cannot be added.
> **Tip:** An application can have any number of tags (including 0).
> **Tip:** A note can be added when creating an application by using `note/`.

Examples:
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google`
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google l/Singapore`
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google t/interview t/priority`
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google l/Boon Lay t/interview`
* `add r/Data Analyst p/92345678 e/recruitment@meta.com c/Meta l/Singapore t/applied note/Met recruiter at career fair`

### Listing all applications : `list`

Shows a list of all application records in Hired!.

Format: `list`

### Editing an application : `edit`

Edits the details of an existing application in Hired!.

Format: `edit INDEX [r/ROLE] [p/PHONE] [e/EMAIL] [c/COMPANY_NAME] [l/COMPANY_LOCATION] [t/TAG]... [note/NOTE]`

* Edits the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* At least one of the optional fields must be provided.
* Existing values will be overwritten by the input values.
* When editing tags, the existing tags of the application will be removed, i.e. tag editing is not cumulative.
* You can remove all tags by typing `t/` without specifying any tag after it.
* You can edit an application's note using `note/NOTE`.
* You can clear an existing note by typing `note/` with nothing after it.

Examples:
* `edit 1 p/91234567 e/hr@google.com` edits the phone number and email of the 1st application.
* `edit 2 r/Backend Engineer c/Shopee` edits the role and company of the 2nd application.
* `edit 3 t/urgent t/interview` replaces the tags of the 3rd application with `urgent` and `interview`.
* `edit 4 t/` removes all tags from the 4th application.
* `edit 1 note/Follow up next Monday` updates the note of the 1st application.
* `edit 1 note/` clears the note of the 1st application.

### Locating applications by role: `find`

Finds applications whose roles contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `engineer` will match `Engineer`
* The order of the keywords does not matter.
* Only the role is searched.
* Only full words will be matched.
* Applications matching at least one keyword will be returned (i.e. `OR` search).

Examples:
* `find engineer` returns applications with roles containing `engineer`
* `find backend frontend` returns applications with roles containing `backend` or `frontend`

### Locating applications by note: `findnote`

Finds applications whose notes contain any of the given keywords.

Format: `findnote KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `follow` will match `Follow`
* The order of the keywords does not matter.
* Only the note field is searched.
* Applications matching at least one keyword will be returned (i.e. `OR` search).

Examples:
* `findnote recruiter` returns applications with notes containing `recruiter`
* `findnote follow Monday` returns applications with notes containing `follow` or `Monday`

### Changing the default status: `status`

Changes the status of an application to APPLIED, INTERVIEWING, OFFERED, REJECTED, or WITHDRAWN.
The accepted input keywords are apply, interviewing, offered, rejected, and withdraw, and they are not case-sensitive.

Format: `status INDEX s/STATUS`

* The status is case-insensitive. e.g. `REJECTED` will turn out to be `rejected`
* Only status given above can be chosen.
* Only one application can be changed at a time.
* Status will appear as a tag in the UI.

Examples:
* `status 1 s/OFFERED` changes the status to `offered`
* `status 1 s/selected` will result in an error as `selected` is not a given status.

### Deleting an application : `delete`

Deletes the specified application record from Hired!.

Format: `delete INDEX`

* Deletes the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`

Examples:
* `list` followed by `delete 2` deletes the 2nd application in the displayed list.
* `find engineer` followed by `delete 1` deletes the 1st application in the results of the `find` command.

### Clearing all entries : `clear`

Clears all application records from Hired!.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Hired! data are saved automatically to the hard disk after any command that changes the data. There is no need to save manually.

### Editing the data file

Hired! data are saved automatically as a JSON file at `[JAR file location]/data/applicationList.json`. Advanced users may update data directly by editing that file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file make its format invalid, Hired! may discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Hired! to behave in unexpected ways (for example, if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install Hired! on the other computer and overwrite the empty data file it creates with the data file from your previous Hired! home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add r/ROLE p/PHONE e/EMAIL c/COMPANY_NAME [l/COMPANY_LOCATION] [t/TAG]... [note/NOTE]` <br> e.g. `add r/Data Analyst p/92345678 e/recruitment@meta.com c/Meta l/Singapore t/applied note/Met recruiter at career fair`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g. `delete 3`
**Edit** | `edit INDEX [r/ROLE] [p/PHONE] [e/EMAIL] [c/COMPANY_NAME] [l/COMPANY_LOCATION] [t/TAG]... [note/NOTE]`<br> e.g. `edit 1 note/Follow up next Monday`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g. `find engineer backend`
**Find Note** | `findnote KEYWORD [MORE_KEYWORDS]`<br> e.g. `findnote recruiter follow`
**List** | `list`
**Status** | `status INDEX s/STATUS` <br> e.g. `status 2 s/offered`
**Help** | `help`