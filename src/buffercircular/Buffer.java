
package buffercircular;

import java.util.Vector;

public class Buffer {
	public int[] num;
	
	private int g = 0;
	private int s = 0;
	private int n;
	
	public Buffer(int n) {
		this.n=n;
		num = new int[n];
	}
	
	public Buffer(){
		this(10);
	}
	
	public void clear(){
		g = 0;
		s = 0;
		n = 0;
	}
	
	public synchronized void set(int valor){
		if (! complete()){
		num[s] = valor;
		s++;
		if (s == num.length)
			s = 0;
		n++;
		notifyAll();
		}else{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized int get(){
		for(;;){
		if (! empty()){
		int aux = num[g];
		g++;
		if (g == num.length)
			g = 0;
		n--;
		notifyAll();
		return aux;
		}
		else{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
	}
	
	public boolean empty(){
		return n == 0;
	}
	
	public boolean complete(){
		return n == num.length;
	}

	public static void main(String[] args) {
		Buffer b = new Buffer();
		Thread prod = new Thread(new Productor(b));
		Thread con = new Thread(new Consumidor(b));
		prod.start();
		con.start();

	}

}
