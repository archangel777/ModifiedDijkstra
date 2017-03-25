package pqueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class VanilaPriorityQueue<T extends Comparable<T>> implements Queue<T> {
	
	private int size = 0;
	private VanilaQueueEntry<T> base;
	
	public VanilaPriorityQueue() {
        this.base = new VanilaQueueEntry<>(null);
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		if (arg0.isEmpty()) return false;
		for (T e : arg0) this.add(e);
		return true;
	}

	@Override
	public void clear() {
		this.base = new VanilaQueueEntry<>(null);
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T e) {
		base.add(new VanilaQueueEntry<T>(e));
		size++;
		return true;
	}

	@Override
	public T element() {
		VanilaQueueEntry<T> res = base.peek();
		if (res == null) return null;
		return res.getEntry();
	}

	@Override
	public boolean offer(T e) {
		base.add(new VanilaQueueEntry<T>(e));
		size++;
		return true;
	}

	@Override
	public T peek() {
		VanilaQueueEntry<T> res = base.peek();
		if (res == null) return null;
		return res.getEntry();
	}

	@Override
	public T poll() {
		VanilaQueueEntry<T> res = base.removeMin();
		if (res == null) return null;
		size--;
		return res.getEntry();
	}

	@Override
	public T remove() {
		VanilaQueueEntry<T> res = base.removeMin();
		if (res == null) return null;
		size--;
		return res.getEntry();
	}

}
