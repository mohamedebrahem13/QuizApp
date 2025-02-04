Quiz App 
Introduction
This Quiz App is an Android application designed to demonstrate modern Android development practices. It is a good example for practicing Clean Architecture, MVI, Room Database, and Jetpack Compose, among other technologies. The app allows users to take a quiz and stores questions locally using Room Database.

Features
Clean Architecture: Separation of concerns into data, domain, and UI layers.
MVI: Implements unidirectional data flow for a predictable state management.
Room Database: Stores quiz questions locally for offline use.
Jetpack Compose: Used for building the UI.
Dagger Hilt: For dependency injection and simplifying class dependencies.
Kotlin: All development is done using Kotlin to ensure modern and concise code.
Mocking Remote Requests: Used dummy data to simulate backend responses.
Horizontal Pager: Smoothly transitions between quiz questions.
Jetpack Navigation: Handles navigation between screens using the new Compose Navigation API.
Technologies Used
Kotlin: The primary language for Android development.
Jetpack Compose: For building a modern UI with less boilerplate.
Room Database: Local database to store quiz questions.
MVI (Model-View-Intent): Ensures unidirectional data flow and predictability.
Dagger Hilt: Dependency Injection to simplify object creation.
KSP: Used with Room for compile-time validation.
Jetpack Navigation: Managing navigation between different screens in a Compose app.
Resource Class: To handle different states (Loading, Success, Error) with Flow.

Architecture
Clean Architecture
The app follows Clean Architecture principles, ensuring a modular structure with clear separation of concerns:

Data Layer: Contains all data-related components like Room Database and network services.
Domain Layer: Holds business logic and entities, ensuring that the app’s logic is independent of external systems.
UI Layer: Responsible for presenting the data and interacting with the user.
MVI (Model-View-Intent)
MVI is used to manage state and handle user actions in a predictable manner. The app is structured as follows:

Model: Represents the data and business logic.
View: Displays the UI based on the data.
Intent: Actions that trigger changes in the model.
Dependency Injection (Dagger Hilt)
Dagger Hilt simplifies dependency injection by generating code that provides necessary objects and simplifies testing. The app uses it for providing instances of ViewModel, Database, and Network components.

App Flow
Quiz Screen: The user is presented with quiz questions.
Result Screen: After completing the quiz, the user sees their score on the result screen.
Getting Started
Prerequisites
Make sure you have the following installed:

Android Studio (latest stable version)
Kotlin (version 1.9 or higher)
Gradle (used for dependency management)
Clone the Repo
Clone this repository to your local machine:

git clone https://github.com/your-username/quiz-app.git
Install Dependencies
Navigate to the project folder and run the following command to download the necessary dependencies:

./gradlew build
Running the App
Once dependencies are installed, you can run the app on your emulator or physical device from Android Studio.

Features Walkthrough
Room Database
We use Room Database to store quiz questions locally. Questions are fetched from a mock service (simulated using dummy data for this example) and stored in the database for offline access.
The database ensures that the user can continue their quiz even if they lose internet connectivity.
Horizontal Pager
To display the quiz questions in a carousel-like format, Jetpack Compose's Horizontal Pager component is used. This gives the user a seamless experience while moving between questions.
Navigation
The app uses Jetpack Compose Navigation with destinations for each screen. Data is passed between screens using serialization.
Dependency Injection with Dagger Hilt
Dagger Hilt is used for providing dependencies across the app. This simplifies the process of managing instances, especially as the project grows in size and complexity.
Testing
Unit tests are written for core functionalities using JUnit and Mockito for mocking remote requests. UI testing is done with Compose Test to ensure smooth UI behavior.

Conclusion
This app serves as a great starting point for practicing modern Android development concepts such as Clean Architecture, MVI, Room, Jetpack Compose, and Dependency Injection. It’s a simple yet powerful example of how to structure an Android app for scalability, maintainability, and ease of testing.

License
MIT License - Feel free to fork, modify, and contribute to this project.

