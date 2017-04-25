package test.sdeTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import ch.epfl.lis.sde.Sde;
import ch.epfl.lis.sde.SdeSettings;
import ch.epfl.lis.sde.examples.ExampleSde;
import ch.epfl.lis.sde.solvers.EulerMaruyama;
import equation.OrdinarySolver;
import equation.RungeKuttaSolver;
import implEqu.GeneEqu;

public class TestEquation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double damping=0.0;
		double stiffness=1.0;
		try {
			System.setOut(new PrintStream(new File("./results.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GeneEqu ge1 = new GeneEqu(damping,stiffness,null);
		OrdinarySolver os = new RungeKuttaSolver(ge1);
		double initialValues[] = new double[]{0.0,1.0};
		List<Double> finalValues=new ArrayList<Double>();
		double[] result=null;
		double precValues[] = new double[initialValues.length];
		System.arraycopy(initialValues, 0, precValues, 0, initialValues.length);
		for(double i=0;i<10.0;i=i+0.1){
			result=os.solve(i+0.1, i, precValues, null);
			if(result!=null && result.length==2){
				finalValues.add(result[1]);
				// Affiche dans le fichier résultat
				System.out.println(result[1]);
				System.arraycopy(result, 0, precValues, 0, result.length);
			} 
			else{
				System.err.println("Error solving equation : aborting");
			}
		}
		
		try {
			System.setOut(new PrintStream(new File("./results_stoch.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExampleSde es = new ExampleSde();
		es.setSigma(0.0);
		DoubleMatrix2D g = new DenseDoubleMatrix2D(1, 1);
		g.assign(0.0);
		DoubleMatrix1D xIn = new DenseDoubleMatrix1D(1);
		xIn.assign(0.0);
		DoubleMatrix1D f = new DenseDoubleMatrix1D(1);
		f.assign(1.0);
		try {
			es.getDriftAndDiffusion(1.0, xIn, f, g);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DoubleMatrix1D dW = new DenseDoubleMatrix1D(1);
		dW.assign(0.5);
		double h = 0.1;
		double t = 1.0;
		DoubleMatrix1D dZ = new DenseDoubleMatrix1D(1);
		dZ.assign(0.1);
		SdeSettings st = SdeSettings.getInstance();
		st.setMaxt(1.0);
		EulerMaruyama em = new EulerMaruyama(); 
		em.setSystem(new Sde(){
			@Override
			public void getDriftAndDiffusion(double t, DoubleMatrix1D xIn, DoubleMatrix1D F, DoubleMatrix2D G)
					throws Exception {
				F.set(0, 1.0);
				G.set(0, 0, 1.0);
			} });
		
		// Pas d'intégration
		em.setH(0.01);
		em.setX(new DenseDoubleMatrix1D(1).assign(0.0));
		
		// Initialise 
		em.initialize();
		t=0;
		List<Double> timeValues = new ArrayList<Double>();
		timeValues.add(t);
		for(int i=0;i<100;i++){
			try {
				t+=em.step();
				timeValues.add(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(em.getX().get(0));
		}
		
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		sc.close();
	}

}
