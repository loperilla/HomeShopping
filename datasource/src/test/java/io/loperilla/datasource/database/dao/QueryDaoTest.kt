package io.loperilla.datasource.database.dao

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import io.loperilla.datasource.database.dao.fake.QueryDaoFake
import io.loperilla.datasource.database.dao.fake.appleQuery
import io.loperilla.datasource.database.dao.fake.pineAppleQuery
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.database.dao
 * Created By Manuel Lopera on 4/9/23 at 20:24
 * All rights reserved 2023
 */
class QueryDaoTest {

    private lateinit var queryDaoFake: QueryDaoFake

    @BeforeEach
    fun setUp() {
        queryDaoFake = QueryDaoFake()
    }

    @Test
    fun `Insert query, check success`() = runBlocking<Unit> {
        // GIVEN
        val query = appleQuery()

        // ACTION
        queryDaoFake.insertNewQuery(query)

        // ASSERTION
        val queryList = queryDaoFake.getPreviousQuery()
        assertThat(queryList).isNotNull()
    }

    @Test
    fun `Insert same query, check replace`() = runBlocking {
        // GIVEN
        val appleQuery1 = appleQuery()
        val appleQuery2 = appleQuery()

        // ACTION
        queryDaoFake.insertNewQuery(appleQuery1)
        queryDaoFake.insertNewQuery(appleQuery2)

        // ASSERTION
        val queryList = queryDaoFake.getPreviousQuery().first()
        assertThat(queryList.size).isEqualTo(1)
    }

    @Test
    fun `Insert some query, remove one successfully`() = runBlocking {
        // GIVEN
        val appleQuery = appleQuery()
        val pineAppleQuery = pineAppleQuery()

        // ACTION
        queryDaoFake.insertNewQuery(appleQuery)
        queryDaoFake.insertNewQuery(pineAppleQuery)

        queryDaoFake.removeQuery(pineAppleQuery)
        // ASSERTION
        val queryList = queryDaoFake.getPreviousQuery().first()

        assertThat(queryList.size).isEqualTo(1)
    }
}
