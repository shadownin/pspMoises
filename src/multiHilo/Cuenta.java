package multiHilo;
public class Cuenta {
	private Fecha fechaCreacion;
	private char[] ccc = new char[20];
	private Double saldo;

	public Cuenta(Fecha fechaCreacion, String ccc, double saldo) {
		this.fechaCreacion = fechaCreacion;
		StringBuilder aux = new StringBuilder(ccc);
		aux.setLength(20);
		this.ccc = aux.toString().toCharArray();
		this.saldo = saldo;
	}

	public void ingresar(double cantidad) {
		synchronized (saldo) {
			saldo += cantidad;
		}
		
	}

	public void reintegrar(double cantidad) throws SinSaldoException {
		 synchronized  (saldo) {
			 if (cantidad > saldo) {
					throw new SinSaldoException("Saldo insuficiente");
		 }
			 saldo -= cantidad;
		}
		
	}

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public char[] getCcc() {
		return ccc;
	}

	public void setCcc(char[] ccc) {
		this.ccc = ccc;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	private class ThreadIngresar implements Runnable {
		private double cantidad;

		public ThreadIngresar(double cantidad) {
			this.cantidad = cantidad;
		}

		public void run() {
			synchronized  (saldo) {
			ingresar(cantidad);
			System.out.println(Thread.currentThread().getName()
					+ " Saldo actual: " + saldo);
			}
		}
	}

	private class ThreadExtraer implements Runnable {
		private double cantidad;

		public ThreadExtraer(double cantidad) {
			this.cantidad = cantidad;
		}

		public void run() {
			synchronized  (saldo) {
			try {
				reintegrar(cantidad);
				System.out.println(Thread.currentThread().getName()
						+ " Saldo actual: " + saldo);
			} catch (SinSaldoException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			}
		}
	}
	public void print(){
		System.out.println("Fecha: ");
		fechaCreacion.print();
		System.out.println(" codigo: "	+ new String(ccc) + " saldo: " + saldo);
	}
	
	public void println(){
		print();
		System.out.println();
	}
	

	public static void main(String args[]) {
		try {
			Cuenta c = new Cuenta(new Fecha(), "52465213259854752162", 1000);
			Thread ingresos[] = new Thread[100];
			for (int i = 0; i < 100; i++) {
				ingresos[i] = new Thread(c.new ThreadIngresar(1));
				ingresos[i].start();
			}
			Thread reintegros[] = new Thread[100];
			for (int i = 0; i < 100; i++) {
				reintegros[i] = new Thread(c.new ThreadExtraer(1));
				reintegros[i].start();
			}
			for(int i = 0; i< 100; i++) {
				ingresos[i].join();
				reintegros[i].join();
			}
			c.println();
		} catch (Exception e) {
			System.err.println("Fecha incorrecta");
			e.printStackTrace();
		}

	}

}
