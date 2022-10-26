---
layout: page
title: Viewing Residents
---

#### [Back to Menu](../UserGuide.md)

Often times, you may find yourself overloaded with information. These commands can help to include, and exclude
fields from being seen, find specific residents, and search residents whose fields match a specific keyword.

### Listing all residents : `list`

Lists *all* the residents in the RC4HDB database. If the table view is showing a filtered portion of the residents,
calling `list` will restore and display the full list of residents.

Additionally, the `list` command allows you to specify fields (represented as columns) to **include** or **exclude** when listing! Use `list /i` or `list /e` followed by the first letter of each field to include or exclude from the table.

Format:

- `list` to display *all* residents from the database with *all* columns included in the table
- `list /i LETTER [MORE_LETTERS]` to display *all* residents from the database while **including** in the table view only the fields corresponding to the specified letters.
- `list /e LETTER [MORE_LETTERS]` to display *all* residents from the database while **excluding** from the table view only the fields corresponding to the specified letters.

Examples:

- `list` returns a table of *all* residents from the database with *all* fields included in the view
- `list /i n p e` returns a table with only the *name*, *phone* and *email* fields included in the view
- `list /e r g h` returns a table with all fields except *room*, *gender* and *house* included in the view

Note:

- Each field to be included or excluded from the table should be entered as a *single letter*, that is, the first letter of the corresponding field name.
- The relative order of each letter *does not matter*, and the letters can be in either upper or lower case. Duplicate letters are ignored.
- Only letters corresponding to the first letter of a valid field in the table can be specified, *any other letter will be considered invalid*.
- Letters *must* be separated by a single whitespace.
- There needs to be at least one field (and hence column) included in the table view at all times.

*(For advanced users!)* The `list` command, as well as the `list /i` and `list /e` extensions, are [*idempotent*](glossary.md#idempotent) and [*state-independent*](glossary.md#state-independent). This means that using the `list` command with a set of *(optional)* letters will return the same result regardless of what the current table looks like. Calling the same command again will not change the table view any further.

[Back to Top](#back-to-menuuserguidemd)

---

### Showing only some columns : `showonly`

Shows only the specified columns in the **current** table view.

If your screen is too cluttered, just use `showonly` to show only the columns you need! This command works similar to `list /i`, with two key differences:

1. You can only use `showonly` on existing columns in the current table view, and
2. The `showonly` command does not modify the list of residents being displayed. Filtered residents stay filtered!

Format: `showonly LETTER [MORE_LETTERS]`

Examples **(sequential)**:

- `showonly n p e` on a full table returns a table with only the *name*, *phone* and *email* columns shown.
- Calling `showonly r g h` on the table from the previous point is **invalid** as the *room*, *gender* and *house* columns are not shown in the present table.
- However, calling `showonly n e` on said table is **valid**, and will return a table with only the *name* and *email* columns shown.

Note:

- Like in `list`, each column to be shown should be entered as a single letter that corresponds to the first letter of the column to be shown.
- The relative order of each letter does not matter, and the letters can be in either upper or lower case. Duplicate letters are ignored.
- Similarly, letters *must* be separated by a single whitespace.
- Only valid letters can be specified, and there needs to be at least one column shown in the table at all times.
- You can always `reset` the table to the full, default view at any time!

*(For advanced users!)* Notice that `showonly`, unlike `list /i`, is dependent on the state of the current table. Hence, some calls to `showonly` may be invalid if the specified columns are not present in the current table view.

[Back to Top](#back-to-menuuserguidemd)

---

### Hiding only some columns : `hideonly`

Hides only the specified columns in the **current** table view.

Use `hideonly` if there are more columns to show than hide. Like `showonly`, `hideonly` differs from `list` in the following ways:

1. You can only use `hideonly` on existing columns in the current table view, and
2. The `hideonly` command does not modify the list of residents being displayed. Residents found using `find` stay displayed in the table!

Format: `hideonly LETTER [MORE_LETTERS]`

Examples **(sequential)**:

- `hideonly n p e m t` on a full table returns a table with only the *name*, *phone*, *email*, *matric* and *tags* columns hidden. In other words, we get a table showing only the *index*, *room*, *gender* and *house* columns.
- Calling `hideonly n e` on the table from the previous point is **invalid** as the *name* and *email* columns are not currently shown in the present table.
- However, calling `hideonly i h` on said table is **valid**, and will return a table with only the *room* and *gender* columns shown, as the *index* and *house* columns have been hidden.

Note:

- Like in `list`, each column to be hidden should be entered as a single letter that corresponds to the first letter of the column to be shown.
- The relative order of each letter does not matter, and the letters can be in either upper or lower case. Duplicate letters are ignored.
- Similarly, letters *must* be separated by a single whitespace.
- Only valid letters can be specified, and there needs to be at least one column shown in the table at all times.
- You can always `reset` the table to the full, default view at any time!

*(For advanced users!)* Notice that `hideonly`, unlike `list /i`, is dependent on the state of the current table. Hence, some calls to `hideonly` may be invalid if the specified columns are not present in the current table view.

[Back to Top](#back-to-menuuserguidemd)

---

### Resetting hidden columns : `reset`

Resets the columns in the table to the default view with *all* columns visible.

Use this when you have called `showonly` or `hideonly` multiple times!

Format: `reset`

Note:
- Any input entered after the `reset` command will be ignored.
- This command is different from the `list` command in that it does not affect the list of residents being displayed.

[Back to Top](#back-to-menuuserguidemd)

---

### Locating residents by name : `find`

Finds residents whose names contain any of the given keywords.

Format: `find NAME [ADDITIONAL_NAMES]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Full and partial words will be matched e.g. `Han` will match `Hans`
* Residents matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`
* `find char li` returns `Charlotte Oliveiro`, `David Li`<br>
  <!--- ![result for 'find alex david'](images/findAlexDavidResult.png) --->

[Back to Top](#back-to-menuuserguidemd)

---

### Filtering residents by field : `filter`

Shows a list of residents whose fields match the input keywords.

Format: `filter KEY/VALUE [ADDITIONAL_KEYS/ADDITIONAL_VALUES]`
* The fields have to be the same (no substrings allowed) for the resident to be filtered.
* Commands with multiple fields require the resident to match all the fields to be filtered.
* Valid keys are those included [here](#format-for-resident-fields), and any additional tags.

Examples:
* `filter h/D g/M` returns residents who are in Draco house, **and** are Male.
* `filter g/M` returns residents who are male.

[Back to Top](#back-to-menuuserguidemd)

---
