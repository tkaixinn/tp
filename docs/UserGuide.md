---
layout: page
title: User Guide
---

Worldly is a **desktop app for exchange students looking to manage their contacts**. It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Worldly will get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Worldly.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar worldly.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list`: Lists all persons.

   * `add name:John Doe phone:98765432 email:johnd@example.com address:John street, block 123, #01-01 channel:EMAIL offset:+08:00`: Adds a person named `John Doe` to the Address Book.

   * `delete 3`: Deletes the 3rd person shown in the current list.

   * `clear`: Deletes all person.

   * `exit`: Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* `UPPER_CASE` indicates the parameters to be supplied by the user.<br>
  e.g. In `add name:NAME`, `NAME` is a parameter which can be used as `add name:John Doe`.

* Parameters in square brackets are optional.<br>
  e.g `name:NAME [tag:TAG]` can be used as `name:John Doe tag:friend` or as `name:John Doe`.

* Parameters with `…`​ after them can be used multiple times, including not at all.<br>
  e.g. `[tag:TAG]...` can be used as ` ` (i.e. 0 times), `tag:friend`, `tag:friend tag:family` etc.

* Parameters can be in any order.<br>
  e.g. If the command specifies `name:NAME phone:PHONE_NUMBER`, `phone:PHONE_NUMBER name:NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. If the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing the help menu: `help`

Shows the description and format of all actions a user can perform with Worldly, as well as the following in separate tabs:
* A table of valid country names for the `country:COUNTRY` field and their corresponding calling codes.
* A table of various regions and their timezones with respect to UTC for reference.
* A table of valid languages for the `language:LANGUAGE` field and the countries that use them.

![help message](images/helpWindow.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add name:NAME phone:PHONE_NUMBER email:EMAIL address:ADDRESS channel:CHANNEL offset:OFFSET [country:COUNTRY] [event:EVENT] [channel:CHANNEL] [language: LANGUAGE] [note:NOTE] [tag:TAG]...`

* A tag with the person's country calling code is automatically added if the phone number starts with `+[COUNTRY CODE]`.
* The channel field may be omitted, but if it is included, it **cannot be left blank**. You must specify one of the allowed channels: PHONE, EMAIL, SMS, WHATSAPP, or TELEGRAM. The channel is **case-insensitive**.
* The offset refers to the timezone offset with respect to UTC and must be specified in +/-HH:MM. You may refer to the help window (with `help`) for a reference table of offset values.
* The language field is optional, but if omitted, defaults to English. It is **case-insensitive** and must match a language in the provided list (in `help`) **exactly**.
* The country field is **case-sensitive** and must match a country in the provided list (in `help`) **exactly**.
* The country, event and note fields can be included and left blank or omitted from the command entirely.
* A person can have any number of tags (including 0). Each tag must be added with `tag:`.

Examples:
* `add name:John Doe phone:98765432 email:johnd@example.com address:John street, block 123, #01-01 channel:EMAIL offset:+08:00 country:Singapore note:does not drink alcohol tag:friends`
* `add name:Betsy Crowe tag:friend email:betsycrowe@example.com address:Newgate Prison phone:1234567 offset:+08:00 channel:TELEGRAM language:chinese tag:criminal`

### Listing all persons: `list`

Shows a list of all persons in the address book. The list is sorted in alphabetical order of the persons' names by default, but if a sort command was previously run (e.g. `sortdate`), it will follow the last requested order.

Format: `list`

### Editing a person: `edit`

Edits an existing person in the address book.

Format: `edit INDEX [name:NAME] [phone:PHONE] [email:EMAIL] [address:ADDRESS] [offset: OFFSET] [country:COUNTRY] [channel: CHANNEL] [language:LANGUAGE] [tag:TAG]...`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the currently displayed person list. The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing country, leaving the prefix blank (i.e. country:) will remove the existing country.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative. You can remove all the person’s tags by typing `tag:` without specifying any tags after it.
* If the currently viewed list is not the main list (e.g. if a `find` command was previously run), the index will refer to that of the **current list**, not the main list.

Examples:
*  `edit 1 phone:91234567 email:johndoe@example.com` edits the phone number and email address of the 1st person in the current list to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 name:Betsy Crower tag:` edits the name of the 2nd person in the current list to be `Betsy Crower` and clears all existing tags.

### Deleting a person: `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the number shown beside a person's name in the currently displayed list.
* The index **must be a positive integer**.

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Finding persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search),
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Finding persons by country: `findcountry`

