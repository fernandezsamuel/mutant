package ar.com.mercadolibre.services;

import org.springframework.stereotype.Service;

@Service
public class MutantService {
	
	// For searching in all 4 direction
	private static final int x[] = { -1,  1, 0, 1};
	private static final int y[] = {  1,  1, 1, 0};
	
	private static final int PATTERN_LENGHT = 4; 
	
	// Masks
	//private static final int LEFT_DIAG_MASK = 1;  // 0000 0001
	//private static final int RIGHT_DIAG_MASK = 2; // 0000 0010
	//private static final int DOWN_MASK = 4;       // 0000 0100
	//private static final int RIGHT_MASK = 8;      // 0000 1000
	
	//private static final int[] dirMasks = { LEFT_DIAG_MASK,  RIGHT_DIAG_MASK, DOWN_MASK, RIGHT_MASK};
	
	//private int[][] searchedDir;
	
	public MutantService() {
		super();
	}

	/**
	 * Searchs for mutant dna in given string array
	 * @param dna complete dna
	 * @return True if exists 2 or more mutants dnas
	 */
	public Boolean isMutant(String[] dna) {
//		searchedDir = new int[dna.length][dna.length];
		return mutantSearch(transformToCharGrid(dna), dna.length);
	}
	
	/**
	 * Transforms given string array to char matrix
	 * @param stringArray to transform to matrix
	 * @return char matrix
	 */
	private char[][] transformToCharGrid(String[] stringArray) {
		 final int size = stringArray.length;
		 char[][] grid = new char[size][size];
		 int row = 0;
		 for(String s: stringArray) {
			 for(int col = 0; col < size; col++) {
				 grid[row][col] = s.charAt(col);
			 }
			 row++;
		 }
		 
		 return grid;
	 }
	 
	/**
	 * Searches a given matrix in all 4 directions
	 * @param grid char grid
	 * @param size grid size
	 * @return True if exists 2 or more mutants dnas
	 */
	private boolean mutantSearch(char grid[][], int size)
	{
		int found = 0;
		 // Consider every point as starting point and search patterns
		 for (int row = 0; row < size; row++) {
		    for (int col = 0; col < size; col++) {
		    	found = search2D(grid, row, col, size, grid[row][col], found); 
	    		if (found > 1) {return true;}
		 	}
		 }
		 
		 return false;
	}
	 
	/** 
	 * This function searches in all 4-direction from point (row, col) in grid[][]
	 * @param grid char grid
	 * @param row current row number
	 * @param col current col number
	 * @param size grid size
	 * @param searchChar char to search
	 * @param found number of ocurrences found
	 * @return number of ocurrences found updated
	 */
	private int search2D(char grid[][], int row, int col, int size, char searchChar, int found)
	{
	
	 // Search word in all 4 directions starting from (row,col)
	 for (int dir = 0; dir < 4; dir++)
	 {
		 // if already search in direction, ignore it
//		 if ((searchedDir[row][col] & dirMasks[dir]) != 0) {continue;}
		 
	     // Initialize starting point for current direction
	     int k;
	     int rowDir = row + x[dir];
	     int colDir = col + y[dir];
	
	     // First character is already checked, match remaining
	     // characters
	     for (k = 1; k < PATTERN_LENGHT; k++)
	     {
	         // If out of bound break
	         if (rowDir >= size || rowDir < 0 || colDir >= size || colDir < 0)
	             break;
	
	         // If not matched,  break
	         if (grid[rowDir][colDir] != searchChar)
	             break;
	
	         // found match, ignore direction search in followings searchs
//	         searchedDir[row][col] |= dirMasks[dir];
	         
	         //  Moving in particular direction
	         rowDir += x[dir];
	         colDir += y[dir];
	     }
	
	     // If all character matched, then value of must
	     // be equal to length of the pattern
	     if (k == PATTERN_LENGHT) {
	    	 found++;
	    	 if (found > 1) {return found;}
	     }
	     
	 }
	 return found;
	}
}
