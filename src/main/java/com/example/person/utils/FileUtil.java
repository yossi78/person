package com.example.person.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;




public class FileUtil {


    public static <T> List<T> fileToListOfObjects(String filePath,Class T) throws Exception {
        List<T> results=null;
        if(filePath.matches(".*.csv")){
            results=readCsvToBeanList(filePath,T,results);
            return results;
        }
        String fileInStr=fileToString(filePath);
        results = parseStringToListOfObjects(fileInStr,T);
        return results;
    }


    private static <T> List<T> readCsvToBeanList(String filePath,Class clazz, List<T> list) throws Exception {
        HeaderColumnNameMappingStrategy ms = new HeaderColumnNameMappingStrategy();
        ms.setType(clazz);
        try (Reader reader = generateBufferReader(filePath)) {
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withMappingStrategy(ms)
                    .build();
            list = cb.parse();
        }
        return list;
    }

    public static String fileToString(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> lines = fileToLines(filePath);
        lines.stream().forEach(c->sb.append(c));
        return sb.toString();
    }

    public static  <T> List<T> parseStringToListOfObjects(String fileAsString , Class T) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        List<T> results = mapper.readValue(fileAsString, typeFactory.constructCollectionType(ArrayList.class,T));
        return results;
    }



    public static List<String> fileToLines(String filePath) throws IOException {
        List<String> lines = fetchLines(filePath);
        return lines;
    }


    public static List<String> fileToWords(String filePath) throws IOException {
        List<String> words = fetchWords(filePath);
        return words;
    }


    public static void writeLinesToFile(String filePath,List<String> lines) throws  IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        for(String line:lines){
            if(line!=null && !line.isEmpty()){
                fileWriter.write(line+"\n");
            }
        }
        fileWriter.close();
        System.out.println("Successfully wrote to the file.");
    }


    private static List<String> fetchLines(String filePath) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = generateBufferReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }



    private static List<String> fetchWords(String filePath) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = generateBufferReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                addWordsFromLine(line,list);
            }
        }
        return list;
    }


    private static void addWordsFromLine(String line,List<String> list){
        List<String> temp = List.of(line.split(" "));
        temp.stream()
                .filter(current->current!=null && !current.isEmpty())
                .forEach(current-> list.add(current));
    }



    public Object parseStringToObject(String str,Class T) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object result = mapper.readValue(str,T);
        return result;
    }


    private static BufferedReader  generateBufferReader(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        return br;
    }


    public static void main(String[] args) throws IOException {
        List<String> words = FileUtil.fileToLines("src\\main\\resources\\test.txt");
        System.out.println("*************   LINES ************************ ");
        words.stream().forEach(System.out::println);
        writeLinesToFile("src\\main\\resources\\XXX.txt",words);

        System.out.println("*************  END OF LINES ************************ \n\n");


    }





}

