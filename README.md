# Android Clean Architecture Sample App

The architecture of the project follows the principles of Clean Architecture.
Developed by some Concepts of Security.
Tring to Build Application Faster and Smaller.

## Languages, libraries and tools used

```python

Kotlin
Room
Android Architecture Components
Android Support Libraries
RxJava2
DaggerHilt
Glide
Databinding
Retrofit
OkHttp
Gson
MVVM
Paging library
Timber
Flow
Paging
Mockito
sdp
NDK (C/C++)
```

Here's how the sample project implements it:

![alt text](https://androidwave.com/wp-content/uploads/2019/05/mvvm-architecture-app-in-android.png)

Let's look at each of the architecture layers :)

### User Interface
### Interactors
### Domain
### Data
### Data Source (Remote - Cache)


## Some points For Build Secury App

* store ApiKey safely in a C/C++ file using NDK
* to Watch what print in LogCati use Timber
* update all me libraries to gain these benefits because some versions of the libraries may introduce a security vulnerability and they fix it in the next version.

## Some points For Build Faster and Smaller App

* Remove unused resources
* Add just Dependencies Needed
* Build Multiple APKs for Screen Densities
* Remove Unused Alternative Resources
* Shrink Unused Code and Resources
* Use Shape Drawable
* Use VectorDrawable

## Some Notes

- I'm using For DaggerHilt at this Application but i have A simple Repositiory Using Dagger2 For Clean Archetecture With
Three Modules Data / Domain / Presentation with Extra Config layer ;)

# Let's look at Application:

First: Application Divided into Three Fragment And One Activity (Single Activity) by Using jetpack Navigation Component 
* Activity Which MainActivity with DrawerLayout & Toolbar & NavigationView 
  for logic init For Nav Controller and at nav_graph xml (Navigation) There is Start Distination For BusinessFragment .
* First Fragment BusinessList and ViewModel i am using DataBinding & Adapter for Paging Library (pagination)
  for logic setUpMayoutManger , subscribtion For Observers , Init For swipLayout and init For menu Option (For Search) 
  there is Navigation When Click at Item To Details and pass args for Business Id
  for viewModel logic There is LiveData For Data State && Error State && Loading State
  and Functions For getData -> init /Default current Query Search (NewYork) and when Searching The current Query Changed
  and InsertForFavorite -> For A cache insert 

* Second Fragment For Details Fragment And viewModel using DataBinding & ConstranLayout at xml Design & DataBindingAdapter
  and viewModel logic FetchData for BusinessDetails with BusinessId

*Third Fragment for Favorite Fragment which Localy get inserted Business and there is Removed Business By Swipe right.


# Thanks.




