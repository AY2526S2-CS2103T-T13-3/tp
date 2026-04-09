---
layout: page
title: User Guide
---

# Hired!
A **desktop app for managing internship applications**, optimized for use via a Command Line Interface (CLI) while still providing the benefits of a Graphical User Interface (GUI).

## Who Hired! is for

Hired! is designed for students and jobseekers who are applying to multiple internship roles at the same time and prefer working with keyboard-friendly tools.

It is especially useful for users who:
* prefer ***entering commands quickly*** instead of clicking through many forms
* want to keep application records, deadlines, notes, resume references, and online assessments ***in one place***
* need a ***lightweight*** way to monitor application progress ***across different companies***

## Why using Hired!

Managing internship applications can become messy very quickly. Important details are often scattered across emails, spreadsheets, calendars, note apps, and downloaded files.

Hired! helps you keep the key parts of each application together in a single desktop app, such as:
* the role and company
* your current application status
* important deadlines
* personal notes from interviews, career fairs, or follow-ups
* an attached resume file path
* online assessment details

This makes it easier to review your progress, identify urgent applications, and keep your application process organised.

## How to use this guide

If you are new to Hired!, follow this order:
1. Read [Quick start](#quick-start) to install and launch the app.
2. Read [Quick tour of the interface](#quick-tour-of-the-interface) to understand the main parts of the app.
3. Try the commands in [5-minute quick tutorial](#5-minute-quick-tutorial).
4. Refer to [Features](#features) for detailed command usage.
5. Use [FAQ](#faq), [Known issues](#known-issues), and [Command summary](#command-summary) as quick references later.

This guide focuses on the commands and behaviours that matter most during normal usage. Less common edge cases are covered only when they are important for avoiding confusion.

* Table of Contents
    * [Quick start](#quick-start)
    * [Features](#features)
        * [Default behavior at a glance](#default-behavior-at-a-glance)
        * [Viewing help : `help`](#viewing-help--help)
        * [Adding an application: `add`](#adding-an-application-add)
        * [Listing all applications : `list`](#listing-all-applications--list)
        * [Editing an application : `edit`](#editing-an-application--edit)
        * [Locating applications by role: `find`](#locating-applications-by-role-find)
        * [Locating applications by note: `findnote`](#locating-applications-by-note-findnote)
        * [Changing an application's status: `status`](#changing-an-applications-status-status)
        * [Setting the deadline for an application : `deadline`](#setting-the-deadline-for-an-application--deadline)
        * [Deleting an application : `delete`](#deleting-an-application--delete)
        * [Identifying urgent applications : `reminder`](#identifying-urgent-applications--reminder)
        * [Sorting applications : `sort`](#sorting-applications--sort)
        * [Undoing previous commands : `undo`](#undoing-previous-commands--undo)
        * [Redoing undone commands : `redo`](#redoing-undone-commands--redo)
        * [Attaching your resume : `resume`](#attaching-your-resume--resume)
        * [Opening your resume : `openresume`](#opening-your-resume--openresume)
        * [Removing your resume : `removeresume`](#removing-your-resume--removeresume)
        * [Setting an online assessment : `assessment`](#setting-an-online-assessment--assessment)
        * [Setting an interview : `interview`](#setting-an-interview--interview)
        * [Removing an event : `removeevent`](#removing-an-event--removeevent)
        * [Clearing all entries : `clear`](#clearing-all-entries--clear)
        * [Exiting the program : `exit`](#exiting-the-program--exit)
        * [Saving the data](#saving-the-data)
        * [Editing the data file](#editing-the-data-file)
        * [Archiving data files `[coming in v6.0]`](#archiving-data-files-coming-in-v60)
    * [FAQ](#faq)
    * [Known issues](#known-issues)
    * [Command summary](#command-summary)
    * [Future Improvement](#future-improvement)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.<br>
   You can download the [Oracle version](https://www.oracle.com/java/technologies/downloads/#java17) or another alternative such as the OpenJDK version.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from the [project release page](https://github.com/AY2526S2-CS2103T-T13-3/tp/releases/tag/v1.0).

3. Copy the file to the folder you want to use as the _home folder_ for Hired!.

4. Open a command terminal, `cd` into the folder you put the jar file in, and run the application using `java -jar hired.jar`.<br>
   In case you are curious about how to deal with `cd`, here is [a simple tutorial](https://www.ibm.com/docs/en/aix/7.1.0?topic=directories-changing-another-directory-cd-command).<br>
   A GUI similar to the one below should appear in a few seconds. Note that the app contains some sample application records.<br>
   Moreover, it is normal to see some warning messages printed on your terminal.<br>
   ![Ui](images/Ui_current.png)

5. Type the command in the command box and press Enter to execute it. For example, typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try to have a taste of **Hired!**:

    * `list` : Lists all existing application records.
    * `add r/Software Engineer p/98765432 e/hr@google.com c/Google t/interview note/Met recruiter at career fair` : Adds an application record for a Software Engineer role at Google with a note.
    * `findnote recruiter` : Finds applications whose notes contain `recruiter`.
    * `delete 3` : Deletes the 3rd application shown in the current list.
    * `clear` : Deletes all application records.
    * `exit` : Exits our app.

6. For command details and usage constraints, start from [Features](#features), then use [FAQ](#faq), [Known issues](#known-issues), and [Command summary](#command-summary) as quick references.

## Quick tour of the interface

After launching Hired!, you will mainly interact with these parts of the app:

![Ui](images/Ui_current.png)

### Command box
This is where you type commands such as `add`, `find`, `deadline`, or `status`.

### Result display
After each command, Hired! shows a success or error message to tell you what happened.

### Application list
This is the main list of applications currently being shown.  
Commands such as `list`, `find`, `findnote`, and `sort` can change which applications appear here and in what order.

### Application card
Each application is shown as a card containing details such as:
* role
* company name
* phone and hr's email
* company location, if available
* tags, if available
* note, if available
* deadline, if available
* resume information, if available
* assessment information, if available

### Displayed index
Each card has an index based on the **currently displayed list**.  
Index-based commands such as `edit`, `delete`, `status`, `deadline`, `resume`, and `removeevent` always use the displayed indexes, not a permanent index.

### Event button
If an application has an online assessment attached, a **View Event** button will appear on its card. Clicking it opens a window showing the full assessment details.

### Reminder highlighting
After you run `reminder` at least once, Hired! can highlight application roles based on deadline urgency:
* red: deadline is within the next 3 days, including today
* orange: deadline is already overdue
* default color: no urgent reminder condition applies

## 5-minute quick tutorial

This short tutorial helps you try the most important features of Hired! in a logical order.

### Step 1: Show all applications

Command:
`list`

Expected result:
All applications are shown in the application list. We have prepared some example loaded in the app.

### Step 2: Add a new application

Command:
`add r/Software Engineer Intern p/98765432 e/hr@example.com c/ExampleCorp l/Singapore t/priority note/Met recruiter at career fair`

Expected result:
A new application for `Software Engineer Intern` at `ExampleCorp` is added to the list.

### Step 3: Find the application by role

Command:
`find software`

Expected result:
Only applications whose role contains `software` are shown.

### Step 4: Set a deadline

Command:
`deadline 1 2026-12-31 23:59`

Expected result:
The first displayed application now shows a deadline of `2026-12-31 23:59`.

### Step 5: Change the application status

Command:
`status 1 s/INTERVIEWING`

Expected result:
The first displayed application now has status `INTERVIEWING`.

### Step 6: Enable reminder highlighting

Command:
`reminder`

Expected result:
The displayed list is sorted by deadline, nearest first.  
Applications may now be highlighted based on deadline urgency.

### Step 7: Attach a resume

Command:
`resume 1 rp/Documents/resume.pdf`

Expected result:
The first displayed application now stores a reference to the resume file.

### Step 8: Add an online assessment

Command:
`assessment 1 el/home et/2026-11-20 14:00 ap/HackerRank al/www.hackerrank.com`

Expected result:
The first displayed application now has an assessment attached, and an **Event** button appears on the application card.

### Step 9: Undo and redo

Command:
`undo`

Expected result:
The most recent data-modifying action is reverted.

Command:
`redo`

Expected result:
The undone action is applied again.

### Step 10: Clear

Command:
`clear`

Expected result:
All applications are deleted.

You are all set! Time to start creating your first application! 
After this tutorial, continue with [Features](#features) for the full command reference.
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
  This applies to prefixed parameters of the same command.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Default behavior at a glance

These defaults are useful when you are trying commands for the first time:

* If no data file exists yet, Hired! starts with sample applications.
* `add` creates an application with default values unless you explicitly set them:
    * status defaults to `APPLIED`
    * deadline defaults to `No deadline set` (shown as no deadline in UI)
    * note defaults to empty
* Reminder highlighting is **disabled by default** until you run `reminder` at least once.
* `list`, `find`, and `findnote` change which applications are currently shown; all index-based commands (`edit`, `delete`, `status`, `deadline`, etc.) use the **currently displayed** list indexes.

### Viewing help : `help`

Opens the help window, which contains the User Guide URL.

![help message](images/helpMessage.png)

Format: `help`

### Adding an application: `add`

Adds an application record to Hired!.

Format: `add r/ROLE p/PHONE e/EMAIL c/COMPANY_NAME [l/COMPANY_LOCATION] [t/TAG]... [note/NOTE]`

**Required prefixes:** `r/`, `p/`, `e/`, and `c/` must be provided for a valid `add` command.

**Optional prefixes:** `l/`, `t/`, and `note/` are optional. If provided, they may appear in any order after the required fields.

> **Note:** In Hired!,
> * `r/` is used for the internship role,
> * `p/` is used for the recruiter or company contact phone number,
> * `c/` is used for the company name, and
> * `l/` (optional) is used for the company location.
>
> > Phone numbers are intentionally not restricted to a fixed length, as valid phone number lengths and formats vary across countries.
>
> Applications are considered duplicates (and cannot be added) only when they have the same identity:
> 1) the same `role`,
> 2) the same `company name`, and
> 3) the same `company location`:
     >    * if both locations are empty (e.g. `l/` is omitted), they are treated as the same;
     >    * if one location is empty and the other is not, they are treated as different.
            > **Tip:** An application can have any number of tags (including 0).
            > **Tip:** A note can be added when creating an application by using `note/`.
            > **Default after successful add:** status is `APPLIED`, deadline is unset, and reminder color remains default until `reminder` is enabled.
> For duplicate checking, the comparison ignores letter case and whitespace differences in `role`, `company name`, and `company location`.
> For example, `Software Engineer` and `softwareengineer` are treated as the same role.
> **Tip:** An application can have any number of tags (including 0).
> **Tip:** A note can be added when creating an application by using `note/`.
> **Default after successful add:** status is `APPLIED`, deadline is unset, and reminder color remains default until `reminder` is enabled.

Examples:
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google`
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google l/Singapore`
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google t/interview t/priority`
* `add r/Software Engineer p/98765432 e/hr@google.com c/Google l/Boon Lay t/interview`
* `add r/Data Analyst p/92345678 e/recruitment@meta.com c/Meta l/Singapore t/applied note/Met recruiter at career fair`

### Listing all applications : `list`

Shows a list of all application records in Hired!.

Format: `list`

* Resets any existing `find` / `findnote` filter and shows all applications.

### Editing an application : `edit`

Edits the details of an existing application in Hired!.

Format: `edit INDEX [r/ROLE] [p/PHONE] [e/EMAIL] [c/COMPANY_NAME] [l/COMPANY_LOCATION] [t/TAG]... [s/STATUS] [d/DEADLINE] [note/NOTE]`

* Edits the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* At least one of the optional fields must be provided.
* Existing values will be overwritten by the input values.
* When editing tags, the existing tags of the application will be removed, i.e. tag editing is not cumulative.
* You can remove all tags by typing `t/` without specifying any tag after it.
* You can edit an application's note using `note/NOTE`.
* You can clear an existing note by typing `note/` with nothing after it.
* You can clear an existing deadline using `d/-`.

Examples:
* `edit 1 p/91234567 e/hr@google.com` edits the phone number and email of the 1st application.
* `edit 2 r/Backend Engineer c/Shopee` edits the role and company of the 2nd application.
* `edit 3 t/urgent t/interview` replaces the tags of the 3rd application with `urgent` and `interview`.
* `edit 4 t/` removes all tags from the 4th application.
* `edit 1 note/Follow up next Monday` updates the note of the 1st application.
* `edit 1 note/` clears the note of the 1st application.

### Locating applications by role: `find`

Finds applications whose roles contain all given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `engineer` will match `Engineer`
* The order of the keywords matters. e.g. `soft eng` will match `Software Engineer`.
* Only the role is searched.
* Partial words will also be matched. e.g. `eng` will match `Engineer`
* Applications matching all keywords will be returned, if given more than 1 keyword (i.e. `AND` search).
* At least one keyword must be provided (e.g. `find` alone is invalid).

Examples:
* `find engineer` returns applications with roles containing `engineer`
* `find quant research` returns applications with roles containing `quant` and `research`

### Locating applications by note: `findnote`

Finds applications whose notes contain any of the given keywords.

Format: `findnote KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `follow` will match `Follow`
* The order of the keywords does not matter.
* Only the note field is searched.
* Partial words will also be matched. e.g. `rec` will match `recruit`
* Applications matching at least one keyword will be returned (i.e. `OR` search).
* At least one keyword must be provided (e.g. `findnote` alone is invalid).

Examples:
* `findnote recruiter` returns applications with notes containing `recruiter`
* `findnote follow Monday` returns applications with notes containing `follow` or `Monday`

### Changing an application's status: `status`

* Changes the status of an application.
* The accepted input keywords are ***APPLIED, INTERVIEWING, OFFERED, REJECTED, or WITHDRAWN***, and they are not case-sensitive.

Format: `status INDEX s/STATUS`

* Edits the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* * The status is case-insensitive. e.g. `REJECTED` will turn out to be `rejected`
* Only status given above can be chosen.
* Only one application can be changed at a time.
* Status will appear as a tag in the UI.

Examples:
* `status 1 s/OFFERED` changes the status to `offered`
* `status 1 s/selected` will result in an error as `selected` is not a valid status.

### Setting the deadline for an application : `deadline`

Sets or updates the deadline for the application identified by its index.

Format: `deadline INDEX DATE_TIME`

* The `DATE_TIME` can be `yyyy-MM-dd`, `yyyy-MM-dd HH:mm`.
* Our app do not accept `yyyy-MM-dd HH:60` or any invalid date and time
* Use `-` to clear an existing deadline (e.g. `deadline 2 -`).
* The index refers to the index number shown in the displayed application list.
* This `deadline` refers to the **interview / application-process deadline** for that application.
* This deadline is used by `reminder` and `sort time`.
* This deadline is **not** the same as online assessment or interview time:
    * If you set an event time via `assessment ... et/...` or `interview ... et/...`, that `et/` value belongs to the event only.
    * `reminder` compares only this `deadline` field, not the event `et/` time.

Examples:
* `deadline 1 2026-12-31`
* `deadline 1 2026-12-31 23:59`

Tips: Note that we can change deadline and status by either using their own command, or using the general edit command.
Examples:
* `status 1 s/OFFERED` is equivalent to `edit 1 s/OFFERED`
* `deadline 2 2026-03-25` is equivalent to `edit 2 d/2026-03-25`
  This is intended to give user more flexibility in entering command.
  This is a feature not a bug.

### Deleting an application : `delete`

Deletes the specified application record from Hired!.

Format: `delete INDEX`

* Deletes the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* This deletes the entire application record (not just one field).

Examples:
* `list` followed by `delete 2` deletes the 2nd application in the displayed list.
* `find engineer` followed by `delete 1` deletes the 1st application in the results of the `find` command.

### Identifying urgent applications : `reminder`

Identifies and highlights applications according to how close their `deadline` is to the current local time.
This feature is UI-only: it does **not** add or remove any tags.

Format: `reminder`

* After executing `reminder`, the application list is re-sorted by `deadline` in ascending order (nearest first).
* Applications with no deadline (i.e. deadline is `-` / "No deadline set") are placed at the bottom and are not highlighted.
* Highlighting is based on the comparison between each application's `deadline` and your current local time:
    * **Red** `role` text: the deadline is within the next **3 days**, including today.
    * ![reminder_red.png](reminder_red.png)
    * **Orange** `role` text: the deadline is already **in the past** (i.e. earlier than the current local time).
    * ![reminder_orange.png](reminder_orange.png)
    * Otherwise, `role` keeps the default color (white).
    * ![reminder_default.png](reminder_default.png)
* Once you have executed `reminder`, the highlighting preference is saved, so restarting the app will keep the red/orange coloring behaviour.
* Before you run `reminder` at least once, reminder highlighting is disabled and `role`/calendar colors stay at default.
* Re-running `reminder` repeatedly without any meaningful change (e.g., highlighting already enabled and deadline order unchanged) is treated as a single effective reminder state in undo/redo history.
    * Practical effect: if you entered `reminder` many times in a row, `undo` usually only needs to cross the first effective `reminder` state once to turn highlighting off.
* Deadline format affects how the comparison is done:
    * If you enter `deadline` as `yyyy-MM-dd`, it is treated as a date and compared using the day window (end of day is handled implicitly for highlighting).
    * If you enter `deadline` as `yyyy-MM-dd HH:mm`, the comparison is accurate to **minutes**.
* Interaction with `deadline` and `edit`:
    * If you change a deadline using `deadline INDEX DATE_TIME` or `edit INDEX d/DATE_TIME`, the UI will re-render and the `role` color will immediately reflect the updated deadline (red/orange based on current local time).
    * The **list order** is re-sorted by deadline **only** when you run either:
        * `reminder`, or
        * `sort time`.

* Updating color at an exact time point (datetime `yyyy-MM-dd HH:mm`):
    * **Precondition:** You have already executed `reminder` at least once in this application (otherwise the highlighting is kept disabled and the `role`/calendar icon will stay at default colors until you run `reminder`).
    * Suppose your current local time is `2026-04-02 15:48` and you set an application deadline to `2026-04-02 15:48` (using `deadline INDEX 2026-04-02 15:48` or `edit INDEX d/2026-04-02 15:48`).
    * After setting the deadline:
        * If the current time is still within the same minute (e.g. `15:48:00`), the deadline is **not considered overdue yet** and the `role` stays **red**.
    * After the deadline minute has passed (e.g. `15:48:01` or any time after that), the deadline becomes **overdue** per the rule above.
    * To make the UI apply the new color at that moment:
        * Click the corresponding application list item (the big box / card area of that `INDEX`) so that the UI re-renders that card, **or**
        * Enter `reminder`.
    * Note: this action refreshes **colors** (to reflect the newly overdue deadline).

### Sorting applications : `sort`

Sorts the current list of applications based on a specified criterion.

Format: `sort [CRITERION]`

* The `CRITERION` is required and must be either `time` or `alphabet`.
* `sort time`: Sorts applications by their deadline (nearest first). Applications without deadlines are placed at the bottom.
* `sort alphabet`: Sorts applications alphabetically by their role name (A-Z).

Examples:
* `sort time`
* `sort alphabet`

### Undoing previous commands : `undo`

Undoes the most recent command that modified the application list.

Format: `undo`

* Keeps track of up to 10 steps of your command history.
* Commands that can be undone include add, delete, edit, clear, status, reminder, deadline, sort, resume, removeresume, assessment, interview, and removeevent.
* You cannot undo if there are no more states to revert to in the history.
* For `reminder`: undo/redo restores both list order and reminder highlighting state together.

Examples:
* `delete 1` followed by `undo` restores the deleted application.

### Redoing undone commands : `redo`

Reverses the most recent `undo` command.

Format: `redo`

* You must perform at least one `undo` command before you can use `redo`.
* If you attempt to redo when no undoable state exists, an error message "No undoable state to redo. Please perform an undo first." will be shown.
* If you execute a data-modifying command (e.g.,`add`) after an undo, the `redo` **history** is cleared.

Examples:
* clear followed by `undo` (restores data), then `redo` (clears data again).

### Attaching your resume : `resume`

Attaches your resume to a specific application.

Format: `resume INDEX rp/RESUME_PATH`

* Edits the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* You must specify the path of you resume.
* The `RESUME_PATH` is the absolute address of your file in your computer.
* This feature will not save your resume in the storage, but just a reference to your own documentation.
* Make sure your file is ended with ".pdf, .doc, or .docx", no other format will be accepted.
* Please don't change the path of the file, or it will result in unexpected errors.
* This action can be undone using `undo`.

Examples:
* (For Windows) `resume 1 rp/C:\Users\john\Documents\resume.pdf` will attach your resume to the first application.
* (For Mac) `resume 1 rp/Users/john/Documents/resume.pdf` will attach your resume to the first application.

### Opening your resume : `openresume`

Opens your resume of a specific application.

Format: `openresume INDEX`

* Opens the resume of the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`

Examples:
* `openresume 2` will open your resume of the first application in the default way.

### Removing your resume : `removeresume`

Removes your resume of a specific application.

Format: `removeresume INDEX`

* Edits the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* This feature will not delete your resume file in your computer, but just remove the reference.
* This action can be undone using `undo`.

Examples:
* `removeresume 3` will remove your resume of the third application.

### Setting an online assessment : `assessment`

Attaches online assessment details to a specific application. Once set, an **Event** button will appear on the application card — clicking it opens a window showing the full event details.

Format: `assessment INDEX el/LOCATION et/DATE_TIME ap/PLATFORM al/LINK`

* Sets the online assessment for the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* All four prefixes are **required**:
    * `el/` — location of the assessment (e.g. `home`, `office`).
    * `et/` — date and time of the assessment in `yyyy-MM-dd HH:mm` format.
    * `ap/` — platform used for the assessment (e.g. `HackerRank`, `Codility`).
    * `al/` — link to the assessment (e.g. `www.hackerrank.com`).
* If the application already has an event (assessment or interview), running `assessment` again will **overwrite** the existing one.
* To remove an existing event, use the [`removeevent`](#removing-an-event--removeevent) command.

> **Note:** The datetime must be in `yyyy-MM-dd HH:mm` format exactly. Invalid dates or times (e.g. `2026-13-01 10:00` or `2026-03-24 10:60`) will not be accepted.

Examples:
* `assessment 1 el/home et/2026-03-24 10:00 ap/HackerRank al/www.hackerrank.com`
* `assessment 2 el/office et/2026-06-15 14:30 ap/Codility al/www.codility.com`

### Setting an interview : `interview`

Attaches interview details to a specific application. Once set, an **Event** button will appear on the application card — clicking it opens a window showing the full interview details.

Format: `interview INDEX el/LOCATION et/DATE_TIME [in/INTERVIEWER_NAME] [it/INTERVIEW_TYPE]`

* Sets the interview for the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* Two prefixes are **required**:
    * `el/` — location of the interview (e.g. `Google HQ`, `Zoom`).
    * `et/` — date and time of the interview in `yyyy-MM-dd HH:mm` format.
* Two prefixes are **optional**:
    * `in/` — name of the interviewer (e.g. `John Doe`). Omit if unknown.
    * `it/` — type of interview (e.g. `technical`, `behavioural`, `panel`). Omit if unknown.
* If the application already has an event (assessment or interview), running `interview` again will **overwrite** the existing one.
* To remove an existing event, use the [`removeevent`](#removing-an-event--removeevent) command.
* This action can be undone using `undo`.

> **Note:** The datetime must be in `yyyy-MM-dd HH:mm` format exactly. Invalid dates or times (e.g. `2026-13-01 10:00` or `2026-03-24 10:60`) will not be accepted.

Examples:
* `interview 1 el/Google HQ et/2026-05-10 14:00 in/John Doe it/technical` — sets a technical interview with an interviewer.
* `interview 2 el/Zoom et/2026-06-01 10:00 it/behavioural` — sets a behavioural interview; interviewer name omitted.
* `interview 3 el/Office et/2026-07-15 09:30` — sets an interview with only the required fields.

### Removing an event : `removeevent`

Removes the event (online assessment or interview) attached to a specific application.

Format: `removeevent INDEX`

* Removes the event from the application at the specified `INDEX`.
* The index refers to the index number shown in the displayed application list.
* The index **must be a positive integer** `1, 2, 3, ...`
* If the application at the given index does not have an event, an error message will be shown and no changes will be made.
* After a successful removal, the **Event** button on the application card will be hidden.
* This action can be undone using `undo`.

Examples:
* `removeevent 1` removes the event from the 1st application.
* `removeevent 3` removes the event from the 3rd application.

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

### Archiving data files `[coming in v6.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install Hired! on the other computer and overwrite the empty data file it creates with the data file from your previous Hired! home folder.

**Q**: Why does `find`/`findnote` say invalid format when I type only the command word?<br>
**A**: Both commands require at least one keyword. For example, use `find engineer` or `findnote recruiter`.

**Q**: Why does `delete 1` remove a whole application instead of one field?<br>
**A**: `delete` removes the entire application record at the displayed index. To change only one field, use `edit`, `status`, or `deadline`.

**Q**: Why are reminder colors not shown right after app startup?<br>
**A**: Reminder highlighting is disabled by default. Run `reminder` once to enable and persist highlighting behavior.

**Q**: Why did `openresume` fail even though I attached a resume before?<br>
**A**: Hired! stores only the file path reference, not a copy of the actual file. If the file was moved, renamed, deleted, or is no longer accessible, `openresume` may fail.

**Q**: Why do reminder colors not update automatically at the exact deadline time?<br>
**A**: Reminder color updates are not driven by a background timer. The UI updates those colors when the relevant application card or list is re-rendered, such as after running `reminder` again.

**Q**: Why does the same index refer to a different application after `find`, `findnote`, `sort`, or `list`?<br>
**A**: Index-based commands always use the **currently displayed list**. When the displayed list changes, the index of an application may also change.

**Q**: Does `resume` store my actual resume file inside the app?<br>
**A**: No. Hired! stores only a reference to the file path you provided.

**Q**: Can I use relative file paths for `resume`?<br>
**A**: Yes, as long as the file path points to an existing `.pdf`, `.doc`, or `.docx` file that your system can access.

**Q**: What is the difference between `deadline` and assessment time?<br>
**A**: `deadline` refers to the application or interview-related deadline used by `reminder` and `sort time`. Assessment time is stored separately inside the online assessment event and does not replace the application's deadline field.
--------------------------------------------------------------------------------------------------------------------

## Known issues

* **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
* **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
* **Displayed indexes are context-dependent**: index-based commands (`edit`, `delete`, `status`, `deadline`, etc.) act on the currently displayed list. After `find`, `findnote`, `sort`, or `list`, the same index may refer to a different application.
* **Reminder color updates are not timer-driven**: when real time crosses a deadline minute, colors update on UI refresh actions (e.g., selecting a card, re-running `reminder`, or other list re-render triggers), not by a background timer.
* **Date-only deadlines are day-based**: `yyyy-MM-dd` deadlines are compared at the date level, while `yyyy-MM-dd HH:mm` uses minute-level comparison.

--------------------------------------------------------------------------------------------------------------------
## What you can do with Hired!

With Hired!, you can:
* add and manage internship applications from multiple companies
* record contact details and notes for each application
* track application progress using statuses such as `APPLIED` and `INTERVIEWING`
* set deadlines and quickly identify urgent applications
* attach a resume file reference to each application
* record online assessment details and open them from the application card
* undo and redo recent changes during active usage

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add r/ROLE p/PHONE e/EMAIL c/COMPANY_NAME [l/COMPANY_LOCATION] [t/TAG]... [note/NOTE]` <br> e.g. `add r/Data Analyst p/92345678 e/recruitment@meta.com c/Meta l/Singapore t/applied note/Met recruiter at career fair`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g. `delete 3`
**Edit** | `edit INDEX [r/ROLE] [p/PHONE] [e/EMAIL] [c/COMPANY_NAME] [l/COMPANY_LOCATION] [t/TAG]... [s/STATUS] [d/DEADLINE] [note/NOTE]`<br> e.g. `edit 1 s/OFFERED d/2026-12-31 23:59 note/Follow up next Monday`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g. `find engineer backend`
**Find Note** | `findnote KEYWORD [MORE_KEYWORDS]`<br> e.g. `findnote recruiter follow`
**List** | `list`
**Status** | `status INDEX s/STATUS` <br> e.g. `status 2 s/offered`
**Deadline** | `deadline INDEX DATE_TIME` <br> e.g. `deadline 1 2026-12-31 23:59`
**Reminder** | `reminder` <br> Re-sorts by deadline (nearest first) and highlights the `role` color based on current local time: red within 3 days (incl. today), orange if overdue, default is white.
**Sort** | `sort [CRITERION]` <br> CRITERION: `time` or `alphabet` <br> e.g. `sort time`, `sort alphabet`
**Undo** | `undo` <br> Reverts the most recent data-modifying command (up to 10 steps).
**Redo** | `redo` <br> Reapplies the most recently undone command.
**Resume** | `resume INDEX rp/RESUME_PATH` <br> Attaches a resume to a specific application.
**Open Resume** | `openresume INDEX` <br> Opens a resume of a specific application.
**Remove Resume** | `removeresume INDEX` <br> Removes a resume of a specific application.
**Assessment** | `assessment INDEX el/LOCATION et/DATE_TIME ap/PLATFORM al/LINK` <br> e.g. `assessment 1 el/home et/2026-03-24 10:00 ap/HackerRank al/www.hackerrank.com`
**Interview** | `interview INDEX el/LOCATION et/DATE_TIME [in/INTERVIEWER_NAME] [it/INTERVIEW_TYPE]` <br> e.g. `interview 1 el/Google HQ et/2026-05-10 14:00 in/John Doe it/technical`
**Remove Event** | `removeevent INDEX` <br> e.g. `removeevent 1`
**Exit** | `exit`
**Help** | `help`


## Future Improvement

1. Add richer filter commands (e.g., by status, company, deadline range, and reminder state).
2. Support export/import profiles for easier migration and backup workflows.
3. Add optional automatic reminder refresh strategy (without requiring manual UI refresh actions).
4. Provide clearer in-app error hints with concrete correction examples for invalid command input.
