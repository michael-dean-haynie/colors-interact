export class CanvasService {
    // a reference to the <canvas> element in the dom
    canvas;
    // the canvas context
    ctx;
    // player size
    playerSize = 50;

    constructor() {
        this.canvas = document.getElementById('board-canvas');
        this.ctx = this.canvas.getContext('2d');

        // Orient as the well-known Cartesian coordinate system, with the origin in the bottom left corner
        this.ctx.translate(0, this.canvas.height);
        this.ctx.scale(1, -1);

    }


    /**
     * Erase and re-paint the whole board with the new game state
     * @param {*} gameState 
     */
    updateBoard(gameState) {
        if (gameState) {
            this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

            // paint each player on the board
            if (gameState.players) {
                gameState.players.forEach((player) => {
                    this.paintPlayer(player);
                });
            }

        }


    }

    /**
     * Paint a player's possition on the board
     * @param {*} player 
     */
    paintPlayer(player) {
        if (player) {
            this.ctx.fillStyle = `rgba(${player.r}, ${player.g}, ${player.b}, 0.5)`;
            this.ctx.fillRect(player.x, player.y, this.playerSize, this.playerSize);
        }
    }


}