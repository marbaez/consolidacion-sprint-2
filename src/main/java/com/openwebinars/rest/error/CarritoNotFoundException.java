package com.openwebinars.rest.error;

public class CarritoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2730111931833397903L;

	public CarritoNotFoundException() {
		super("No se han encontrado carrito");
	}

	public CarritoNotFoundException(Long id) {
		super("No se ha encontrado ningún elemento con el ID " + id);
	}
	
	

}
