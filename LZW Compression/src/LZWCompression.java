import java.util.HashMap;
import java.util.Stack;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
public class LZWCompression{
    private final int MAX_SIZE=9;
    HashMap<String,Integer> dictionary=new HashMap<String,Integer>();
    public LZWCompression(){
        for(int i=0;i<255;i++) {
            dictionary.put(""+(char)(i),i);
        }
    }
    public void compressFile(String file) throws IOException{
        BufferedReader reader=new BufferedReader(new FileReader(file));
        try{
            StringBuilder compressed=new StringBuilder();
            String line=reader.readLine();
            while(line!=null){
                compressed.append(line);
                line=reader.readLine();
            }
             try{
                 BufferedWriter out = new BufferedWriter(new FileWriter(file+" compressed"));
                 out.write(compressLine(compressed.toString()));
                 out.close();
                 System.out.println("File created successfully");
              }
              catch (IOException e){
              }
        } finally{
            reader.close();
        }
    }
    public String compressLine(String str){
        String compressed="";
        String current="";
        for(int i=0;i<str.length();i++){
            char next=str.charAt(i);
            if(dictionary.containsKey(current+next)){
                current+=next;
            }
            else{
                compressed+=this.binary(dictionary.get(current))+" ";
                dictionary.put(current+next,dictionary.size()+1);
                current=""+next;
            }
        }
        compressed+=this.binary(dictionary.get(current))+" ";
        //System.out.println(compressed);
        return(compressed);
    }
    public String binary(int num){
        String str="";
        Stack<Integer> stack=new Stack<>();
        while(num>0){
            stack.push(num%2);
            num/=2;
        }
        while(!(stack.isEmpty())){
            str+=stack.pop();
        }
        String zeros=""; // add zeros at the beginning of numbers in binary that are less than 2^(MAX_SIZE-1)
        for(int i=0;i<MAX_SIZE-str.length();i++) {
            zeros+="0";
        }
        return(zeros+str);
    }
    public void printHashMap(){
        for(HashMap.Entry<String,Integer> entry:dictionary.entrySet()){
            System.out.println(entry.getKey());
        }
    }
    public String decompress(String compressed){
        if(compressed.equals("")){
            return("");
        }
        String str=compressed.substring(0,MAX_SIZE);
        int bin=Integer.parseInt(str,2);
        String value="";
        for(HashMap.Entry<String,Integer> entry:dictionary.entrySet()){
            if(entry.getValue().equals(bin)){
                value=entry.getKey();
            }
        }
        return(""+value+this.decompress(compressed.substring(MAX_SIZE+1)));
    }
}
