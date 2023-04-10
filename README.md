# The Rick and Morty Android app
### Android sample project
The goal of this project is to showcase a possible approach to developing an Android app.
## UI
The project is developed fully with [Jetpack Compose](https://developer.android.com/jetpack/compose).
### Screens
There are 3 screens in the app. Home, Character Detail and Episode Detail. There is also basic handling of error and loading states.
#### Home Screen
<img src = "https://github.com/minarja1/RickAndMorty/blob/develop/screenshots/Screenshot_20230409_200613.png?raw=true" width = 300px/>

There are 2 tabs on the home screen. One with a list of characters and one with a list of episodes. The lists are lazy loaded using [Jetpack Paging library](https://developer.android.com/jetpack/androidx/releases/paging).
#### Character detail
<img src = "https://github.com/minarja1/RickAndMorty/blob/develop/screenshots/Screenshot_20230409_200731.png?raw=true" width = 300px/>

Character detail screen contains basic character info and expandable list of episodes the character appeared in. The items are clickable and navigate to Episode detail.
#### Episode detail
<img src = "https://github.com/minarja1/RickAndMorty/blob/develop/screenshots/Screenshot_20230409_200740.png?raw=true" width = 300px/>

Episode detail screen contains basic episode info and scrollable list of characters that appeared in the episode.

### Navigation
[Compose Navigation](https://developer.android.com/jetpack/compose/navigation) is used to navigate between screens. See [Navigation.kt](https://github.com/minarja1/RickAndMorty/blob/develop/app/src/main/java/cz/minarik/rickandmorty/navigation/Navigation.kt) for implementation details.

## Development
### Code style
[Detekt](https://detekt.dev/) is used to maintain the style and quality of the code.  The check is run server-side on every pull request with [reviewdog] (https://github.com/alaegin/Detekt-Action). It should also be run client-side as part of pre-commit git hook. To enable this, move the [pre-commit.yml](https://github.com/minarja1/RickAndMorty/blob/develop/config/git/pre-commit) file to .git/hooks.

// todo use other static analysis tools like ktlint/lint/spotless
### Architecture
Currently widely popular architectural approach is implemented using **[clean architecture](https://developer.android.com/topic/architecture)** and [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) pattern. The code is split into 3 main layers: **data**, **domain** and **UI**. All business logic is implemented in **use-case** files.

// todo the layers should be modules, not just packages
### Testing
Business logic is unit tested using [kotest](https://kotest.io/) and [mockk](https://mockk.io/). See the [test package](https://github.com/minarja1/RickAndMorty/tree/develop/app/src/test/java/cz/minarik/rickandmorty) for implementation details.

// todo add instrumented UI tests using [Compose Testing](https://developer.android.com/jetpack/compose/testing)
### Dependency injection
[Koin](https://insert-koin.io/) is used to take care of dependency injection. See the [di package](https://github.com/minarja1/RickAndMorty/tree/develop/app/src/main/java/cz/minarik/rickandmorty/di).
### API
The app is loading data from [The Rick and Morty API](https://rickandmortyapi.com/). It is using the [GraphQL version](https://rickandmortyapi.com/graphql) of all endpoints. [Apollo](https://www.apollographql.com/docs/kotlin/v2/) library is used to implement the GraphQL requests.
