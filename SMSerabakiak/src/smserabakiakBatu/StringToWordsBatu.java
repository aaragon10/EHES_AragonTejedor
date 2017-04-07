package smserabakiakBatu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class StringToWordsBatu {
	public static void main(String[] args) throws Exception{
		FileReader fiTrain=null;
		FileReader fiTest=null;
		FileReader fiDev=null;
		try {
			fiTrain= new FileReader(args[0]);
			fiDev= new FileReader(args[1]); 
			fiTest= new FileReader(args[2]);
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:"+args[0]);
		}
		// 1.3. Load the instances
		Instances dataTrain=null;
		Instances dataTest=null;
		Instances dataDev=null;
		try {
			dataTrain = new Instances(fiTrain);
			dataTest = new Instances(fiTest);
			dataDev = new Instances(fiDev);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+args[0]);
		}
		// 1.4. Close the file
		try {
			fiTrain.close();
			fiTest.close();
			fiDev.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		dataTrain.setClassIndex(dataTrain.numAttributes()-1);
		System.out.println(dataTrain.numInstances());
		dataDev.setClassIndex(dataDev.numAttributes()-1);
		System.out.println(dataDev.numInstances());
		System.out.println(dataTest.numInstances());
		
		for(int i=0;i<dataDev.numInstances();i++){
			dataTrain.add(dataDev.get(i));
		}
		System.out.println(dataTrain.numInstances());
		for(int i=0;i<dataTest.numInstances();i++){
			dataTrain.add(dataTest.get(i));
		}
		System.out.println(dataTrain.numInstances());
		
		StringToWordVector sw=new StringToWordVector();
		sw.setLowerCaseTokens(true);
		sw.setInputFormat(dataTrain);
		Instances newData=Filter.useFilter(dataTrain, sw);
		System.out.println(dataTrain.numInstances());
		/*SparseToNonSparse s=new SparseToNonSparse();
		s.setInputFormat(newData);
		Instances new2Data=Filter.useFilter(newData, s);*/
		ArffSaver saver= new ArffSaver();
		File f=new File(args[3]);
		saver.setInstances(newData);
		saver.setFile(f);
		saver.writeBatch();
	}
	

}
