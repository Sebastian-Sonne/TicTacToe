# Tic Tac Toe

This project is a simple implementation of the classic Tic Tac Toe game in Java. 

## Features

- Simple and straightforward gameplay.
- Easy to understand User Interface using the Java Swing libraries
- Players take turns marking spaces on a 3x3 grid.
- Win and draw condition detection to determine when a player has won, or wheter it is a tie.


## Getting Started

1. Open your terminal. Navigate to a folder of your choice and clone this repo, or, download the bin folder alternatively 

```bash
    git clone https://github.com/sebastian-sonne/ticTacToe.git
```

2. Within this folder, navigate to the bin folder. Replace "/path/to/this/project" with your actual path

```bash
    cd /path/to/this/project/bin
```
3. Run the following command to execute the java file and start the game
```bash
    java Frame
```

Note: Java needs to be installed on your computer

## Controls

- Choose the position on the grid by entering the corresponding number (1-9) on the keyboard.
- Follow the prompts to make your moves.


## Contributing

Contributions are appreciated! If you have ideas for improvements or new features, feel free to open an issue or submit a pull request. You can find a list of all open issues [here](https://github.com/sebastian-sonne/ticTacToe/issues).

To start contributing, fork this Repo using
```bash
    git clone https://github.com/sebastian-sonne/ticTacToe.git
```

The java files are located in the src folder. When you have completed your contribution, compile both the Frame.java, and the Panel.java file like so:

1. Navigate to the project folder using your terminal. Replace "/path/to/this/project" with your actual path
```bash
    cd /path/to/this/project
```

2. Run the following command to compile the files
```bash
    javac src/*.java
```
3. Move the generate .class files into the /bin filder
``` bash
robocopy src bin *.class /MOV
```
4. Commit you changes in a new branch onto git and open a pull request. Don't forget to add a clear description.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Enjoy playing Tic Tac Toe! 🎮