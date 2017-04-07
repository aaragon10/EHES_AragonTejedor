package smserabakiakBatu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class TFIDFBatu {
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
		
		StringToWordVector sw=new StringToWordVector();
		sw.setInputFormat(data);
		sw.setLowerCaseTokens(true);
		sw.setOutputWordCounts(true);
		sw.setTFTransform(true);
		sw.setIDFTransform(true);
		
		Instances newData=Filter.useFilter(data, sw);
		/*SparseToNonSparse s=new SparseToNonSparse();
		s.setInputFormat(newData);
		Instances new2Data=Filter.useFilter(newData, s);*/
		/*ArffSaver saver= new ArffSaver();
		File f=new File(args[]);
		saver.setInstances(newData);
		saver.setFile(f);
		saver.writeBatch();*/
	}

}
