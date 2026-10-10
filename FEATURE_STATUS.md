# Feature status

## Included and implemented in source
- Space-themed KitaBlox settings screen and category navigation.
- Escape/pause menu button added using Fabric ScreenEvents and a `Screen`-targeted invoker.
- Configurable keybinding for opening settings.
- Atomic JSON config writes with defaults on missing/malformed config.
- NVIDIA-named reversible profile: clouds off, entity shadows off.
- Entity Optimization: reversible entity-shadow option.
- Animation Optimization: reversible view-bobbing option.
- Restore prior options when all active profiles are disabled.
- Low Fire: suppresses the first-person fire overlay using the `InGameHud.renderOverlay` hook documented for Yarn 1.21.11.
- GitHub Actions runs `gradle build`, checks the production jar contains mod metadata and mixin configuration, and uploads only the normal jar.

## Not yet implemented (not clickable)
- Particle optimization.
- Consumable optimizer.
- Crystal visual optimization.
- Respawn anchor visual optimization.
- Small totem.
- Small totem pop.
- Client-side combo HUD.

These are marked unavailable rather than represented as working features. Add them only with verified Minecraft 1.21.11 hooks and after a successful build and in-game test.
