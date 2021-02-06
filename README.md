# Omino

Application that arranges blocks on a board.  
There might be extra condition applied:
- blocks might be only arranged on a square, minimal square size is desired.
- blocks might be arranged on a minimal area rectangle, but cutting blocks is possible. Whole rectangle must be covered, minimal number of cuts is desired.

![omino-app](https://raw.githubusercontent.com/tomasz-herman/Omino/master/omino-gallery/omino-app.png)

There are several algorithms implemented that either optimize a desired problem or time.

# Building

To build application:

1. Make sure you are in project directory:
```
cd Omino
```
2. Build the project using `gradle`:
```
gradle runtime
```
3. After successful build application should appear be in `omino-app\build\image`