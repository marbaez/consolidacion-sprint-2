package com.openwebinars.rest.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class PreCompra {

	@Id
	@GeneratedValue
	private Long id;

	private String cliente;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonManagedReference
	@Builder.Default
	@OneToMany(mappedBy = "preCompra", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineaPreCompra> lineas = new HashSet<>();

	public float getTotal() {
		return (float) lineas.stream().mapToDouble(LineaPreCompra::getSubtotal).sum();
	}

	/**
	 * Métodos helper
	 */

	public void addLineaPreCompra(LineaPreCompra lp) {
		lineas.add(lp);
		lp.setPreCompra(this);
	}

	public void removeLineaPreCompra(LineaPreCompra lp) {
		lineas.remove(lp);
		lp.setPreCompra(null);
	}

	
}