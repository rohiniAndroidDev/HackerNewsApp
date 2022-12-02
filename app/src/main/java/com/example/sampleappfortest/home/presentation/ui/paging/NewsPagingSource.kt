package com.example.sampleappfortest.home.presentation.ui.paging


import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sampleappfortest.common.*
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

class NewsPagingSource(
    private val getItem:suspend (Int)->ApiResult<NewsItem>,
    private val ids: List<Int>
) :
    PagingSource<Int, NewsItem>() {
    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        return withContext(Dispatchers.Default) {
            val position = params.key ?: START_PAGE_INDEX
            val list = mutableListOf<NewsItem>()

            var minOffset = (position * MAX_ITEMS_LIMIT) - 1

            if (minOffset > ids.size) {
                // no data to fetch
            } else {
                var maxOffset = (position * MAX_ITEMS_LIMIT) - 1 + MAX_ITEMS_LIMIT

                if (maxOffset >= ids.size) {
                    maxOffset = ids.size - 1
                }

                if (minOffset >= maxOffset) {
                    minOffset = maxOffset - MAX_ITEMS_LIMIT
                }

                val multipleIds = ids.subList(
                    minOffset,
                    maxOffset
                )

                printLogStatement(position)

                val runningTasks = multipleIds.map { id ->
                    async { // this will allow us to run multiple tasks in parallel
                        val apiResponse = getItem(id)
                        id to apiResponse // associate id and response for later
                    }
                }
//          delay(3000)
                val responses = runningTasks.awaitAll()
                responses.forEach { (_, response) ->
                    if (response.success) {
                        list.add(response.result!!)
                    }
                }
            }
            LoadResult.Page(
                data = list,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )
        }
    }

    private fun printLogStatement(position: Int) {
        val from = (position - 1) * MAX_ITEMS_LIMIT
        val to = (position - 1) * MAX_ITEMS_LIMIT + MAX_ITEMS_LIMIT
        val logStmt = "Sublist from: $from to  $to"
    }
}
