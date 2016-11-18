package wijtse.controller;

import javafx.scene.canvas.GraphicsContext;
import wijtse.model.game.Board;

import java.util.Date;

/**
 * Created by wijtse on 17-11-16.
 */
public class Clock extends Thread{

    public static final int TICKS_PER_SECOND = 1;

    private int tickLength;
    private boolean running;
    private Board board;
    private GraphicsContext graphics;

    public Clock(Board board, GraphicsContext graphics) {
        tickLength = 1000 / TICKS_PER_SECOND;
        running = true;

        this.board = board;
        this.graphics = graphics;
    }

    @Override
    public void run() {
        long startTime;
        while (running) {
            startTime = new Date().getTime();
            tick();
            waitForNextTick(startTime);
        }
    }

    private void tick() {
        board.update();
        board.draw(graphics);
    }

    private void waitForNextTick(long startTime) {
        long timePassed = new Date().getTime() - startTime;
        long timeToWait = tickLength - timePassed;
        if (timeToWait > 0) {
            try {
                Thread.sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't keep up!");
        }
    }

}
