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
    public static final int HIDDEN_LAYERS = 1;
    public static final int NEURONS_PER_HIDDEN_LAYER = 15;
    private static final int DNA_SIZE = INPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + OUTPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + (HIDDEN_LAYERS - 1) * NEURONS_PER_HIDDEN_LAYER * NEURONS_PER_HIDDEN_LAYER;

    private int width;
    private int height;
    private int maxPopulation;
    private int maxFood;
    private double foodSpawnRate;
    private int snakesDied;
    private int bestFitness;
    private int generation;

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
        this.generation = 0;

        geneticAlgorithm = new GeneticAlgorithm(DNA_SIZE);

        foodLocations = new ArrayList<>();
        population = new ArrayList<>();

        for (int i = 0; i < maxPopulation; i++) {
            population.add(new Snake(this, width / 4 * 3, height / 2, geneticAlgorithm.getRandomDNA()));
        }
//        population.add(new Snake(this, width / 4 * 3, height / 2, new ArrayList<>(Arrays.asList(new Double[]{1.5928094036950329, 0.9001733199085038, -1.1778166595800288, -2.2154132113021827, 3.199786638722295, -2.649784614267027, -1.3036860443149463, 2.385204299526394, 3.110701933450092, -0.03719263125500838, -0.5605384798506305, 0.2741991968781585, 1.0120508352228468, 3.500431613089643, 1.8388705151656835, -0.7747724829281628, 2.399639152207775, -2.600050933485739, -3.0175642323600176, -3.6222753118166633, 3.4962232325728833, -1.4183588545020323, -1.6573257998028614, -0.07376271948451585, -2.7341373874298442, 0.9434259848324453, 1.450037909689426, 0.7679938002828299, 0.9117452794192813, -0.9444580798986468, 0.7284600092028466, -1.563318149169735, -1.2193648446967371, -2.3378999551002977, -3.550462871992301, 0.05041489739573812, 2.6354973929644894, 1.9955405049215447, -0.9072809426759605, -2.569015240247782, 1.770538862738423, 2.1017313928346173, 1.659031347187316, -2.8437595762251373, 3.3757705564632623, -3.1374296286561085, 2.4257173710887994, 0.1677686483326699, -1.348479235303035, -3.04357814446674, 2.9566219500650517, -3.030255526876396, -2.863232918891388, 1.1814983382800985, -3.9104515998161338, 0.06251873015248677, -3.6339413498226687, 1.1459709265905733, 3.8945991947371024, 3.6059641549218284, 0.3984290821126182, -3.3898442836905804, -1.455914269722344, 2.2521581920598424, 2.3369947530723305, 2.005742738292719, 0.6772602319853629, 2.2234539635785735, -2.6134340535934415, 3.397972302191816, -2.2269411077779777, 2.7427921499847185, -2.478124606036385, 2.353385844499469, -0.1273657207382657, 3.132084901041, 3.3721614640191904, -3.3946844808013665, -1.3305047450066771, 1.6332078038338818, 0.6374626129445522, 0.7407042043023377, -2.5317558190045464, 0.32565776898620946, 1.1588854632204884, -0.7604680463389002, 1.3916201229283818, 1.6901790790220268, 2.229654250620678, -3.3836587759039443, 3.626059868265636, -3.9477290861247294, -0.2495182109667402, -2.7945479055854845, -2.5103372144694305, -3.1871823947183113, 2.9929770428114413, 0.5537488716386605, -0.9772472613761316, -0.8495491766296528, 3.594205887359845, 3.776194782695404, -0.4327684605302382, -0.8459789911619984, 0.06391267076674634, 2.0343295793922085, 2.7061743423266007, -0.22473821050635845, 0.6545502903994533, -2.9032503824076326, -3.2628848079521102, 3.643889645021817, 1.7774934824518285, -0.12948915383039594, -3.775398052938068, -2.901975555635536, -0.11968878533017335, 2.786354211503019, -1.4317283756287331, 1.8356957419494186, 2.7821831681924767, -1.1791081912095986, 0.9281045916846553, 1.1753383264649262, 1.3356288382266461, -0.11989328830279611, -1.3138609153484762, 2.1987326601933823, -3.459079523534748, 3.1115417044731553, -0.705325896244962, -1.717694124599329, -1.34789743408247, -1.9271965273459042, -3.5429758615910325, -0.37005025062565533, -0.22122999061555415, -2.4396168389683135, 1.4527502685724638, -1.6553786438615923, 2.224953206563149, 2.5369297467444003, 2.919003892418064, 1.029515768385994, -0.35487199608494, -2.6899628415690415, 2.8009769999917253, -0.7328870362777904, 0.38665416376705775, 2.027127072402381, 0.5189772900307563, -3.6761451018638587, 1.9721409564222006, -0.22374693356215314, -2.922088565960319, -1.2764041244729656, -3.275011636608312, -0.31206316416323343, -3.2848130335917842, -0.453183365882845, 0.7223852534575776, -3.5541670922338193, 2.7998587078407944, 3.1961056770778304, -1.7978922798966002, 1.9177698310078846, 2.957164230547593, 3.4306655734531164, -3.1825650306203492, 2.6014749977589773, 0.17570594890647584, 3.4585698550919313, -2.20744998120381, 0.3472969139915465, 2.1648818933524865, 0.13510554324173274, -1.5954139394259625, -0.8963849084057891, -3.540525380663066, 0.33710588790516205, 3.4300471140131563, 0.49714760852008233, -0.34889238177191917, 1.5298209274413672, -1.3210605301343934, -1.6799867013969783, 0.32743321302902917, -1.9815931799954827, -3.6721853682963257, 3.715228369459102, 2.9671126425169083, 1.9290823290494377, 1.7299386337649016, -3.7400063862512765, -3.8290064021271766, -1.9406877779708713, -0.18095831306505872, -0.05125357178896017, 2.441057062944525, -3.1656427976215857, -0.11096002762351187, 0.0307099126856194, -3.7324589786494906, -3.349388595972128, -3.2896748636993873, 1.2241727145300825, 0.415736281215092, -3.329466069272182, 0.6532741325762679, -3.97829299520492, -2.087438487206441, 3.598368845295023, -1.2432370515066058, 2.896691032216803, -3.686730962288971, 2.3555023187261606, -3.936267879352809, 1.7159386553827387, -2.784576871683284, 1.6430254927990138, -2.677105817238515, 2.8455268102427302, -2.239017922048574, -1.8571635696897921, -3.1291747173420967, 0.06430745525159498, -2.2854754508677084, -1.6361690175278971, 0.7876071443159063, 2.58475680207314, 0.6315090687231555, -2.2726444362842653, 0.26360085154116586, -2.246136427189354, 2.979091418656081, 1.6044080796704643, 2.60096396436463, -0.9802727196478687, -3.606047218540165, -0.555057294600827, -2.712423025390529, 0.599260858035767, -3.0207381914533267, 0.13560784270256132, 3.1494010683993316, 1.7192539935895832, -3.818040148454526, -2.5477149863471187, 1.1272711414350036, 0.31624804067564316, 3.686868339929248, 3.2779379171026024, -2.9708725207919997, -3.720595135891047, 0.6880576742921862}))));

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
            if (snake.isAlive()) {
                snake.adjustDirection();
                snake.move();
            }
        }

        //Wait for the population to die and start a new generation
//        boolean everySnakeIsDead = true;
//        for (Snake snake : population) {
//            if (snake.isAlive()) {
//                everySnakeIsDead = false;
//                break;
//            }
//        }
//        if (everySnakeIsDead) {
//            generation++;
//            ArrayList<Snake> bestSnakes = getTwoBestSnakes();
//            population.clear();
//            for (int i = 0; i < maxPopulation; i++) {
//                population.add(new Snake(this, width / 4 * 3, height / 2, geneticAlgorithm.mutate(geneticAlgorithm.breed(bestSnakes.get(0).getDna(), bestSnakes.get(1).getDna()))));
//            }
//        }

        //Keep a constant population
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
//        graphics.fillText("Generation: " + generation, 20, 30);
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