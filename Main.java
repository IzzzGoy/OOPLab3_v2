package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.bots.*;
import sample.tribe.TribesType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    private Random random = new Random();
    private boolean startUp = true;
    private boolean isPause = false;
    private boolean game = true;
    private int Xint;
    private int Yint;
    private Player player;
    private ArrayList<Bot> bots = new ArrayList<>(3);
    private Controller controller;
    private Timer timer;
    private AnimationTimer animationTimer;
    private int playerEVOChanse = 0;
    private int bot1EVOChanse = 0;
    private int bot2EVOChanse = 0;
    private int bot3EVOChanse = 0;
    private World world = new World(50);

    {
        initBots(Color.web("#ef5350"), "Bot1");
        initBots(Color.GRAY, "Bot2");
        initBots(Color.ROSYBROWN, "Bot3");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        ScrollPane gamePane = (ScrollPane) root.lookup("#gamePane");
        Button addAttack = (Button) root.lookup("#AddAttackButton");
        addAttack.onMouseClickedProperty().set(mouseEvent -> player.addAttack());
        Button addFoodProdution = (Button) root.lookup("#AddFoodProdutionButton");
        addFoodProdution.onMouseClickedProperty().set(mouseEvent -> player.addFoodProduction());
        Button addBorning = (Button) root.lookup("#AddBorningButton");
        addBorning.onMouseClickedProperty().set(mouseEvent -> player.addBorn());

        root.onKeyPressedProperty().set(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isPause = !isPause;
            }
        });

        gamePane.onKeyPressedProperty().set(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isPause = !isPause;
            }
        });

        gamePane.onMouseClickedProperty().set(mouseEvent -> {
            if (startUp) {
                double x = mouseEvent.getX() + (1000 - gamePane.getWidth()) * gamePane.getHvalue();
                double y = mouseEvent.getY() + (1000 - gamePane.getHeight()) * gamePane.getVvalue();

                Xint = ((int) x) / 20;
                Yint = ((int) y / 20);

                player = new Player(Color.web("#43a047"), "Player", TribesType.PREDATORS, world.getLand().get(Xint + Yint * 50), world);
                startUp = false;

                draw(controller);

            } else {
                double x = mouseEvent.getX() + (1000 - gamePane.getWidth()) * gamePane.getHvalue();
                double y = mouseEvent.getY() + (1000 - gamePane.getHeight()) * gamePane.getVvalue();

                Xint = ((int) x) / 20;
                Yint = ((int) y / 20);
                Territory territory = world.getLand().get(Xint + Yint * 50);
                controller.setCellStats(territory.getOwnerName(), Integer.toString(territory.getPopulation()), Integer.toString(territory.getWater()), Integer.toString((int) territory.getPlants()), Integer.toString((int) territory.getAnimals()));
            }
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                int defitedCount = 0;
                changeInfo();
                if (player != null) {
                    if (player.getPopulation() == 0) defitedCount++;
                    for (Bot bot : bots) {
                        if (bot.getPopulation() == 0) defitedCount++;
                    }
                    if (defitedCount == 3) {
                        game = false;
                        controller.clearField();
                        controller.showGameOver();
                        animationTimer.stop();
                    } else {
                        draw(controller);
                        controller.EVOPintsValueRefresh(Integer.toString(player.getEvolutionPoints()));
                        controller.playerStatRefresh(Long.toString(player.getPopulation()), Double.toString(player.getTribe().getAttack()), Double.toString(player.getTribe().getFood_production()), Double.toString(player.getTribe().getBorn()));
                    }
                } else {
                    controller.clearField();
                    controller.initField();
                }
            }
        };
        animationTimer.start();

        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    while (player == null) {
                        Thread.sleep(600);
                    }
                    while (game) {
                        if (!isPause) {
                            for (Bot bot : bots) {
                                bot.update();
                            }
                            world.update();
                            EVOChanseRoll();
                        }
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }, 0, 600);


        primaryStage.setTitle("Lab3");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void draw(Controller controller) {
        controller.clearField();
        for (int i = 0; i < world.getLand().size(); i++) {
            if (world.getLand().get(i).getOwner() != null) {
                if (world.getLand().get(i).getOwnerName().equals(player.getName())) {
                    controller.draw((i % world.length()) * 20, (i / world.length()) * 20, player.getColor());
                } else if (world.getLand().get(i).getOwnerName().equals(bots.get(0).getName())) {
                    controller.draw((i % world.length()) * 20, (i / world.length()) * 20, bots.get(0).getColor());
                } else if (world.getLand().get(i).getOwnerName().equals(bots.get(1).getName())) {
                    controller.draw((i % world.length()) * 20, (i / world.length()) * 20, bots.get(1).getColor());
                } else if (world.getLand().get(i).getOwnerName().equals(bots.get(2).getName())) {
                    controller.draw((i % world.length()) * 20, (i / world.length()) * 20, bots.get(2).getColor());
                }
            }

        }
        controller.initField();
    }

    private void changeInfo() {
        controller.setCellStats(world.getLand().get(Xint + Yint * world.length()).getOwnerName(), Integer.toString(world.getLand().get(Xint + Yint * world.length()).getPopulation()), Integer.toString(world.getLand().get(Xint + Yint * world.length()).getWater()), Integer.toString((int) world.getLand().get(Xint + Yint * world.length()).getPlants()), Integer.toString((int) world.getLand().get(Xint + Yint * world.length()).getAnimals()));
    }

    private void EVOChanseRoll() {
        playerEVOChanse += random.nextInt(50);
        bot1EVOChanse += random.nextInt(50);
        bot2EVOChanse += random.nextInt(50);
        bot3EVOChanse += random.nextInt(50);
        if (playerEVOChanse >= 100) {
            player.addEvolutionPoints();
            playerEVOChanse = 0;
        }
        if (bot1EVOChanse >= 100) {
            bots.get(0).addEvolutionPoints();
            bot1EVOChanse = 0;
        }
        if (bot2EVOChanse >= 100) {
            bots.get(1).addEvolutionPoints();
            bot2EVOChanse = 0;
        }
        if (bot3EVOChanse >= 100) {
            bots.get(2).addEvolutionPoints();
            bot3EVOChanse = 0;
        }
    }

    void initBots(Color color, String name) {
        switch (BotsType.getRandomType()) {
            case MONSTER:
                bots.add(new Monster(color, name, TribesType.getRandomType(), world.getLand().get(random.nextInt(world.size())), world));
                break;
            case ATTACKER:
                bots.add(new Attacker(color, name, TribesType.getRandomType(), world.getLand().get(random.nextInt(world.size())), world));
                break;
            case POPULATION_MASTER:
                bots.add(new PopulationMaster(color, name, TribesType.getRandomType(), world.getLand().get(random.nextInt(world.size())), world));
                break;
            case DEFENDER:
                bots.add(new Defender(color, name, TribesType.getRandomType(), world.getLand().get(random.nextInt(world.size())), world));
                break;
        }
    }
}
