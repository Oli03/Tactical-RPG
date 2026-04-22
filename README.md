# Introduction

This is a turn-based strategy game where the player explores a board, moves between cells, and engages in combat with other entities. The game combines movement, positioning, and battle mechanics into a simple grid-based experience.

---

# Class Documentation

## Overview

| Modules | Objects                                                                                                                                       |
|---------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| `Model` | `CombatStats`, `CombatSystem`, `EntitySpawner`, `Game`, `GameBoard`, `GameEntity`, `MovementController`, `Pair`, `Position`                   |
| `UI`    | `ActionPanel`, `App`, `CellBlinkAnimator`, `GameController`, `GameOverHandler`, `KeyboardHandler`, `MapCellFactory`, `MapView`, `TurnManager` |

---

## Model

### `CombatStats`
The `CombatStats` class is responsible for tracking and managing the combat-related statistics of a player. This includes attributes such as health and the playable area.

### `CombatSystem`
The `CombatSystem` class manages the combat mechanics of the game.

### `EntitySpawner`
The `EntitySpawner` class is responsible for spawning entities in the game world and setting the playable area for the player.

### `Game`
The `Game` class is the main entry point of the application. It initializes the game, sets up the game loop, and manages the overall game state.

### `GameBoard`
The `GameBoard` class represents the map where the player and other entities interact.

### `GameEntity`
The `GameEntity` class is a base class for all entities in the game.

### `MovementController`
The `MovementController` class is responsible for handling the movement of entities in the game.

### `Pair`
The `Pair` class represents a pair of values used by the model.

### `Position`
The `Position` class represents the coordinates of a location on the map which may or may not contain an entity.

---

## UI

### `ActionPanel`
The `ActionPanel` class is responsible for displaying the player's available actions and handling user input for those actions.

### `App`
The `App` class initialize the logic and UI of the game, and starts the application.

### `CellBlinkAnimator`
The `CellBlinkAnimator` class is responsible for animating the blinking effect of cells on the game board.

### `GameController`
The `GameController` class initializes the UI components such as game state, turn management, combat controls, and keyboard input handling.

### `GameOverHandler`
The `GameOverHandler` class is responsible for handling the game over state.

### `KeyboardHandler`
The `KeyboardHandler` class is responsible for handling keyboard input from the user and translating it into actions within the game.

### `MapCellFactory`
The `MapCellFactory` class is responsible for creating cells in the interface's game map with a specific background depending on the entities' position.

### `MapView`
The `MapView` class is responsible for rendering the game board and its entities on the screen.

### `TurnManager`
The `TurnManager` class is responsible for managing turn flow in the UI.
