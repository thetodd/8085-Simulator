8085-Simulator
==============

**Master-Branch:** [![Build status](https://travis-ci.org/thetodd/8085-Simulator.svg?branch=master)](https://travis-ci.org/thetodd/8085-Simulator)

**Developer-Branch:** [![Build status](https://travis-ci.org/thetodd/8085-Simulator.svg?branch=development)](https://travis-ci.org/thetodd/8085-Simulator)

This project simulates a 8085 processor.

![screenshot](https://raw.githubusercontent.com/thetodd/8085-Simulator/gh-pages/screenshots/screenshot8085.PNG)

###Supported Features
- 8085 assembler
- syntax highlighting
- editable Memory
- breakpoints
- user-defined codesegments (ORG operator)
- labels
- run code step-by-step

###Building
Building is provided by *ant*.

```
ant dist
```

creates a directory called `dist` which contains a jar file. Currently this jar file isn't able to be started standalone.

To clean up your development environment you can run

```
ant clean
```

which deletes the prior builded classes and jar files.

###ToDo
You can find some issues in the [issue tab](https://github.com/thetodd/8085-Simulator/issues) on GitHub. Feel free to create a pull request!

###License
This software is published under the MIT License. The SWT parts got other licenses. Please visit the jar files.