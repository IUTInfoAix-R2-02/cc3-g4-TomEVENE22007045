package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    //Imports text field
    @FXML
    private TextField comp1;
    @FXML
    private TextField comp2;
    @FXML
    private TextField comp3;
    @FXML
    private TextField comp4;
    @FXML
    private TextField comp5;
    @FXML
    private TextField comp6;

    //import Pane of Circle
    @FXML
    private Pane paneCircle;

    //import of Labels
    @FXML
    private Label errorTitleLabel;
    @FXML
    private Label errorLabel;

    //Variables
    private double value;

    private ArrayList<TextField> textFields = new ArrayList<>();

    private ArrayList<Circle> dotsList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFields.add(comp1);
        textFields.add(comp2);
        textFields.add(comp3);
        textFields.add(comp4);
        textFields.add(comp5);
        textFields.add(comp6);
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    @FXML
    public void handlerTextFiled(ActionEvent event){
        Node node = (Node) event.getSource() ;
        int axeId = Integer.parseInt(node.getId().substring(4));
        try {
            Double value = Double.parseDouble(textFields.get(axeId - 1).getText());
            if(value < 0.0 || value > 20.0){
                setErrorText("Les valeurs doivent être entre 0 et 20", true);
                return;
            }else{
                setErrorText("", false);
            }

            if(getCircleFromId("dots." + axeId) != null){
                System.out.println("ici");
                Circle dots = getCircleFromId("dots." + axeId);
                dots.setCenterX(getXRadarChart(value, axeId));
                dots.setCenterY(getYRadarChart(value, axeId));
                return;
            }

            Circle dots = new Circle(getXRadarChart(value, axeId), getYRadarChart(value, axeId), 5);
            dots.setId("dots."+axeId);
            dotsList.add(dots);
            paneCircle.getChildren().add(dots);

        }catch (Exception e){
            setErrorText("Uniquement sout le format (20.0)", true);
            return;
        }
    }

    private void setErrorText(String textError,Boolean visible){
        errorTitleLabel.setVisible(visible);
        errorLabel.setVisible(visible);
        errorLabel.setText(textError);
    }

    public void handlerClearButton(){
        for (TextField t : textFields){
            t.setText("");
        }
        for(Circle c : dotsList){
            paneCircle.getChildren().remove(c);
        }
    }

    public Circle getCircleFromId(String id){
        for(Circle c : dotsList){
            if(Objects.equals(c.getId(), id)){
                return c;
            }
        }
        return null;
    }

}
