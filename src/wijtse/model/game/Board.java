package wijtse.model.game;

import javafx.scene.canvas.GraphicsContext;
import wijtse.model.brain.GeneticAlgorithm;
import wijtse.view.BoardView;

import java.util.ArrayList;

/**
 * Created by wijtse on 17-11-16.
 */
public class Board {

    //Snake's brain structure:
    public static final int INPUT_NEURONS = 15;
    public static final int OUTPUT_NEURONS = 2;
    public static final int HIDDEN_LAYERS = 1;
    public static final int NEURONS_PER_HIDDEN_LAYER = 15;
    private static final int DNA_SIZE = INPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + OUTPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + (HIDDEN_LAYERS - 1) * NEURONS_PER_HIDDEN_LAYER * NEURONS_PER_HIDDEN_LAYER;

    private int width;
    private int height;
    private int maxPopulation;
    private int maxFood;
    private double foodSpawnRate;

    private GeneticAlgorithm geneticAlgorithm;

    private ArrayList<ArrayList<Integer>> foodLocations;
    private ArrayList<Snake> population;

    public Board(int width, int height, int maxPopulation, int maxFood, double foodSpawnRate) {
        this.width = width;
        this.height = height;
        this.maxPopulation = maxPopulation;
        this.maxFood = maxFood;
        this.foodSpawnRate = foodSpawnRate;

        geneticAlgorithm = new GeneticAlgorithm(DNA_SIZE);

        foodLocations = new ArrayList<>();
        population = new ArrayList<>();

        for (int i = 0; i < maxPopulation; i++) {
            population.add(new Snake(this, width / 4 * 3, height / 2, geneticAlgorithm.getRandomDNA()));
        }

        for (int i = 0; i < maxFood; i++) {
            ArrayList<Integer> foodLocation = new ArrayList<>(2);
            foodLocation.add((int) (Math.random() * width));
            foodLocation.add((int) (Math.random() * height));
            foodLocations.add(foodLocation);
        }
    }

    public void update() {
        spawnFood();
        for (Snake snake : population) {
            snake.adjustDirection();
            snake.move();
        }
    }

    public void spawnFood() {
        if (foodLocations.size() < maxFood) {
            if (Math.random() < foodSpawnRate) {
                ArrayList<Integer> foodLocation = new ArrayList<>(2);
                foodLocation.add((int) (Math.random() * width));
                foodLocation.add((int) (Math.random() * height));
                foodLocations.add(foodLocation);
            }
        }
    }

    public boolean eatFood(int x, int y) {
        boolean result = false;
        int foodNr = 0;
        for (ArrayList<Integer> foodLocation : foodLocations) {
            if (foodLocation.get(0) == x && foodLocation.get(1) == y) {
                result = true;
                break;
            }
            foodNr++;
        }

        //Remove the food if eaten
        if (result) {
            foodLocations.remove(foodNr);
        }
        return result;
    }

    public boolean isThereFoodAt(int x, int y) {
        boolean result = false;
        for (ArrayList<Integer> foodLocation : foodLocations) {
            result = foodLocation.get(0) == x && foodLocation.get(1) == y;
            break;
        }
        return result;
    }

    public void draw(GraphicsContext graphics) {
        //Draw background
        graphics.setFill(BoardView.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width * BoardView.BOARD_TILE_SIZE, height * BoardView.BOARD_TILE_SIZE);

        //Draw snakes
        for (Snake snake : population) {
            snake.draw(graphics);
        }

        //Draw food
        graphics.setFill(BoardView.FOOD_COLOR);
        for (ArrayList<Integer> foodLocation : foodLocations) {
            graphics.fillOval(foodLocation.get(0) * BoardView.BOARD_TILE_SIZE, foodLocation.get(1) * BoardView.BOARD_TILE_SIZE, BoardView.BOARD_TILE_SIZE, BoardView.BOARD_TILE_SIZE);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
