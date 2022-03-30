package com.mycompany.pizzeria;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

public class PrimaryController implements Initializable {

    ObservableList<String> listaMasa
            = FXCollections.observableArrayList("Normal", "Integral");

    ObservableList<String> listaTipo
            = FXCollections.observableArrayList("Basica", "Cuatro quesos", "Barbacoa", "Mexicana");

    ObservableList<String> listaIngredientes
            = FXCollections.observableArrayList("Jamon", "Queso", "Tomate", "Cebolla", "Olivas");

    ObservableList<String> listaMedida
            = FXCollections.observableArrayList("Pequeña", "Mediana", "Familiar");

    @FXML
    private ToggleGroup masa;
    @FXML
    private RadioButton normal;
    @FXML
    private RadioButton integral;
    @FXML
    private ComboBox<String> tipo;
    @FXML
    private Spinner<String> medida;
    @FXML
    private ListView<String> ingredientes;
    @FXML
    private Button botonCalculo;
    @FXML
    private Label precioTotal;
    @FXML
    private TextField pedido;
    @FXML
    private Label masaPedido;
    @FXML
    private Label tipoPedido;
    @FXML
    private Label extrasPedido;
    @FXML
    private Label medidaPedido;

    HashMap<String, Double> precios = new HashMap<>();

    //variables extras
    private double precioMasa = 0;
    private double precioExtras = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Codigo del tipo de Pizza
        tipo.setItems(listaTipo);
        tipo.setValue("");
        this.tipo.setItems(listaTipo);

        //Codigo de los ingredientes extra
        ingredientes.setItems(listaIngredientes);
        ingredientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Codigo del tamaño de la pizza
        SpinnerValueFactory.ListSpinnerValueFactory<String> valores = new SpinnerValueFactory.ListSpinnerValueFactory(listaMedida);
        medida.setValueFactory(valores);

        //Parejas de valores del HashMap de precios
        precios.put("Normal", 3.00);
        precios.put("Integral", 3.50);
        precios.put("Basica", 3.00);
        precios.put("Cuatro quesos", 5.00);
        precios.put("Barbacoa", 7.00);
        precios.put("Mexicana", 8.50);
        precios.put("Jamon", 0.50);
        precios.put("Queso", 0.75);
        precios.put("Tomate", 1.50);
        precios.put("Cebolla", 0.75);
        precios.put("Olivas", 1.00);
        precios.put("Pequeña", 100.00);
        precios.put("Mediana", 15.00);
        precios.put("Familiar", 30.00);
    }

    @FXML
    private void pulsarMasa(ActionEvent event) {

        if (normal.isSelected()) {
            masaPedido.setText("Normal " + precios.get("Normal") + " €");
            precioMasa = precios.get("Normal");
        } else if (integral.isSelected()) {
            masaPedido.setText("Integral " + precios.get("Integral") + " €");
            precioMasa = precios.get("Integral");
        }
    }

    @FXML
    private void elegirTipo(ActionEvent event) {

        String tipoSeleccionado = tipo.getValue();

        switch (tipoSeleccionado) {
            case "Basica":
                tipoPedido.setText("Básica: " + precios.get("Basica") + " €");
                break;
            case "Cuatro quesos":
                tipoPedido.setText("Cuatro quesos: " + precios.get("Cuatro quesos") + " €");
                break;
            case "Barbacoa":
                tipoPedido.setText("Barbacoa: " + precios.get("Barbacoa") + " €");
                break;
            case "Mexicana":
                tipoPedido.setText("Mexicana: " + precios.get("Mexicana") + " €");
                break;
        }
    }

    @FXML
    private void medida(MouseEvent event) {

        String medidaSeleccionada = medida.getValue();

        switch (medidaSeleccionada) {
            case "Pequeña":
                medidaPedido.setText("Pequeña");
                break;
            case "Mediana":
                medidaPedido.setText("Mediana + " + precios.get("Mediana") + "% ");
                break;
            case "Familiar":
                medidaPedido.setText("Familiar + " + precios.get("Familiar") + "% ");
                break;
        }
    }

    @FXML
    private void calcularPedido(ActionEvent event) {

        Double precioPedido = 0.0;


        //devuelve precio del tipo
        Double precioTipo = precios.get(tipo.getValue());

        //devuelte precio del tamaño
        Double precioMedida = precios.get(medida.getValue());
        
        Double previaPrecio = (precioMasa + precioTipo + precioExtras);
        
        precioPedido = previaPrecio + (previaPrecio * (precioMedida/100));
        
        precioTotal.setText(precioPedido + " €");
    }

    @FXML
    private void extras(MouseEvent event) {

        ObservableList<String> select = ingredientes.getSelectionModel().getSelectedItems();
        String listaExtra = "";
        double precioSelect = 0;
        for (Object ingrediente : select) {
            listaExtra = listaExtra + ingrediente + " " + precios.get(ingrediente) + " € ";
            precioSelect = precioSelect + precios.get(ingrediente);
        }
        precioExtras = precioSelect;
        extrasPedido.setText(listaExtra);
    }

}
