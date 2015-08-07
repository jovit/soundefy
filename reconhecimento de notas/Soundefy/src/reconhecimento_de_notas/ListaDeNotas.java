package reconhecimento_de_notas;

import java.util.ArrayList;

public class ListaDeNotas {
	private final ArrayList<Nota> listaDeNotas;
	private final ArrayList<Integer> listaDeContador;
	
	
	public ListaDeNotas()
	{
		listaDeNotas = new ArrayList<>();
		listaDeContador = new ArrayList<>();
	}
	
	public void inserirNota(Nota n)
	{
		int index = listaDeNotas.indexOf(n);
		if (index >= 0)
		{
			listaDeContador.set(index, listaDeContador.get(index) + 1);
		}
		else
		{
			listaDeNotas.add(n);
			listaDeContador.add(1);
		}
	}
	public Nota notaMaisTocada()
	{
		Nota n = null;
		int maiorQtd = Integer.MIN_VALUE;
		int tam = listaDeContador.size();
		for (int i = 0;i < tam;i++)
		{
			if (listaDeContador.get(i) > maiorQtd)
			{
				maiorQtd = listaDeContador.get(i);
				n = listaDeNotas.get(i);
			}
		}
		return n;
	}
}
