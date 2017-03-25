package pqueue;

import java.lang.reflect.Array;

public class VanilaQueueEntry<T extends Comparable<T>> {
	private int size;
	private T entry;
	private VanilaQueueEntry<T>[] children;
	
	public VanilaQueueEntry(T entry) {
		this.size = 0;
		this.entry = entry;
		@SuppressWarnings("unchecked")
        final VanilaQueueEntry<T>[] a = new VanilaQueueEntry[5];
        this.children = a;
	}
	
	public T getEntry() {
		return this.entry;
	}
	
	public void add(VanilaQueueEntry<T> e) {
		children[size] = e;
		size++;
		if (size >= 4) {
			int keep1, keep2;
			
			if (children[0].getEntry().compareTo(children[1].getEntry()) < 0) {
				children[0].add(children[1]);
				keep1 = 0;
			} else {
				children[1].add(children[0]);
				keep1 = 1;
			}
			
			if (children[2].getEntry().compareTo(children[3].getEntry()) < 0) {
				children[2].add(children[3]);
				keep2 = 2;
			} else {
				children[3].add(children[2]);
				keep2 = 3;
			}
			
			children[0] = children[keep1];
			
			children[1] = children[keep2];
			
			if (size == 5) children[2] = children[4];
			
			size -= 2;
			
		}
	}
	
	public VanilaQueueEntry<T> removeMin() {
		if (size == 0) return null;
		int minPos = 0;
		for (int i = 1; i < size; i++) {
			if (children[i].getEntry().compareTo(children[minPos].getEntry()) < 0) {
				minPos = i;
			}
		}
		VanilaQueueEntry<T> min = children[minPos];
		for (int i = minPos; i < size-1; i++) {
			children[i] = children[i + 1];
		}
		size--;
		min.expandTo(this);
		return min;
	}
	
	public void expandTo(VanilaQueueEntry<T> parent) {
		for (int i = 0; i < size; i++) {
			parent.add(children[i]);
		}
	}
	
	public VanilaQueueEntry<T> peek() {
		if (size == 0) return null;
		VanilaQueueEntry<T> min = children[0];
		for (int i = 1; i < size; i++) {
			if (children[i].getEntry().compareTo(min.getEntry()) < 0) {
				min = children[i];
			}
		}
		return min;
	}
	
	public int getSize() {
		return size;
	}
}
