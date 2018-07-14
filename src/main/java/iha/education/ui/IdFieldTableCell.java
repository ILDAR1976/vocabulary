package iha.education.ui;

import iha.education.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

@SuppressWarnings({ "restriction", "unused" })
public class IdFieldTableCell<S, T> extends TableCell<S, T> {
	
	public static <S> Callback<TableColumn<S,String>, TableCell<S,String>> forTableColumn() {
        return item -> new IdFieldTableCell<S,String>();
    }
	
	public IdFieldTableCell() {
		super();
	}

	// --- converter
    private ObjectProperty<String> converter =
            new SimpleObjectProperty<String>(this, "converter");

    public final ObjectProperty<String> converterProperty() {
        return converter;
    }
    
    public final String getConverter() {
        return converterProperty().get();
    }
	
	 @Override public void startEdit() {
		 super.startEdit();
	 }
	 
	 @Override public void updateItem(T item, boolean empty) {
		 super.updateItem(item, false);
		 if (item !=null) {
			 this.setText(item.toString());
		 }
	 }
	 
	 @Override public void cancelEdit() {
	        super.cancelEdit();
	 }
	 

}
