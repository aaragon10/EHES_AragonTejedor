package smserabakiak;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class aurreprozesatzailea {
	public static void main(String[] args) throws IOException{
		FileWriter fw=new FileWriter(args[1]);
		FileReader fr= new FileReader(args[0]);
		Scanner sarrera= new Scanner(fr);
		String lerro;
		String[] izena=args[0].split("/");
		//FileSeparator erabili ubuntun / jartzeko eta windowsen \
		String izena1=izena[izena.length-1];
		fw.write("@relation "+izena1+"\n");
		fw.write("@attribute contents string\n");
		
		fw.write("@attribute class \n");
		//klaseen balioak textutik lortu
		while(sarrera.hasNext()){
			lerro=sarrera.nextLine();
			String[] s=lerro.split("\t");
			
		}
		
		
	}

}
