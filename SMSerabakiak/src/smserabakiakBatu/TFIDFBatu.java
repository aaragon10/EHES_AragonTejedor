package smserabakiakBatu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class TFIDFBatu {
	public TFIDFBatu(){
		
	}
	public void tfidf(Instances data, String arg4,String arg5,String arg6) throws Exception{
		data.setClassIndex(0);
		
		StringToWordVector sw=new StringToWordVector();
		sw.setInputFormat(data);
		sw.setLowerCaseTokens(true);
		sw.setOutputWordCounts(true);
		sw.setTFTransform(true);
		sw.setIDFTransform(true);
		Instances newData=Filter.useFilter(data, sw);
		ArffSortu arf=new ArffSortu();
		Instances train=new Instances(data, 0, 3374);
		Instances dev=new Instances(data, 3374, 1100);
		Instances test=new Instances(data,4474,1100);
		arf.sortu(train, arg4);
		arf.sortu(dev, arg5);
		arf.sortu(test, arg6);
	}
}
