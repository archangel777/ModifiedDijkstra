package test;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import com.graphhopper.util.StopWatch;

import pqueue.VanilaPriorityQueue;

public class NewPriorityQueueTest {
	
	public static void main(String[] args) {
		StopWatch sw = new StopWatch();
		Random r = new Random();
		int testNumber = 1000;
		
		Queue<Integer> q = new VanilaPriorityQueue<>();
		
		sw.start();
		
		for (int i = 0; i < testNumber/2; i++) {
			q.offer(r.nextInt(10000));
		}
		
		for (int i = 0; i < testNumber/4; i++) {
			q.poll();
		}
		
		for (int i = 0; i < testNumber/2; i++) {
			q.offer(r.nextInt(10000));
		}
		while (!q.isEmpty()) q.poll();
		
		sw.stop();
		System.out.println("Time vanila: " + sw.getNanos());
		
		Queue<Integer> p = new PriorityQueue<>();
		
		sw = new StopWatch();
		sw.start();
		for (int i = 0; i < testNumber/2; i++) {
			p.offer(r.nextInt(10000));
		}
		
		for (int i = 0; i < testNumber/4; i++) {
			p.poll();
		}
		
		for (int i = 0; i < testNumber/2; i++) {
			p.offer(r.nextInt(10000));
		}
		while (!p.isEmpty()) p.poll();
		sw.stop();
		
		System.out.println("Time Priority Queue: " + sw.getNanos());

	}
}
