package utils;

import java.util.Iterator;

public class BigArray<T> implements Iterable<T>{
	
	private long size;
	private int iMax, jMax;
	private T[][] array;
	
	@SuppressWarnings("unchecked")
	public BigArray(long size) {
		this.size = size;
		double div = Math.ceil(size/(double)Integer.MAX_VALUE);
		this.iMax = (int) div;
		this.jMax = (int) Math.ceil(size/div);
		array = (T[][]) new Object[this.iMax][this.jMax];
	}
	
	public long size() {
		return this.size;
	}
	
	public T get(long index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Trying to access index " + index + " in array of size " + this.size);
		double dIndex = (double) index;
		int i = (int) (dIndex / jMax);
		int j = (int) (dIndex - i * jMax);
		return array[i][j];
	}
	
	public void set(long index, T element) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Trying to access index " + index + " in array of size " + this.size);
		double dIndex = (double) index;
		int i = (int) (dIndex / jMax);
		int j = (int) (dIndex - i * jMax);
		array[i][j] = element;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private int i = 0, j = 0;

			@Override
			public boolean hasNext() {
				return i < iMax && j < jMax;
			}

			@Override
			public T next() {
				T res = array[i][j];
				j++;
				if (j == jMax) {
					i++;
					j = 0;
				}
				return res;
			}
			
		};
	}
}
