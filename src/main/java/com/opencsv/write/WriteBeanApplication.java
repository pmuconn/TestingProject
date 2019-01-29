package com.opencsv.write;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class WriteBeanApplication {

    public static void main(String[] args) {
        writeSyncCsvFromBeanExample();
    }
    
    public static String writeSyncCsvFromBeanExample() {
        Path path = null;
        try {
            path = Helpers.fileOutBeanPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return writeCsvFromBean(path);
    }
    
    public static String writeCsvFromBean(Path path) {
        try {
            Writer writer = new FileWriter(path.toString());

            //Custom Mapping Strategy with beans that contain both @CsvBindByName and @CsvBindByPosition
            final CustomMappingStrategy<WriteExampleBean> mappingStrategy = new CustomMappingStrategy<>();
            mappingStrategy.setType(WriteExampleBean.class);
            
            
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            		.withMappingStrategy(mappingStrategy)
                .build();

            List<CsvBean> list = new ArrayList<>();
            list.add(new WriteExampleBean("Test1", "sfdsf", "fdfd"));
            list.add(new WriteExampleBean("Test2", "ipso", "facto"));

            sbc.write(list);
            writer.close();

        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return Helpers.readFile(path);
    }    
    
}
