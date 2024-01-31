# FoodCLUB Android Team Repository

## Overview

Welcome aboard fellow developer, this is the FoodCLUB Android repository which you
are going to be using to develop new features. Please read this file, to get familiar
with our conventions, so that the code is readable and maintainable.



## Project Structure

### Visualisation

![Screenshot 2024-01-26 112751](https://github.com/FoodCLUBDevelopment/Frontend-Android-Jetpack/assets/58441652/e8d47123-30e0-46ea-a09c-5dcc06a69c97)



### Explanation

| Package                         | Description                     |
| ------------------------------- | ------------------------------- |
| config                          | Package where we put all configuration of the app. It includes fonts, theme colour, composables styling (for example button whose style repeats in code) |
| domain                          | Contains all data classes (models) and enums used in the app (it is not place where we define data classes used to retrieve data from the Retrofit). |
| navigation                      | Package containing all navigation graphs. We divided navigation graph into separate files to improve code readability. |
| localdatasource                 | This is package containing our local database Room and data access objects. We store here data which must be displayed when user is offline. |
| network                         | Contains all functionality regarding remote data sources. For now, we only have retrofit to make calls to rest api, but in the future we may want to add other data sources. |
| network.remotedatasource        | All remote calls which include updating local database are defined here. It is nothing more than making the code cleaner and scalable. |
| network.retrofit.dtoModels      | Contains all data classes used to retrieve information from api (as objects). |
| network.retrofit.responses      | Contains all data classes used as containers for dtoModels or dtoSubModels. |
| network.retrofit.services       | Contains api call routes. If you know a concept of MVC it’s simply a equivalent to controller.<br><br>We should create different interfaces for different database groups. For example, all things regarding profile like follow/unfollow, getProfile, postProfile, editProfile should be in one interface and things regarding Post like createPost, deletePost, viewPost etc. should be in the other interface. |
| network.retrofit.utils          | All utils are stored in this file. Regarding login and logic behind sending authorisation token and others. |
| network.retrofit.RetrofitModule | This file contains singletons holding retrofit instances for each interface. It’s important they are singletons to create retrofit instances only once. So, when you create a service class, you need to initialise it in RetrofitModule. |
| repositories                    | Contains classes which take care of: <ul><li>Using retrofit instances to get data as dtoModels</li><li>Map dtoModels to base models. We should map all dtoModels to base models! Even if it’s repeated code – explanation in Architecture section</li><li>Handle error responses and returning them as appropriate error message</li><li>Some of repositories require data even if offline, so there is offline handling added too</li></ul> |
| utils                           | Package containing all other classes used in our project |
| utils.composables               | Very often some composables like text fields, buttons are the same in many places in the code. All repeated composables should be put in this package to minimalize repeated code. Please remember to document every composable using standard java docs style. |
| utils.helpers                   | Other classes like math helpers, parsing some numbers to text etc. I would say this is the place for uncategorised functions when we don’t know where to put them. |
| viewModels                      | Contains all viewModels. |
| views                           | Contains all views. |



## Architecture

We are using MVVM (Model View ViewModel) architecture. I recommend comprehensive reading how it works, but to read further you need to watch [this video](https://www.youtube.com/watch?v=-xTqfilaYow)

Diagram below represents, how MVVM is implemented in our codebase.

![1_fQjXLyRi5hbP4J7XY0zpnQ](https://github.com/FoodCLUBDevelopment/Frontend-Android-Jetpack/assets/58441652/ac63ccee-5527-46d5-aff4-d78cf9a83409)

### In practice

#### View

This file should contain read only states from viewModel (using collectAsState() method) and define UI.

> :bulb: **Tip:** Important thing is to divide whole UI into smaller parts, so that all the ui code is split into functions.

> :warning: **Warning:** Do not put any logic except logic necessary to determine how the ui should render.

###### &nbsp;Example 1

“Verify Account” view has “Resend code” functionality which is available to press after 60 seconds from the previous press. Then I put the counter logic inside the view.

###### &nbsp;Example 2

“Profile View” is common for “View My Profile” and “View Other User Profile” functionalities, but I want to show follow/unfollow button only in the second case. Many people would put logic inside view which is wrong! There should be a method in viewModel returning boolean whether id of currently showing user is the same as id of logged in user. In view, we make only if statement inside of which we call the mentioned method.



#### ViewModel

We expose properties and fields of the Model fetched from repository inside this class and define variables like errors, loading status, data variable (like profile, user, post etc.) and others. Important thing – data which is passed to view should be passed as State object. This is simply object containing all variables you want to send. For example, I want to pass full name and email to Login view, so I create object called LoginState where I put fullname and email variables. Then whenever you get data from the repository, you need to update the state which would trigger UI update (Jetpack Compose is smart, so it will automatically refresh the view).

> :memo: **Note:** When we call api, it takes time until data is retrieved from remote server, so you need to take it into consideration when loading data in a view.

> :bulb: **Tip:** Variables should actually be defined as MutableStateFlow(). It’s because StateFlow is android build class which makes ui recompose only if the state value changes. It notifies view (where the state is collected) about the change and so the ui recomposes, but only things which changed are recomposed – which clearly makes Jetpack Compose very efficient.

The other important thing is passing functions. Functions in the view should be implemented as interface called Events. It makes code much cleaner and easier to maintain.



#### Model

This can be implemented in many ways, but we decided to divide the Model into packages: domain, network, repositories. This part of architecture is responsible for:

- Business logic
- Getting data from remote data source (network)
- As the models which we get from data source may sometimes differ from the models we use in the viewModels we should map dtoModel (network) to base model (base).

> :memo: **Note:** You ask, why to create dtoModels and base models if they are the same? In the future, we may want to get information from many data sources and for instance, get some portion of information from retrofit and the rest from the other data source. If both data relate to the same kind of object like Person, Post, Recipe etc., but first data contains name and the other one age, we should actually merge information from two data sources and put them in one model. We need to think about code architecture now, so that project is more scalable in the future and we may want to add some fields to a model used in our app. In the other hand, we can’t use the same dtoModel for both data sources as the change in one data source should not have any impact on the other ones.

- We create repository class to contain methods like getPost, createPost, editPost, getPostsList etc.

> :memo: **Note:** Inside these methods we should call our data source instance to get dtoModel and map it using mapper functions to base model. Also, all error handling should be put inside repository and if response is not successful, we should parse dtoModel to errorModel. Repository method returns either Response.Success<baseModel> or Response.Error<Any object we need for our usecase>

> :memo: **Note:** Some of views must be displayed even if we don’t have internet connection (for example Profile). The approach is to save remote data to local database whenever it is retrieved and if we’re offline, then we get last updated data from local datasource.



## Helpful Diagrams

#### Getting and posting data

Below I give some illustration how architecture works. Remember that some of the views would only retrieve remote data source and would not update local database.

![app-architecture](https://github.com/FoodCLUBDevelopment/Frontend-Android-Jetpack/assets/58441652/8789c58a-2194-4061-a9e8-2037e373bc73)

#### Navigation

Below, you can see how our navigation graph is constructed.

![Navigation graph](https://github.com/FoodCLUBDevelopment/Frontend-Android-Jetpack/assets/58441652/099fcaee-b4be-45af-aa96-893cb8972541)

## Tips and Todos

### Don’t Use Magic Numbers and Hardcoded Strings, Why?

Hardcoding strings or using magic numbers in code is generally considered a bad practice for several reasons:

1. **Lack of Readability and Understanding:** Hardcoded values can be confusing for someone reading the code, including the original developer or others who might maintain the code later. Without context or comments, it's not clear what these numbers represent.
2. **Maintenance Challenges:** If you use magic numbers throughout your code and need to change a value later, you may have to hunt through your codebase to find all occurrences of that number.
3. **Reduced Flexibility:** Hardcoding values directly into the code makes it less flexible. If you ever need to change a parameter or constant, you would have to modify the code directly, potentially introducing bugs.

To prevent these issues, we always follow FIGMA design values and define our padding, font size, width, height etc. values to res folder. 

res/values/ package contains all of our resources that we use in our views.

Example of **bad practice** using magic number and a hard coded string: 

![Picture1](https://github.com/FoodCLUBDevelopment/Frontend-Android-Jetpack/assets/58441652/df8428e8-fd6e-4530-ba9f-90df3c80bd51)

Example of a **good practice** using **dimensionResource** and **stringResource**:

![Picture2](https://github.com/FoodCLUBDevelopment/Frontend-Android-Jetpack/assets/58441652/fd66ebb1-643e-4cb8-907c-507d97298957)

> :warning: When you don’t follow this approach, your pull requests will not be accepted and you will need to apply these to every changed file you have worked on. Also, Elliot will be pissed about it, be aware.



### Don’t Use Deprecated Libraries

Using deprecated libraries in a project is generally discouraged for several reasons:
1. **Lack of Support and Updates:** Deprecated libraries are no longer actively maintained or supported by their developers. This means that any bugs, security vulnerabilities, or compatibility issues that arise will not be addressed in future updates. Using such libraries exposes our project to potential risks.
2. **Security Concerns:** Deprecated libraries may have known security vulnerabilities that will not be patched. As a result, your project could become susceptible to attacks or breaches. It's crucial to use up-to-date libraries to benefit from the latest security patches and improvements.
3. **Compatibility Issues:** Deprecated libraries might not be compatible with newer versions of other dependencies, leading to integration problems and increased maintenance efforts. That’s why we need to maintain all of our dependencies with the latest versions.

When you need to implement a new feature and you need to add a dependency for it, add the latest “working” version of it. Some versions may not work with our current dependencies so you need to check the current compatible version and use that.



### Don’t Use Excessive Comments

While comments are essential for explaining complex logic or documenting crucial information, using excessive comments in code can have drawbacks. Here are some reasons why it's generally advised to avoid excessive comments:
1. **Code Should Be Self-Explanatory:** Well-written code should be self-explanatory, with clear and meaningful variable/method names and a logical structure. Excessive comments might indicate that the code itself is not clear and could be improved for readability.
2. **Redundancy:** If the code is straightforward and the logic is apparent, adding comments that merely restate the obvious can be redundant and clutter the codebase. This redundancy can make the code harder to read and understand.
3. **Readability Impact:** Excessive comments can clutter the code, making it visually overwhelming and harder to read. Clear, concise comments are more likely to be read and understood, enhancing the overall readability of the code.
4. **Maintenance Overhead:** Excessive comments can become outdated over time, especially if the code undergoes changes. Maintaining both the code and its corresponding comments can lead to inconsistencies and increase the likelihood of errors.

> :bulb: **Tip:** If your function will be used on multiple places and it is located in utils/composables package, you can document it using java docs standard commenting scheme.



## Testing

In order to test your app, please login using following credentials:
Login: User1
Password: P4$$word

This user has userid = 1, so please bear in mind what userid you're using for posting some information to the backend via Postman.
