package com.opencsv.write;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class WriteExampleBean extends CsvBean {

	@CsvBindByName
    @CsvBindByPosition(position = 0)    
    private String colA;

	@CsvBindByName(column = "middle")
    @CsvBindByPosition(position = 1)    
	private String colB;

	@CsvBindByName(column = "last")
//    @CsvBindByPosition(position = 2)     //just showing that with the customMappingStrategy, if there is no csvBindingByPosition, this column is ignored.
    private String colC;

    public WriteExampleBean(String colA, String colB, String colC) {
        this.colA = colA;
        this.colB = colB;
        this.colC = colC;
    }

    public String getColA() {
        return colA;
    }

    public void setColA(String colA) {
        this.colA = colA;
    }

    public String getColB() {
        return colB;
    }

    public void setColB(String colB) {
        this.colB = colB;
    }

    public String getColC() {
        return colC;
    }

    public void setColC(String colC) {
        this.colC = colC;
    }
}
