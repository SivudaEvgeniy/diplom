
public class PolniyPrognoz {
	
	private SystemFunctions f;
	private int n;
	private double eps;
	private double[] x;
	private double norm;
	double beta;
	double gamma;
	
	public PolniyPrognoz() {
		
	}
	
//	public PolniyPrognoz(int n,double eps, double[] x) {
//		this.n = n;
//		f = new SystemEquations(n);
//		this.eps = eps;
//		this.x = x;
//		beta=0.001 + (int) (Math.random() * 0.1);
//		gamma=beta*beta;
//	}
	
	public double[] solve() {
		int i;
		for(i=0;i<10000;i++) {
			i++;
			double normPrevious=Vector.norma(x);//ещё не верно
			double normLast;
			double[] xPlus=shag1();
			x=shag2(xPlus);
			normLast=Vector.norma(x);
			if (shag3()) {
				break;
			}
			shag4(normPrevious,normLast,xPlus);
		}
		return x;
	}
	
	private double[] shag1() {
		return Matrix.SLAUSolution(f.dF(x),f.minusFunc(x));
	}
	
	private double[] shag2(double[] inc) {
		return Vector.summ(x, Vector.skalar(beta,inc));
	}
	
	private boolean shag3() {                                         // возвращает true, то конец просчётов
		if (Vector.norma(f.func(x))<eps) {                           // иначе ---> шаг 4
			return true;
		}
		else return false;
	}
	
	private void shag4(double normPrevious, double normLast, double[] xPlus) {
		gamma*=Vector.norma(Vector.summ(x, xPlus))/normLast;
		if (normLast<normPrevious) {
			beta=1;
		}
		else {
			newBeta(normPrevious,normLast, xPlus);
		}
			newGamma(normPrevious,normLast,xPlus);
	}
	
	private double newBeta(double normPrevious, double normLast, double[] xPlus) {
		return Math.min(1, (gamma*normPrevious)/(beta*Vector.norma(Vector.summ(x, xPlus)))); 
	}
	
	private double newGamma(double normPrevious, double normLast, double[] xPlus) {
		return normPrevious*gamma/Vector.norma(Vector.summ(x, xPlus)); 
	}
	
	public void show() {
		Vector.show(x);
		System.out.println("норма = "+Vector.norma(f.func(x)));
	}
	
}