import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.Animation;
import java.util.Random;
import javafx.scene.input.KeyCode;
import java.util.Timer;
import java.util.TimerTask;



public class BolaClase extends Application
{
    private int velocidadEnX;
    private int velocidadEnY;
    private int velocidadPlataforma;
    private static int RADIO = 20;
    private int tiempoEnSegundos;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage escenario)
    {
        Group contenedor = new Group();

        velocidadEnX = 1;
        velocidadEnY = 1;
        tiempoEnSegundos = 70;

        Circle circulo = new Circle();
        circulo.setFill(Color.RED);  
        circulo.setRadius(RADIO);

        Rectangle plataforma = new Rectangle();
        plataforma.setWidth(50);
        plataforma.setHeight(5);
        plataforma.setTranslateX(225);
        plataforma.setTranslateY(480);
        plataforma.setFill(Color.BLUE);
        contenedor.getChildren().add(plataforma);

        velocidadPlataforma = 1;

        Random aleatorio = new Random();

        circulo.setCenterX(20 + aleatorio.nextInt(500 - 40));
        circulo.setCenterY(50);
        contenedor.getChildren().add(circulo);
        
        Label tiempoPasado = new Label("0");
        contenedor.getChildren().add(tiempoPasado);

        Scene escena = new Scene(contenedor, 500, 500);
        escenario.setScene(escena);
        escenario.show();

        Timeline timeline = new Timeline();
        KeyFrame keyframe = new KeyFrame(Duration.seconds(0.01), event -> {

                        // Controlamos si la bola rebota a ziquierda o derecha
                        if (circulo.getBoundsInParent().getMinX() <= 0 ||
                        circulo.getBoundsInParent().getMaxX() >= escena.getWidth()) {
                            velocidadEnX = -velocidadEnX;                              
                        }

                        // Conrolamos si la bola rebota arriba y abajo
                        if (circulo.getBoundsInParent().getMinY() <= 0) {
                            velocidadEnY = -velocidadEnY;
                        }

                        if (circulo.getBoundsInParent().getMaxY() == plataforma.getBoundsInParent().getMinY()) {
                            double centroEnXDeLaBola = circulo.getBoundsInParent().getMinX() + RADIO;
                            double minEnXDeLaPlataforma = plataforma.getBoundsInParent().getMinX();
                            double maxEnXDeLaPlataforma = plataforma.getBoundsInParent().getMaxX();
                            if ((centroEnXDeLaBola >= minEnXDeLaPlataforma) &&
                            (centroEnXDeLaBola <= maxEnXDeLaPlataforma)) {
                                //La bola esta sobre la plataforma
                                velocidadEnY = -velocidadEnY;
                            }
                        }

                        circulo.setTranslateX(circulo.getTranslateX() + velocidadEnX);
                        circulo.setTranslateY(circulo.getTranslateY() + velocidadEnY);

                        plataforma.setTranslateX(plataforma.getTranslateX() + velocidadPlataforma);
                        if (plataforma.getBoundsInParent().getMinX() == 0  || 
                        plataforma.getBoundsInParent().getMaxX() == escena.getWidth()) {
                            velocidadPlataforma = 0;
                        }
                        
                        // Actualizamos la etiqueta del tiempo
                        int minutos = tiempoEnSegundos / 60;
                        int segundos = tiempoEnSegundos % 60;
                        tiempoPasado.setText(minutos + ":" + segundos);                        

                        // Comrpobamos si el juego debe detenerse
                        if (circulo.getBoundsInParent().getMinY() > escena.getHeight()) {
                            Label mensajeGameOver = new Label("Game over");
                            mensajeGameOver.setTranslateX(escena.getWidth() / 2);
                            mensajeGameOver.setTranslateY(escena.getHeight() / 2);
                            contenedor.getChildren().add(mensajeGameOver);
                            timeline.stop();
                        }
                        
                    });  
        timeline.getKeyFrames().add(keyframe);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();     

        escena.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.RIGHT && 
                plataforma.getBoundsInParent().getMaxX() != escena.getWidth()) {
                    velocidadPlataforma = 1;
                }
                else if (event.getCode() == KeyCode.LEFT && 
                plataforma.getBoundsInParent().getMinX() != 0) {
                    velocidadPlataforma = -1;
                }
            });

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                tiempoEnSegundos++;
            }                        
        };
        Timer timer = new Timer();
        timer.schedule(tarea, 0, 1000);
            
    }

}
