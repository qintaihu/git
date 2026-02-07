# Agent Skills - Build Troubleshooting

This directory contains reusable agent skills for resolving common build and environment issues.

## Directory Structure
```
.agents/
└── skills/
    ├── nodejs-installation/
    │   └── SKILL.md
    ├── npm-permission-errors/
    │   └── SKILL.md
    ├── esbuild-missing-binary/
    │   └── SKILL.md
    ├── java-version-incompatibility/
    │   └── SKILL.md
    ├── maven-dependency-issues/
    │   └── SKILL.md
    └── README.md (this file)
```

## How to Use

### In VS Code Chat Sessions
Add this path to Agent Skills Locations:
```
.agents/skills
```

Each skill subfolder contains a `SKILL.md` file that the agent can use to resolve issues.

### Manually
Open any `SKILL.md` file to view:
- Problem description with error messages
- Root cause analysis
- Step-by-step solutions with code examples
- Related skills
- Prevention tips

## Available Skills

| Skill | Category | Severity | Description |
|-------|----------|----------|-------------|
| [nodejs-installation](nodejs-installation/SKILL.md) | Environment Setup | High | Install Node.js and fix PATH issues |
| [npm-permission-errors](npm-permission-errors/SKILL.md) | Dependency Management | High | Fix EPERM/EBUSY errors during npm install |
| [esbuild-missing-binary](esbuild-missing-binary/SKILL.md) | Build Tools | High | Resolve missing platform binaries in esbuild |
| [java-version-incompatibility](java-version-incompatibility/SKILL.md) | Java Build | Critical | Handle Java version mismatches with Spring Boot |
| [maven-dependency-issues](maven-dependency-issues/SKILL.md) | Java Build | High | Resolve Maven repository and dependency issues |

## Adding New Skills

To add a new skill:

1. Create a new subfolder: `.agents/skills/my-new-skill/`
2. Create a `SKILL.md` file with:
   - ## Problem (with error messages)
   - ## Root Cause
   - ## Solution (with steps and code)
   - ## Related Skills (if applicable)
3. Update this README with the new skill details

## Last Updated
2026-02-07

## Related Documents
- See [BUILD_TROUBLESHOOTING.md](../../BUILD_TROUBLESHOOTING.md) for detailed troubleshooting guide
- See project [README.md](../../README.md) for overall project setup
