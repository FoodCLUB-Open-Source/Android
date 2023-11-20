# FoodCLUB Android Team Repository

## Overview

Welcome aboard fellow developer, this is the FoodCLUB Android repository which you
are going to be using to develop new features. Please read this file, to get familiar
with our conventions, so that the code is readable and maintainable.

## Project Structure

### Visualisation

![project structure visualisation](https://kretu.sts3.pl/project_structure.png)

### Explanation

| Package                         | Description                     |
| ------------------------------- | ------------------------------- |
| config                          | Package where we put all configuration of the app. It includes fonts, theme colour, composables styling (for example button whose style repeats in code) |
| domain                          | Contains all data classes (models) and enums used in the app (it is not place where we define data classes used to retrieve data from the Retrofit). |
| navigation                      | Package containing all navigation graphs. We divided navigation graph into separate files to improve code readability. |
| network                         | Contains all functionality regarding remote data sources. For now, we only have retrofit to make calls to rest api, but in the future we may want to add other data sources. |
| network.retrofit.dtoModels      | Contains all data classes used to retrieve information from api (as objects). |
| network.retrofit.responses      | Contains all data classes used as containers for dtoModels or dtoSubModels. |
| network.retrofit.services       | Contains api call routes. If you know a concept of MVC it’s simply a equivalent to controller.<br><br>We should create different interfaces for different database groups. For example, all things regarding profile like follow/unfollow, getProfile, postProfile, editProfile should be in one interface and things regarding Post like createPost, deletePost, viewPost etc. should be in the other interface. |
| network.retrofit.RetrofitModule | This file contains singletons holding retrofit instances for each interface. It’s important they are singletons to create retrofit instances only once. So, when you create a service class, you need to initialise it in RetrofitModule. |
| repositories                    | Contains classes which take care of: <ul><li>Using retrofit instances to get data as dtoModels</li><li>Map dtoModels to base models. We should map all dtoModels to base models! Even if it’s repeated code – explanation in Architecture section</li><li>Handle error responses and returning them as appropriate error message</li></ul> |
| utils                           | Package containing all other classes used in our project |
| utils.composables               | Very often some composables like text fields, buttons are the same in many places in the code. All repeated composables should be put in this package to minimalize repeated code. |
| utils.helpers                   | Other classes like math helpers, parsing some numbers to text etc. I would say this is the place for uncategorised functions when we don’t know where to put them. |
| viewModels                      | Contains all viewModels. |
| views                          | Contains all views. |

## Architecture

We are using MVVM (Model View ViewModel) architecture. I recommend comprehensive reading how it works, but to read further you need to watch [this video](https://www.youtube.com/watch?v=-xTqfilaYow)

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

We expose properties and fields of the Model fetched from repository inside this class and define variables like errors, loading status, data variable (like profile, user, post etc.) and others.  

> :memo: **Note:** When we call api, it takes time until data is retrieved from remote server, so you need to take it into consideration when loading data in a view.

> :bulb: **Tip:** Variables should actually be defined as MutableStateFlow(). It’s because StateFlow is android build class which makes ui recompose only if the state value changes. It notifies view (where the state is collected) about the change and so the ui recomposes, but only things which changed are recomposed – which clearly makes Jetpack Compose very efficient.


#### Model

This can be implemented in many ways, but we decided to divide the Model into packages: domain, network, repositories. This part of architecture is responsible for:

- Business logic
- Getting data from remote data source (network)
- As the models which we get from data source may sometimes differ from the models we use in the viewModels we should map dtoModel (network) to base model (base).

> :memo: **Note:** You ask, why to create dtoModels and base models if they are the same? In the future, we may want to get information from many data sources and for instance, get some portion of information from retrofit and the rest from the other data source. If both data relate to the same kind of object like Person, Post, Recipe etc., but first data contains name and the other one age, we should actually merge information from two data sources and put them in one model. We need to think about code architecture now, so that project is more scalable in the future and we may want to add some fields to a model used in our app. In the other hand, we can’t use the same dtoModel for both data sources as the change in one data source should not have any impact on the other ones.

- We create repository class to contain methods like getPost, createPost, editPost, getPostsList etc.

> :memo: **Note:** Inside these methods we should call our data source instance to get dtoModel and map it using mapper functions to base model. Also, all error handling should be put inside repository and if response is not successful, we should parse dtoModel to errorModel. Repository method returns either Response.Success<baseModel> or Response.Error<Any object we need for our usecase>

## Testing

In order to test your app, please login using following credentials:
Login: User1
Password: P4$$word

This user has userid = 1, so please bear in mind what userid you're using for posting some information to the backend via Postman.
