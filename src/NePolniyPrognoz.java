
public class NePolniyPrognoz {

	private SystemFunctions f;
	private int n;
	private double eps;
	private double[] x;
	private double norm;
	double beta;
	double gamma;
	
	public NePolniyPrognoz() {
		
	}
	
//	public NePolniyPrognoz(int n,double eps, double[] x) {
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
			double normPrevious=Vector.norma(x);
			double normLast;
			x=shag2(shag1());
			normLast=Vector.norma(x);
			if (shag3()) {
				break;
			}
			shag4(normPrevious,normLast);
		}
		return x;
	}
	
	private double[] shag1() {
		return Matrix.SLAUSolution(f.dF(x),f.minusFunc(x));
	}
	
	private double[] shag2(double[] inc) {
		return Vector.summ(x, Vector.skalar(beta,inc));
	}
	
	private boolean shag3() {                                         // возвращает true, то конец прощётов
		if (Vector.norma(f.func(x))<eps) {                           // иначе ---> шаг 4
			return true;
		}
		else return false;
	}
	
	private void shag4(double normPrevious, double normLast) {
		if (normLast<normPrevious) {
			beta=1;
			newGamma(normPrevious,normLast);
		}
		else {
			newGamma(normPrevious,normLast);
			newBeta();
		}
	}

	private double newBeta() {
		return Math.min(1, gamma/beta);
	}
	
	private double newGamma(double normPrevious, double normLast) {
		return normPrevious*gamma/normLast;
	}

	public void show() {
		Vector.show(x);
		System.out.println("норма = "+Vector.norma(f.func(x)));
	}
	
}
