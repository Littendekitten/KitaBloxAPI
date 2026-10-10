# KitaBloxAPI — source repair package

Target: Minecraft Java 1.21.11, Fabric Loader 0.18.4, Java 21, Yarn `1.21.11+build.6`.

## What changed

- Removes the crashing `GameMenuScreenMixin` that shadowed `addDrawableChild` on the wrong target class.
- Adds the pause-menu button with Fabric `ScreenEvents.AFTER_INIT` and a `ScreenAccessor` invoker targeting `Screen`, where `addDrawableChild` is declared.
- Keeps a narrow Low Fire hook on the documented 1.21.11 `InGameHud.renderOverlay(DrawContext, Identifier, float)` method; it is only active when Low Fire is enabled.
- Adds a dark space-themed settings screen with responsive sections.
- Adds persistent JSON configuration and a reversible performance profile.
- Implements verified settings: Low Fire (hides the first-person fire overlay), NVIDIA-named profile (cloud rendering OFF + entity shadows OFF), entity-shadow optimization, and view-bobbing optimization.
- Clearly labels other requested visual features as unavailable rather than presenting non-functional toggles.
- Replaces the noisy CI task-list check with an actual Gradle build and production-JAR verification. Only the production JAR is uploaded as the artifact.

## Build using GitHub Actions (recommended for low-powered computers)

1. Download and extract this ZIP.
2. In the GitHub repository, upload/replace the project files using the GitHub web editor. Keep the `.github/workflows/build.yml` path.
3. Commit the changes to `main`.
4. Open **Actions → Build KitaBloxAPI** and wait for a green check.
5. Open the successful run summary and download `KitaBloxAPI-JAR`.
6. The ZIP artifact should contain the normal production JAR, not the `-sources.jar`.
7. Install the JAR into the `mods` folder of a Minecraft 1.21.11 Fabric instance. Keep Fabric API installed.

This project uses the GitHub Actions `gradle` command with Gradle 8.14 because the existing repository did not include the Gradle wrapper scripts/JAR. The workflow downloads a compatible Gradle distribution on GitHub's hosted runner.

## Important verification status

This environment could inspect the public repository and current Yarn documentation, but it could not download the Gradle/Minecraft dependencies or run Minecraft. This ZIP is **source code, not a compiled or tested mod JAR**. GitHub Actions must compile it. A successful build proves compilation/packaging, not in-game behavior; test the resulting JAR in an isolated instance before using it with other mods.

The following requested features remain intentionally unavailable in this source package because their 1.21.11 rendering hooks have not been validated here: particle optimization, consumable optimization, crystal/anchor render optimization, small totem, small totem pop, and combo HUD. They appear as informational rows, not working toggles. They need correct per-version implementations and another build/test cycle before being enabled. Low Fire is implemented as hiding the first-person fire overlay, not merely lowering its position.
