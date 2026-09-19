# frontend-kotlin

Android app of **Ark** (ISIS3510, team 31). Kotlin + Jetpack Compose.
The design decisions are in the [design wiki](https://github.com/ISIS3510-202620-S3-G31/design/wiki) (MS6 and MS7).

## How to run

You need the latest stable Android Studio (older versions don't open the project because of the
Gradle plugin version) and Android SDK 36.

1. Clone the repo and open it in Android Studio.
2. Wait for the Gradle sync.
3. Run `app` on an emulator or a phone with Android 8.0 or newer.

Or from the terminal: `./gradlew installDebug`

If Android Studio offers to change the Gradle or Java version, or creates new files inside `gradle/`,
don't commit them. They change the build for everybody.

## Project structure

```
app/src/main/java/com/isis3510/ark/
├── MainActivity.kt
└── ui/
    ├── theme/        colors, fonts and shapes from MS6
    ├── components/   composables used by more than one screen
    ├── navigation/   destinations and the nav graph
    └── screens/      one package per view (toolhub, custombreathing, screamtank, ...)
```

We use MVVM (MS7). The ViewModel and the state of a view go in the same package as its screen.
Each view has its own package, so to work on your view you only touch your own files.

## Git workflow

We follow the [Git flow guide of the course](http://uniandes-se4ma.gitlab.io/guides/gitFlow.html).

| Branch | Purpose |
|---|---|
| `main` | Stable code. It only receives `develop` when we close a sprint. |
| `develop` | Where all the work is merged. |
| `feature/<name>-#<issue>` | New view or component, for example `feature/scream-tank-#8`. |
| `fix/<name>-#<issue>` | Fix or improvement of something that already exists. |

Steps:

1. Every branch starts with an issue. It can be an issue of this repo or of the
   [design repo](https://github.com/ISIS3510-202620-S3-G31/design/issues).
2. Create the branch from the latest `develop`. Lowercase with hyphens, and the `#` needs quotes:
   ```sh
   git checkout develop
   git pull
   git checkout -b "feature/scream-tank-#8"
   ```
3. Open the pull request to `develop`. **Check the base before creating it**, GitHub selects `main` by default.
4. Link the issue in the description: `Closes #8`. If the issue is in the design repo write
   `Closes ISIS3510-202620-S3-G31/design#181`. Add a screenshot from the emulator.
5. A teammate reviews it and merges with **Squash and merge**.
6. Delete the branch and close the issue by hand (GitHub only closes it alone when the merge goes to `main`).

Nobody pushes directly to `main` or `develop`, and each member codes their own views.

## Design rules (short version of MS6)

- **Colors:** only the ones in `ui/theme/Color.kt`, no hex values in the screens.
- **Fonts:** Sorean only for H1 titles, Figtree for the rest. Use `MaterialTheme.typography`
  (H1 `headlineLarge`, H2 `titleLarge`, H3 `titleMedium`, body `bodyMedium`).
- **Icons:** solid fill and rounded corners, no emojis. 22 dp in the nav bar, 34 dp in tool cards.
- **Touch targets:** at least 48 x 48 dp.
- **Accessibility:** color is never the only cue, and buttons with only an icon need a `contentDescription`.
- **Navigation:** 3 bottom tabs (Tools, Random, Stats) and a back button at the top left of every tool.

## Fonts

Figtree uses the SIL Open Font License (`licenses/figtree-OFL.txt`). Sorean is free for personal use;
this is an academic project with no commercial use.
