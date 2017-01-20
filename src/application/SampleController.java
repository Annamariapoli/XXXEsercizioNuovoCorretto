package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bean.Station;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m){
		this.m=m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Station> comboStart;

    @FXML
    private ComboBox<Station> comboEnd;

    @FXML
    private Button btnCalcola;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcola(ActionEvent event) {
    	txtResult.clear();
    	Station start = comboStart.getValue();
    	Station end = comboEnd.getValue();
    	if(start== null && end == null){
    		txtResult.appendText("Seleziona due stazioni \n");
    		return;
    	}
    	if(start.equals(end)){
    		txtResult.appendText("Seleziona due stazioni diverse \n");
    		return;
    	}
    	
    	m.buildGraph();
    	
    	
//    	double distanza = m.getDistanzaTraStation(start, end);
//    	txtResult.appendText("La distanza  tra le due è " +distanza+  " km!   \n");
    	
    	double lunghezzaCammino = m.getDistanzaTraStation(start, end);
    	txtResult.appendText("La lunghezza del cammino è:  "+lunghezzaCammino+" km. \n");
    	
    	List<Station> cammino = m.getCammino(start, end);
    	txtResult.appendText("Il cammino è:  [\n");
    	for(Station c : cammino){
    		txtResult.appendText(c + " \n");
    	}
    	txtResult.appendText("]   \n  ");
    	
    	double tempoPercorrenzaMinimo = m.getTempoPercorrenzaMinimo(start, end);
    	txtResult.appendText("Il tempo di percorrenza minimo tra le due stazioni è " + tempoPercorrenzaMinimo+ " secondi  \n");
    	
    	
//    	int durataSecondi = m.durataMinima(start, end);
//    	if(durataSecondi == 0){
//    		txtResult.appendText("Le due stazioni non sono collegate da nessun trip! \n");
//    		return;
//    	}
//    	txtResult.appendText("La durata minima tra le due è : "+ durataSecondi+ " secondi ! \n");
    }

    @FXML
    void initialize() {
        assert comboStart != null : "fx:id=\"comboStart\" was not injected: check your FXML file 'Sample.fxml'.";
        assert comboEnd != null : "fx:id=\"comboEnd\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

        comboStart.getItems().addAll(m.getAllStation());
        comboEnd.getItems().addAll(m.getAllStation());
    }
}
