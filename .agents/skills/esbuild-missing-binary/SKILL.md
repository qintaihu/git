# esbuild Missing Platform Binary

## Problem
esbuild fails during build with message about missing platform-specific binary for Windows.

```
Error: The package "@esbuild/win32-x64" could not be found, and is needed by esbuild.

If you are installing esbuild with npm, make sure that you don't specify the 
"--no-optional" or "--omit=optional" flags. The optionalDependencies feature 
of "package.json" is used by esbuild to install the correct binary executable 
for your current platform.
```

## Root Cause
- npm was run with `--no-optional` or `--omit=optional` flags
- Optional dependencies (particularly esbuild's platform-specific binaries) were not downloaded
- esbuild needs platform-specific binary for Windows (win32-x64), macOS (darwin-*), or Linux (linux-*)

## Solution

### Step 1: Stop Node Processes
```powershell
Get-Process node -ErrorAction SilentlyContinue | Stop-Process -Force
```

### Step 2: Remove Corrupted Installation
```powershell
Remove-Item -Path "node_modules" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "package-lock.json" -Force -ErrorAction SilentlyContinue
```

### Step 3: Reinstall WITH Optional Dependencies
```powershell
# DO NOT use --no-optional or --omit=optional
npm install

# Or with full path if PATH issues:
& "C:\Program Files\nodejs\npm.cmd" install
```

### Step 4: Force Install (if permission errors)
```powershell
& "C:\Program Files\nodejs\npm.cmd" install --force
```

## Prevention
- **NEVER** use `--no-optional` or `--omit=optional` flags with npm
- Vite, esbuild, and similar build tools require platform-specific binaries
- Default npm install behavior includes optional dependencies (correct behavior)

## Related Skills
- npm-permission-errors
- nodejs-installation
