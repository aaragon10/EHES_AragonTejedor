package smserabakiakBatu;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class BaggingParametroak {
	Classifier cs;
	int bagSize;
	Instances data;
	public BaggingParametroak(Instances pData,Classifier pCs,int pBagSize,String path){
		this.data=pData;
		this.cs=pCs;
		this.bagSize=pBagSize;
	}
	public void modelaEgin() throws FileNotFoundException, IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("path"));
		 oos.writeObject(cs);
		 oos.flush();
		 oos.close();
	}
	
	
}
