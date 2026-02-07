# npm Permission Errors

## Problem
npm install fails with file permission errors on Windows, preventing dependency installation.

```
npm error code 1
npm error EPERM: operation not permitted, rmdir 'D:\...\node_modules\...'
npm error EBUSY: resource busy or locked, rmdir 'D:\...\node_modules\vue-demi'
npm error command C:\WINDOWS\system32\cmd.exe /d /s /c node install.js
```

## Root Cause
- Node.js processes are still running and holding locks on files
- Previous npm installation failed, leaving corrupted node_modules
- File permissions on Windows preventing deletion/modification
- npm/node path not properly set in environment

## Solution

### Step 1: Stop All Node Processes
```powershell
Get-Process node -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2
```

### Step 2: Clean Up Corrupted Installation
```powershell
Remove-Item -Path "node_modules" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "package-lock.json" -Force -ErrorAction SilentlyContinue
```

### Step 3: Use Full Path to npm (if PATH issues)
```powershell
& "C:\Program Files\nodejs\npm.cmd" install
```

### Step 4: Force Install (if permission persists)
```powershell
Get-Process node -ErrorAction SilentlyContinue | Stop-Process -Force
& "C:\Program Files\nodejs\npm.cmd" install --force
```

## Prevention
- Always stop node processes before retrying npm install
- Don't use npm with `--unsafe-perm=false` on Windows
- Close IDE/editors that may lock files in node_modules

## Related Skills
- nodejs-installation
- esbuild-missing-binary
