# 아프리카TV 사전과제

## About
[Afreeca Open API](https://developers.afreecatv.com/?szWork=openapi)를 사용한 방송리스트 조회 및 카테고리 필터링 가능한 어플리케이션

## Tech
- `Kotlin`
- `MVVM` `Clean Architecture`
- `databinding`
- `Coroutine` `Flow`
- `Retrofit2` `OkHttp3`
- `Hilt`
- `Paging3`
- `Navigtaion`
- `Glide`

## 요구사항
- 3개 이상의 카테고리 탭 구성
- 리스트 필수 포함요소(썸네일, 제목)
- 리스트 페이징
- 상세 페이지

<img src="https://user-images.githubusercontent.com/86879099/211156543-4a1d192d-1ef0-4c45-97a1-dd2192f2737f.gif" width="220" height="440"/> <img src="https://user-images.githubusercontent.com/86879099/211156546-45294509-aafb-4f6a-84d4-7c92a40e8ad7.gif" width="220" height="440"/>

## Feature
#### • 카테고리
```kotlin
// data.repository
class AfreecaRepositoryImpl @Inject constructor(
    private val afreecaDataSource: AfreecaDataSource
) : AfreecaRepository {
  …
    override fun getCategoryList(): Flow<List<Category>> = flow {
        runCatching {
            afreecaDataSource.getCategoryList()
        }.onSuccess { list ->
            emit(list.filter { category ->
                category.number in listOf(
                    CategoryType.EAT.categoryNumber,
                    CategoryType.TALK.categoryNumber,
                    CategoryType.GAME.categoryNumber
                )
            })
        }.onFailure { exception ->
            throw exception
        }
    }
  …
}

// domain.model
enum class CategoryType(val categoryNumber: String) {
    TALK(categoryNumber = "00130000"),
    EAT(categoryNumber = "00330000"),
    GAME(categoryNumber = "00040000");
}

```
- 3개의 탭을 구성할 카테고리의 키 값을 비교하기 위해 enum class를 활용하여 카테고리 키값을 필터링 합니다.

```kotlin
// presentation.category
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private val _categoryList = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val categoryList = _categoryList.asStateFlow()

    fun getCategoryList() {
        viewModelScope.launch {
            getCategoryListUseCase()
                .catch {
                    _categoryList.value = CategoryUiState.Error
                }
                .collect { list ->
                    _categoryList.value = CategoryUiState.Success(list)
                }
        }
    }
}
// presentation.category
sealed class CategoryUiState {
    data class Success(val data: List<Category>) : CategoryUiState()

    object Loading : CategoryUiState()
    object Error : CategoryUiState()
}

// presentation.category.CategoryFragment
  …
    private fun initCategory() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categoryList.collect { categoryList ->
                    when (categoryList) {
                        is CategoryUiState.Success -> {
                            with(binding) {
                                ivRestartFetchCategory.isVisible = false
                                vpMain.adapter =
                                    PagerAdapter(this@CategoryFragment, categoryList.data)
                                TabLayoutMediator(tlMain, vpMain) { tab, position ->
                                    tab.text = categoryList.data[position].name
                                }.attach()
                            }
                        }
                        is CategoryUiState.Error -> {
                            showErrorMessage(
                                requireView(),
                                getString(R.string.error_fetch_category)
                            )
                            binding.ivRestartFetchCategory.isVisible = true
                        }
                        is CategoryUiState.Loading -> {}
                    }
                }
            }
        }
    }
  …
```

- 뷰모델의 categoryList를 collect하여 CategoryUiState.Success일 때 PagerAdapter에게 data를 전달합니다.

```kotlin
// presentation.main
class PagerAdapter(
    fragment: Fragment,
    private val categoryList: List<Category>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = categoryList.size

    override fun createFragment(position: Int): Fragment {
        return BroadcastFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CATEGORY, categoryList[position])
            }
        }
    }

    companion object {
        const val ARG_CATEGORY = "key_category"
    }
}

// presentation.broadcast.BroadcastFragment
  …
  
  private val categoryDetailAdapter: CategoryDetailAdapter by lazy {
      CategoryDetailAdapter(itemClickListener = { detailCategory ->
          onCategoryDetailClick(detailCategory)
      })
  }
    
  private lateinit var category: Category
    
  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      initData()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        …
    }
  
  private fun initView() {
        binding.apply {
            …
            rvDetailCategory.apply {
                adapter = categoryDetailAdapter
                isGone = category.child.isEmpty()
                val detailList = mutableListOf<DetailCategory>().apply {
                    add(DetailCategory(getString(R.string.all), category.number))
                    addAll(category.child)
                }
                categoryDetailAdapter.submitList(detailList)
            }
            …
        }
    }
  
    private fun initData() {
        arguments?.takeIf { it.containsKey(ARG_CATEGORY) }?.apply {
            category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializable(ARG_CATEGORY, Category::class.java)
            } else {
                getSerializable(ARG_CATEGORY) as Category
            } ?: Category()
            broadcastViewModel.getSelectedCategoryBroadCastList(category.number)
        }
    }
    …
    private fun onCategoryDetailClick(category: DetailCategory) {
        broadcastViewModel.getSelectedCategoryBroadCastList(category.number)
    }
  …
```
- 전달받은 data를 통해 리스트의 size만큼 BroadcastFragment()를 생성하고 categoryList[position]에 해당하는 Category를 bundle로 넘겨줍니다.
- BroadcastFragment에서 onCreate시 bundle에서 Category를 받아와 broadcastViewModel.getSelectedCategoryBroadCastList(category.number)을 통해 Category의 number에 해당하는 방송리스트를 호출합니다.
- 뷰를 init할 때 category의 child가 있으면 서브카테고리 리스트가 rvDetailCategory를 통해 보여집니다.
- 카테고리의 클릭 이벤트는 CategoryDeatilAdapter에 람다를 통해 전달됩니다.

### • 리스트 페이징

```kotlin
// data.source.datasource
class AfreecaDataSource @Inject constructor(
    private val afreecaService: AfreecaService
) {
    suspend fun getBroadCastList(
        selectValue: String,
        page: Int
    ): List<Broadcast> {
        val response = afreecaService.getBroadCastList(
            selectValue = selectValue,
            page = page
        )
        return if (response.isSuccessful) {
            response.body()?.broadcastList?.toBroadCast() ?: emptyList()
        } else {
            throw Exception(response.message())
        }
    }
    
  …    
}

// data.source.paging
class AfreecaPagingSource(
    private val afreecaDataSource: AfreecaDataSource,
    private val selectValue: String,
) : PagingSource<Int, Broadcast>() {

    override fun getRefreshKey(state: PagingState<Int, Broadcast>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Broadcast> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val broadCastList =
                afreecaDataSource.getBroadCastList(
                    selectValue = selectValue,
                    page = position
                )
            LoadResult.Page(
                data = broadCastList,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (broadCastList.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
```
- Paging3를 사용하였으며 datasource를 통해 Page의 data를 제공합니다.

```kotlin
// data.repository
class AfreecaRepositoryImpl @Inject constructor(
    private val afreecaDataSource: AfreecaDataSource
) : AfreecaRepository {
    override fun getBroadcastList(
        selectValue: String
    ): Flow<PagingData<Broadcast>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            AfreecaPagingSource(afreecaDataSource, selectValue)
        }.flow
  …
}

// presentation.broadcast
@HiltViewModel
class BroadcastViewModel @Inject constructor(
    private val getBroadcastListUseCase: GetBroadcastListUseCase
) : ViewModel() {

    private val _broadcastList = MutableStateFlow<PagingData<Broadcast>>(PagingData.empty())
    val broadcastList = _broadcastList.asStateFlow()

    private var selectedCategoryNumber = ""

    fun getSelectedCategoryBroadCastList(categoryNumber: String) {
        updateSelectedNumber(categoryNumber)
        getBroadcastList()
    }

    private fun getBroadcastList() {
        viewModelScope.launch {
            getBroadcastListUseCase(selectedCategoryNumber)
                .cachedIn(this)
                .collectLatest { data ->
                    _broadcastList.emit(data)
                }
        }
    }
    
    private fun updateSelectedNumber(categoryNumber: String) {
      selectedCategoryNumber = categoryNumber
    }
  …
}

// presentation.broadcast.BroadcastFragment
  …
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }
  …
   private fun initData() {
        arguments?.takeIf { it.containsKey(ARG_CATEGORY) }?.apply {
            category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializable(ARG_CATEGORY, Category::class.java)
            } else {
                getSerializable(ARG_CATEGORY) as Category
            } ?: Category()
            broadcastViewModel.getSelectedCategoryBroadCastList(category.number)
        }
    }
  
    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
               launch {
                  broadcastViewModel.broadcastList.collectLatest { pagingData ->
                     broadcastAdapter.submitData(pagingData)
                     binding.srlBroadcast.isRefreshing = false
                  }
               }
               …
            }
        }
    }
    …
```
- PagerAdapter에서 bundle로 전달된 Category로 onCreate시 뷰모델의 getSelectedCategoryBroadCastList(category.number)을 호출합니다.
- broadcast뷰모델에서 리스트를 받는 선택된 카테고리의 숫자의 방송리스트를 받아옵니다.
- BroadcastFragment에서 뷰모델의 broadcastList를 collectLatest하여 어댑터에 submit하여 방송리스트를 보여줍니다.

### • 상세화면

```kotlin
  // presentation.broadcast.BroadcastFragmet
  …
  private val broadcastAdapter: BroadcastPagingAdapter by lazy {
      BroadcastPagingAdapter(itemClickListener = { broadcast ->
          onBroadcastItemClick(broadcast)
      })
  }
  …
  private fun onBroadcastItemClick(broadcast: Broadcast) {
      val action = CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(broadcast)
      requireView().findNavController().navigate(action)
  }
  …
  
  // presentation.detail.DetailFragment
  …
  private val navArgs by navArgs<DetailFragmentArgs>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      initData()
      initView()
  }

  private fun initData() {
      binding.broadcast = navArgs.broadcast
  }
  …
```
- 각 방송 아이템의 Broadcast를 Navigtaion의 navArgs를 통해 전달합니다.

### • 예외처리

<img src="https://user-images.githubusercontent.com/86879099/211168202-a4c69a0d-8328-458b-9c1e-6a2180cc3f70.gif" width="220" height="440"/> <img src="https://user-images.githubusercontent.com/86879099/211168204-584cf412-9e8e-445c-a231-9f7a85a09582.gif" width="220" height="440"/>

```kotlin
// presentation.utils
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager: ConnectivityManager =
        getSystemService(ConnectivityManager::class.java)
    val network: Network = connectivityManager.activeNetwork ?: return false
    val actNetwork: NetworkCapabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        else -> false
    }
}

// presentation.category.CategoryFragment
  …
  private var isNetworkConnected = false
  
  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      checkNetworkState()
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      if (isNetworkConnected) {
            initCategory()
      }
      …
  }
  
  private fun checkNetworkState() {
      if (!requireContext().isNetworkConnected()) {
          requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
          showNetworkErrorDialog()
          isNetworkConnected = false
      } else {
          categoryViewModel.getCategoryList()
          isNetworkConnected = true
      }
  }
  private val onBackPressedCallback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {}
  }
  …  
```
- 네트워크 연결x 상태로 앱을 실행하면 다이얼로그가 띄워지며 앱을 종료하도록 유도합니다.
- 다이얼로그가 띄워졌을 시 뒤로가기 및 배경터치(setCancelable(false))를 막습니다.

```kotlin
  // presentation.broadcast.BrodcastFragment
  …
  private fun collectFlow() {
      viewLifecycleOwner.lifecycleScope.launch {
          viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
              launch {
                  broadcastAdapter.loadStateFlow.collectLatest { loadState ->
                    if (loadState.source.refresh is LoadState.Error) {
                          showErrorMessage(
                              requireView(),
                              getString(R.string.error_fail_to_fetch_list)
                          )
                      }
                      if (loadState.source.append is LoadState.Error) {
                          showErrorMessage(
                              requireView(),
                              getString(R.string.error_fetch_more_list)
                          )
                      }
                   }
                }
            }
        }
    }
    …
```
- PagingAdapter의 loadState를 collectLatest하여 loadState.souce.refresh와 loadState.source.append가 LoadState.Error상태일 때 Snackbar메세지를 띄웁니다.
- SwipeRefreshLayout을 활용하여 방송 데이터를 새로 받아옵니다.
