# Rick and Morty
### Android sample project
The goal of this project is to showcase a possible approach to developing an Android app.
## Development
### Code style
[Detekt](https://detekt.dev/) is used to maintain the style and quality of the code.  The check is run server-side on every pull request with [reviewdog](https://github.com/alaegin/Detekt-Action)  It should also be run client-side as part of pre-commit git hook. To enable this, move the [pre-commit.yml](https://github.com/minarja1/RickAndMorty/blob/develop/config/git/pre-commit) file to .git/hooks.

// todo use other static analysis tools like ktlint/lint/spotless
### Architecture
Currently widely popular architectural approach is implemented using **[clean architecture](https://developer.android.com/topic/architecture)**. and [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) pattern. The code is split into 3 main layers: **data**, **domain** and **UI**. All business logic is implemented in **use-case** files.

// todo the layers should be modules, not just packages
### Testing
All business logic is unit tested using [kotest](https://kotest.io/) and [mockk](https://mockk.io/). See the [test package](https://github.com/minarja1/RickAndMorty/tree/develop/app/src/test/java/cz/minarik/rickandmorty) for implementation details.

// todo add instrumented UI tests using [Compose Testing](https://developer.android.com/jetpack/compose/testing)
### Dependency injection
[Koin](https://insert-koin.io/) is used to take care of dependency injection (instantiating necessary classes). See the [di package](https://github.com/minarja1/RickAndMorty/tree/develop/app/src/main/java/cz/minarik/rickandmorty/di).
### API
The app is loading data from [The Rick and Morty API](https://rickandmortyapi.com/). It is using the [GraphQL version](https://rickandmortyapi.com/graphql) of all endpoints. [Apollo](https://www.apollographql.com/docs/kotlin/v2/) library is used to implement requests.
