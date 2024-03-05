package src.com.pkg.fileOperations;

import java.io.*;
import java.util.ArrayList;

public class FileCreate {
    public String template = "";
    public boolean createFile(String fileName){
        try{
            String resultRootDirectory = "result"+File.separator;
            String directoryPath = resultRootDirectory + "com" + File.separator + 
            "zlabs" + File.separator + 
            "distdb" + File.separator + 
            "ddtables" + File.separator + 
            "distdbmi";
            
            File file = new File(directoryPath+File.separator+fileName);
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(getTemplate());
            } catch (Exception e) {
                System.out.println("Exception while creating file");
                return false;
            }
        }catch(Exception e){
            System.out.println("Exception while saving file");
            return false;
        }
        return true;
    }
    public void setTemplate(String tableName,ArrayList<String> columns){
        template += "package com.zlabs.distdb.ddtables.distdbmi;\n\n";                    
        template += "public final class "+tableName+"{\n";

        for(int i = 0;i < columns.size();i++){
            template += " \tpublic static final String "+columns.get(i)+" = " + "\""+columns.get(i)+"\";\n";
            template += " \tpublic static final int "+columns.get(i)+"_IDX = "+String.valueOf(i)+";\n";
        }

        template += "\n\tprivate "+tableName+"() {}\n";
        template += "}";
    }
    public String getTemplate(){
        return this.template;
    }
}
