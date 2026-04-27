# Software Design and Engineering Enhancement Narrative

## Artifact Description

The artifact selected for this enhancement is the Digital Planner Time application, originally developed in Android Studio using Kotlin and Jetpack Compose. This application was created during earlier coursework as a mobile solution for managing events. It allows users to log in, create and manage events, and optionally interact with system level features such as SMS permissions.

The original version of the application was structured around multiple composable screens, including a login screen, event dashboard, and SMS permission screen. Navigation between these screens was handled manually using a simple state based approach within the main activity. Data was managed through a repository that interfaced with a local SQLite database, allowing events to be stored and retrieved.

This artifact demonstrates foundational skills in software design and engineering, including user interface development, state management, and separation of concerns between the UI and data layers.

---

## Justification for Inclusion

This artifact was selected because it represents one of the most complete and realistic applications developed throughout the program. It showcases my ability to design and implement a functional mobile application using modern Android development practices.

The project highlights several important software engineering concepts. The use of Jetpack Compose demonstrates an understanding of reactive UI design, where the interface updates dynamically based on state changes. The repository pattern shows an effort to separate data access from user interface logic, which improves maintainability and scalability. Additionally, the use of multiple composable screens illustrates an understanding of modular design.

This artifact also provides a strong opportunity for enhancement. While the original implementation was functional, it included areas where the design could be improved, particularly in navigation structure, input validation, and overall usability. These gaps made it a good candidate for demonstrating growth in software design and engineering practices.

---

## Enhancement Overview

The enhancements made to this artifact focused on improving the overall structure, usability, and maintainability of the application.

One of the primary changes was simplifying the system by removing the SMS permission feature. While this feature demonstrated interaction with Android system permissions, it was not essential to the core purpose of the application and added unnecessary complexity. Removing it allowed the design to become more focused and easier to maintain.

The user interface was also improved to create a more polished and consistent experience. Layout adjustments were made to improve spacing, readability, and overall usability. These changes help ensure that the application is easier to navigate and aligns better with standard mobile design expectations.

Additional functionality was introduced to improve event management. This includes enhancements such as improved editing capabilities and the addition of a delete confirmation dialog. These features provide users with more control over their data and reduce the likelihood of accidental actions.

Input validation was also strengthened to ensure that event data is more reliable. By validating fields such as titles, dates, and phone numbers, the application now prevents invalid data from being stored in the system.

---

## Reflection on the Enhancement Process

Through the process of enhancing this artifact, I developed a deeper understanding of software design principles and how they apply to real world applications.

One of the main challenges was recognizing that a working application is not always a well designed application. The original version functioned correctly, but it lacked structure in certain areas and included features that did not align with the core purpose. This experience reinforced the importance of simplicity and focused design.

Another key takeaway was the importance of separation of concerns. While the original project used a repository pattern, the enhancements helped reinforce how important it is to clearly separate user interface logic from data handling and system interactions. This makes the application easier to maintain and extend in the future.

I also learned how small improvements to usability can significantly impact the overall quality of an application. Adding confirmation dialogs and improving layout structure may seem minor, but they contribute to a more professional and user friendly experience.

Throughout this process, I applied feedback from earlier coursework and used it to guide my improvements. This included refining the structure of the application, improving readability, and focusing on features that provide real value to the user.

---

## Alignment with Course Outcomes

This enhancement demonstrates growth in several key course outcomes related to software design and engineering.

It shows the ability to design and develop computing solutions that meet user needs while considering usability and maintainability. The improvements made to the application reflect a more thoughtful approach to system design and user interaction.

It also demonstrates the use of well founded techniques and tools, including modern Android development practices and structured design patterns. The application reflects an understanding of how to build scalable and maintainable software systems.

Finally, this work highlights the ability to evaluate an existing system, identify weaknesses, and implement meaningful improvements. This reflects a transition from simply writing code to thinking critically about software design decisions and their impact on the overall system.

---

## Summary

Overall, the Digital Planner Time application serves as a strong representation of my growth in software design and engineering. The enhancements made to this project improved its structure, usability, and alignment with best practices, while also demonstrating my ability to refine and evolve an existing system into a more complete and professional application.
