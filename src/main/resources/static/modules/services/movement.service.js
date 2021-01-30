import { DirectionEnum } from "../enums/direction.enum.js";

/**
 * Listens for keyboard events. Determines current direction. Takes action when it changes.
 */
export class MovementService {
    // stack to resolve most recent key pressed/unpressed (could be multiple at a time)
    directionStack = [DirectionEnum.STILL];

    lastDirection = DirectionEnum.STILL;

    constructor() {
        // listen for keydown events
        window.addEventListener('keydown', (event) => {
            const direction = this.directionFromKeyboardEvent(event);
            if (direction === undefined) {
                return;
            }

            // add direction to stack if it's not in there somewhere already
            if (!this.directionStack.includes(direction)) {
                this.directionStack.push(direction);
            }

            this.handleIfDirectionChanged()
        });

        // listen for keyup events
        window.addEventListener('keyup', (event) => {
            const direction = this.directionFromKeyboardEvent(event);
            if (direction === undefined) {
                return;
            }

            // remove direction from anywhere in in stack
            this.directionStack = this.directionStack.filter(dir => dir != direction);

            this.handleIfDirectionChanged()
        });
    }

    /**
     * Takes action if the current direction changed since last direction
     */
    handleIfDirectionChanged() {
        const currentDirection = this.getCurrentDirection();
        if (currentDirection !== this.lastDirection) {
            // update lastDirection
            this.lastDirection = currentDirection;

            // handle
            console.log(currentDirection);
        }
    }


    /**
     * Uses directionStack to determine the current direction.
     */
    getCurrentDirection() {
        return this.directionStack[this.directionStack.length - 1];
    }


    /**
     * Discerns the direction of a keyboard event.
     * If the keyboard event is not a direction, return undefined;
     * @param {KeyboardEvent} event 
     */
    directionFromKeyboardEvent(event) {
        switch (event.key) {
            case 'ArrowUp':
                return DirectionEnum.UP;
            case 'ArrowRight':
                return DirectionEnum.RIGHT;
            case 'ArrowDown':
                return DirectionEnum.DOWN;
            case 'ArrowLeft':
                return DirectionEnum.LEFT;
            default:
                return undefined;
        }
    }
}