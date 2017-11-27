package com.softtechdesign.ga;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GAReadFromFile {
    ArrayList<Integer> wlist = new ArrayList<Integer>();
    ArrayList<String> genlist = new ArrayList<String>();
    
    public GAReadFromFile() {
    }
    
    /**
     * Method is reading file from the given directory and stores the data in two arrays 'possibleGenes' and 'weight'
     * file format:
     * | no of edges
     * | no of node A |space|
     * @return
     * @throws FileNotFoundException 
     */
    public int openAndRead(String location) throws FileNotFoundException {
    	int size = -1;
    	Scanner s = new Scanner(new File(location));
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	while (s.hasNext()){
    	    list.add(s.nextInt());
    	}
    	s.close();
    	System.out.println("Iloœæ krawêdzi: " + list.get(0).toString());
    	
    	setWeight(list);
    	
    	setGenes(list);
    	
    	
    	return size;
    }
    
    public void setWeight (ArrayList<Integer> list) {  	
    	for(int i=0; i < list.size(); i++) {
    		if((i>0) && ((i%4 == 0) || (i%4 == 3)) ) {
    			wlist.add(list.get(i));
    		}
    	}
    	
    }
    
    public void setGenes (ArrayList<Integer> list) {
    	for(int i=0; i< list.size(); i++) {
    		if((i>0) && (i%4 == 1)) {
    			genlist.add(String.valueOf(list.get(i) +"-" + String.valueOf(list.get(i+1) )));
    			genlist.add(String.valueOf(list.get(i+1) +"-" + String.valueOf(list.get(i) )));
    			
    		}
    	}
    }
    
   public ArrayList<Integer> getWeightList() {
	   return wlist;
   }
   public ArrayList<String> getGeneList() {
	   return genlist;
   }
     


}
