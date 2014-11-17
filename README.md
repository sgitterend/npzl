# Design document
Design document for the staff's choice n-Puzzle app


## Description

The app n-Puzzle is a standard n-puzzle game app. It has three difficulty levels, from n = 3 through to n = 5.
The user should first be able to select a level (ImageSelection activity), and consequently play the game on that level (GamePlay activity).
There will be three different images used as puzzles, one for each difficulty level.
The game-state will be saved when the game is exited, and the user should be able to change to a different difficulty level at any point during gameplay by pressing the menu button.
When the game is completed a screen will show a congratulatory message (YouWin activity).

## Design

The app will use android support library v7 for most of its functionality. It is designed with android 2.1 (API level 7) required as a minimum to run correctly.

## Controller
An onclicklistener will be used to see what tile the user wants to move.

## Model
The main model will be in the 2nd activity, namely GamePlay, where a GridView will be used to project the puzzle's state.
The bitmap class will be used to crop the images as needed for their difficulty level.
After any move, if the puzzle is solved (i.e. the order of the tiles is correct) the YouWin activity will show.

## View
In every grid of the GridView, there wille be a View with an object to save the characteristics of the specific tile. ImageViewwill be used to display the cropped images in the grids. This will be updated when the user clicks a valid tile.

### Wireframe
A wireframe for the app can be found here:
http://imgur.com/P9GOgjM
