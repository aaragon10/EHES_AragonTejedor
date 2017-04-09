package smserabakiakBatu;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class CrossValidation10 {
	private Instances data;
	private Classifier estimatzaile;
	private Evaluation evaluator;
	public CrossValidation10(Classifier pEstimatzaile,Instances pData){
		this.data=pData;
		this.estimatzaile=pEstimatzaile;
		
	}
	public Evaluation ebaluatu() throws Exception{
		this.evaluator = new Evaluation(this.data);
		this.evaluator.crossValidateModel(this.estimatzaile, this.data, 10, new Random(1));
		return this.evaluator;
	}

}
