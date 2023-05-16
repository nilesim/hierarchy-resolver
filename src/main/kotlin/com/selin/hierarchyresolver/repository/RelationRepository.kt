package com.selin.hierarchyresolver.repository

import com.selin.hierarchyresolver.domain.model.Relation
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RelationRepository : CrudRepository<Relation, UUID> {
	fun findOneByName(name: String): Relation?
	override fun findAll(): MutableIterable<Relation>
	fun countBySupervisorIdNull(): Int
	fun findBySupervisorIdNull(): Relation?
	fun findBySupervisorId(supervisorId: UUID): MutableIterable<Relation>?
}
