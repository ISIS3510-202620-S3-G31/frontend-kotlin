# frontend-kotlin

Android app of **Ark** (ISIS3510, team 31), written in Kotlin with Jetpack Compose.
The design decisions (colours, fonts, icons, mascot, navigation) are in the
[design wiki](https://github.com/ISIS3510-202620-S3-G31/design/wiki), mainly in MS6 and MS7.

## Requirements

- Android Studio (latest stable) with the Android SDK 36
- JDK 17 or newer (the one bundled with Android Studio works)

## How to run

1. Clone the repo and open the folder in Android Studio.
2. Wait for the Gradle sync to finish.
3. Run the `app` configuration on an emulator or a phone (Android 8.0 or newer).

From the terminal:

```sh
./gradlew installDebug
```

## Project structure

```
app/src/main/java/com/isis3510/ark/
├── MainActivity.kt
└── ui/
    ├── theme/        colours, typography and shapes from MS6
    ├── components/   composables shared by more than one screen
    ├── navigation/   destinations and the navigation graph
    └── screens/      one package per view (toolhub, custombreathing, screamtank, ...)
```

The app follows MVVM (MS7, section 2). When a view needs logic, its ViewModel goes in the
same package as the screen, and the `domain/` and `data/` packages are created next to `ui/`.

Every view already has a placeholder screen and a route in `ui/navigation/ArkNavHost.kt`.
To implement your view you only need to edit the files inside your own package in `ui/screens/`,
so two people should never touch the same file.

## Git workflow

We use Gitflow:

| Branch | Purpose |
|---|---|
| `main` | Stable code only. It is updated from `development` at the end of each sprint. |
| `development` | Integration branch. All the features are merged here. |
| `feature/<name>` | One branch per view or task. It starts from `development` and goes back to `development`. |
| `hotfix/<name>` | Urgent fix. It starts from `main` and goes back to `main` and `development`. |

Rules:

1. Nobody pushes directly to `main` or `development`. Everything goes through a pull request.
2. Create your branch from the latest `development`:
   ```sh
   git checkout development
   git pull
   git checkout -b feature/scream-tank
   ```
3. Use lowercase and hyphens for the branch name, for example `feature/tool-hub` or `feature/custom-breathing`.
4. Link the pull request to its issue and ask one teammate to review it. Add a screenshot from the emulator.
5. Each member codes their own views on their own branch.
6. Delete the feature branch after the merge.

## Design rules

Short version of what is in the wiki, so it is at hand while coding:

- **Colours:** use only the ones in `ui/theme/Color.kt`. Never hardcode a hex value in a screen.
- **Fonts:** Sorean only for H1 titles, Figtree for everything else. Use the styles from
  `MaterialTheme.typography` (H1 `headlineLarge`, H2 `titleLarge`, H3 `titleMedium`, body `bodyMedium`).
- **Icons:** solid fill, rounded corners, 22 dp in the navigation bar and 34 dp in tool cards.
- **Touch targets:** at least 48 x 48 dp, even if the icon is smaller.
- **Accessibility:** colour is never the only cue. Add an icon, a label or a shape too.
- **Navigation:** 3 bottom tabs (Tools, Random, Stats) and a back button at the top left of every tool.

## Fonts

- Figtree is under the SIL Open Font License (see `licenses/figtree-OFL.txt`).
- Sorean is free for personal use. This is an academic project with no commercial use.
