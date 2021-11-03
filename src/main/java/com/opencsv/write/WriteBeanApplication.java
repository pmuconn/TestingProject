package com.opencsv.write;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class WriteBeanApplication {

    public static void main(String[] args) {
        writeSyncCsvFromBeanExample();
        basicCsvWriter();
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

    public static String basicCsvWriter() {
    	final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";
    	Path path = Paths.get(STRING_ARRAY_SAMPLE);
    	
    	System.out.println("Planning to write to : " + path);
    	try {
            Writer writer = Files.newBufferedWriter(path);

//            CSVWriter csvWriter = new CSVWriter(writer,
//                    CSVWriter.DEFAULT_SEPARATOR,
//                    CSVWriter.NO_QUOTE_CHARACTER,
//                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
//                    CSVWriter.DEFAULT_LINE_END);

            CSVWriter csvWriter = new CSVWriter(writer,
                  CSVWriter.DEFAULT_SEPARATOR,
                  CSVWriter.NO_QUOTE_CHARACTER ,
                  CSVWriter.NO_ESCAPE_CHARACTER, //NO_ESCAPE_CHARACTER
                  CSVWriter.DEFAULT_LINE_END
                  );
            
            
            String[] headerRecord = {"Name", "Email", "Phone", "Country"};
            csvWriter.writeNext(headerRecord);
            

            csvWriter.writeNext(new String[]{"Sundar Pichai", "sundar.pichai@gmail.com", "\"my,string,value\"", "India"});
            csvWriter.writeNext(new String[]{"Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"});
            
          //close the writer
            writer.close();
            
    	} catch (Exception ex) {
            Helpers.err(ex);
    	}
    	
    	return Helpers.readFile(path);    	
    }

    
}
