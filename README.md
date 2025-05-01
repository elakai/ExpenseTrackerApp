<p align="center">
  <a href="app/src/main/res/drawable/ic_launcher.png">
    <img src="app/src/main/res/drawable/ic_launcher.png" alt="Logo" width=90 height=90>
  </a>


  <h3 align="center">Logo</h3>

  <p align="center">
    "Cat Got Your Wallet?" is an Android-based mobile application developed to facilitate effective personal expense management. The application provides users with a streamlined interface for recording and categorizing financial transactions, enabling comprehensive tracking of expenses. Utilizing data visualization techniques, specifically pie charts, the application presents users with a clear and concise representation of their spending patterns.
    <br>
    <a href="https://reponame/issues/new?template=bug.md">Report bug</a>
    ·
    <a href="https://reponame/issues/new?template=feature.md&labels=feature">Request feature</a>
  </p>
</p>


## Table of contents

- [Installation](#installation)
- [Status](#status)
- [What's included](#whats-included)
- [Bugs and feature requests](#bugs-and-feature-requests)
- [Contributing](#contributing)
- [Creators](#creators)
- [Thanks](#thanks)
- [Copyright and license](#copyright-and-license)


## Installation
instructions

## Status
- timeline of activities

## What's included

Directory structure

```text
├───app
│   │   .gitignore
│   │   build.gradle.kts
│   │   proguard-rules.pro
│   │
│   └───src
│       ├───androidTest
│       │   └───java
│       │       └───com
│       │           └───example
│       │               └───myapplication
│       │                       ExampleInstrumentedTest.kt
│       │
│       ├───main
│       │   │   AndroidManifest.xml
│       │   │   button-playstore.png
│       │   │
│       │   ├───java
│       │   │   └───com
│       │   │       └───example
│       │   │           └───myapplication
│       │   │               │   MainActivity.kt
│       │   │               │
│       │   │               ├───data
│       │   │               │       AppDatabase.kt
│       │   │               │       Expense.kt
│       │   │               │       ExpenseDao.kt
│       │   │               │
│       │   │               └───ui
│       │   │                   ├───components
│       │   │                   │       ChartView.kt
│       │   │                   │
│       │   │                   ├───navigation
│       │   │                   │       NavGraph.kt
│       │   │                   │
│       │   │                   ├───screens
│       │   │                   │       AddExpenseScreen.kt
│       │   │                   │       ChartScreen.kt
│       │   │                   │       ExpenseListScreen.kt
│       │   │                   │       HomeScreen.kt
│       │   │                   │       ToBuyScreen.kt
│       │   │                   │
│       │   │                   ├───theme
│       │   │                   │       Color.kt
│       │   │                   │       Theme.kt
│       │   │                   │       Type.kt
│       │   │                   │
│       │   │                   └───viewmodel
│       │   │                           ExpenseViewModel.kt
│       │   │                           ExpenseViewModelFactory.kt
│       │   │                           ExpenseViewModelInterface.kt
│       │   │                           MockViewModels.kt
│       │   │                           PreviewExpenseViewModel.kt
│       │   │
│       │   └───res
│       │       ├───drawable
│       │       │       bg.png
│       │       │       bg1.png
│       │       │       bg2.png
│       │       │       bg3.png
│       │       │       bg4.png
│       │       │       bg5.png
│       │       │       bg6.png
│       │       │       button.png
│       │       │       ic_launcher.png
│       │       │       ic_launcher_background.xml
│       │       │       ic_launcher_foreground.xml
│       │       │       tmp.png
│       │       │
│       │       ├───font
│       │       │       fresh_season.otf
│       │       │       lemon_tuesday.otf
│       │       │
│       │       ├───layout
│       │       │       activity_main.xml
│       │       │
│       │       ├───mipmap-anydpi-v26
│       │       │       button.xml
│       │       │       button_round.xml
│       │       │
│       │       ├───mipmap-hdpi
│       │       │       button.webp
│       │       │       button_round.webp
│       │       │       ic_launcher_foreground.webp
│       │       │
│       │       ├───mipmap-mdpi
│       │       │       button.webp
│       │       │       button_round.webp
│       │       │       ic_launcher_foreground.webp
│       │       │
│       │       ├───mipmap-xhdpi
│       │       │       button.webp
│       │       │       button_round.webp
│       │       │       ic_launcher_foreground.webp
│       │       │
│       │       ├───mipmap-xxhdpi
│       │       │       button.webp
│       │       │       button_round.webp
│       │       │       ic_launcher_foreground.webp
│       │       │
│       │       ├───mipmap-xxxhdpi
│       │       │       button.webp
│       │       │       button_round.webp
│       │       │       ic_launcher_foreground.webp
│       │       │
│       │       ├───values
│       │       │       colors.xml
│       │       │       ic_launcher_background.xml
│       │       │       strings.xml
│       │       │       themes.xml
│       │       │
│       │       └───xml
│       │               backup_rules.xml
│       │               data_extraction_rules.xml
│       │
│       └───test
│           └───java
│               └───com
│                   └───example
│                       └───myapplication
│                               ExampleUnitTest.kt
│
├───gradle
│   │   libs.versions.toml
│   │
│   └───wrapper
│           gradle-wrapper.jar
│           gradle-wrapper.properties
│
└───public
        index.html
        style.css
```

## Bugs and feature requests

Have a bug or a feature request? Please first read the [issue guidelines](https://reponame/blob/master/CONTRIBUTING.md) and search for existing and closed issues. If your problem or idea is not addressed yet, [please open a new issue](https://reponame/issues/new).

## Contributing

Please read through our [contributing guidelines](https://reponame/blob/master/CONTRIBUTING.md). Included are directions for opening issues, coding standards, and notes on development.

Moreover, all HTML and CSS should conform to the [Code Guide](https://github.com/mdo/code-guide), maintained by [Main author](https://github.com/usernamemainauthor).

Editor preferences are available in the [editor config](https://reponame/blob/master/.editorconfig) for easy use in common text editors. Read more and download plugins at <https://editorconfig.org/>.

## Creators


**Pam**

- <https://gitlab.com/pmagnifico>

## Thanks

Some Text

## Copyright and license

Code and documentation copyright 2023-2024 the authors. Code released under the [MIT License](https://reponame/blob/master/LICENSE).

Enjoy :metal:



[ci]: https://about.gitlab.com/gitlab-ci/
[index.html]: https://gitlab.com/pages/plain-html/blob/master/public/index.html
[userpages]: https://docs.gitlab.com/ce/user/project/pages/introduction.html#user-or-group-pages
[projpages]: https://docs.gitlab.com/ce/user/project/pages/introduction.html#project-pages
