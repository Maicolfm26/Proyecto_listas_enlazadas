package co.edu.uniquindio.listas.model;

public enum Requerida {
	// Tipos de productos disponibles
		OBLIGATORIA(0), OPCIONAL(1);

		/**   
		 * Atributo de la enumeracion   
		 */
		
		private int numTipo;

		/**   
		 * Metodo constructor      
		 * @param numTipo  
		 */  
		
		private Requerida(int numTipo) {   
			this.numTipo = numTipo; 
		 
		 }

		/** 
		 * Metodo accesor 
		 * 
		 * @return 
		 */
		
		public int getNumTipo() {
			return numTipo;
		}

		/** 
		 * Representacion en string del valor elegido
		 */
		
		public String toString() {
			String tipo = "";
			switch (numTipo) {
			case 0:
				tipo = "Obligatoria";
				break;
			case 1:
				tipo = "Opcional";
				break;
			default:
				tipo = "No especificado";
			}
			return tipo;
		}
}
