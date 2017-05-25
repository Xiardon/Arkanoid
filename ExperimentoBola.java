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
    private Stage escenario;

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
        circulo.setCenterX(new Random().nextDouble() + 20);
        circulo.setCenterY(250 - 20);
        
        rectangulo.setFill(Color.BLUE);
        rectangulo.setX(250);
        rectangulo.setY(450);
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
        
        tm = new Timeline(new KeyFrame(Duration.millis(10), event->{
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

        Button bt = new Button("Reset");
        bt.setOnAction(event -> {
            tm.stop();
            escenario.close();
            startGame(escenario);
            });
            
        contenedor.getChildren().addAll(circulo, rectangulo, bt, finJuego);

        Scene escena = new Scene(contenedor, 500, 500);
        escena.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.LEFT && !(rectangulo.getBoundsInParent().getMinX() == 0)){
                velocidadRectangulo = -1;
            }else if(event.getCode() == KeyCode.RIGHT && !(rectangulo.getBoundsInParent().getMaxX() == 500)){
                velocidadRectangulo = 1;
            }
        });
        escenario.setScene(escena);
        escenario.show();

    }

    
    
    
    
    

}
