Zevo
====

Generic evolutionary computation framework

...I will update the description and add more details in the future...

Since the project is maven-based, you can run 'mvn install' to build it.
Then, you can run an example model for the framework by running 'mvn exec:java' in the 'model-couple-with-dog' module.

The above example solves the following matching problem:
There are 50 males, 50 females and 50 dogs.
Each male rated each female and dog from 0 to 49, Each female rated each male and dog, and each dog rated each male and female from 0 to 49 (yes, in my world dogs have their opinion, so that's possible...)
The problem is finding the best 50-triplets whose sum of ratings is the optimal.

You can play with the configuration by altering settings in the file: Config_CoupleWithDog.xml
You can see the ratings (preferences) in the file: CoupleWithDog_Preferences.xml

Good luck.
Feel free to extend the framework, it was implemented a few years ago and I haven't touched it since. :)
-Zvika
