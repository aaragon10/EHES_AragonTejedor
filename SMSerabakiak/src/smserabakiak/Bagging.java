package smserabakiak;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Add;
import weka.gui.beans.Filter;

public class Bagging {
	public static void main(String[] args) throws Exception{
		FileReader fi=null;
		FileReader fi1=null;
		try {
			fi= new FileReader(args[0]);
			fi1= new FileReader(args[1]); 
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:"+args[0]);
		}
		// 1.3. Load the instances
		Instances dataTrain=null;
		Instances dataDev=null;
		try {
			dataTrain = new Instances(fi);
			dataDev = new Instances(fi1);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+args[0]);
		}
		// 1.4. Close the file
		try {
			fi.close();
			fi1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		dataTrain.setClassIndex(dataTrain.numAttributes()-1);
		dataDev.setClassIndex(dataDev.numAttributes()-1);
		//Konpatibilizatu biak aurrera jarraitu baino lehen
		
	}
}
