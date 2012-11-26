package buffercircular;

public class Productor implements Runnable {

	private Buffer buffer;

	public Productor(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		for(;;){
		try {
			Thread.sleep(1000);
			int aleatorio = (int)(Math.random()*200);
			buffer.set(aleatorio);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		
	}

	
}
