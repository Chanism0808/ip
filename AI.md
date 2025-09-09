Tools Used: ChatGPT 
o	Explaining Java concepts (e.g., static methods, packages, object responsibilities).
o	Providing guidance on class design (e.g., whether TaskList should depend on Ui).
o	Suggesting refactoring strategies (e.g., separating logic into storage, parser, ui, and tasks packages).
o   Create JDocs comments
o   Seek help to assist me with CheckStyle, helping me to edit and to follow desired Coding Style
o   Seek help to assist me with CodeQuality
o   Refactoring suggestions (e.g., extracting helper methods, separating CLI and GUI logic).
o   Explanations of programming concepts (e.g., inheritance vs composition, handling different UI classes).
o   Brainstorming design alternatives (e.g., using a Commands class, functional interfaces).

How AI Was Used Per Increment: Increment A-MoreOOP
Asked AI about Ui responsibilities and how to integrate them into TaskList.
Learned that TaskList should not hold a Ui dependency, and instead the main class should be the one handle printing.

Increment A-Packages
Consulted AI on how to organize code into packages (dupe.parser, dupe.tasks, dupe.storage, dupe.ui) and how to declare package statements properly.
AI clarified why the main class Dupe needs to be in dupe and run as java dupe.Dupe.

General Debugging
Consulted AI when IntelliJ could not find the main class (ClassNotFoundException).
Solution: Run using the fully qualified class name (dupe.Dupe) instead of Dupe.

Observations
	What worked well:
o	AI was extremely useful for explaining design trade-offs, example where Ui should be used.
o	Helpful in debugging package-related errors, which would have taken much longer to figure out manually.

	What didn't work so well:
o	Sometimes AI proposed designs that violated separation of concerns (e.g., putting Ui directly inside TaskList), and I had ask follow-up questions.
o	Some AI-generated code required manual debugging and adjustments (e.g., off-by-one errors in TaskList).

	Overall Productivity:
Using AI sped up my coding process significantly by reducing the amount of debugging effort.
However, I still had to understand the code deeply to adapt AI’s suggestions and ensure correctness.

