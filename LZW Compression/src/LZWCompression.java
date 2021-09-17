import java.util.*;
import java.io.*;
import java.nio.file.Files;
public class LZWCompression{
    private final int MAX_SIZE=9;
    public LZWCompression(){}
    public void compressFile(String file) throws IOException{
        BufferedReader reader=new BufferedReader(new FileReader(file));
        File compressedFile=new File(file+" compressed");
        String str="";
        try{
            String line=reader.readLine();
            while(line!=null){
                str+=line;
                line=reader.readLine();
            }
            try(FileOutputStream os=new FileOutputStream(compressedFile)){
                os.write(this.compress(str));
            }
            reader.close();
            System.out.println("File compressed succesfully");
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @SuppressWarnings("deprecation")
	public byte[] compress(String str){
    	HashMap<String,Integer> dictionary=new HashMap<String,Integer>();
    	for(int i=0;i<255;i++) dictionary.put(""+(char)(i),i);
        ArrayList<Byte> arr=new ArrayList<Byte>();
        String current="";
        for(int i=0;str!=null&&i<str.length();i++){
            char next=str.charAt(i);
            if(dictionary.containsKey(current+next)){
                current+=next;
            }
            else{
            	if(dictionary.get(current)!=null){
            		arr.add((new Integer(dictionary.get(current)/256)).byteValue());
            		arr.add((new Integer(dictionary.get(current)).byteValue()));
            	}
                dictionary.put(current+next,dictionary.size()+1);
                current=""+next;
            }
        }
        if(current!=""){
            arr.add((new Integer(dictionary.get(current)/256)).byteValue());
            arr.add((new Integer(dictionary.get(current)).byteValue()));
        }
        byte[] list=new byte[arr.size()];
        for(int i=0;i<arr.size();i++){
            list[i]=arr.get(i);
        }
        return(list);
    }
    public String binary(int num){
    	String binary=Integer.toBinaryString(num);
    	while(binary.length()<MAX_SIZE) binary="0"+binary;
    	return binary;
    }
    /**public String decompress(String compressed){
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
        return ""+value+this.decompress(compressed.substring(MAX_SIZE+1));
    }**/
    /**public void decompressFile(String fileName){
        String str="";
        try{
            byte[] fileContent=Files.readAllBytes((new File(fileName)).toPath());
            for(int i=0;i<fileContent.length;i+=2){
                int num=fileContent[i];
                int num2=fileContent[i+1];
                for(HashMap.Entry<String,Integer> entry:dictionary.entrySet()){
                    if(entry.getValue().equals(num*256+num2)){
                        str+=entry.getKey();
                    }
                }
            }
            System.out.println(str);
        } catch(IOException e){
            e.printStackTrace();
        }
    }**/
    public String decompress(String file) throws FileNotFoundException{
		Scanner s = new Scanner(new File(file));
		ArrayList<String> list=new ArrayList<String>();
		while(s.hasNext()){
			list.add(s.next());
		}
		s.close();
		HashMap<Integer,String> dictionary=new HashMap<Integer,String>();
		int size=256;
		for(int i=0;i<size;i++) dictionary.put(i,""+(char)i);
		String place=""+list.remove(0);
		String last=place;
		for (String i:list){
			String input;
			if(dictionary.containsKey(i)){
				input=dictionary.get(i);
			} else{
				input=last+last.charAt(0);
			}
			last=last+input;
			dictionary.put(size++,place+input.charAt(0));
			place=input;
		}
		return(last);
	}
}

