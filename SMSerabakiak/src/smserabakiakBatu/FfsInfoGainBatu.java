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
	public FfsInfoGainBatu(){
		
	}
	public void infoGainGaratu(Instances data,String arg4,String arg5,String arg6) throws Exception{
		data.setClassIndex(0);
		Instances train=new Instances(data, 0, 3374);
		Instances dev=new Instances(data, 3374, 1100);
		Instances test=new Instances(data,4474,1100);
		AttributeSelection filter= new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search=new Ranker();
		search.setThreshold(0.001);
		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(train);
		Instances newTrain = Filter.useFilter(train, filter);
		AtributuakEzabatu az=new AtributuakEzabatu();
		Instances newDev=az.ezabatu(dev, newTrain);
		Instances newTest=az.ezabatu(test, newTrain);
		ArffSortu arf=new ArffSortu();
		arf.sortu(newTrain, arg4);
		arf.sortu(newDev, arg5);
		arf.sortu(newTest, arg6);
		
	}
}
