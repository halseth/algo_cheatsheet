import java.util.ArrayList;


public class CountingTree_AddToInterval_GetSumFromInterval_Michal {

	private int OFFSET = 135000; // This may have to be increased for big input
	private int bonus[];
	private int tree[];
	
	//For setting max value in an interval and retrieving max in an interval, see comments
	// NB: += becomes =
	
	//Returns: what is in interval l to r (inclusive)
	public int query(int l, int r){
	    l+=OFFSET-1; r+=OFFSET+1; 
	    ArrayList<Integer> left = new ArrayList<Integer>(); 
	    ArrayList<Integer> right = new ArrayList<Integer>();
	    while ((l>>1)!=(r>>1)) {
			left.add(l); right.add(r);
			l>>=1; r>>=1;
	    }
	    int root=(l>>1), bs=0;
	    while (l>>1 != 0) { // while (l>0)
	    	bs+=bonus[l>>1]; // bs= Math.max(bs,bonus[l])
	    	l>>=1;
	    }
	    int lbonus=bs+bonus[root<<1]; // = Math.max(bs,bonus[root<<1]);		
	    int rbonus=bs+bonus[(root<<1)+1]; // = Math.max(bs,bonus[(root<<1)+1]);
	    int result=0;
	    for(int i = left.size()-1; i >= 0; i--){	    							
			if (!(left.get(i)%2 == 1)){
				result+=((lbonus+bonus[left.get(i)^1])<<i)+tree[left.get(i)^1];
				// = Math.max(result,Math.max(tree[left.get(i)^1],lbonus));
			}									
			if ( (right.get(i)%2 ==1)){
				result+=((rbonus+bonus[right.get(i)^1])<<i)+tree[right.get(i)^1];
				// = Math.max(result,Math.max(tree[right.get(i)^1],rbonus));
			}
			lbonus+=bonus[left.get(i)]; // = Math.max(lbonus,bonus[left.get(i)]); 
			rbonus+=bonus[right.get(i)]; // = Math.max(rbonus,bonus[right.get(i)]);
	    }
	    return result;
	}
	
	//Adds v to all cells in interval [l,r]
	public void add(int l, int r, int v){
	    l+=OFFSET-1; r+=OFFSET+1;
	    int chunk=0,lcount=0,rcount=0;
	    while ((l>>1)!=(r>>1)){
			if (!(l%2 == 1)) {  // Substitute whole block with: {tree[l^1]=v; bonus[l^1]=v;}
			    bonus[l^1]+=v;
			    lcount+=v<<chunk;
			}
			if ( (r%2 == 1)) { // {tree[r^1]=v; bonus[r^1]=v;}
			    bonus[r^1]+=v;
			    rcount+=v<<chunk;
			}
			tree[l>>1]+=lcount; // = Math.max(bonus[l>>1], Math.max(tree[l],tree[l^1]));
			tree[r>>1]+=rcount; // = Math.max(bonus[r>>1], Math.max(tree[r],tree[r^1]));
			l>>=1; r>>=1; chunk++;
	    }
	    lcount+=rcount;
	    while ((l >> 1 != 0)) {
	    	tree[l>>1]+=lcount; // = Math.max(bonus[l>>1], Math.max(tree[l],tree[l^1]));
	    	l>>=1;
	    }
	}
	
	public CountingTree_AddToInterval_GetSumFromInterval_Michal(){
		bonus = new int[OFFSET*2];
		tree = new int[OFFSET*2];
	}
}