package han.controller.game;

import han.model.game.Segment;
import han.model.network.Network;
import han.view.game.GameView;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;

import java.util.Date;

import static han.view.game.GameView.GRID_SIZE;
import static han.view.network.NetworkView.CANVAS_HEIGHT;
import static han.view.network.NetworkView.CANVAS_WIDTH;


/**
 * Created by han on 18-11-16.
 */
public class Clock implements Runnable {

    public static final int TICK_LENGTH = 1000; //40 ticks/s
    private boolean running = true;
    private NetworkView networkView;
    private GameView gameView;

    public Clock(NetworkView networkView, GameView gameView) {
        this.networkView = networkView;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long startTime;
        while (running) {
            startTime = new Date().getTime();
            tick();
            waitForNextTick(startTime);
        }
    }

    private void tick() {
        System.out.println("tick");
        gameView.getBoard().getSnake().onTick();
        gameView.initGame(gameView.getCanvas().getGraphicsContext2D());

    }


    private void waitForNextTick(long startTime) {
        long timePassed = new Date().getTime() - startTime;
        long timeToWait = TICK_LENGTH - timePassed;
        if (timeToWait > 0) {
            try {
                Thread.sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't keep up!");
            running = false;
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
