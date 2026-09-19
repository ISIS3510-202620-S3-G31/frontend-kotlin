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

We follow the Git flow guide of the course
([Git Flow - Best Practices](http://uniandes-se4ma.gitlab.io/guides/gitFlow.html)).

| Branch | Purpose |
|---|---|
| `main` | Stable code only. `develop` is merged here when we reach a milestone (end of sprint) and the app is tested by the team. That merge is a release. |
| `develop` | Integration branch, created from `main`. All the work is merged here. |
| `feature/<issue-simplified-name>-#<issue-number>` | New feature or component. It starts from `develop` and goes back to `develop`. Example: `feature/tool-hub-#180`. |
| `fix/<issue-simplified-name>-#<issue-number>` | Error fix or enhancement of a feature. It starts from `develop` and goes back to `develop`. Example: `fix/bottom-bar-padding-#12`. |

Rules:

1. Every change starts with an issue. The issues of the views are in the
   [design repo](https://github.com/ISIS3510-202620-S3-G31/design/issues) and the ones about the
   Kotlin code are in this repo. Keep them small: one issue should take less than a week.
2. Create your branch from the latest `develop` and put the issue number at the end of the name.
   The `#` needs quotes in the terminal:
   ```sh
   git checkout develop
   git pull
   git checkout -b "feature/scream-tank-#181"
   ```
3. Each member codes their own views on their own branch.
4. Nobody pushes directly to `main` or `develop`. When the issue is solved, open a pull request
   to `develop` and ask a teammate to review it. If changes are requested, push them to the same
   branch and ask for the review again on the same pull request.
5. Link the issue in the pull request description with a keyword, for example `Closes #2`.
   For an issue of the design repo write the full reference:
   `Closes ISIS3510-202620-S3-G31/design#180`. Add a screenshot from the emulator.
6. Merge with **Squash and merge**, so each pull request is one commit in `develop`.
7. Delete the branch after the merge.
8. GitHub only closes issues by itself when the merge goes to the default branch (`main`).
   Since we merge to `develop`, close the issue by hand after the merge.

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
