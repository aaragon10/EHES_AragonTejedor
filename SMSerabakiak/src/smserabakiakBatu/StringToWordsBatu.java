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

		dataDev.setClassIndex(dataDev.numAttributes()-1);
		
		for(int i=0;i<dataDev.numInstances();i++){
			dataTrain.add(dataDev.get(i));
		}
		for(int i=0;i<dataTest.numInstances();i++){
			dataTrain.add(dataTest.get(i));
		}
		System.out.println(dataTrain.numAttributes());
		System.out.println(dataDev.numAttributes());
		System.out.println(dataTest.numAttributes());
		StringToWordVector sw=new StringToWordVector();
		sw.setLowerCaseTokens(true);
		sw.setInputFormat(dataTrain);
		Instances newData=Filter.useFilter(dataTrain, sw);
		if(args[3].equals("i")){
			FfsInfoGainBatu fss=new FfsInfoGainBatu();
			fss.infoGainGaratu(newData,args[4], args[5], args[6]);
		}else if(args[3].equals("s")){
			TFIDFBatu tf=new TFIDFBatu();
			tf.tfidf(newData, args[4], args[5], args[6]);
		}else{
			System.out.println("Flag-a txarto sartu duzu");
		}
	}
	

}
