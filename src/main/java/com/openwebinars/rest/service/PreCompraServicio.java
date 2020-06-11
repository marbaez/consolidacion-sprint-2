package com.openwebinars.rest.service;

import org.springframework.stereotype.Service;

import com.openwebinars.rest.modelo.PreCompra;
import com.openwebinars.rest.repos.PreCompraRepositorio;
import com.openwebinars.rest.service.base.BaseService;

@Service
public class PreCompraServicio extends BaseService<PreCompra, Long, PreCompraRepositorio> {

	public void deleteCarrito(Long id) {
		deleteCarrito(id);
	}

}
