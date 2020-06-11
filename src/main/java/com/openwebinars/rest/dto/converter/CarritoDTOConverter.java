package com.openwebinars.rest.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.openwebinars.rest.dto.CarritoDTO;
import com.openwebinars.rest.modelo.LineaPreCompra;
import com.openwebinars.rest.modelo.PreCompra;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarritoDTOConverter {
	
	public List<CarritoDTO> converPreCompraToCarritoDTO(PreCompra preCompra) {
		
		Set<LineaPreCompra> lineaPreCompra = preCompra.getLineas();
		List<CarritoDTO> listaCarritoDTO = new ArrayList<CarritoDTO>();
		
		for (LineaPreCompra linea : lineaPreCompra) {
			
			CarritoDTO carritoDTO = new CarritoDTO();
			carritoDTO.setId(linea.getProducto().getId());
			carritoDTO.setNombre(linea.getProducto().getNombre());
			carritoDTO.setPrecio(linea.getProducto().getPrecio());
			carritoDTO.setSubtotal(linea.getSubtotal());
			carritoDTO.setTotal(preCompra.getTotal());
		}

		return listaCarritoDTO;
	}

}
