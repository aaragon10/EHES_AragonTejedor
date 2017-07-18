package smserabakiakBatu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;

public class BaggingBatu {
	public static void main(String[] args) throws Exception{
		FileReader fi=null;
		FileReader fi1=null;
		FileWriter fw1=new FileWriter(args[3]);
		FileWriter fw2=new FileWriter(args[4]);
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
		if(args[5].equals("i")){
			dataTrain.setClassIndex(dataTrain.numAttributes()-1);
			dataDev.setClassIndex(dataDev.numAttributes()-1);
		}else if(args[5].equals("s")){
			dataTrain.setClassIndex(0);
			dataDev.setClassIndex(0);
		}else{
			System.out.println("Flag-a txarto sartu duzu");
		}
		NaiveBayes nb= new NaiveBayes();
		//Baseline
		nb.buildClassifier(dataTrain);
		Evaluation baseline=new Evaluation(dataTrain);
		baseline.evaluateModel(nb, dataDev);
		double fmeasureBaseline=baseline.weightedFMeasure()/*fMeasure(0)*/;
		System.out.println(fmeasureBaseline);
		fw1.write("Hold out: Train vs Dev");
		fw1.write(baseline.toSummaryString());
		fw1.write(baseline.toClassDetailsString());
		fw1.write(baseline.toMatrixString());
		
		//Bagging
		Classifier cf=null;
		Bagging bgg=new Bagging();
		J48 j48=new J48();
		IBk ib= new IBk(3);
		double fmOptimoa=0;
		int jOptimoa=0;
		int iOptimoa=0;
		for(int i=0;i<3;i++){
			bgg.setNumIterations(3);
			if(i==0){
				bgg.setClassifier(ib);
				System.out.println("Ibk");
				
			}else if(i==1){
				bgg.setClassifier(j48);
				System.out.println("J48");
			}else{
				bgg.setClassifier(nb);
				System.out.println("Naive Bayes");
			}
			for(int j=5;j<30;j+=5){
				bgg.setBagSizePercent(j);
				bgg.buildClassifier(dataTrain);
				Evaluation eval=new Evaluation(dataTrain);
				eval.evaluateModel(bgg, dataDev);
				//System.out.println(eval.weightedFMeasure());
				if(fmOptimoa</*eval.fMeasure(0))*/eval.weightedFMeasure()){
					fmOptimoa=eval.weightedFMeasure()/*eval.fMeasure(0)*/;
					cf=bgg.getClassifier();
					jOptimoa=j;
					iOptimoa=i;
				}
			}
		}
		bgg.setBagSizePercent(jOptimoa);
		System.out.println(jOptimoa);
		System.out.println(iOptimoa);
		System.out.println(fmOptimoa);
		bgg.setClassifier(cf);
		bgg.buildClassifier(dataTrain);
		Evaluation eval=new Evaluation(dataTrain);
		eval.evaluateModel(bgg, dataDev);
		fw2.write("Hold out: Train vs Dev");
		fw2.write(eval.toSummaryString());
		fw2.write(eval.toClassDetailsString());
		fw2.write(eval.toMatrixString());
		dataTrain.addAll(dataDev);
		nb.buildClassifier(dataTrain);
		CrossValidation10 cs1=new CrossValidation10(nb, dataTrain);
		Evaluation csEval1=cs1.ebaluatu();
		fw1.write("10-Cross Validation :Train U Dev");
		fw1.write(csEval1.toSummaryString());
		fw1.write(csEval1.toClassDetailsString());
		fw1.write(csEval1.toMatrixString());
		Evaluation eval4=new Evaluation(dataTrain);
		eval4.evaluateModel(nb, dataTrain);
		fw1.write("Ez zintzoa :Train U Dev");
		fw1.write(eval4.toSummaryString());
		fw1.write(eval4.toClassDetailsString());
		fw1.write(eval4.toMatrixString());
		//Biak konparatu
		if(fmOptimoa<fmeasureBaseline){
			NaiveBayes naive=new NaiveBayes();
			naive.buildClassifier(dataTrain);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(args[2]));
			oos.writeObject(bgg);
			oos.flush();
			oos.close();
		}else{
			bgg.setBagSizePercent(jOptimoa);
			bgg.setClassifier(cf);
			bgg.buildClassifier(dataTrain);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(args[2]));
			oos.writeObject(bgg);
			oos.flush();
			oos.close();
			CrossValidation10 cs=new CrossValidation10(bgg, dataTrain);
			Evaluation csEval=cs.ebaluatu();
			fw2.write("10-Cross Validation :Train U Dev");
			fw2.write(csEval.toSummaryString());
			fw2.write(csEval.toClassDetailsString());
			fw2.write(csEval.toMatrixString());
			Evaluation eval3=new Evaluation(dataTrain);
			eval3.evaluateModel(bgg, dataTrain);
			fw2.write("Ez zintzoa :Train U Dev");
			fw2.write(eval3.toSummaryString());
			fw2.write(eval3.toClassDetailsString());
			fw2.write(eval3.toMatrixString());
			
		}
		fw1.close();
		fw2.close();
	}
}
