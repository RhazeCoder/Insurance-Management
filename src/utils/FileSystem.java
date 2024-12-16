package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private static final String FILE_NAME = System.getProperty("user.dir") + "\\InsuranceRecords.txt";
    private static final String TEMP_FILE_NAME = System.getProperty("user.dir") + "\\temp.txt";
    
    private static File file = new File(FILE_NAME);
 
    private static void create() throws IOException {
    	if(!file.exists())
    		file.createNewFile();
    }
    
    public String[] read() throws IOException {
        create();
        List<String> lines = new ArrayList<>();

        try (FileReader fileReader = new FileReader(FILE_NAME);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line); 
            }
            return lines.toArray(new String[0]);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new String[] { e.getMessage() };
        }
    }
    
    public void write(String content) throws IOException {
    	create();
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter writer = new PrintWriter(bufferedWriter)) {
            
            writer.println(content);
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void writeEdit(List<String> updatedContent) throws IOException {
        create();
        
        File tempFile = new File(TEMP_FILE_NAME);

        try (FileWriter fileWriter = new FileWriter(tempFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter writer = new PrintWriter(bufferedWriter)) {

            for (String line : updatedContent) {
                writer.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (file.exists()) {
            file.delete(); 	
        }
        
        tempFile.renameTo(file);
    }

}