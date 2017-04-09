package smserabakiakBatu;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class ArffSortu {
	public ArffSortu(){}
	public void sortu(Instances data,String arg) throws IOException{
		ArffSaver saver= new ArffSaver();
		File f=new File(arg);
		saver.setInstances(data);
		saver.setFile(f);
		saver.writeBatch();
	}
}
