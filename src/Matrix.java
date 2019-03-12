
public class Matrix {
	
	private double[][] values;
	private int size;
	
	Matrix(){
		
	}
	
	Matrix(double[][] arg){
		size = arg.length;
		values = new double[size][size];
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				values[i][j]=arg[i][j];
			}
		}
	}
	
	private boolean chekForNull(int line) {//проверка на ноль
		if(values[line][line]==0) {
			return true;
		}
		else return false;
	}
	
	private int findMaxInColumn(int column) {//пойск максимального в столбце
		int indexMax=column;
		for(int i=column+1;i<values.length;i++) {
			if(Math.abs(values[i][column])>Math.abs(values[indexMax][column])) {
				indexMax=i;
			}
		}
		return indexMax;
	}
	
	private void swap(int line1, int line2) {//поменять местами строки
		double[] buff=values[line1];
		values[line1]=values[line2];
		values[line2]=buff;
	}
	
	private void toUnity(int line) {//к еденице главную дивгональ
		double del=values[line][line];
		for(int i=0;i<values.length+1;i++) {
			values[line][i]/=del;
		}
	}
	
	public void finish() {
		for(int i=values.length-1;i>0;i--) {
			for(int j=i-1;j>-1;j--) {
				raznica(j,lineXConst(i,values[j][i]));
			}
		}
	}
	
	private void raznica(int line2, double[] line1) {// line2-line1
		for(int i=0;i<values.length+1;i++) {
			values[line2][i]-=line1[i];
		}
	}
	
	private double[] lineXConst(int line,double constanta) {//строка умноженная на константу
		double[] result=new double[values.length+1];
		for(int i=0;i<values.length+1;i++) {
			result[i]=values[line][i]*constanta;
		}
		return result;
	}
	
	private void zeroing(int line) {
		for(int i=line+1;i<values.length;i++) {
			raznica(i,lineXConst(line,values[i][line]));
		}
	}
	
	private double[] backCourse() {
		double[] result=new double[values.length];
		result[values.length-1]=values[values.length-1][values.length];
		for(int i=values.length-2;i>-1;i--) {
			for(int j=i+1;j<values.length-1;j++) {
				result[i]-=values[i][j]*result[j];
			}
		}
		return result;
	}
	
	public double[] pesult() {
		for(int i=0;i<values.length-1;i++) {

			swap(i,findMaxInColumn(i));
			toUnity(i);
			zeroing(i);

		}
		if(values[values.length-1][values.length-1]!=0) {
			toUnity(values.length-1);
			zeroing(values.length-1);
		}
		return backCourse();
		
	}
	
	public void showMatrix() {
		for(int i=0;i<values.length;i++) {
			for(int j=0;j<values.length;j++) {
				System.out.print(values[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public double[] resultat() {
		double[] res = new double[values.length];
		for(int i=0;i<values.length;i++) {
			res[i]=values[i][values.length];
		}
		return res;
	}
	
	public static double[] SLAUSolution(double[][] matr,double[] x) {       //метод Гаусса 
		int n=matr.length;                                                   //для СЛАУ
		double[][] test= new double[n][n+1];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				test[i][j]=matr[i][j];
			}
			test[i][n]=x[i];
		}
		Matrix matrix=new Matrix(test);;
		matrix.pesult();
		matrix.finish();
		return matrix.resultat();
	}
	
	
	//МЕТОДЫ ДЛЯ ТРЁХДИАГОНАЛЬНОЙ ПРОГОНКИ --->
	
	
	
	private double[][] transformeMatrixToFourVectorsForSweepMethod(double[] arg){
		double[][] result = new double[4][size];
		
		result[1][0]=values[0][0];
		result[2][0]=values[0][0+1];
		result[3][0]=arg[0];
//		result[3][0]=arg[1];
		for(int i=1;i<size-1;i++) {			
			result[0][i]=values[i][i-1];
			result[1][i]=values[i][i];
			result[2][i]=values[i][i+1];
			result[3][i]=arg[i];
//			result[3][i]=arg[i+1];
		}
		result[0][size-1]=values[size-1][size-2];
		result[1][size-1]=values[size-1][size-1];
		result[3][size-1]=arg[size-1];
//		result[3][size-1]=arg[size-2];
		return result;
	}
	
	
	private double[][] straingthCourseForSweepMethod(double[][] arg){
		double[][] result = new double [2][size];
		double y=arg[1][0];
		result[0][0]=-arg[2][0]/y;
		result[1][0]=arg[3][0]/y;
		for(int i=1;i<size;i++) {
			y=arg[1][i]+arg[0][i]*result[0][i-1];
			if (i<size-1) {
				result[0][i]=-arg[2][i]/y;
			}
			result[1][i]=(arg[3][i]-arg[0][i]*result[1][i-1])/y;
		}
		return result;
	}
	
	private double[] reverseForSweepMethod(double[][] arg) {
		double[] result = new double[size];
		result[size-1]=arg[1][size-1];
		for(int i=size-2;i>-1;i--) {
			result[i]=arg[0][i]*result[i+1]+arg[1][i];
		}
		return result;
	}
	
	public double[] sweepMethod(double[] arg) {
		return reverseForSweepMethod(straingthCourseForSweepMethod(transformeMatrixToFourVectorsForSweepMethod(arg)));
	}

}
