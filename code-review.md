# Informal Code Review

## Overview

This informal code review evaluates the original implementations of three artifacts developed throughout the Computer Science program. The purpose of this review is to examine the functionality of each system prior to enhancement, analyze strengths and limitations, and outline the improvements that were later implemented.

The artifacts are presented in the following order:
- Algorithms and Data Structures  
- Databases  
- Software Design and Engineering  

Each section includes an overview of functionality, a code analysis, and planned improvements.

---

# Algorithms and Data Structures

## Existing Functionality

The original artifact is a Java-based Pet Boarding System designed using object oriented programming principles. The system includes a base Pet class and derived classes such as Dog and Cat, which demonstrate inheritance and specialization.

Each class defines attributes such as name, age, number of days staying, and owner information. The Dog class extends the base class by adding a grooming attribute. Getter and setter methods are used to access and modify data, demonstrating encapsulation.

The program is executed through the BoardingDriver class, where objects are manually created and displayed. Each object stores its own data, and output is generated using display methods. The system operates with a small number of predefined objects and does not allow for dynamic interaction.

## Code Analysis

The original implementation demonstrates a solid understanding of object oriented design concepts, including inheritance, polymorphism, and encapsulation. The structure is organized and readable, making it easy to understand how data is stored and accessed.

However, the system has several limitations. All data is hardcoded in the main method, which prevents scalability and flexibility. The program does not use any collection-based data structures, such as lists or maps, to manage multiple objects dynamically.

There are also no algorithms for searching, sorting, or managing data beyond simple object creation and display. This limits the system’s ability to handle larger or more complex datasets.

## Planned Improvements

To address these limitations, the system was enhanced by introducing a dynamic data structure, such as an ArrayList, to store and manage multiple pet objects.

A management layer was added to handle operations such as adding pets, storing them in a collection, and iterating through the data. This improves scalability and separates responsibilities within the program.

Additional improvements include the introduction of basic algorithms for searching and organizing data. These changes transform the system from a static example into a more flexible and functional application.

---

# Databases

## Existing Functionality

The original database artifact consists of SQL queries and table creation performed in MySQL. The work includes defining table structures, such as a customer table, and executing queries to analyze data.

The queries demonstrate the use of joins, grouping, and aggregation functions. For example, queries were used to count returns by state and calculate percentages of returns by product. The results were presented through screenshots showing query outputs.

The original work focuses on individual SQL operations rather than a fully integrated system. Data analysis is performed using static queries without a structured workflow or dynamic interaction.

## Code Analysis

The original implementation demonstrates a foundational understanding of relational database concepts, including table creation, primary keys, joins, and aggregation.

The use of multiple joins shows the ability to connect related data across tables, and grouping functions demonstrate an understanding of how to summarize data effectively.

However, the system is limited in several ways. The database design is not fully developed, and relationships between tables are not clearly defined through a complete schema. The work relies heavily on static queries and does not include reusable or parameterized logic.

Additionally, the artifact does not include a method for importing or managing larger datasets, which limits its practicality in real-world scenarios.

## Planned Improvements

The enhanced version addresses these limitations by developing a complete relational database schema. Multiple related tables were created, including customers, orders, products, and returns, with proper use of primary and foreign keys.

A Python script was introduced to import data from CSV files into the database, allowing the system to work with a larger and more realistic dataset.

Additional queries were developed to perform more advanced analysis, including multi-table joins and aggregated calculations. These improvements create a more functional and scalable database system that better reflects real-world use.

---

# Software Design and Engineering

## Existing Functionality

The original artifact is the Digital Planner Time application developed using Kotlin and Jetpack Compose. The application includes multiple composable screens, such as a login screen, event dashboard, and SMS permission screen.

The LoginScreen handles user input and authentication, while the EventDashboardScreen allows users to create, edit, and view events. Data is managed through a repository connected to a local SQLite database.

Navigation is controlled through the main activity using a state-based approach. The application uses conditional logic to determine which screen is displayed at any given time.

The SMSPermissionScreen manages runtime permissions required for sending SMS messages and updates the interface based on permission status.

## Code Analysis

The original implementation demonstrates a strong understanding of modern Android development practices. The use of Jetpack Compose reflects knowledge of reactive UI design, and the repository pattern supports separation of concerns between the user interface and data layer.

State management is handled effectively using mutable state and rememberSaveable, allowing the application to respond dynamically to user input and lifecycle changes.

However, there are several limitations in the design. Navigation is handled manually using conditional logic, which does not scale well as the application grows. Input validation is limited, which can lead to invalid data being stored.

Error handling is minimal, and the application lacks a structured approach to managing failures. The SMS permission feature introduces additional complexity without directly supporting the core purpose of the application.

## Planned Improvements

The application was enhanced by simplifying its overall structure and focusing on core functionality. The SMS permission feature was removed to reduce unnecessary complexity.

The user interface was refined to improve layout, readability, and usability. Input validation was strengthened to ensure that data entered by users is accurate and reliable.

Additional functionality was introduced to improve event management, including enhanced editing capabilities and a delete confirmation dialog.

The overall structure of the application was improved to better separate responsibilities between the user interface, business logic, and data handling, resulting in a more maintainable and scalable design.

---

## Summary

This code review highlights the progression of each artifact from its original implementation to a more refined and functional system. Across all three artifacts, the enhancements focus on improving scalability, usability, and overall system design.

These changes demonstrate growth in the ability to evaluate existing systems, identify limitations, and implement meaningful improvements that align with best practices in software development.
