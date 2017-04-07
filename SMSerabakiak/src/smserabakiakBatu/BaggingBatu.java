package smserabakiakBatu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class BaggingBatu {
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
		
		NaiveBayes nb= new NaiveBayes();
		//Baseline
		nb.buildClassifier(dataTrain);
		Evaluation baseline=new Evaluation(dataTrain);
		baseline.evaluateModel(nb, dataDev);
		double fmeasureBaseline=baseline.fMeasure(dataDev.classIndex());
		
		//Bagging
		Classifier cf=null;
		Bagging bgg=new Bagging();
		J48 j48=new J48();
		IBk ib= new IBk(3);
		int bagSize=0;
		double fmOptimoa=0;
		int iOptimoa=0;
		for(int i=5;i<35;i+=5){
			bgg.setBagSizePercent(i);
			for(int j=0;j<3;j++){
				if(j==0){
					bgg.setClassifier(nb);
				}else if(j==1){
					bgg.setClassifier(j48);
				}else{
					bgg.setClassifier(ib);
				}
				bgg.buildClassifier(dataTrain);
				Evaluation eval=new Evaluation(dataTrain);
				eval.evaluateModel(bgg, dataDev);
				if(fmOptimoa<eval.fMeasure(dataDev.classIndex())){
					fmOptimoa=eval.fMeasure(dataDev.classIndex());
					cf=bgg.getClassifier();
					iOptimoa=i;
					
				}
			}
		}
		dataTrain.addAll(dataDev);
		
		//Biak konparatu
		if(fmOptimoa<fmeasureBaseline){
			NaiveBayes naive=new NaiveBayes();
			naive.buildClassifier(dataTrain);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(args[3]));
			oos.writeObject(bgg);
			oos.flush();
			oos.close();
		}else{
			BaggingParametroak bp=new BaggingParametroak(dataTrain, cf, iOptimoa, args[3]);
		}
		
	}
}
