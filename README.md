# Himeji
Himeji Map Viewer is a Java application used top create top-down renders of Minecraft maps. Himeji has options to allow for rendering certain areas of maps, rendering within a certain elevation range, and has options for adding or removing details to renders.

![picture alt](https://tknouchi.files.wordpress.com/2020/03/sample.png)

## License
Himeji Map Viewer is licensed under [General Public License version 3](https://www.gnu.org/licenses/).

## Installation
- Download the precompiled, runnable .jar on [GitHub](https://github.com/TakenouchiTR/Himeji/blob/master/HMV.jar), or you can click [here](https://github.com/TakenouchiTR/Himeji/blob/master/HMV.jar?raw=true) for a direct download.
- Clone the repo with `git clone https://github.com/TakenouchiTR/Himeji.git`

## Usage
1. Run the jar as you would any executable.
2. Press the "World" button to select the root folder for a world
    1. The dialog will automatically open at the default Minecraft saves folder
    2. The folder may also be typed/pasted in the text field
3. Press the "Output" button to select where you want the image to be saved to
    1. The dialog will automatically open at the default Minecraft saves folder
    2. The file path may also be typed/pasted in the text field
4. Optionally, select the render area and dimension by pressing the "Set Bounds" button
5. Press the "Start" button to begin rendering; large worlds may take a long time

## References
- Himji Map Viewer uses [JNBT](http://jnbt.sourceforge.net/) to read NBT files.
- Thanks to [Robin Sonnabend](https://github.com/YSelfTool)'s [MapRend](https://github.com/YSelfTool/MapRend), whose source code was incredibly useful in learning how to read NBTs.
