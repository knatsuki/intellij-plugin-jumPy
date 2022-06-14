# intelli-plugin-jumPy

![Build](https://github.com/mukatalab/intelli-plugin-jumPy/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)


<!-- Plugin description -->
Easily navigate between source and test files following standard application organizations where the test directory
essentially mirrors the source directory.

```text
my_package_1/
my_package_2/

tests/
  __init__.py
  my_package_1/
  my_package_2/
```

Or

```text
my_package_1/

tests/
  __init__.py
  unit/
    __init__.py
    my_package_1/
  functional/
    __init__.py
    my_package_1/
```

#### Contract

- Source and test directory reside under the same project content root.
- The package tree structure for each test directory and the package directory should mirror one another.

Pycharm's default _Go To Test_ functionality is not too practical from personal experience. Namely, it assumes
somewhat of a 1-1 source class to TestCase class organization that doesn't conform naturally with python projects
organized around python modules.

- Tests aren't always organized around units of TestCase classes. They are more often organized around _test module_
  files.
-

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "
  intelli-plugin-jumPy"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/mukatalab/intelli-plugin-jumPy/releases/latest) and install it
  manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
