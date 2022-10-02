package dad;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IMC extends Application {

	private VBox vbox;
	private HBox hbPeso, hbAltura, hbIMC, hbFooter;
	private Label pesoLabel, kgLabel, alturaLabel, cmLabel, imc, tipos;
	private Scene escena;
	private TextField peso;
	private TextField altura;
	
	private DoubleProperty pesoDP = new SimpleDoubleProperty(), alturaDP = new SimpleDoubleProperty(), resDP = new SimpleDoubleProperty();
	private StringProperty s = new SimpleStringProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Creando los distintos HBox
		hbPeso = new HBox();
		peso = new TextField();
		hbPeso.setSpacing(5);
		pesoLabel = new Label("Peso: ");
		kgLabel = new Label("kg");
		hbPeso.getChildren().addAll(pesoLabel, peso, kgLabel);
		
		hbAltura = new HBox();
		altura = new TextField();
		hbAltura.setSpacing(5);
		alturaLabel = new Label("Altura: ");
		cmLabel = new Label("cm");
		hbAltura.getChildren().addAll(alturaLabel, altura, cmLabel);
		
		s.bind(altura.textProperty());
		s.addListener((o, ov, nv) -> {
			calcularIMC();
		});
		
		hbIMC = new HBox();
		hbIMC.setSpacing(5);
		imc = new Label("IMC: (peso * altura ^ 2)");
		hbIMC.getChildren().add(imc);
		
		hbFooter = new HBox();
		hbFooter.setSpacing(5);
		tipos = new Label("Bajo Peso | Normal | Sobrepeso | Obeso");
		hbFooter.getChildren().addAll(tipos);
		
		
		vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setAlignment(Pos.CENTER);
		vbox.setFillWidth(false);
		vbox.getChildren().addAll(hbPeso, hbAltura, hbIMC, hbFooter);
		
		escena = new Scene(vbox, 320, 200);
		
		primaryStage.setTitle("IMC");
		primaryStage.setScene(escena);
		primaryStage.show();
		
	}
	
	
	private void calcularIMC() {
		if(peso.getLength() > 0 && altura.getLength() > 0) {
			
			pesoDP.set(Double.parseDouble(peso.getText()));

			//Recordamos que altura está en CMs por ende, tendremos que pasarlo a metros dividiendo entre 100.			
			alturaDP.set(Double.parseDouble(altura.getText()) / 100);
			
			resDP.bind(pesoDP.divide((alturaDP.multiply(alturaDP))));
			
			imc.setText("IMC: " + String.format("%.2f", resDP.get()));
			if(resDP.get() < 18.5) 
				tipos.setText("Bajo peso");
			else if(resDP.get() > 18.5 && resDP.get() < 25)
				tipos.setText("Normal");
			else if(resDP.get() >= 25 && resDP.get() < 30)
				tipos.setText("Sobrepeso");
			else if(resDP.get() >= 30)
				tipos.setText("Obeso");
		} else {
			imc.setText("IMC: (peso * altura ^ 2)");
			tipos.setText("Bajo Peso | Normal | Sobrepeso | Obeso");
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
	
	
}
