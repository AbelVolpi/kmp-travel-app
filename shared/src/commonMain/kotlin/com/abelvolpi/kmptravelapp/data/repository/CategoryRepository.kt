package com.abelvolpi.kmptravelapp.data.repository

import com.abelvolpi.kmptravelapp.data.datasource.local.CategoryLocalDataSource
import com.abelvolpi.kmptravelapp.data.datasource.remote.CategoryRemoteDataSource
import com.abelvolpi.kmptravelapp.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource,
    private val localDataSource: CategoryLocalDataSource
) {

    // call in init or when fetch in menu
    suspend fun fetchCategories() {
        remoteDataSource.getCategories().collect { categories ->
            localDataSource.deleteAllCategories()
            localDataSource.saveCategories(categories)
        }
    }

    fun getCategories(): Flow<List<Category>> = flow {
        emit(localDataSource.getAllCategories())
    }
}