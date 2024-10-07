# CBA test task project
## Account App
This is an Android application that displays account information and a list of transactions using MVVM architecture, Jetpack Compose, 
Hilt for Dependency Injection, Material Design 3, and Coroutines. 
Data is fetched from a local JSON file (exercise.json) stored in the assets folder.

 #### Features: 
	•   Displays account details (balance, available funds, account number).
	•	Displays a scrollable list of transactions grouped by date.
	•	Transactions can be clicked to navigate to a detailed view.
	•	Data is dynamically loaded from a local exercise.json file.
	•	MVVM architecture ensures separation of concerns and clean code principles.
	•	Basic accessibility support (voice over and dynamic text size changes).
	•	Unit testing with JUnit5 and Mockk.

#### Technologies Used

	•	Kotlin: Programming language for all components.
	•	Jetpack Compose: Modern toolkit for building UI natively.
	•	Hilt: For dependency injection.
	•	Coroutines: For handling asynchronous operations.
	•	Material Design 3: UI styling and theming.
	•	MVVM Architecture: To separate business logic and UI concerns.

Getting Started

Prerequisites

Ensure you have the following installed:

	•	Android Studio (latest version)
	•	Gradle 7.0 or later
	•	Kotlin 1.5 or later
	•	Android SDK with API level 21 or higher

#### Setup Instructions

	1.	Clone the repository: git clone https://github.com/irinaabdriaeva/commbank.git
    2.	Open the project in Android Studioand sync the project.

#### Future Enhancements

	•	Add error handling for failed data loading.
	•	Improve the detail screen to show more transaction information.
    •	Implement support for refreshing by pulling the list.
    •	Increase unit tests coverage.
    •	Create ui tests.
	•	Add more accessibility features.

#### License

This project is licensed under the Apache-2.0 license - see the LICENSE file for details.

