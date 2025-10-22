# GitHub Actions Workflows

This directory contains GitHub Actions workflows for automating the build and release process.

## Workflows

### 1. Release Workflow (`release.yml`)

**Trigger**: Pushing version tags (e.g., `v1.0.0`, `v2.1.3`)

**Purpose**: Automatically creates GitHub releases with the built JAR file.

**Steps**:
1. Checks out the code
2. Sets up Java 21 (Temurin distribution)
3. Builds the project with Gradle
4. Creates a GitHub release with the JAR file attached
5. Generates release notes automatically

**Usage**:
```bash
# Create a new version tag
git tag v1.0.0

# Push the tag to GitHub
git push origin v1.0.0
```

The workflow will automatically:
- Build the plugin
- Create a release named "WorldLink 1.0.0"
- Attach the JAR file
- Generate release notes from commits

### 2. Build Workflow (`build.yml`)

**Trigger**: Pushes to main/master branch and pull requests

**Purpose**: Continuous Integration - ensures the code builds successfully.

**Steps**:
1. Checks out the code
2. Sets up Java 21 (Temurin distribution)
3. Builds the project with Gradle
4. Uploads build artifacts (retained for 7 days)

**Artifacts**: The built JAR files are available as workflow artifacts for testing.

## Requirements

- Java 21 (configured in workflows)
- Gradle (using wrapper)
- GitHub repository write permissions for releases

## Customization

### Changing Release Format

Edit `.github/workflows/release.yml` to customize:
- Release title format (line 50)
- Release body/description (lines 51-63)
- Tag pattern (line 6)

### Changing Build Branches

Edit `.github/workflows/build.yml` to customize:
- Branches that trigger builds (lines 6-7 and 9-10)
- Artifact retention period (line 40)

## Troubleshooting

### Release not created

1. Ensure the tag follows the pattern `v*.*.*` (e.g., `v1.0.0`)
2. Check that repository permissions allow creating releases
3. Review the Actions tab for error logs

### Build fails

1. Check Java version compatibility (requires Java 21)
2. Review Gradle build logs in the Actions tab
3. Ensure dependencies are correctly configured

## Security

- The workflows use pinned action versions for security
- GitHub tokens are automatically provided (no manual configuration needed)
- Artifacts are retained for 7 days by default
