# Android application designed to showcase modern Android development techniques, including Clean Architecture, MVVM pattern, Dependency Injection with Dagger-Hilt, and the usage of Kotlin Symbol Processing (KSP).

## Table of Contents

Project Structure
##  Data Layer
- api: Contains API service definitions using Retrofit.
- di: Dependency injection setup using Dagger-Hilt.
- model: Data models and mappers.
- persistence: Room database setup and DAO definitions.
repository: Implementation of repository interfaces.
##  Domain Layer
- model: Domain models.
- repository: Repository interfaces.
- usecase: Use cases encapsulating specific business logic.
## Presentation Layer
- components: UI components.
- screen: Composables representing different screens.
- uievent: UI events handled by ViewModels.
- uistate: UI state managed by ViewModels.

# Dependencies
- Retrofit: For network calls.
- Room: For local database.
- Dagger-Hilt: For dependency injection.
- Kotlin Symbol Processing (KSP): For code generation.
- Jetpack Compose: For UI.

 
# Testing
Data Layer: 88% (no database tests included)
Domain Layer: 92% (no DI tests included)
Presentation Layer: 100% (ViewModel unit tests)
 - Mapper Tests
Ensures that data models are correctly mapped to domain models and vice versa.
