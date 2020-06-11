package com.openwebinars.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.openwebinars.rest.modelo.LineaPreCompra;
import com.openwebinars.rest.modelo.Producto;

@Service
public interface LineaPreCompraRepositorio extends JpaRepository<LineaPreCompra,Long>, JpaSpecificationExecutor<LineaPreCompra>{

}
