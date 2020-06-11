package com.openwebinars.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.openwebinars.rest.dto.CarritoDTO;
import com.openwebinars.rest.dto.converter.CarritoDTOConverter;
import com.openwebinars.rest.error.CarritoNotFoundException;
import com.openwebinars.rest.modelo.LineaPreCompra;
import com.openwebinars.rest.modelo.PreCompra;
import com.openwebinars.rest.service.LineaPreCompraServicio;
import com.openwebinars.rest.service.PreCompraServicio;
import com.openwebinars.rest.util.pagination.PaginationLinksUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CarritoController {
	
	private final PaginationLinksUtils paginationLinksUtils;
	private final PreCompraServicio preCompraServicio;
	private final LineaPreCompraServicio lineaPreCompraServicio;
	private final CarritoDTOConverter carritoDTOConverter;
	
	@GetMapping("/carrito")
	public ResponseEntity<?> carrito(Pageable pageable, HttpServletRequest request) throws CarritoNotFoundException {
		
		Page<PreCompra> result = preCompraServicio.findAll(pageable);

		if (result.isEmpty()) {
			throw new CarritoNotFoundException();
		} else {
			
			Page<List<CarritoDTO>> listaCarritoDTO = result.map(carritoDTOConverter::converPreCompraToCarritoDTO);
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(listaCarritoDTO, uriBuilder))
					.body(listaCarritoDTO);
		}

	}
	
	@GetMapping("/carrito/{id}/{idProducto}")
	public LineaPreCompra obtenerUno(@PathVariable Long id, @PathVariable Long idProducto) throws CarritoNotFoundException {
		
		return lineaPreCompraServicio.findProducto(id,idProducto);
	}
	
	@PostMapping(value = "/addItem/{id}")
	public ResponseEntity<?> addItemCarrito(@PathVariable Long id, @RequestPart("nuevo") CarritoDTO itemCarrito) {
		return lineaPreCompraServicio.addItemCarrito(id,itemCarrito);
	}
	
	@DeleteMapping(value = "/deleteItem/{id}/{idProducto}")
	public ResponseEntity<Void> deleteItemCarrito(@PathVariable Long id, @PathVariable Long idProducto) {
		lineaPreCompraServicio.deleteItemCarrito(id,idProducto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/deleteCarrito/{id}")
	public ResponseEntity<Void> deleteCarrito(@PathVariable Long id ){
		preCompraServicio.deleteCarrito(id);
		return ResponseEntity.noContent().build();
	}
}
