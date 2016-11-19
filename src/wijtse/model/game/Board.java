package wijtse.model.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
    public static final int HIDDEN_LAYERS = 2;
    public static final int NEURONS_PER_HIDDEN_LAYER = 15;
    private static final int DNA_SIZE = INPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + OUTPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + (HIDDEN_LAYERS - 1) * NEURONS_PER_HIDDEN_LAYER * NEURONS_PER_HIDDEN_LAYER;

    private int width;
    private int height;
    private int maxPopulation;
    private int maxFood;
    private double foodSpawnRate;
    private int snakesDied;
    private int bestFitness;

    private GeneticAlgorithm geneticAlgorithm;

    private ArrayList<ArrayList<Integer>> foodLocations;
    private ArrayList<Snake> population;

    public Board(int width, int height, int maxPopulation, int maxFood, double foodSpawnRate) {

        this.width = width;
        this.height = height;
        this.maxPopulation = maxPopulation;
        this.maxFood = maxFood;
        this.foodSpawnRate = foodSpawnRate;
        this.snakesDied = 0;
        this.bestFitness = 0;

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

        for (int i = population.size() - 1; i >= 0; i--) {
            if (!population.get(i).isAlive()) {
                population.remove(i);
                snakesDied++;
                ArrayList<Snake> bestSnakes = getTwoBestSnakes();
                population.add(new Snake(this, width / 4 * 3, height / 2, geneticAlgorithm.mutate(geneticAlgorithm.breed(bestSnakes.get(0).getDna(), bestSnakes.get(1).getDna()))));
            }
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
            if (result) {
                break;
            }
        }
        return result;
    }

    public void draw(GraphicsContext graphics) {
        //Draw background
        graphics.setFill(BoardView.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width * BoardView.BOARD_TILE_SIZE, height * BoardView.BOARD_TILE_SIZE);

//        graphics.setStroke(Color.GRAY); //Draw background grid
//        for (int i = 1; i < width; i++) {
//            graphics.strokeLine(i*BoardView.BOARD_TILE_SIZE, 0, i*BoardView.BOARD_TILE_SIZE, height*BoardView.BOARD_TILE_SIZE);
//        }
//        for (int i = 1; i < height; i++) {
//            graphics.strokeLine(0, i*BoardView.BOARD_TILE_SIZE, width*BoardView.BOARD_TILE_SIZE, i*BoardView.BOARD_TILE_SIZE);
//        }

        //Draw snakes
        for (Snake snake : population) {
            snake.draw(graphics);
        }

        //Draw food
        graphics.setFill(BoardView.FOOD_COLOR);
        for (ArrayList<Integer> foodLocation : foodLocations) {
            graphics.fillOval(foodLocation.get(0) * BoardView.BOARD_TILE_SIZE, foodLocation.get(1) * BoardView.BOARD_TILE_SIZE, BoardView.BOARD_TILE_SIZE, BoardView.BOARD_TILE_SIZE);
        }

        int currentBestFitness = getTwoBestSnakes().get(0).getFitness();
        if (currentBestFitness > bestFitness) {
            bestFitness = currentBestFitness;
        }
        graphics.setFill(Color.WHITE);
        graphics.fillText("Snakes died: " + snakesDied, 20, 30);
        graphics.fillText("Best fitness: " + bestFitness, 20, 30 + graphics.getFont().getSize() + 4);
        graphics.fillText("Best cur. fitness: " + currentBestFitness, 20, 30 + 2 * (graphics.getFont().getSize() + 4));
        graphics.setFill(Color.BLACK);
    }

    public ArrayList<Snake> getTwoBestSnakes() {
        ArrayList<Snake> result = new ArrayList<>();

        ArrayList<Integer> highScores = new ArrayList<>(2);
        highScores.add(-1);
        highScores.add(-1);
        for (Snake snake : population) {
            if (snake.getFitness() > highScores.get(0)) {
                highScores.add(0, snake.getFitness());
                result.add(0, snake);
            } else if (snake.getFitness() > highScores.get(1)) {
                highScores.add(1, snake.getFitness());
                result.add(1, snake);
            }
        }

        return result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void openBrainViewOfSnake(int x, int y) {
        for (Snake snake : population) {
            if (snake.getHeadX() == x && snake.getHeadY() == y) {
                snake.openBrainView();
                break;
            }
        }

    }
}