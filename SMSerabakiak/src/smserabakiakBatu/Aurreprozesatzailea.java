package smserabakiakBatu;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Aurreprozesatzailea {
	public static void main(String[] args) throws IOException{
		FileWriter fw=new FileWriter(args[1]);
		FileReader fr= new FileReader(args[0]);
		Scanner sarrera= new Scanner(fr);
		String lerro;
		HashMap<String,LinkedList<String>> map=new HashMap<String,LinkedList<String>>();
		if(args[2].equals("-u")){
			while(sarrera.hasNext()){
				lerro=sarrera.nextLine();
				String[] s=lerro.split("\t");
				String klasea=s[0];
				String aux=s[1];
				String sms="";
				for(int j=0;j<aux.length();j++){
					char c=aux.charAt(j);
					if((c!='@')&&(c!=',')&&(c!='%')&&(c!='#')&&(c!='/')&&(c!='\'')&&(c!='"')){
						sms=sms+aux.charAt(j);
					}
				}
				if(map.containsKey(klasea)){
					map.get(klasea).add(sms);
				}else{
					LinkedList<String> lista=new LinkedList<>();
					lista.add(sms);
					map.put(klasea, lista);
				}
			}
			String[] izena=args[0].split("/");
			String izena1=izena[izena.length-1];
			fw.write("@relation "+izena1+"\n\n");
			fw.write("@attribute textua string\n");
			fw.write("@attribute klasea {");
			Set<String> key=map.keySet();
			Iterator<String> it=key.iterator();
			String klase;
			int i=0;
			while(it.hasNext()){
				klase=it.next();
				if(i==key.size()-1){
					fw.write(klase);
				}else{
					fw.write(klase+",");
					i++;
				}
			}
			fw.write("}\n\n");
			fw.write("@data\n");
			for(String k:map.keySet()){
				LinkedList<String> lista1=map.get(k);
				Iterator<String> itr=lista1.iterator();
				String unekoa;
				while(itr.hasNext()){
					unekoa=itr.next();
					fw.write("'"+unekoa+"'"+","+k+"\n");
				}
			}
		}else if(args[2].equals("u")){
			String[] izena=args[0].split("/");
			String izena1=izena[izena.length-1];
			fw.write("@relation "+izena1+"\n\n");
			fw.write("@attribute textua string\n");
			fw.write("@attribute klasea {' '}\n");
			fw.write("@data\n");
			while(sarrera.hasNext()){
				lerro=sarrera.nextLine();
				String sms="";
				for(int j=0;j<lerro.length();j++){
					char c=lerro.charAt(j);
					if((c!='@')&&(c!=',')&&(c!='%')&&(c!='#')&&(c!='/')&&(c!='\'')&&(c!='"')){
						sms=sms+lerro.charAt(j);
					}
				}
				fw.write("'"+sms+"',?\n");
			}
			
		}
		fw.close();
		fr.close();
		sarrera.close();
	}
}
