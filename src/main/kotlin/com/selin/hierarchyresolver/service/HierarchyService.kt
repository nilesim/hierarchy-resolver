package com.selin.hierarchyresolver.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import com.selin.hierarchyresolver.domain.model.Relation
import com.selin.hierarchyresolver.repository.RelationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Service
class HierarchyService(
		private val repo: RelationRepository
) {
	@Transactional
	fun arrangeStructure(relations: JsonNode): ObjectNode {
		val fields: Iterator<Map.Entry<String, JsonNode>> = relations.fields()
		while (fields.hasNext()) {
			val field = fields.next()
			val fieldValue = field.value.asText()
			val supervisor = repo.findOneByName(fieldValue) ?: let { repo.save(Relation(name = fieldValue)) }
			val employee = repo.findOneByName(field.key) ?: let { Relation(name = field.key) }
			employee.supervisorId = supervisor.id
			repo.save(employee)
		}

		val rootCount = repo.countBySupervisorIdNull()
		if (rootCount == 0) throw ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "This hierarchy contain loops.")
		if (rootCount > 1) throw ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "This hierarchy has multiple roots.")
		val result: ObjectNode = JsonNodeFactory.instance.objectNode()
		repo.findBySupervisorIdNull()?.let { result.set<JsonNode>(it.name, getChild(it.id!!)); }
		return result
	}

	fun getChild(id: UUID): JsonNode {
		val relations = repo.findBySupervisorId(id) ?: let {
			return TextNode("")
		}
		val child: ObjectNode = JsonNodeFactory.instance.objectNode()
		relations.map {
			child.set<JsonNode>(it.name, getChild(it.id!!))
		}
		return child
	}

	fun getSupervisorsOfEmployee(name: String): ObjectNode {
		val result: ObjectNode = JsonNodeFactory.instance.objectNode()
		repo.findOneByName(name)?.let {
			it.supervisorId?.let { repo.findByIdOrNull(it)?.let { k ->
					result.set<JsonNode>("supervisor", TextNode(k.name))
					k.supervisorId?.let { repo.findByIdOrNull(it)?.let { r ->
							result.set<JsonNode>("superiorVisor", TextNode(r.name))
						}
					}
				}
			}
		}
		return result
	}
}
