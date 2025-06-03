package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.local.CategoryLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.CategoryRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource,
    private val localDataSource: CategoryLocalDataSource
) {
    suspend fun fetchCategories() {
        remoteDataSource.getItems().collect { categories ->
            localDataSource.deleteAllCategories()
            localDataSource.saveCategories(categories)
        }
    }

    fun getCategories(): Flow<List<Category>> = flow {
        emit(localDataSource.getCategories())
    }
    fun getRemoteCategories(): Flow<List<Category>> = remoteDataSource.getItems()

    fun getCategoryById(id: String): Flow<Category?> = remoteDataSource.getItemById(id)

    fun createCategory(category: Category): Flow<Boolean> = remoteDataSource.createCategory(category)

    fun updateCategory(category: Category): Flow<Boolean> = remoteDataSource.updateCategory(category)

    fun deleteCategory(categoryId: String): Flow<Boolean> = remoteDataSource.deleteCategory(categoryId)
}
