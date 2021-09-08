import java.io.IOException;
public class LZWCompressionMain{
	public static void main(String[] args) throws IOException{
		LZWCompression compressor1=new LZWCompression();
		LZWCompression compressor2=new LZWCompression();
		LZWCompression compressor3=new LZWCompression();
		System.out.println("lzw-file1.txt: ");
		compressor1.compressFile("lzw-file1.txt");
		compressor1.decompressFile("lzw-file1.txt");
		System.out.println("lzw-file2.txt: ");
		compressor2.compressFile("lzw-file2.txt");
		compressor2.decompressFile("lzw-file2.txt");
		System.out.println("lzw-file3.txt: ");
		compressor3.compressFile("lzw-file3.txt");
		compressor3.decompressFile("lzw-file3.txt");
	}
}
