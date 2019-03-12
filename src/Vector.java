
public class Vector {

	private int size;
	private double values;
	public Vector() {
		
	}
	
	public Vector(int n) {
	size = n;	
	}
	
	public static double[] summ(double[] a, double[] b) {                 //сумма двух векторов
		int size=a.length;
		double[] result = new double[size];
		for(int i=0;i<size;i++) {
			result[i]=a[i]+b[i];
		}
		return result;
	}
	
	public static double norma(double[] vect) {                             //норма вектора
		int size=vect.length;
		double result = 0;
		for(int i=0;i<size;i++) {
			result+=vect[i]*vect[i];
		}
		return Math.sqrt(result);
	}
	
	public static void show(double[] vect) {                         //вывести вектор на консоль
		for(int i=0;i<vect.length;i++) {
			System.out.println("x["+(i+1)+"]"+vect[i]);
		}
	}
	
	public static double[] skalar(double skalar, double[] vect) {            //домножение вектора на скаляр
		int size=vect.length;
		double[] result = vect;
		for(int i=0;i<size;i++) {
			result[i]*=skalar;
		}
		return result;
	}
	
	public static double[] random(int n) {
		double[] vect = new double[n];
		for(int i=0;i<n;i++) {
			vect[i]=0;//Math.random()/2+0.5;
		}
		return vect;
	}

}
