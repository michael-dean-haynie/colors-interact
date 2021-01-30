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

        const gameState = {
            players: [
                {
                    x: 10,
                    y: 10,
                    r: 54,
                    g: 184,
                    b: 142
                },
                {
                    x: 100,
                    y: 100,
                    r: 165,
                    g: 103,
                    b: 214
                },
            ]
        };

        this.updateBoard(gameState);
        setTimeout(() => {
            gameState.players[0].x += 30;
            gameState.players[1].y += 30;
            this.updateBoard(gameState);
        }, 3000);
    }


    /**
     * Erase and re-paint the whole board with the new game state
     * @param {*} gameState 
     */
    updateBoard(gameState) {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

        if (gameState) {
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