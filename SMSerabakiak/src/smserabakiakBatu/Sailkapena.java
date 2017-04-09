package smserabakiakBatu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class Sailkapena {
	
	private static Instances test;
	private static Classifier model;
	
	public static void main(String[] args) throws Exception {
		arfformodel(args);
		test.setClassIndex(0);
		Instances sailkatuta = classify(model, test);
		SparseToNonSparse s=new SparseToNonSparse();
		s.setInputFormat(sailkatuta);
		Instances new2Data=Filter.useFilter(sailkatuta, s);
		ArffSaver save = new ArffSaver();
		File f = new File(args[2]);
		save.setInstances(new2Data);
		save.setFile(f);
		save.writeBatch();
		
	}

	private static void arfformodel(String[] args) throws Exception {
			model = (Classifier) SerializationHelper.read(args[1]);
			System.out.println("Modeloa irakurri da");
			BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));
			test = new Instances(br);
			System.out.println("test fitxategia kargatu da");
			
	}
	
	public static Instances classify(Classifier sailkatzailea, Instances data) throws Exception {
		Instances sailkatuta = new Instances(data);
		for (int i =0; i < data.numInstances(); i++) {
			double clsLabel = sailkatzailea.classifyInstance(data.instance(i));
			sailkatuta.instance(i).setClassValue(clsLabel);
		}
		return sailkatuta;
	}
}
