package iha.education.ui;

import iha.education.Application;
import iha.education.entity.PartSpeech;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

@SuppressWarnings({ "restriction", "unused" })
public class PartSpeechFieldTableCell<S, T> extends TableCell<S, T> {
	
	public static <S> Callback<TableColumn<S,PartSpeech>, TableCell<S,PartSpeech>> forTableColumn() {
        return item -> new PartSpeechFieldTableCell<S,PartSpeech>();
    }
	
	public PartSpeechFieldTableCell() {
		super();
	}

	// --- converter
    private ObjectProperty<PartSpeech> converter =
            new SimpleObjectProperty<PartSpeech>(this, "converter");

    public final ObjectProperty<PartSpeech> converterProperty() {
        return converter;
    }
    
    public final PartSpeech getConverter() {
        return converterProperty().get();
    }
	
	 @Override public void startEdit() {
		 super.startEdit();
	 }
	 
	 @Override public void updateItem(T item, boolean empty) {
		 super.updateItem(item, false);
		 if (item !=null) {
			 this.setText(((PartSpeech) item).getName() + " - " + ((PartSpeech) item).getTranslate());
		 }
	 }
	 
	 @Override public void cancelEdit() {
	        super.cancelEdit();
	 }
	 

}
