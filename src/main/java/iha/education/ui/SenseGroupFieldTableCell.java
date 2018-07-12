package iha.education.ui;

import iha.education.Application;
import iha.education.entity.SenseGroup;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

@SuppressWarnings({ "restriction", "unused" })
public class SenseGroupFieldTableCell<S, T> extends TableCell<S, T> {
	
	public static <S> Callback<TableColumn<S,SenseGroup>, TableCell<S,SenseGroup>> forTableColumn() {
        return item -> new SenseGroupFieldTableCell<S,SenseGroup>();
    }
	
	public SenseGroupFieldTableCell() {
		super();
	}

	// --- converter
    private ObjectProperty<SenseGroup> converter =
            new SimpleObjectProperty<SenseGroup>(this, "converter");

    public final ObjectProperty<SenseGroup> converterProperty() {
        return converter;
    }
    
    public final SenseGroup getConverter() {
        return converterProperty().get();
    }
	
	 @Override public void startEdit() {
		 super.startEdit();
	 }
	 
	 @Override public void updateItem(T item, boolean empty) {
		 super.updateItem(item, false);
		 if (item !=null) {
			 this.setText(((SenseGroup) item).getName() + " - " + ((SenseGroup) item).getTranslate());
		 }
	 }
	 
	 @Override public void cancelEdit() {
	        super.cancelEdit();
	 }
	 

}
