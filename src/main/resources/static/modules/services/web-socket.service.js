
export class WebSocketService {
    // Injected dependencies
    canvasService;

    // The stomp client to communicate with game server
    stompClient;

    constructor(
        canvasService
    ) {
        this.canvasService = canvasService;

        this.connect();
    }

    connect() {
        const socket = new SockJS('/colors-interact-websocket');
        this.stompClient = Stomp.over(socket);
        // stop debug messages
        this.stompClient.debug = () => { };
        this.stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            this.stompClient.subscribe('/topic/game-state', (message) => {
                const gameState = JSON.parse(message.body);
                this.canvasService.updateBoard(gameState);
            });
            this.sendStartGameCommand();
            setTimeout(() => {
                this.sendNewPlayerCommand();
            }, 1000)
        });
    }

    sendStartGameCommand() {
        console.log('sending start game command');
        this.stompClient.send("/app/game/start", {}, '');
    }

    sendNewPlayerCommand() {
        console.log('sending new player command');
        this.stompClient.send("/app/player/new", {}, JSON.stringify({
            player: {
                name: 'Michael',
                id: 'Michael'
            }
        }));
    }

    sendChangeDirectionCommand(direction) {
        console.log(`sending change direction command: ${direction}`);
        this.stompClient.send("/app/player/change-direction", {}, JSON.stringify({
            player: {
                name: 'Michael',
                id: 'Michael'
            },
            direction: direction
        }));
    }


}