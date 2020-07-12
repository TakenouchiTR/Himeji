# Himeji
Himeji Map Viewer (HMV) is an application used to create top-down renders of Minecraft Java Edition maps. HMV was created with the goal of being as accessible as possible, while still giving the user as many choices for map customization as possible. All beta and release versions of Minecraft are supported, though there may be issues in specific snapshots versions. Java was specifically chosen as users must already have it installed to play minecraft, greatly reducing the barrier to entry. HMV has many options for customizing map renders, including personalizing block colors, adjusting biome colors, and selecting specific regions of the map to render by themselves.

![picture alt](https://tknouchi.files.wordpress.com/2020/03/sample.png)

## License
Himeji Map Viewer is licensed under [General Public License version 3](https://www.gnu.org/licenses/).

## Installation
- Make sure that you have Java 8 installed before running. Java 8 can be installed from the [Oracle website](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).
- Download the precompiled, executable .jar on [GitHub](https://github.com/TakenouchiTR/Himeji/blob/master/HMV.jar), or you can click [here](https://github.com/TakenouchiTR/Himeji/blob/master/HMV.jar?raw=true) for a direct download.
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
5. Press the "Start" button to begin rendering; large worlds may take a long time (17 minutes for 1.75GB world)

## Features
All features can be done within the program, using the program's UI:
- Customize each block's output colors
- Marking blocks as invisible, having them be ignored when rendering
- Biomes can be customized in multiple ways:
  - Grass, foliage, and water tints can be changed individually
  - To what degree the blocks are tinted
  - Which blocks are considered grass, foliage, or water
- Whether to render the map at night, and how bright the ambient light is during night renders
- Change the transparency of water, adjusting how much blocks below it are tinted
- Adjust how intense the shadows and highlights are, or remove them entirely
- Speed up the render by choosing to render only a certain area of the map.
  - Save preset areas to quickly render them again in the future
- Limited Mod compatibility: Add blocks and biomes from mods from version 1.13+

## Known Issues
- Biome colors in worlds with 3D biomes are "blocky."

## Previews
**Biome Colors:**\
![picture alt](https://i.imgur.com/SYd99pF.gif)

**Night Brightness:**\
![picture alt](https://i.imgur.com/JBekjB1.gif)

**Shadow Intensity:**\
![picture alt](https://i.imgur.com/tkDDjjN.gif)

**Water Transparency:**\
![picture alt](https://i.imgur.com/0lmmjIx.gif)

## References
- Himji Map Viewer uses [JNBT](http://jnbt.sourceforge.net/) to read NBT files.
- Thanks to [Robin Sonnabend](https://github.com/YSelfTool)'s [MapRend](https://github.com/YSelfTool/MapRend), whose source code was incredibly useful in learning how to read NBTs.
