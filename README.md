# ProjectTopaz
An opensource weather app mostly for learning purpose, work is in progress...

# Update
  ## 04/03/2018
    - Migrated the code to Model-View-Presenter (MVP) pattern 
    - Implemented Dependency Injection with Dagger 2
    - Added data persistence with a local Databse using DB Flow 
    - Optimize call on API
    - Fixed some minor bugs on display
 
# Evolution to come
  - Implement the strict MVP pattern
  - Add UI test with espresso ( or other tools )
  - Change the UI to make it more user friendly
  - Create a Restful API to proxify the call towards the openweathermap API ( have to find the language to work with )
  - Handle a database on the remote Restful API to synchronize with multiple devices
  - ... 
  
# Developpement
The app was developped with:

  - Android Studio
  - [EventBus](https://github.com/greenrobot/EventBus)
  - [Retrofit](http://square.github.io/retrofit/)
  - [Gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
  - [RxAndroid](https://github.com/ReactiveX/RxAndroid/)
  - [ButterKnife](http://jakewharton.github.io/butterknife/)
  - MVP pattern
  - [Dagger2  (DI)](https://google.github.io/dagger/)
  - [DB Flow](https://github.com/Raizlabs/DBFlow)
  - [Android Debug Database ](https://github.com/amitshekhariitbhu/Android-Debug-Database)

# Screenshot

![list of cardview](https://i.imgur.com/6jt21jgl.png "1st")
![expanded cardview](https://i.imgur.com/wbAPxJgl.png "2nd")


# License
![gpl3 license](http://www.gnu.org/graphics/gplv3-127x51.png "GPL3 License")

This application is Free Software: You can use, study share and improve it at your will. Specifically you can redistribute and/or modify it under the terms of the <a href="https://www.gnu.org/licenses/gpl.html">GNU General Public License</a> as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

Data provided by <a href="http://openweathermap.org/">OpenWeatherMap</a> via their public API.

Logo icon is made by <a href="https://smashicons.com/">Smashicons</a> from <a href="https://www.flaticon.com/">Flaticon</a>.

<a href="https://erikflowers.github.io/weather-icons">Weather icons</a> were created and maintained by <a href="http://www.twitter.com/artill">Lukas Bischoff</a> and <a href="http://www.twitter.com/erik_flowers">Erik Flowers</a> under the <a href="http://scripts.sil.org/OFL">SIL OFL 1.1</a> license.

