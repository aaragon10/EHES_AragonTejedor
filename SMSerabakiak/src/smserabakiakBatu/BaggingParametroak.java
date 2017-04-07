package smserabakiakBatu;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;

public class BaggingParametroak {
	Bagging bgg=new Bagging();
	Classifier cs;
	int bagSize;
	Instances data;
	public BaggingParametroak(Instances pData,Classifier pCs,int pBagSize,String path){
		this.data=pData;
		this.cs=pCs;
		this.bagSize=pBagSize;
	}
	public void modelaEgin() throws Exception{
		this.bgg.setBagSizePercent(bagSize);
		this.bgg.setClassifier(cs);
		this.bgg.buildClassifier(data);
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("path"));
		 oos.writeObject(bgg);
		 oos.flush();
		 oos.close();
	}
	
	
}
