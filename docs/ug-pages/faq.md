---
layout: page
title: FAQ
---

#### [Back to Menu](../UserGuide.md)

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: First, export the data from your computer using the export command and transfer the file to the new computer. 
Install RC4HDB on the other computer and use the import command to import the data from the csv file.

**Q**: Can I search using fields other than the name?<br>
**A**: You can use the filter command to search for people using the other fields.

**Q**: What is the difference between `list /i n p e` and `showonly n p e`? <br>
**A**: There are two differences. Firstly, calling `list` will result in *all* residents being listed. This means that 
any effects of `find` or `filter` will be erased, and the full resident list will be displayed in the table. Secondly, 
`list /i` (and any other version of `list`) will produce the same result *regardless* of the current state of the table. 
This is unlike `showonly`, which can only restrict the table view based on columns that are already present.  


[Back to Top](#back-to-menu)
