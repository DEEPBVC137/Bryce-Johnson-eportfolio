# Informal Code Review

## Overview

This section provides an analysis of the original implementation of the Digital Planner Time application. The goal of this review is to evaluate the existing system prior to enhancement, identify strengths and weaknesses, and explain the reasoning behind the improvements that were later implemented.

---

## Existing Functionality

The Digital Planner Time application is structured using multiple composable screens built with Kotlin and Jetpack Compose. The primary components include a login screen, an event dashboard, and an SMS permission screen.

The LoginScreen serves as the entry point for the application. It collects user input for login and account creation and uses state variables to dynamically update the interface. It also interacts with a repository layer that manages stored data.

The MainActivity acts as the central controller of the application. It manages navigation between screens using a simple state-based approach. A conditional structure determines which screen is displayed based on the current application state.

The EventDashboardScreen provides the main functionality of the application. It allows users to create, edit, and view events. Data is retrieved and stored through a repository connected to a local SQLite database. The screen also includes logic for handling SMS permissions.

The SMSPermissionScreen is responsible for requesting and managing runtime permissions required for sending SMS messages. The interface updates based on whether permission has been granted.

---

## Code Analysis

The original implementation demonstrates a solid foundation in mobile application development. The application separates concerns by dividing responsibilities between UI components and data access through the use of a repository pattern.

The use of Jetpack Compose reflects an understanding of modern UI development and reactive programming. State is managed using mutable state and rememberSaveable, allowing the interface to respond dynamically to user input and lifecycle events.

However, there are several limitations in the original design.

Navigation is handled manually through conditional logic, which works for a small application but does not scale well. As the application grows, this approach can become difficult to maintain.

Input validation is minimal, which introduces the risk of invalid or incomplete data being stored. Error handling is also limited, with no structured approach to managing failures or unexpected input.

Additionally, the SMS permission feature adds complexity without directly supporting the core purpose of the application. This results in unnecessary overhead within the system.

---

## Planned Improvements

Based on this analysis, several improvements were identified and implemented during the enhancement phase.

The SMS permission feature was removed to simplify the application and focus on core functionality. This reduced unnecessary complexity and improved maintainability.

The user interface was refined to improve layout, spacing, and overall usability. These changes create a more consistent and user-friendly experience.

Input validation was strengthened to ensure that event data is accurate and reliable before being stored. This improves data integrity and system reliability.

Additional functionality was introduced to enhance event management, including improved editing capabilities and the addition of a delete confirmation dialog.

Finally, the overall structure of the application was improved to better separate responsibilities between the user interface, business logic, and data handling.

---

## Summary

The original Digital Planner Time application demonstrates a strong starting point in software design and mobile development. The enhancements made to the system address key limitations related to usability, structure, and scalability, resulting in a more refined and maintainable application.
