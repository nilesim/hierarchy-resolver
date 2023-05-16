package com.selin.hierarchyresolver.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.selin.hierarchyresolver.domain.exception.ErrorResponse
import com.selin.hierarchyresolver.repository.RelationRepository
import com.selin.hierarchyresolver.service.HierarchyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/hierarchies")
class HierarchyController(
	val service: HierarchyService,
	val repository: RelationRepository
) {

	@ExceptionHandler(ResponseStatusException::class)
	fun handleBadRequestException(ex: ResponseStatusException): ErrorResponse {
		return ErrorResponse(ex.status.toString(), ex.reason, listOf())
	}

	@PostMapping
	fun arrangeStructure(@RequestBody relations: JsonNode): ObjectNode {
		return service.arrangeStructure(relations)
	}

	@GetMapping("/get-supervisors")
	fun getSupervisorsOfEmployee(@RequestParam name: String): ObjectNode {
		return service.getSupervisorsOfEmployee(name)
	}

	@GetMapping("/all")
	fun findAll() = repository.findAll()
}
