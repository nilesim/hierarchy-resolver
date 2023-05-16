package com.selin.hierarchyresolver.domain.model

import javax.persistence.Id
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class Relation(
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
		val id: UUID? = null,
	val name: String = "",
	var supervisorId: UUID? = null
)

