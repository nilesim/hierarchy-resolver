package com.personio.selin.hierarchyresolver.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.personio.selin.hierarchyresolver.repository.RelationRepository
import com.personio.selin.hierarchyresolver.service.HierarchyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/hierarchies")
class HierarchyController(
	val service: HierarchyService,
	val repository: RelationRepository) {

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
