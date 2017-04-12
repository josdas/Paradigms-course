public class BinarySearch {
	// (l; r)
	//n = a.size
    //prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x)
    //R = result 
    //a[n] = -inf
    //a[-1] = inf
    //post:	l <= R <= r && ((R < n && x >= a[R]) || (R == n && a[n - 1] > x)) && ((R == 0 && x >= a[0]) || (R > 0 && a[R - 1] > x))
    static int binarySearchLeft (int l, int r, int x, int a[]) {                                                                                 
    	//prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x)
   		if (l == r - 1) {                                                                                                    
   			//prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && l == r - 1
   			//l == r - 1 && (r == n || x >= a[r]) && (l == -1 || a[l] > x) -> ((R < n && x >= a[R]) || (R == n && a[n - 1] > x)) && ((R == 0 && x >= a[0]) || (R > 0 && a[R - 1] > x))  
    		return r;
       	} else {
       		//prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && l != r - 1
   			int s = (l + r) / 2;                                                                                                                          
      		//prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && l != r - 1 && s == (l + r) / 2
       		if (a[s] <= x) {                                                                                                                                                 
	      		//prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && l != r - 1 && s == (l + r) / 2 && a[s] <= x
       			//a[s] <= x -> (r == n || x >= a[r])
       			//s == (l + r) / 2  -> -1 <= l < r <= n 
       			//(r - l + 1) >= 2 * (r` - l`) 
       			return binarySearchLeft(l, s, x, a);
       		} else {
       			//prec: -1 <= l < r <= n && (0 < i < n : a[i - 1] >= a[i]) && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && l != r - 1 && s == (l + r) / 2 && a[s] > x
       			//a[s] > x -> (l == -1 || a[l] > x)
       			//s == (l + r) / 2  -> -1 <= l < r <= n
       			//(r - l + 1) >= 2 * (r` - l`) 
       			return binarySearchLeft(s, r, x, a);
       		}
       	}
	}

	// (l; r)
	//n = a.size
    //prec: (0 < i < n : a[i - 1] >= a[i])
    //R = result
    //a[n] = -inf
    //a[-1] = inf
    //post: ((R < n && x >= a[R]) || (R == n && a[n - 1] > x)) && ((R == 0 && x >= a[0]) || (R > 0 && a[R - 1] > x))
    static int binarySearchWithoutRecursion (int x, int a[]) {
    	//prec: (0 < i < n : a[i - 1] >= a[i])
    	int l = -1;
    	//prec: (0 < i < n : a[i - 1] >= a[i]) && l == -1
    	int r = a.length;
    	//prec: (0 < i < n : a[i - 1] >= a[i]) && l == -1 && r == n
    	//post: r - l <= 1 && r == R && ((R < n && x >= a[R]) || (R == n && a[n - 1] > x)) && ((R == 0 && x >= a[0]) || (R > 0 && a[R - 1] > x))                                                                                                                                                                                        
    	//inv:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && (r - l + 1) >= 2 * (r` - l`)
    	while (r - l > 1) {
    	    //prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1
    		int s = (l + r) / 2;                                                                                                                         
    		//prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s = (l + r) / 2
    		if (a[s] <= x) {    
    			//prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s = (l + r) / 2 && a[s] <= x
    			//a[s] <= x -> (r == n || x >= a[r])
       			//s == (l + r) / 2  -> -1 <= l < r <= n
    			r = s;
    		} else {
    			//prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s = (l + r) / 2 && a[s] > x;
    			//a[s] > x -> (l == -1 || a[l] > x)
       			//s == (l + r) / 2  -> -1 <= l < r <= n
    			l = s;
    		}                       
    		//post: (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s == (l + r) / 2 && a[s] <= x && (r - l + 1) >= 2 * (r` - l`)
    	}
    	return r;   
    }

    // (l; r)
	//n = a.size
    //prec: (0 < i < n : a[i - 1] >= a[i])
    //R = result 
    //a[n] = -inf
    //a[-1] = inf                                                                                                     
    //post: ((R < n - 1 && x < a[R + 1]) || (R == n - 1 && a[n - 1] >= x)) && a[R] >= x
    static int binarySearchRight (int x, int a[]) {
    	//prec: (0 < i < n : a[i - 1] >= a[i])
    	int l = -1;
    	//prec: (0 < i < n : a[i - 1] >= a[i]) && l == -1
    	int r = a.length;
    	//prec: (0 < i < n : a[i - 1] >= a[i]) && l == -1 && r == n
    	//post: r - l <= 1 && l == R && ((R < n - 1 && x < a[R + 1]) || (R == n - 1 && a[n - 1] >= x)) && a[R] >= x                                                                                                                                                                                        
    	//inv:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && (r - l + 1) >= 2 * (r` - l`)
    	while (r - l > 1) {
    	    //prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1
    		int s = (l + r) / 2;                                                                                                                         
    		//prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s = (l + r) / 2
    		if (a[s] < x) {    
    			//prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s = (l + r) / 2 && a[s] <= x
    			//a[s] <= x -> (r == n || x >= a[r])
       			//s == (l + r) / 2  -> -1 <= l < r <= n
    			r = s;
    		} else {
    			//prec:  (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s = (l + r) / 2 && a[s] > x;
    			//a[s] > x -> (l == -1 || a[l] > x)
       			//s == (l + r) / 2  -> -1 <= l < r <= n
    			l = s;
    		}                       
    		//post: (0 < i < n : a[i - 1] >= a[i]) && -1 <= l < r <= n && (r == n || x >= a[r]) && (l == -1 || a[l] > x) && r - l > 1 && s == (l + r) / 2 && a[s] <= x && (r - l + 1) >= 2 * (r` - l`)
    	}
    	return l;   
    }

    static int getPosition(int a[], int x, int index) {
    	    if(index < a.length && a[index] == x) {
    		    return index;
    	    }
    	    return -index - 1;	
    }
          

    public static void main(final String[] args) {
    	int x = Integer.parseInt(args[0]);
    	int[] array = new int[args.length - 1]; 
    	for (int i = 1; i < args.length; i++) {
    		array[i - 1] = Integer.parseInt(args[i]);
      	}                    

      	int l =	binarySearchLeft(-1, array.length, x, array);
      	int r = binarySearchRight(x, array);
      	
      	System.out.println(l + " " + (r - l + 1));
    }
}