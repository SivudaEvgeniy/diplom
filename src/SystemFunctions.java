import org.mariuszgromada.math.mxparser.Function;

public class SystemFunctions {

	private int size;
	private double h;
	private double tau;
	private double c;
	private double power;
	private double[] oldTochka;
	private Function nu1;          
	private Function nu2;  
	private double t;
	
	SystemFunctions(int size,double h, double tau, double c, double power, double[] oldTochka,
			Function nu1, Function nu2,double t){
		this.size=size;
		this.h=h;
		this.tau=tau;
		this.c=c;
		this.power=power;
		this.oldTochka=oldTochka;
		this.nu1=nu1;
		this.nu2=nu2;
		this.t=t;
	}
	
	public void setOldTochka(double[] arg) {
		oldTochka=Vector.skalar(1, arg);
	}
	
	public double[] func(double[] newTochka ) {
		double[] result=new  double[size];
		result[0]=newTochka[0]-nu1.calculate(t);
		for(int i=1;i<size-1;i++) {
			result[i]=newTochka[i-1]-(2+(h*h/tau))*newTochka[i]+newTochka[i+1]+h*h*c*Math.pow(newTochka[i], power)+(h*h/tau)*oldTochka[i];
		}
		result[size-1]=newTochka[size-1]-nu2.calculate(t);

		return result;

	}
	
	public double[] minusFunc(double[] tochka) {
		double[] result=new double[size];
		result=func(tochka);
		for(int i=0;i<size;i++) {
			result[i]*=-1;
		}
		return result;
	}
	
	public double[][] dF(double[] tochka) {
		double[][] result= new double[size][size];
		result[0][0]=1;
		result[size-1][size-1]=1;
		for(int i=1;i<size-1;i++) {
			result[i][i-1]=1;
			result[i][i]=-(2+h*h/tau)+power*h*h*c*Math.pow(tochka[i],power-1);
			result[i][i+1]=1;
		}
		return result;
	}

}
