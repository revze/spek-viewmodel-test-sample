package com.example.belajarspek

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import com.example.belajarspek.domain.usecase.GetPostUseCase
import com.example.belajarspek.domain.usecase.GetProfileUseCase
import com.example.belajarspek.presentation.MainViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ExperimentalCoroutinesApi
@RunWith(JUnitPlatform::class)
object MainViewModelTest : Spek({

    Feature("User Feeds") {
        val profileUseCase = mockk<GetProfileUseCase>(relaxed = true)
        val postUseCase = mockk<GetPostUseCase>(relaxed = true)
        val loadingObserver = mockk<Observer<Boolean>>(relaxed = true)
        val profileNameObserver = mockk<Observer<String>>(relaxed = true)
        val postObserver = mockk<Observer<List<String>>>(relaxed = true)

        val testDispatcher = TestCoroutineDispatcher()
        val profileName = "Revando"
        val postList = mutableListOf(
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit",
            "Sed ut perspiciatis unde omnis iste natus",
            "Li Europan lingues es membres del sam familie"
        )

        lateinit var mainViewModel: MainViewModel

        beforeEachScenario {
            ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }

                override fun isMainThread(): Boolean {
                    return true
                }

                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }
            })

            MockKAnnotations.init(this, relaxed = true)
            Dispatchers.setMain(testDispatcher)
            mainViewModel =
                MainViewModel(profileUseCase, postUseCase)
            mainViewModel.isLoading.observeForever(loadingObserver)
            mainViewModel.profileName.observeForever(profileNameObserver)
            mainViewModel.posts.observeForever(postObserver)
        }

        afterEachScenario {
            ArchTaskExecutor.getInstance().setDelegate(null)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }

        Scenario("getting profile") {
            Given("a name") {
                coEvery {
                    profileUseCase()
                } returns profileName
            }

            When("getting the profile") {
                mainViewModel.getProfile()
            }

            Then("should hide loader and return profile name") {
                coVerifyOrder {
                    loadingObserver.onChanged(true)
                    loadingObserver.onChanged(false)
                    profileNameObserver.onChanged(profileName)
                }
            }
        }

        Scenario("getting post list") {
            Given("a post list") {
                coEvery {
                    postUseCase()
                } returns postList
            }

            When("getting the post") {
                mainViewModel.getPost()
            }

            Then("should hide loader and return post") {
                coVerifyOrder {
                    loadingObserver.onChanged(true)
                    loadingObserver.onChanged(false)
                    postObserver.onChanged(postList)
                }
            }
        }
    }
})