package han.controller.game;

import han.model.network.Network;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;

import java.util.Date;

import static han.view.network.NetworkView.CANVAS_HEIGHT;
import static han.view.network.NetworkView.CANVAS_WIDTH;

/**
 * Created by han on 18-11-16.
 */
public class Clock implements Runnable {

    public static final int TICK_LENGTH = 2000; //40 ticks/s
    private boolean running = true;

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
        NeuronSnake.setNetwork(new Network(NeuronSnake.getNetwork().getAmountOfInputNodes(),
                NeuronSnake.getNetwork().getAmountOfHiddenNodeGroups(),
                NeuronSnake.getNetwork().getAmountOfHiddenNodes(), NeuronSnake.getNetwork().getAmountOfOutputNodes()));
        NeuronSnake.getNwView().getCanvas().getGraphicsContext2D().setFill(Color.BLACK);
        NeuronSnake.getNwView().getCanvas().getGraphicsContext2D().fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        NeuronSnake.getNwView().initNetwork(NeuronSnake.getNwView().getCanvas().getGraphicsContext2D());
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
}
