package com.openwebinars.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.openwebinars.rest.modelo.PreCompra;

@Service
public interface PreCompraRepositorio extends JpaRepository<PreCompra,Long>{

}
