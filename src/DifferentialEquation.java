import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class DifferentialEquation {

	private double c;
	private int power;
	private Function nu0;          //начальное условие
	private Function nu1;          //первое граничное
	private Function nu2;          // второе граничное
	private double a;              // левая граница по х
	private double b;               // правая граница по х 
	private double T;               //граница по t
	private double deltaX;           //шаг по х
	private double deltaT;           //шаг по t              
	private double t; 
	private double[] u;
	private double[] xValue;      //надо ли?
	private double[][] matr;
	private int sizeX;
	private int sizeT;
	
	
	DifferentialEquation(double c, int power,String nu0, String nu1, String nu2,double a,double b,
						double T, int sizeX,int sizeT) {
		this.c=c;
		this.power=power;
		this.nu0=new Function("nu0(x)="+nu0);
		this.nu1=new Function("nu1(t)="+nu1);
		this.nu2=new Function("nu2(t)="+nu2);
		this.a=a;
		this.b=b;
		this.T=T;
		t=0;
		
		this.sizeT=sizeT;
		deltaX=(b-a)/sizeX;
		deltaT=T/sizeT;
		sizeX++;
		this.sizeX=sizeX;
		u=new double[sizeX];
		xValue= new double[sizeX];
		for(int i=0;i<sizeX;i++) {
			xValue[i]=a+deltaX*i;
			u[i]=this.nu0.calculate(xValue[i]);
		}
	}
	
	public double[] solve1() {
		while(t<=T) {
			Matrix matrix = new Matrix(getMatrix1());
			u=matrix.sweepMethod(getRight1(u));
			if(deltaT*Vector.norma(u)>1) {
				deltaT = 1/(2*Vector.norma(u));
			}
			if(Vector.norma(u)>1000000) {
				break;
			}
			t+=deltaT;
		}
		return u;
	}
	
	
	
	private double[][] getMatrix1(){
		matr = new double[sizeX][sizeX];
		matr[0][0]=1;
		matr[sizeX-1][sizeX-1]=1;
		for(int i=1;i<sizeX-1;i++) {
			matr[i][i-1]=1;
			matr[i][i]=-(2+deltaX*deltaX/deltaT);
			matr[i][i+1]=1;
		}
		return matr;
	}
	
	
	private double[] getRight1(double[] y) {
		double[] result = new double[sizeX];
		result[0]=nu1.calculate(t);
		result[sizeX-1]=nu2.calculate(t);
		for(int i=1;i<sizeX-1;i++) {
			result[i]=-Math.pow(deltaX,2)*((y[i]/deltaT)+(c*Math.pow(y[i],power)));
		}
		return result;
	}
	
	
	public double[] solve2() {
		while(t<=T) {
			Matrix matrix = new Matrix(getMatrix2());
			u=matrix.sweepMethod(getRight2(u));
			if(deltaT*Vector.norma(u)>1) {
				deltaT = 1/(2*Vector.norma(u));
			}
			if(Vector.norma(u)>1000000) {
				break;
			}
			t+=deltaT;
		}
		return u;
	}
	
	
	
	private double[][] getMatrix2(){
		matr = new double[sizeX][sizeX];
		matr[0][0]=1;
		matr[sizeX-1][sizeX-1]=1;
		for(int i=1;i<sizeX-1;i++) {
			matr[i][i-1]=1;
			matr[i][i]=(Math.pow(deltaX, 2)*c*Math.pow(u[i], power-1)-2-Math.pow(deltaX, 2)/deltaT);
			matr[i][i+1]=1;
		}
		return matr;
	}
	
	
	private double[] getRight2(double[] y) {
		double[] result = new double[sizeX];
		result[0]=nu1.calculate(t);
		result[sizeX-1]=nu2.calculate(t);
		for(int i=1;i<sizeX-1;i++) {
			result[i]=-Math.pow(deltaX,2)*y[i]/deltaT;
		}
		return result;
	}
	
	
	
	
	
	
	
	public double[] solve3() {
		while(t<=T) {
//			Matrix matrix = new Matrix(getMatrix3());
			
//			double[] newU=matrix.sweepMethod(getRight1(u));
//			Expression e= new Expression("nu1("+a+")",nu1);
//			u[0]=e.calculate();
//			for(int i=1;i<sizeX-1;i++) {
//				u[i]=newU[i-1];
//			}
//			e= new Expression("nu1("+b+")",nu1);
//			u[sizeX-1]=e.calculate();
			
//			u=matrix.sweepMethod(getRight3(u));
			
			SystemFunctions f = new SystemFunctions(sizeX, deltaX, deltaT, c, power, u, nu1,nu2,t);
			u=Nuton.solve(u,f,0.0000001);
			if(deltaT*Vector.norma(u)>1) {
				deltaT = 1/(2*Vector.norma(u));
			}
			if(Vector.norma(u)>1000000) {
				break;
			}
			t+=deltaT;
		}
		return u;
	}
	
	
	
//	private double[][] getMatrix3(){
//		matr = new double[sizeX][sizeX];
//		matr[0][0]=1;
//		matr[sizeX-1][sizeX-1]=1;
//		for(int i=1;i<sizeX-1;i++) {
//			matr[i][i-1]=1;
//			matr[i][i]=(Math.pow(deltaX, 2)*c*Math.pow(u[i], power-1)-2-Math.pow(deltaX, 2)/deltaT);
//			matr[i][i+1]=1;
//		}
//		return matr;
//	}
//	
//	
//	private double[] getRight3(double[] y) {
//		double[] result = new double[sizeX];
//		result[0]=nu1.calculate(t);
//		result[sizeX-1]=nu2.calculate(t);
//		for(int i=1;i<sizeX-1;i++) {
//			result[i]=-Math.pow(deltaX,2)*y[i]/deltaT;
//		}
//		return result;
//	}
	
	
}
