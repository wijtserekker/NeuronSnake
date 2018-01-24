package wijtse.controller;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import wijtse.model.game.Board;
import wijtse.view.BoardView;

import java.util.Date;

/**
 * Created by wijtse on 17-11-16.
 */
public class Clock extends Thread{

    public static final int TICKS_PER_SECOND = 8;

    private int tickLength;
    private boolean running;
    private boolean pause;
    private Board board;
    private BoardView view;
    private int speed;

    private Object pauseMonitor = new Object();

    public Clock(Board board) {
        tickLength = 1000 / TICKS_PER_SECOND;
        running = true;
        pause = false;
        speed = 1;

        this.board = board;
    }

    @Override
    public void run() {
        long startTime;
        while (running) {
            if (pause) {
                waitForResume();
            }
            startTime = new Date().getTime();
            tick();
            waitForNextTick(startTime);
        }
    }

    private void tick() {
        board.update();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                view.draw(board);
            }
        });
    }

    private void waitForNextTick(long startTime) {
        long timePassed = new Date().getTime() - startTime;
        long timeToWait = tickLength / speed - timePassed;
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

    private void waitForResume() {
        synchronized (pauseMonitor) {
            try {
                pauseMonitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseOrResume() {
        if (pause) {
            pause = false;
            synchronized (pauseMonitor) {
                pauseMonitor.notify();
            }
        } else {
            pause = true;
        }
    }

    public void incSpeed() {
        speed++;
    }

    public void decSpeed() {
        speed--;
        if (speed <= 0) {
            speed = 1;
        }
    }

    public void pleaseStop() {
        running = false;
    }

    public void openBrainViewOfSnake(int x, int y) {
        board.openBrainViewOfSnake(x, y);
    }

    public void setView(BoardView view) {
        this.view = view;
    }
}
