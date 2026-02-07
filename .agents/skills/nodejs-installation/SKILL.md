# Node.js Installation

## Problem
Node.js is not installed or not recognized in PATH, preventing npm and JavaScript-based tools from running.

```
npm : 无法将"npm"项识别为 cmdlet、函数、脚本文件或可运行程序的名称
node : 无法将"node"项识别为 cmdlet、函数、脚本文件或可运行程序的名称
```

## Root Cause
- System does not have Node.js installed
- Node.js is installed but not added to system PATH
- Node.js installation is corrupted or incomplete

## Solution

### Step 1: Check Current Installation
```powershell
node --version
npm --version
```

### Step 2: Install Node.js (if not installed)
**Option A: Using Windows Package Manager (Recommended)**
```powershell
winget install OpenJS.NodeJS
```

**Option B: Manual Installation**
- Download LTS version from https://nodejs.org/
- Run the installer and follow the setup wizard
- Ensure "Add to PATH" is selected during installation

### Step 3: Verify Installation
```powershell
node --version
npm --version
```

### Step 4: Update PATH (if needed)
If npm is still not recognized after installation, manually add Node.js to PATH:
```powershell
$env:PATH += ";C:\Program Files\nodejs"
node --version  # Verify
```

## Related Skills
- npm-permission-errors
- esbuild-missing-binary
