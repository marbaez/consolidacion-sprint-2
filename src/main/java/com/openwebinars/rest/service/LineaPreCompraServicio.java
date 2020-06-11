package com.openwebinars.rest.service;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openwebinars.rest.dto.CarritoDTO;
import com.openwebinars.rest.modelo.LineaPreCompra;
import com.openwebinars.rest.modelo.PreCompra;
import com.openwebinars.rest.modelo.Producto;
import com.openwebinars.rest.repos.LineaPreCompraRepositorio;
import com.openwebinars.rest.service.base.BaseService;

@Service
public class LineaPreCompraServicio extends BaseService<LineaPreCompra, Long, LineaPreCompraRepositorio> {
	
	private ProductoServicio productoServicio;
	private PreCompraServicio preCompraServicio;

	public LineaPreCompra findProducto(Long idPreCompra, Long idProducto) {
		
		Optional<Long> idPreCompOpt = Optional.of(idPreCompra);
		Optional<Long> idProdOpt = Optional.of(idProducto);
		
		Specification<LineaPreCompra> idPreCompraSpec = new Specification<LineaPreCompra>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<LineaPreCompra> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (idPreCompOpt.isPresent()) {
					return criteriaBuilder.equal(criteriaBuilder.lower(root.get("preCompra.id")),idPreCompOpt.get());
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // Es decir, que no filtramos nada
				}
			}
			
		};
		
		Specification<LineaPreCompra> idProductoCompraSpec = new Specification<LineaPreCompra>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<LineaPreCompra> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (idProdOpt.isPresent()) {
					return criteriaBuilder.equal(criteriaBuilder.lower(root.get("producto.id")),idProdOpt.get());
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // Es decir, que no filtramos nada
				}
			}
			
		};
		
		Specification<LineaPreCompra> ambas = idPreCompraSpec.and(idProductoCompraSpec);
	
		return this.repositorio.findOne(ambas).get();
	}

	public ResponseEntity<?> addItemCarrito(Long idPreCompra, CarritoDTO itemCarrito) {
		
		LineaPreCompra lineaPreCompra = new LineaPreCompra();
		lineaPreCompra.setCantidad(itemCarrito.getCantidad());
		
		Optional<Producto> producto = productoServicio.findById(itemCarrito.getId());
		
		lineaPreCompra.setProducto(producto.get());
		
		Optional<PreCompra> preCompra = preCompraServicio.findById(idPreCompra);
		
		lineaPreCompra.setPreCompra(preCompra.get());

		this.repositorio.save(lineaPreCompra);
		return null;
	}

	public void deleteItemCarrito(Long idPreCompra, Long idProducto) {
		
		LineaPreCompra deleteItemCarrito = findProducto(idPreCompra, idProducto);
		
		this.repositorio.deleteById(deleteItemCarrito.getId());
	}

}
