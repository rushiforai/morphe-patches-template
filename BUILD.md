# Build this bundle inside Morphe Pilot

Morphe patches are Kotlin compiled by Gradle against `app.morphe:morphe-patcher`, which
comes from the Morphe GitHub Packages registry. The app does the whole build for you in
GitHub Actions, so no PC, Termux or root is involved.

## One-time setup (in this app)

1. Settings -> GitHub build: paste a token with `repo`, `workflow` and `read:packages`
   access, then set owner (blank = you), repository name and private/public.
2. The app creates the repository, pushes this project and its build workflow, and stores
   the token as the `GPR_TOKEN` Actions secret (encrypted with the repository public key).

## Build

Project tab -> **Build .mpp**. The app pushes the project, triggers the workflow, waits
for the run and hands you the `.mpp`.

## Apply (no root)

- Share the `.mpp` to Morphe Manager, or import it there, pick the target app and patch.
- Morphe Desktop and `morphe-cli` accept the same bundle on a computer.

The generated workflow lives in `.github/workflows/build.yml`.