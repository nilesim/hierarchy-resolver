package com.personio.selin.hierarchyresolver.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.personio.selin.hierarchyresolver.domain.model.Relation
import com.personio.selin.hierarchyresolver.repository.RelationRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.util.*
import java.util.Map
import kotlin.collections.mutableListOf

internal class HierarchyServiceTest {
	private val relationRepository: RelationRepository = mockk()
	private val hierarchyService = HierarchyService(relationRepository)

	@Test
	fun whenArrangeStructure_success_thenReturnObjectNode() {
		//given
		val parentUUID = UUID.randomUUID()
		val child1UUID = UUID.randomUUID()
		val child2UUID = UUID.randomUUID()
		val relation = Relation(id = UUID.randomUUID(), name = "Childwhatever", supervisorId = parentUUID)
		val relations = mutableListOf(
			Relation(id = child1UUID, name = "Pete", supervisorId = parentUUID),
			Relation(id = child2UUID, name = "Barbara", supervisorId = parentUUID)
		)
		val relationNick = Relation(id = parentUUID, name = "Nick", supervisorId = null)
		every { relationRepository.findOneByName("Pete") } returns null
		every { relationRepository.findOneByName("Barbara") } returns null
		every { relationRepository.findOneByName("Nick") } returns relationNick
		every { relationRepository.save(any()) } returns relation
		every { relationRepository.countBySupervisorIdNull() } returns 1
		every { relationRepository.findBySupervisorIdNull() } returns relationNick
		every { relationRepository.findBySupervisorId(parentUUID) } returns relations
		every { relationRepository.findBySupervisorId(child1UUID) } returns mutableListOf()
		every { relationRepository.findBySupervisorId(child2UUID) } returns mutableListOf()

		val relationMap = Map.of(
			"Pete", "Nick",
			"Barbara", "Nick"
		)
		val relationNode = ObjectMapper().valueToTree<ObjectNode>(relationMap)

		//when
		val result = hierarchyService.arrangeStructure(relationNode)

		val resultNode = ObjectMapper().readTree(
			"{\n" +
					"            \"Nick\": {\n" +
					"                \"Pete\": {},\n" +
					"                \"Barbara\": {}\n" +
					"            }\n" +
					"}"
		)
		//then
		verify(exactly = 1) { relationRepository.findOneByName("Pete") }
		assertEquals(resultNode, result)
	}

	@Test
	fun whenArrangeStructure_loop_fail_thenReturnException() {
		//given
		val expectedMessage = "This hierarchy contain loops."
		val parentUUID = UUID.randomUUID()
		val child1UUID = UUID.randomUUID()
		val child2UUID = UUID.randomUUID()
		val relation = Relation(id = UUID.randomUUID(), name = "Childwhatever", supervisorId = parentUUID)
		val relationPete = Relation(id = child1UUID, name = "Pete", supervisorId = parentUUID)
		val relationBarbara = Relation(id = child2UUID, name = "Barbara", supervisorId = parentUUID)
		val relations = mutableListOf(relationPete, relationBarbara)
		val relationNick = Relation(id = parentUUID, name = "Nick", supervisorId = child2UUID)
		every { relationRepository.findOneByName("Pete") } returns relationPete
		every { relationRepository.findOneByName("Barbara") } returns relationBarbara
		every { relationRepository.findOneByName("Nick") } returns relationNick
		every { relationRepository.save(any()) } returns relation
		every { relationRepository.countBySupervisorIdNull() } returns 0
		every { relationRepository.findBySupervisorIdNull() } returns relationNick
		every { relationRepository.findBySupervisorId(parentUUID) } returns relations
		every { relationRepository.findBySupervisorId(child1UUID) } returns mutableListOf()
		every { relationRepository.findBySupervisorId(child2UUID) } returns mutableListOf()

		val relationMap = Map.of(
			"Pete", "Nick",
			"Barbara", "Nick",
			"Nick", "Pete"
		)
		val relationNode = ObjectMapper().valueToTree<ObjectNode>(relationMap)

		//when
		val exception: Exception = assertThrows(Exception::class.java) {
			hierarchyService.arrangeStructure(relationNode)
		}

		//then
		val actualMessage = exception.message
		assertTrue(actualMessage!!.contains(expectedMessage))
	}

	@Test
	fun whenArrangeStructure_multipleroot_fail_thenReturnException() {
		//given
		val expectedMessage = "This hierarchy has multiple roots."
		val parentUUID = UUID.randomUUID()
		val child1UUID = UUID.randomUUID()
		val child2UUID = UUID.randomUUID()
		val relation = Relation(id = UUID.randomUUID(), name = "Childwhatever", supervisorId = parentUUID)
		val relationPete = Relation(id = child1UUID, name = "Pete", supervisorId = parentUUID)
		val relationBarbara = Relation(id = child2UUID, name = "Barbara", supervisorId = parentUUID)
		val relationNick = Relation(id = parentUUID, name = "Nick", supervisorId = null)
		val relationSophie = Relation(id = parentUUID, name = "Sophie", supervisorId = null)
		every { relationRepository.findOneByName("Pete") } returns relationPete
		every { relationRepository.findOneByName("Barbara") } returns relationBarbara
		every { relationRepository.findOneByName("Nick") } returns relationNick
		every { relationRepository.findOneByName("Sophie") } returns relationSophie
		every { relationRepository.save(any()) } returns relation
		every { relationRepository.countBySupervisorIdNull() } returns 2

		val relationMap = Map.of(
			"Pete", "Nick",
			"Barbara", "Sophie"
		)
		val relationNode = ObjectMapper().valueToTree<ObjectNode>(relationMap)

		//when
		val exception: Exception = assertThrows(Exception::class.java) {
			hierarchyService.arrangeStructure(relationNode)
		}

		//then
		val actualMessage = exception.message
		assertTrue(actualMessage!!.contains(expectedMessage))
	}

	@Test
	fun whenGetSupervisorsOfEmployee_success_thenReturnObjectNode() {
		//given
		val name = "Pete"
		val supervisor = "Nick"
		val superiorVisor = "Sophie"
		val parentUUID = UUID.randomUUID()
		val superParentUUID = UUID.randomUUID()
		val relation = Relation(id = UUID.randomUUID(), name = name, supervisorId = parentUUID)
		val relationSuper = Relation(id = parentUUID, name = supervisor, supervisorId = superParentUUID)
		val relationSuperior = Relation(id = parentUUID, name = superiorVisor, supervisorId = null)
		every { relationRepository.findOneByName(name) } returns relation
		every { relationRepository.findByIdOrNull(parentUUID) } returns relationSuper
		every { relationRepository.findByIdOrNull(superParentUUID) } returns relationSuperior
		val resultNode = ObjectMapper().readTree(
			"{\n" +
					"    \"supervisor\": \"Nick\",\n" +
					"    \"superiorVisor\": \"Sophie\"\n" +
					"}"
		)

		//when
		val result = hierarchyService.getSupervisorsOfEmployee(name)

		//then
		verify(exactly = 1) { relationRepository.findOneByName(name) }
		assertEquals(resultNode, result)
	}

}
