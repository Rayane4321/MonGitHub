package Conteneur;

import java.util.ArrayList;

public class test {
	private static StringBuilder sb;
	private static ArrayList <Character> lettresDuMot = new ArrayList <Character>();


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		char p = 'p';
		char d = 'd';
		char s = 's';
		char z = 'e';
		char e = 'e';
		char a = 'a';


		lettresDuMot.add(p);
		lettresDuMot.add(d);
		lettresDuMot.add(s);
		lettresDuMot.add(e);
		lettresDuMot.add(z);
		lettresDuMot.add(a);

		
		initialiserTab();

		

	}

	public static StringBuilder initialiserTab() {
	    sb = new StringBuilder();
		for (Character caractère : lettresDuMot) {
			sb.append("_ ");
			System.out.print(sb);
		}
		return sb;
	}

}
