import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.util.*;

public class ExperimentoBola extends Application
{
            boolean enMovimiento = true;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage escenario)
    {
        Group contenedor = new Group();

        Circle circulo = new Circle();
        circulo.setFill(Color.RED);  
        circulo.setRadius(20);
        circulo.setCenterX(250);
        circulo.setCenterY(250);
        contenedor.getChildren().add(circulo);

        Timeline tm = new Timeline(new KeyFrame(Duration.millis(10), event->{
                        circulo.setTranslateX(circulo.getTranslateX() + 1);
                        circulo.setTranslateY(circulo.getTranslateY() + 1);
          
                    }));
                    
        tm.setAutoReverse(true);
        tm.setCycleCount(Timeline.INDEFINITE);
        
        tm.play();

       Button bt = new Button("Iniciar/Parar");
       bt.setOnAction(event -> {
           if(enMovimiento){
               tm.stop();
            }else{
                tm.play();
            }
            enMovimiento = !enMovimiento;
        });
        contenedor.getChildren().add(bt);

        Scene escena = new Scene(contenedor, 500, 500);
        escenario.setScene(escena);
        escenario.show();

    }

}
