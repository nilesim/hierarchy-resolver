package com.personio.selin.hierarchyresolver.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
/*
import com.personio.selin.hierarchyresolver.repository.HierarchyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import java.util.UUID
*/
@ExperimentalCoroutinesApi
internal class HierarchyServiceTest {
/*
    private val hierarchyService = HierarchyService()
	@MockK
    private val hierarchyRepository = HierarchyRepository()

    @Test
    fun `when recipientAccountService#create is called it should correctly create RecipientAccount`() {
        runBlockingTest {
            // Given
            val recipientAccount =
                listOf(getRecipientAccount(id = null, targetCustomerId = null, version = null, createdAt = null, lastModifiedAt = null))
            val createRecipientAccountRequest = getCreateRecipientAccountRequest()
            val expected = listOf(getRecipientAccount())

            coEvery { customerService.existsById(createRecipientAccountRequest.customerId) } returns true
            coEvery {
                recipientAccountRepository.existsByCustomerIdAndTargetInfo(
                    any(),
                    any()
                )
            } returns false
            coEvery { recipientAccountRepository.saveAll(recipientAccount) } returns expected.asFlow()
            every { appProperties.bulkCreateRecipientAccountLimit } returns 20

            // When
            recipientAccountService.create(createRecipientAccountRequest)

            // Then
            coVerify(exactly = 1) { recipientAccountRepository.saveAll(recipientAccount) }
            coVerify(exactly = 1) { customerService.existsById(recipientAccount[0].customerId) }
            coVerify(exactly = 1) {
                recipientAccountRepository.existsByCustomerIdAndTargetInfo(
                    any(),
                    any()
                )
            }
        }
    }

    @Test
    fun `when recipientAccountService#create is called with too many recipients it should throw BadRequestException`() {
        runBlockingTest {
            // Given
            val createRecipientAccountRequest = getCreateRecipientAccountRequest(
                recipientAccountRequestList = MutableList(25) { getRecipientAccountRequest() }
            )

            every { appProperties.bulkCreateRecipientAccountLimit } returns 20

            // Then
            Assertions.assertThrows(BadRequestException::class.java) {
                runBlockingTest { recipientAccountService.create(createRecipientAccountRequest) }
            }
        }
    }

    @Test
    fun `when recipientAccountService#create is called with non-existing customer it should throw NotFoundException`() {
        runBlockingTest {
            // Given
            val recipientAccount =
                getRecipientAccount(id = null, version = null, createdAt = null, lastModifiedAt = null)
            val createRecipientAccountRequest = getCreateRecipientAccountRequest()

            coEvery { customerService.existsById(recipientAccount.customerId) } returns false
            every { appProperties.bulkCreateRecipientAccountLimit } returns 20

            Assertions.assertThrows(NotFoundException::class.java) {
                runBlockingTest { recipientAccountService.create(createRecipientAccountRequest) }
            }
        }
    }

    @Test
    fun `when recipientAccountService#create is called with non-existing customer it should throw ConflictException`() {
        runBlockingTest {
            // Given
            val recipientAccount =
                getRecipientAccount(id = null, version = null, createdAt = null, lastModifiedAt = null)
            val createRecipientAccountRequest = getCreateRecipientAccountRequest()

            coEvery { customerService.existsById(recipientAccount.customerId) } returns true
            coEvery {
                recipientAccountRepository.existsByCustomerIdAndTargetInfo(
                    recipientAccount.customerId,
                    recipientAccount.targetInfo
                )
            } returns true
            every { appProperties.bulkCreateRecipientAccountLimit } returns 20

            Assertions.assertThrows(ConflictException::class.java) {
                runBlockingTest { recipientAccountService.create(createRecipientAccountRequest) }
            }
        }
    }

    @Test
    fun `when recipientAccountService#updateById is called it should correctly update and return RecipientAccount`() {
        runBlockingTest {
            // Given
            val updateRecipientAccountRequest = getUpdateRecipientAccountRequest()
            val recipientAccount = getRecipientAccount(
                targetName = "Name updated",
                description = "Desc updated",
                targetInfo = "12314134 updated"
            )

            coEvery { customerService.existsById(UUID.fromString("75e5624e-7453-42de-b4e2-645a385bdbae")) } returns true
            coEvery { recipientAccountRepository.findById(UUID.fromString("65e5624e-7453-42de-b4e2-645a385bdbae")) } returns recipientAccount
            coEvery {
                recipientAccountRepository.existsByCustomerIdAndTargetInfo(
                    UUID.fromString("75e5624e-7453-42de-b4e2-645a385bdbae"),
                    updateRecipientAccountRequest.targetInfo!!
                )
            } returns false
            coEvery { recipientAccountRepository.save(recipientAccount) } returns recipientAccount
            every { appProperties.bulkCreateRecipientAccountLimit } returns 20

            // When
            val actual = recipientAccountService.updateById(
                UUID.fromString("65e5624e-7453-42de-b4e2-645a385bdbae"),
                updateRecipientAccountRequest
            )

            // Then
            Assertions.assertEquals(recipientAccount, actual)
        }
    }



*/
}
