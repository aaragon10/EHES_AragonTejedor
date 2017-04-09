package smserabakiakBatu;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Reorder;

public class AtributuakEzabatu {
	public AtributuakEzabatu(){
		
	}
	public Instances ezabatu(Instances ezabatzeko,Instances newTrain) throws Exception{
		boolean aurkituta=false;
		int[] atributuak = new int[ezabatzeko.numAttributes()-newTrain.numAttributes()];
		int indizea=0;
		for(int i=0;i<ezabatzeko.numAttributes();i++){
			String izena=ezabatzeko.attribute(i).name();
			aurkituta=false;
			for(int j=0;j<newTrain.numAttributes();j++){
				if(izena.equals(newTrain.attribute(j).name())){
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
		r.setInputFormat(ezabatzeko);
		Instances newTest=Filter.useFilter(ezabatzeko, r);
		
		Reorder reo=new Reorder();
		reo.setInputFormat(newTrain);
		Instances newTest2=Filter.useFilter(newTest, reo);
		return newTest2;
		
	}

}
