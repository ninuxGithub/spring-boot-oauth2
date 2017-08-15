import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator {
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		
		list.add(1);
		list.add(2);
		
		for(Iterator<Integer> itr = list.iterator(); itr.hasNext();) {
			Integer next = itr.next();
			System.out.println(next);
		}
		
	}

}
