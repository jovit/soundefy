package reconhecimento_de_notas;

public class Main {
	public static void main(String args[]){
		PitchDetector p = new PitchDetector();
		Thread t = new Thread(p);
		t.start();
		while(true){
			try{
				Thread.sleep(1000);
				Nota maisTocada = p.getNotaMaisTocada();
				if(maisTocada != null)
					System.out.println(maisTocada.getNome());
				else
					System.out.println("Faça um barulho");
	
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
