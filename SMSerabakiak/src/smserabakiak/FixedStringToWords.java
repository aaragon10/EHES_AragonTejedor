package smserabakiak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;

public class FixedStringToWords {
	public static void main(String[] args) throws Exception{
		FileReader fi=null;
		try {
			fi= new FileReader(args[0]); 
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:"+args[0]);
		}
		// 1.3. Load the instances
		Instances data=null;
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+args[0]);
		}
		// 1.4. Close the file
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		data.setClassIndex(data.numAttributes()-1);
		FixedDictionaryStringToWordVector fx=new FixedDictionaryStringToWordVector();
		fx.setInputFormat(data);
		fx.setDictionaryFile(new File("/home/ieltxu/git/EHES_AragonTejedor/SMSerabakiak/dictionary.txt"));
		fx.setLowerCaseTokens(true);
		fx.setOutputWordCounts(true);
		fx.setTFTransform(true);
		fx.setIDFTransform(true);
		
		Instances newData=Filter.useFilter(data, fx);
		
		ArffSaver saver= new ArffSaver();
		File f=new File(args[1]);
		saver.setInstances(newData);
		saver.setFile(f);
		saver.writeBatch();
	}
}
