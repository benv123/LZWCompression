import java.io.IOException;
public class LZWCompressionMain{
	public static void main(String[] args) throws IOException{
		LZWCompression compressor=new LZWCompression();
		System.out.println("lzw-file1.txt: ");
		compressor.compressFile("lzw-file1.txt: ");
		System.out.println("lzw-file2.txt: ");
		compressor.compressFile("lzw-file2.txt: ");
		System.out.println("lzw-file3.txt: ");
		compressor.compressFile("lzw-file3.txt: ");
	}
}
