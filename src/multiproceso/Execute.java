package multiproceso;

import java.io.*;
import java.util.Vector;

//captura la salida (stdio) de un proceso externo

public class Execute {
	public static void main(String args[]) {
		try {
			String linea;

			Vector<Process> procesos = new Vector<Process>();
			Vector<BufferedReader> flujosEntrada = new Vector<BufferedReader>();
			Vector<BufferedReader> flujosErrores = new Vector<BufferedReader>();

			for (int i = 0; i < args.length; i++) {
				procesos.add(Runtime.getRuntime().exec(args[i]));
				flujosEntrada.add(new BufferedReader(
						new InputStreamReader(procesos.elementAt(i)
								.getInputStream())));
				flujosErrores.add(new BufferedReader(
						new InputStreamReader(procesos.elementAt(i)
								.getErrorStream())));
			}

			for (int i = 0; i < args.length; i++) {
				// std input
				while ((linea = flujosEntrada.elementAt(i).readLine()) != null) {
					System.out.println(linea);
				}
				flujosEntrada.elementAt(i).close();

				// stderr
				while ((linea = flujosErrores.elementAt(i).readLine()) != null) {
					System.out.println(linea);
				}
				flujosErrores.elementAt(i).close();

				Thread.sleep(10000); //Dormir 10 seg
				procesos.elementAt(i).destroy(); //Matar los procesos
				System.out.println("comando: " + args[i] + " Salida: " + procesos.elementAt(i).exitValue());
				procesos.elementAt(i).waitFor(); // Esperar
				
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
