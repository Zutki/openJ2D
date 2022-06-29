# Minecraft 2D
Minecraft2D (Due to be renamed in the future) is a game engine build around the concept of Minecraft in a 2D environment. The main goal of MC2D is to provide an environment in which mods
are a core component of the game. As MC2D by itself does not provide gameplay, gameplay would be introduced by any installed mods.

## Note:
In no way am I a good java programmer, this is a rewrite of the entire project. Almost all the code here is probably going to be completely different from any of the original code.
And finally, one of my major downfalls as a person. This project has a lifespan, I will work on it for a bit, however, I will eventually stop working on it. So if you are somehow actually interested in seeing this project finish. Feel free to harass me to work on it.

## Goals
- To in a way follow a proper structure based loosely on the way Fabric and Forge handle Minecraft mappings.
- Have an infinite map.
- Use regular minecraft resource packs for compatibility with already existing creations.
- To have a built-in mod loader. The idea is to have the game be constructed in way where mod support is kept in mind.
- Proper Javadoc documentation

## Info
The version that is provided in class files indicate the version of the game it was developed for
Minecraft2D has some of the following dependencies:
- Log4j2
- slf4j

The render engine used by MC2D is built on Swing and AWT. The engine attempts to solve speed issues caused by these libraries by running all rendering tasks in a separate thread.

## Mod Loader Information
The mod loader will have some limitations that may later get solved.

### Current ***Known*** mod support limitations
- Cannot make custom registry types without doing things like extending the registry class to add support