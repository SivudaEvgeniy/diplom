
public class Nuton {

//	private SystemFunctions f;
//	private int n;
//	private double eps;
//	private double[] x;
//	private double norm;
	
	public Nuton() {
		
	}
	
//	public Nuton(int n,double eps, double[] x) {
//		this.n = n;
//		f = new SystemEquations(n);
//		this.eps = eps;
//		this.x = x;
//	}
	
	private static double[] findInc(SystemFunctions f, double[] x) {
		Matrix matrix = new Matrix(f.dF(x));
		return matrix.sweepMethod(f.minusFunc(x));
//		return Matrix.SLAUSolution(f.dF(x),f.minusFunc(x));
	}
	
	private static double[] increment(double[] x, double[] inc) {
		return Vector.summ(x,inc);
	}
	
	public static double[] solve(double[] x, SystemFunctions f, double eps) {
		int i=0;
		do {
			i++;
			x=increment(x,findInc(f,x));
		}while(Vector.norma(f.func(x)) > eps );
		return x;
	}
	
//	public void show() {
//		Vector.show(x);
//		System.out.println("норма = "+Vector.norma(f.func(x)));
//	}

}
