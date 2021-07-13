# What is used in project?
Technologies that have been used in this projects are :

- **Kotlin**
- **Clean Architecture** : An app using layered architecture based on [Clean Architecture by Uncle Bob](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).
- **Retrofit** : [To retrieve data from the network](https://github.com/square/retrofit)
- **Architecture components and using MVVM** : LiveData and ViewModel
- **Coroutines** : [Coroutines guide](https://kotlinlang.org/docs/coroutines-guide.html) - Coroutines are a new way of managing background threads that can simplify code by reducing the need for callbacks. They convert async callbacks for long-running tasks, such as database or network access, into sequential code. We use coroutines to do tasks in a background thread. This goes very well with the idea of use cases, single actions that the ViewModel calls depending of its needs. The guideline should be that every task executed by a use case should be done in a background thread, so, in the main thread, we could show a loading screen or any alternative, and the UI doesn’t get blocked.
- **Navigation component** : At last it's settled, single activity is what's Google recommend now. Navigation editor makes things easy for us to design navigation path of our app.
- **Koin** : [Start Koin](https://insert-koin.io/) - Using Dependency injection to manage the dependencies in an optimal way.
- **Glide** : To view images from the network - [An image loading and caching library for Android focused on smooth scrolling](https://github.com/bumptech/glide)
- **Constraint-Layout** : [Build a Responsive UI with ConstraintLayout | Android Developers](https://developer.android.com/training/constraint-layout/index.html)
- **Google Map**
- **DataBinding** : The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
 </br>**Pros**: Null safety, Type safety and Supports both Kotlin and Java, **View binding** and **data binding** are recommended approach instead of *Kotlin Synthetics* or old way *findViewById*
- **Unit Test** and **UI Test** : Mockito and Espresso 





Splash Screen | List View | Detail View | Map 
------------ | ------------- | ------------- | -------------
![Alt text](https://monosnap.com/image/ZxewHkoHRrufORUqBZFedGwxZGjvMf) | ![Alt text](https://monosnap.com/image/CJ0gOwglhn6WheOHOMhDYOBYoragZJ) | ![Alt text](https://monosnap.com/image/YfuA2llQYjKgPkx6dVDd0IbTNGNJmv) | ![Alt text](https://monosnap.com/image/fL6qCeMqi9tnbNfTssHTFPl5ZpfBPL)
