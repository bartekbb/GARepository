package com.softtechdesign.ga;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.softtechdesign.ga.*;

/*
*/

public class GAMaze extends GAStringsSeq
{
	static int[] weight = {6,4,7,2,1,8,6,5,3,4};
    static String[] possibleGenes = { "1-2", "2-1", "1-3", "3-1","2-3", "3-2", "1-4", "4-1", "3-4", "4-3" };
    static int first_run;
    

    public GAMaze(int ch_size) throws GAException
    {
        super(  ch_size, //size of chromosome
                300, //population has N chromosomes
                0.7, //crossover probability
                10, //random selection chance % (regardless of fitness)
                500, //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                20, //max generations per prelim run
                0.06, //chromosome mutation prob.
                0, //number of decimal places in chrom
                possibleGenes, //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                true); //compute statisitics?
    }
    


    protected double getFitness(int iChromIndex)
    {
    	int possible_size= possibleGenes.length;
        ChromStrings chromosome = (ChromStrings)this.getChromosome(iChromIndex);
        double fitness = 0;
        String[] genes= chromosome.getGenes();
        double sum=0;
        double old_weight=100;
        // warunek startu od 1
        if (genes[0].charAt(0) == '1') fitness += 1;
        for (int i = 0; i < chromosomeDim; i++)
        {
        	// warunek ci¹g³oœci
           if(i > 0 ) {
        	   if (genes[i-1].charAt(2) == genes[i].charAt(0)) fitness +=1;
           }           
	       // warunek minimum wagi cz.1
           int index=0;;
	       for (int j=0; j< possible_size; j++) { // 
	    	   if(genes[i].equals(possibleGenes[j])) {
	    		   index = j;
	    		   break;
	    	   }
	       }
            sum += weight[index];
            
            // warunek to do
        }
        // warunek ostatni wraca do 1
        if(genes[chromosomeDim-1].charAt(2) == "1".charAt(0)) fitness +=1;
        
        // waga c.d
        if (sum < old_weight) { fitness +=10; old_weight=sum; }
        // warunek wszystkie krawedzie
        int[] used_edge = new int[chromosomeDim];
       
        
        
        // znalezienie wszystkich parzystych indeksów u¿ytych krawêdzi w jednym chromosomie
        for(int i = 0; i < chromosomeDim; i++) {
        	 for(int j = 0 ; j< possible_size; j++) {
        		 if( genes[i] == possibleGenes[j]) {
        			 if(j%2 == 0) {
        				 used_edge[i] = j;
        			 }else used_edge[i] = j-1;
        			 
        		 }
        	 }
        }
        int found =0;
        // sprawdzenie czy s¹ wszystkie indexy 0..2..4.. liczb.kraw*2-2
        for (int i =0 ; i< (possibleGenes.length-2); i+=2) {
        	if (contains(used_edge, i)){
        		found = 1;
        	} else {found =0; break;}
        }
        if (found == 1) fitness +=50;
        
        return (fitness);
    }
    
    public static boolean contains(int[] arr, int targetValue) {
        for (int s: arr) {
            if (s == targetValue)
                return true;
        }
        return false;
    }


    public static void main(String[] args) throws GAException
    {
        System.out.println("Maze GA...");
        int solution_found =0;
        int start_edges = 5;
        int no_of_repeats = 10, current_cycle =0;
        double best_res =0;
        
        while(solution_found == 0) {
        	GAMaze gaMaze = new GAMaze(start_edges);
            Thread threadMaze = new Thread(gaMaze);
            threadMaze.start();
	        try
	        {
	            threadMaze.join();
	        }
	        catch (InterruptedException e)
	        {
	            System.out.println(e.getMessage());
	        }
	        
	        try(BufferedReader br = new BufferedReader(new FileReader("C:/file/best.txt"))) {
	            StringBuilder sb = new StringBuilder();
	            String line = br.readLine();

	            while (line != null) {
	                sb.append(line);
	                sb.append(System.lineSeparator());
	                line = br.readLine();
	            }
	            String everything = sb.toString();
	            best_res = Double.parseDouble(everything);
	            System.out.println("najlepszy to: " + best_res);
	        } catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        if (best_res == 67.0) solution_found=1;
	        if (current_cycle == no_of_repeats) {
	        	start_edges +=1;
	        	current_cycle = 0;
	        }
	        current_cycle += 1;
	        
        }
    }

}