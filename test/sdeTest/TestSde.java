package test.sdeTest;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import ch.epfl.lis.sde.Sde;

public class TestSde extends Sde {

	@Override
	public void getDriftAndDiffusion(double t, DoubleMatrix1D Xin, DoubleMatrix1D F, DoubleMatrix2D G)
			throws Exception {
		F.set(1, Xin.get(1));
		G.set(1, 1, 0.0);
		
	}

}