Finds persons who are from the given country.

Format: `findcountry COUNTRY`

* The search is **case-sensitive**. e.g `Singapore` is a valid country but not `singapore`.
* Refer to the full list of valid country names in the help window.
  ![result for 'findcountry Singapore'](images/findcountry.png)

Examples:
* `findcountry Singapore`

### Finding persons by tag: `findtag`

Finds persons who are tagged with all of the given keywords.

Format: `findtag TAG [MORE_TAGS]`

* The search is **case-insensitive**.
* Only returns persons who match ALL the tags.
* Only full words will be matched e.g. `friend` will not match `friends`.
![result for 'findtag friends owesMoney'](images/findtag.png)

Examples:
* `findtag friends`
* `findtag friends colleagues`

### Archiving a person: `archive`

Archives the person at the specified `INDEX` from the current list.

Format: `archive INDEX`

* Command format and function is similar to delete, except the person is just moved to a separate list accessible
with `archivelist` and can be unarchived at a later time.
* Displayed list will be main list (`list`) after archiving is done, regardless of previously applied filters (e.g. `find`).

Examples:
* `list` followed by `archive 2` archives the 2nd person in the address book.

### Unarchiving a person: `unarchive`

Unarchives the person at the specified `INDEX` from the current list.

Format: `unarchive INDEX`

* Unarchives the person at the specified `INDEX` from the current list, moving them back to the main list (`list`).
* Displayed list will be archive list (`archivelist`) after archiving is done, regardless of previously applied filters (e.g. `find`).

Examples:
* `archivelist` followed by `unarchive 1` unarchives the 1st person in the archive list.

### Listing all archived persons: `archivelist`

Shows a list of all archived persons in the address book.

Format: `archivelist`

### Sorting persons by name: `sortname`

Sorts the persons by alphabetical order of their names.

Format: `sortname`

* This is the default sorting order upon opening the app.

### Sorting persons by country: `sortcountry`

Sorts the persons by country.

Format: `sortcountry`

* Persons are sorted in alphabetical order of their countries.
* Within each country, persons are sorted in alphabetical order of their names.
* Persons without a country are pushed to the end of the list.
* After calling this command, persons remain sorted by country upon calling other commands such as add or delete.
* Refer to the full list of valid country names in the help window.

### Sorting persons by date: `sortdate`

Sorts the persons by the date added.

Format: `sortdate`

* Functions similarly to sortcountry.

### Clearing all entries: `clear`

Clears all entries from the address book, **both in the main list and the archive**.

Format: `clear`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving the data

Worldly's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Worldly data is saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Worldly will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br><br>
Furthermore, certain edits can cause Worldly to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Worldly home folder.

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format and Examples
--------|------------------
**Help** | `help`
**Add** | `add name:NAME phone:PHONE_NUMBER email:EMAIL address:ADDRESS offset:OFFSET [country:COUNTRY] [event:EVENT] [channel:CHANNEL] [language:LANGUAGE] [note:NOTE] [tag:TAG]...` <br> e.g., `add name:John Doe phone:98765432 email:johnd@example.com address:John street, block 123, #01-01 offset:+08:00 country:Singapore channel:email note:does not drink alcohol tag:friends`
**List** | `list`
**Edit** | `edit INDEX [name:NAME] [phone:PHONE_NUMBER] [email:EMAIL] [address:ADDRESS] [offset:OFFSET] [country:COUNTRY] [channel:CHANNEL] [language:LANGUAGE] [tag:TAG]...`<br> e.g., `edit 2 name:James Lee email:jameslee@example.com`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find Country** | `findcountry COUNTRY`<br> e.g., `find Singapore`
**Find Tag** | `findtag TAG [MORE_TAGS]`<br> e.g., `find friends`
**Archive** | `archive INDEX`<br> e.g., `archive 2`
**Unarchive** | `unarchive INDEX`<br> e.g., `unarchive 1`
**List Archived** | `archivelist`
**Sort by Name** | `sortname`
**Sort by Country** | `sortcountry`
**Sort by Date Added** | `sortdate`
**Clear** | `clear`
**Exit** | `exit`
