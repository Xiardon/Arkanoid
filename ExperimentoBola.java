import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.util.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.geometry.*;
import java.util.*;
public class ExperimentoBola extends Application
{
    private boolean enMovimiento = true;
    private int velocidadCirculoX = 1;
    private int velocidadCirculoY = 1;
    private int velocidadRectangulo = 0;
    private Circle circulo = new Circle();
    private double xMax;
    private double xMin;
    private double yMax;
    private double yMin;
    private Rectangle rectangulo = new Rectangle();
    private Timeline tm;
    private long tiempoEnSegundos;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage escenario)
    {
        startGame(escenario);
    }

    public void startGame(Stage escenario){
        Group contenedor = new Group();

        circulo.setFill(Color.RED);  
        circulo.setRadius(20);
        circulo.setCenterX(new Random().nextInt(500 - 40));
        circulo.setCenterY(100);

        rectangulo.setFill(Color.BLUE);
        rectangulo.setX(250);
        rectangulo.setY(490);
        rectangulo.setHeight(10);
        rectangulo.setWidth(70);

        Label finJuego = new Label("Game Over");
        finJuego.setVisible(false);
        finJuego.setMaxWidth(200);
        finJuego.setMaxHeight(100);
        finJuego.setFont(new Font(30));
        finJuego.setAlignment(Pos.CENTER_LEFT);
        finJuego.layoutXProperty().bind(escenario.widthProperty().subtract(finJuego.widthProperty()).divide(2));
        finJuego.setTranslateY(200);

        Label contador = new Label();
        contador.setFont(new Font(20));
        contador.setTranslateX(250);

        TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    tiempoEnSegundos++;
                }                        
            };
        Timer timer = new Timer();
        timer.schedule(tarea, 0, 1000);

        tm = new Timeline(new KeyFrame(Duration.millis(10), event->{
                    long segundos = tiempoEnSegundos % 60;
                    long minutos = tiempoEnSegundos / 60;
                    contador.setText(minutos + ":" + segundos + " Tiempo");
                    circulo.setTranslateX(circulo.getTranslateX() + velocidadCirculoX);
                    circulo.setTranslateY(circulo.getTranslateY() + velocidadCirculoY);
                    xMin = circulo.getBoundsInParent().getMinX();
                    xMax = circulo.getBoundsInParent().getMaxX();
                    yMin = circulo.getBoundsInParent().getMinY();
                    yMax = circulo.getBoundsInParent().getMaxY();
                    rectangulo.setTranslateX(rectangulo.getTranslateX() + velocidadRectangulo);
                    if(xMax > 500 || xMin < 0){
                        velocidadCirculoX = -velocidadCirculoX;
                    }else if(yMin < 0){
                        velocidadCirculoY = -velocidadCirculoY;
                    }
                    if(yMin > 500){
                        finJuego.setVisible(true);
                        tm.stop();
                    }

                    if(circulo.getBoundsInParent().intersects(rectangulo.getBoundsInParent())){
                        velocidadCirculoY = -velocidadCirculoY;
                    }
                    if(rectangulo.getBoundsInParent().getMaxX() == 500 || rectangulo.getBoundsInParent().getMinX() == 0){
                        velocidadRectangulo = 0;
                    }

                }));
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.playFromStart();

        ArrayList<Rectangle> ladrillos = new ArrayList<>();
        for(int i = 0; i < 5000; i++){
            Rectangle ladrillo =  new Rectangle();
            ladrillo.setFill(Color.BLACK);
            ladrillo.setWidth(50);
            ladrillo.setHeight(30);
            ladrillo.setTranslateX(new Random().nextInt(500 - 50));
            ladrillo.setTranslateY(new Random().nextInt(250 - 10));
            ladrillos.add(ladrillo);
        }
        int x = 0;
        while(x < ladrillos.size()){
            Rectangle ladrillo =  ladrillos.get(x);
            int i = x + 1;
            while(i < ladrillos.size()){
                Rectangle ladrillo1 = ladrillos.get(i);
                if(ladrillo.getBoundsInParent().intersects(ladrillo1.getBoundsInParent())){
                    ladrillo1.setVisible(false);
                    
                }
                i++;
            }
            x ++;
        }
        contenedor.getChildren().addAll(ladrillos);

        Button bt = new Button("Reset");
        bt.setOnAction(event -> {
                tm.stop();
                escenario.close();
                startGame(escenario);
            });

        contenedor.getChildren().addAll(circulo, rectangulo, bt, finJuego, contador);
        Scene escena = new Scene(contenedor, 500, 500);
        escena.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.LEFT && !(rectangulo.getBoundsInParent().getMinX() == 0)){
                    velocidadRectangulo = -1;
                }else if(event.getCode() == KeyCode.RIGHT && !(rectangulo.getBoundsInParent().getMaxX() == 500)){
                    velocidadRectangulo = 1;
                }
            });
        escenario.setScene(escena);
        escenario.setTitle("ARKANOID");
        escenario.show();

    }

}
