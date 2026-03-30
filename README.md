# Tasks App 📝

A simple and efficient Task Management (CRUD) Android application built with modern development practices.

## 🚀 Features
- **Create, Read, Update, and Delete** tasks.
- **Task completion** toggle.
- **Clear all** tasks at once.
- **Persistent storage** so your tasks are saved locally.

## 🛠 Tech Stack
- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Modern declarative UI)
- **Database:** [Room (SQLite)](https://developer.android.com/training/data-storage/room) (Persistent local storage)
- **Architecture:** Clean Architecture + MVVM (Model-View-ViewModel)
- **Concurrency:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Dependency Management:** Version Catalog (libs.versions.toml)

## 🏗 Architecture
The project follows **Clean Architecture** principles to ensure scalability and maintainability:
- **Domain Layer:** Contains Business Logic, Models, and Use Cases.
- **Data Layer:** Handles Database interactions (Room) and Repository implementations.
- **Presentation Layer:** Managed by ViewModels and rendered using Jetpack Compose Screens.

## 📸 Screenshots
*(Tip: Add your screenshots here to make it look even better!)*

## 📥 Getting Started
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/tasks-app.git
   ```
2. Open the project in **Android Studio** (Ladybug or newer recommended).
3. Build and Run the project on an emulator or physical device.

## 📝 License
This project is for study purposes. Feel free to use and modify it!
