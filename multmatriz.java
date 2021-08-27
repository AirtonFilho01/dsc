package multMatrizes;

import java.util.Scanner;


import java.util.ArrayList;
import java.util.Random;

public class multmatriz {

	public int linhasMat1;
	public int colunasMat1;
	public int linhasMat2;
	public int colunasMat2;

	public int[][] mat1;
	public int[][] mat2;

	public int[][] res;

	public ArrayList<Thread> pool = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		multmatriz m = new multmatriz();

		m.criarMatrizes();
		for (int i = 0; i < m.linhasMat1; i++) {
			for (int j = 0; j < m.colunasMat2; j++) {
				m.pool.add(m.multThread(i, j));
}
		}
		
		System.out.println("");	
		for (int i = 0; i < m.pool.size(); i++) {
			m.pool.get(i).start();
		}
		
		for (int i = 0; i < m.pool.size(); i++) {
			m.pool.get(i).join();
		}
		m.print(m.res,m.linhasMat1 ,m.colunasMat2);
	}

	public void criarMatrizes() {
		Scanner s = new Scanner(System.in);
		Random gerador = new Random();

		System.out.println("Digite o numero de linhas da matriz primeira");
		linhasMat1 = s.nextInt();

		System.out.println("Digite o numero de colunas da primeira matriz");
		colunasMat1 = s.nextInt();

		System.out.println("Digite o numero de linhas da segunda matriz");
		linhasMat2 = s.nextInt();

		System.out.println("Digite o numero de colunas da segunda matriz");
		colunasMat2 = s.nextInt();

		if (colunasMat1 != linhasMat2) {
			System.out.println("Não é possivel multiplicar essas matrizes");
			System.exit(0);
		}

		mat1 = new int[linhasMat1][colunasMat1];
		mat2 = new int[linhasMat2][colunasMat2];
		res = new int [linhasMat1][colunasMat2];

		for (int i = 0; i < linhasMat1; i++) {
			for (int j = 0; j < colunasMat1; j++) {
				mat1[i][j] = gerador.nextInt(10);
			}
		}

		for (int i = 0; i < linhasMat2; i++) {
			for (int j = 0; j < colunasMat2; j++) {
				mat2[i][j] = gerador.nextInt(10);
			}
		}

		print(mat1, linhasMat1, colunasMat1);
		print(mat2, linhasMat2, colunasMat2);



	}

	public void print(int[][] matriz, int linha, int coluna) {

		String linhaprint;
		System.out.println("");
		for (int i = 0; i < linha; i++) {
			linhaprint = "";
			for (int j = 0; j < coluna; j++) {
				linhaprint += Integer.toString(matriz[i][j]) + " ";
			}
			System.out.println(linhaprint);
		}
	}

	public int[] toVector(int[][] matriz,int linha,int coluna, int mat) {
		int[] vetor =null;
		/*quando mat == 1, estamos operando na matriz 1
		 * quando mat == 2, estamos operando na matriz 2
		 * quando linha == -1, extraimos a coluna
		 * quando coluna == -1, extraimos a linha
		 * 
		 * */
		//   

		if (mat == 1) {
			if (linha == -1) {
				vetor = new int[colunasMat1];
				for (int i = 0; i < linhasMat1; i++) {
					vetor[i] = matriz[i][coluna];
				}
			}
			if (coluna == -1) {
				vetor = new int[colunasMat1];
				for (int i = 0; i < colunasMat1; i++) {
					vetor[i] = matriz[linha][i];
				}
			}
		}

		if (mat == 2) {
			if (linha == -1) {
				vetor = new int[linhasMat2];
				for (int i = 0; i < linhasMat2; i++) {
					vetor[i] = matriz[i][coluna];
				}
			}
			if (coluna == -1) {
				vetor = new int[linhasMat2];
				for (int i = 0; i < colunasMat2; i++) {
					vetor[i] = matriz[linha][i];
				}
			}
		}
		return vetor;		
	}

	public int multVector(int[] vetA, int[] vetB) {
		int resmult =0;

		for (int i = 0; i < vetA.length; i++) {
			resmult += vetA[i]  * vetB[i];
		}

		return resmult;
	}

	public Thread multThread(int linha, int coluna) {
		

		Thread t = new Thread() {
			int[] vetA;
			int[] vetB;
			
			@Override
			public void run() {
				System.out.println("no thread "+linha+ " "+ coluna);
				// TODO Auto-generated method stub
				vetA = toVector(mat1, linha, -1, 1);
				vetB = toVector(mat2, -1,coluna, 2);

				res[linha][coluna] =  multVector(vetA, vetB); 
				System.out.println("Fim do thread "+linha+ " "+ coluna);
			}
		
		};
		//t.run();
		
		//t.start();
		
		return t;
	}

}
