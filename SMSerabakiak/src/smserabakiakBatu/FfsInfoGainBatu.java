package smserabakiakBatu;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Reorder;

public class FfsInfoGainBatu {
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
		data.setClassIndex(0);
		//System.out.println(data.numInstances());
		/*for(int i=data.numInstances()-1;i>4473;i--){
			data.remove(i);
		}*/
		Instances trainDev=new Instances(data, 0, 4474);
		Instances test=new Instances(data,4474,1100);
		AttributeSelection filter= new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search=new Ranker();
		search.setThreshold(0.001);

		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(trainDev);
		Instances newData = Filter.useFilter(trainDev, filter);
		boolean aurkituta=false;
		int[] atributuak = new int[test.numAttributes()-newData.numAttributes()];
		int indizea=0;
		for(int i=0;i<test.numAttributes();i++){
			String izena=test.attribute(i).name();
			aurkituta=false;
			for(int j=0;j<newData.numAttributes();j++){
				if(izena.equals(newData.attribute(j).name())){
					aurkituta=true;
				}
			}
			if(!aurkituta){
			
				atributuak[indizea]=i;
				indizea++;
			}
		}
		
		Remove r=new Remove();
		r.setAttributeIndicesArray(atributuak);
		System.out.println(r.getAttributeIndices());
		r.setInputFormat(test);
		Instances newTest=Filter.useFilter(test, r);
		System.out.println(newTest.numAttributes());
		System.out.println(newTest.equalHeaders(newData));
		Reorder reo=new Reorder();
		reo.setInputFormat(newData);
		Instances newTest2=Filter.useFilter(newTest, reo);
		System.out.println(newTest2.equalHeaders(newData));
		
		ArffSaver saver1= new ArffSaver();
		File f1=new File(args[1]);
		saver1.setInstances(newData);
		saver1.setFile(f1);
		saver1.writeBatch();
		ArffSaver saver= new ArffSaver();
		File f=new File(args[2]);
		saver.setInstances(newTest2);
		saver.setFile(f);
		saver.writeBatch();
	}
}
