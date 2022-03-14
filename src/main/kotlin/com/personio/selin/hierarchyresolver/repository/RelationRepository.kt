package com.personio.selin.hierarchyresolver.repository

import com.personio.selin.hierarchyresolver.domain.model.Relation
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.CrudRepository
//import kotlinx.coroutines.flow.Flow
import java.util.*

interface RelationRepository : CrudRepository<Relation, UUID> {
	fun findOneByName(name: String): Relation?
	override fun findAll(): MutableIterable<Relation>
	fun countBySupervisorIdNull(): Int
	fun findBySupervisorIdNull(): Relation?
	fun findBySupervisorId(supervisorId: UUID): MutableIterable<Relation>?
}
