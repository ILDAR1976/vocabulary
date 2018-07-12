package iha.education.ui;

import iha.education.Application;
import iha.education.entity.SubGroup;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

@SuppressWarnings({ "restriction", "unused" })
public class SubGroupFieldTableCell<S, T> extends TableCell<S, T> {
	
	public static <S> Callback<TableColumn<S,SubGroup>, TableCell<S,SubGroup>> forTableColumn() {
        return item -> new SubGroupFieldTableCell<S,SubGroup>();
    }
	
	public SubGroupFieldTableCell() {
		super();
	}

	// --- converter
    private ObjectProperty<SubGroup> converter =
            new SimpleObjectProperty<SubGroup>(this, "converter");

    public final ObjectProperty<SubGroup> converterProperty() {
        return converter;
    }
    
    public final SubGroup getConverter() {
        return converterProperty().get();
    }
	
	 @Override public void startEdit() {
		 super.startEdit();
	 }
	 
	 @Override public void updateItem(T item, boolean empty) {
		 super.updateItem(item, false);
		 if (item !=null) {
			 this.setText(((SubGroup) item).getName() + " - " + ((SubGroup) item).getTranslate());
		 }
	 }
	 
	 @Override public void cancelEdit() {
	        super.cancelEdit();
	 }
	 

}
