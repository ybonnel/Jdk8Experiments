Jdk8Experiments
===============

My experiments with jdk8

**This is JDK8 experiments, so JDK8 is needed :)**

## First case : movies

### Dataset

The dataset is a list of movies with for each movies the list of actors of it : [movies-mpaa](http://introcs.cs.princeton.edu/java/data/movies-mpaa.txt)

### Code

The code is under package [fr.ybonnel.movies](https://github.com/ybonnel/Jdk8Experiments/tree/master/src/main/java/fr/ybonnel/movies)
Main class is [Movies](https://github.com/ybonnel/Jdk8Experiments/blob/master/src/main/java/fr/ybonnel/movies/Movies.java)

### Find the actor who played in more movies

Result :
```
Start Sequential
Actor{firstName='Frank', lastName='Welker'}=92
End of Sequential, time : 4 min 55 s 704 ms
Start Parallel
Actor{firstName='Frank', lastName='Welker'}=92
End of Parallel, time : 1 min 24 s 452 ms
Start From movies
Actor{firstName='Frank', lastName='Welker'}=92
End of From movies, time : 224 ms
Start From movies parallel
Actor{firstName='Frank', lastName='Welker'}=92
End of From movies parallel, time : 149 ms
```



