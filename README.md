# Issue Tracking System (Java + MySQL)

A beginner-friendly console project for practicing Java, OOP, JDBC, SQL, validation, and error handling.

## Features in this starter
- Create an issue with title, description, and priority
- List all issues
- Update issue status (OPEN, IN_PROGRESS, RESOLVED)
- Update issue priority (LOW, MEDIUM, HIGH)
- Validate empty titles, priority/status values, and numeric IDs
- Use `PreparedStatement` for user-provided SQL values
- Display helpful messages for common database failures

## Requirements
- JDK 17 or later
- MySQL Server
- Maven

## Setup
1. Open MySQL Workbench or the MySQL command line.
2. Run the SQL in `sql/schema.sql`.
3. Set environment variables for your database credentials.

Windows PowerShell example (replace the password with your own):
```powershell
$env:ISSUE_DB_URL="jdbc:mysql://localhost:3306/issue_tracker"
$env:ISSUE_DB_USER="root"
$env:ISSUE_DB_PASSWORD="${DB_PASSWORD}"
```
Keep your actual password private and never push it to GitHub.

4. From the project folder, compile:
```bash
mvn clean package
```
5. Run with Maven. If you use an IDE, run `com.mounika.issuetracker.Main`.
A convenient Maven exec plugin can be added later; the simplest beginner option is to run `Main` from IntelliJ IDEA or VS Code with Java support and the Maven dependencies imported.

## Manual test checklist
- Create an issue with a valid title and HIGH priority.
- Try creating an issue with an empty title.
- Try an invalid priority such as URGENT.
- View all issues and verify the created issue appears.
- Update an existing issue to IN_PROGRESS.
- Update an existing issue to RESOLVED.
- Enter an ID that does not exist and confirm the program reports no issue found.
- Enter a non-numeric ID and verify the input prompt recovers.
- Stop MySQL and observe the connection error message.

## Suggested next improvements (build these yourself)
1. Add search by status.
2. Add an issue detail view by ID.
3. Add an `updated_at` column.
4. Add unit tests for validation logic.
5. Add a simple web UI only after the console version works.

## Resume wording after you have built and tested it
**Issue Tracking System | Java, JDBC, MySQL**
- Built a Java console application to create, view, and update software issues stored in MySQL.
- Implemented JDBC database operations, input validation, status/priority updates, and error handling.
- Tested core workflows including issue creation, invalid inputs, and updates for nonexistent IDs.

Only use these bullets after you have implemented and verified the corresponding features.
