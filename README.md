# Welcome to A Stupid Tower Defense repo !

It is a rather barebones tower defense game for a uni project.


## Features

You can launch the game and play until victory over the scenario you chose.
The gameplay consists in putting towers on the buildable areas and trying to survive through all levels.
There is a "skill issue edition" of the game, named 2 which is possible to finish even if you are not really good.
<br>Some little extras to the base game :
 - Ice Caster tower
 - Funny sounds (keep your computer's volume **low** to avoid surprises)
 - Somehow helpful error detection with precise exceptions

There is currently two games, one difficult and the other easier, respectively named 1 and 2, choose the one you want when prompted at launch.


## Building and launching

You will likely need JDK 21 or higher, older versions aren't supported and will never be, at least you are lucky we did not use java23's features.
<br>Nothing more should be necessary as there is no external libraries, StdDraw is already in the source tree.


### Windows

Windows is not supported, we don't know about powershell. Launch it from VScodium and it *should* work.
<br>*Works on Apollinaire's machine* trademark.


### Linux :

First open a terminal.

#### Building

```sh
git clone git@github.com:NyanMaths/astod.git
cd astod/src
javac -d ../bin game/ASToD.java
cd ..
```

#### Launching

On Wayland (Java my beloved...) : `DISPLAY=:0 /usr/bin/env java -XX:+ShowCodeDetailsInExceptionMessages -cp bin game/ASToD`
<br>On Xorg : `/usr/bin/env java -XX:+ShowCodeDetailsInExceptionMessages -cp bin game/ASToD`


## Todo : todo


## Authors

Participating students :
 - BRISSY Apollinaire
 - GATE Tudi
