package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BigList<T> implements Iterable<T>{
	
	private long size;
	private List<List<T>> array;
	
	public BigList() {
		array = new ArrayList<List<T>>();
		array.add(new ArrayList<>());
		size = 0;
	}
	
	public BigList(long size) {
		this.size = size;
		int nRows = (int) Math.ceil(size/(double)Integer.MAX_VALUE);
		array = new ArrayList<>(nRows);
		for (int i = 0; i < nRows-1; i++) {
			array.add(new ArrayList<>(Integer.MAX_VALUE));
		}
		array.add(new ArrayList<>((int)(size - nRows * Integer.MAX_VALUE)));
	}
	
	public long size() {
		return this.size;
	}
	
	public T get(long index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Trying to access index " + index + " in array of size " + this.size);
		int i = (int) (index / Integer.MAX_VALUE);
		int j = (int) (index - i * Integer.MAX_VALUE);
		return array.get(i).get(j);
	}
	
	public void set(long index, T element) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Trying to access index " + index + " in array of size " + this.size);
		int i = (int) (index / Integer.MAX_VALUE);
		int j = (int) (index - i * Integer.MAX_VALUE);
		array.get(i).set(j, element);
	}
	
	public void add(T element) {
		if (array.get(array.size() - 1).size() >= Integer.MAX_VALUE)
			array.add(new ArrayList<>());
		array.get(array.size() - 1).add(element);
		this.size++;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private int pos = 0;

			@Override
			public boolean hasNext() {
				return pos < size;
			}

			@Override
			public T next() {
				T res = get(pos);
				pos++;
				return res;
			}
			
		};
	}
}
