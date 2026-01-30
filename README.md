# Harry Potter Characters App ⚡🦉

A modern, high-performance Android application built with Jetpack Compose that allows users to explore characters from the Harry Potter universe. The app features a stunning "magical" aesthetic with house-specific themes and offline-first capabilities.

## ✨ Features

- **Character Exploration**: Browse a comprehensive list of characters from the Wizarding World.
- **Detailed Profiles**: View in-depth information about each character, including their house, wand, actor, and more.
- **Offline-First**: Data is cached locally using Room, ensuring a seamless experience even without an internet connection.
- **Paging Support**: High-performance scrolling through large datasets using the Paging 3 library.
- **Magical UI**: Dynamic themes based on Hogwarts houses (Gryffindor, Slytherin, Ravenclaw, Hufflepuff).
- **Favorites**: Mark your favorite characters to keep them at the top of your wand's reach.
- **Skeleton Loaders**: Smooth, pulsing shimmer effects during data loading.

## 🛠️ Technology Stack & Libraries

The app is built using the latest Android development practices and modern libraries:

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For building a modern, declarative UI.
- **Navigation 3 (Alpha)**: Utilizing the latest experimental navigation framework from Google.
- **Paging 3**: For efficient loading and displaying of large character lists.
- **Room**: Local database for offline persistence and single source of truth.
- **Ktor**: Asynchronous HTTP client for network requests.
- **Koin**: Lightweight dependency injection framework.
- **Coil**: Image loading library backed by Kotlin Coroutines.
- **Kotlinx Serialization**: Type-safe JSON parsing.
- **Coroutines & Flow**: For reactive and asynchronous programming.

## 🏗️ Architecture

The project follows the **MVI (Model-View-Intent)** pattern combined with **Clean Architecture** principles:

- **Data Layer**: API interaction and local database management.
- **Domain Layer**: Business logic and use cases.
- **Presentation Layer**: UI state management using ViewModels and Jetpack Compose.

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/RomanPolach/HarryPotter.git
   ```
2. Open the project in **Android Studio (Ladybug or newer)**.
3. Build and run the app on an emulator or physical device.

---
*Created with magic and modern Android code.* 🏮✨
