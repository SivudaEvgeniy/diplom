
public class GUI {

	public static void main(String[] args) {
		DifferentialEquation DU= new DifferentialEquation(1.4, 2, "1.5*sin(x)", "0", "0", 0, Math.PI, 1, 20, 100);
//		Vector.show(u);
		Vector.show(DU.solve1());
		Vector.show(DU.solve2());
		Vector.show(DU.solve3());
//		Matrix m =new Matrix(getMatrix1());
//		m.showMatrix();
//		Vector.show(getRight1(u));
	}
}
