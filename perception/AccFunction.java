/**
 * 
 */
package perception;

/**
 * @author jegou
 *
 */
public abstract class AccFunction {
	private volatile double _s;
	private volatile double _sDot;
	
	public AccFunction(){
		_s=0.0;
		_sDot=0.0;
	}
	
	protected double getS() {
		return _s;
	}

	protected double getsDot() {
		return _sDot;
	}
	
	public void updateValues(double s, double sDot){
		_s=s;
		_sDot=sDot;
	}
	
	/**
	 * Fonction renvoyant une valeur d'accumulation en fonction de la valeur des signaux passés en paramètre
	 * @param s
	 * @param sDot
	 */
	public abstract double getAcc(double s,double sDot);
	
	
	/**
	 * Fonction renvoyant une valeur d'accumulation en fonction des valeurs de signaux mis à jour par updateSignal
	 * @return
	 */
	public abstract double getAcc();
	
	
}
