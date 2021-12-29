# How chunking works

Chunks are represented by a 2D arraylist of type Chunk.

Main point of writing this is explain how you can have negative chunk positions while not having negative indexs.

A chunk can have negative coordinates, these coordinates **DO NOT** correspond to array indexs, to get the index of a coordinate, the Tools class will have a function specifically to do that.


The chunk with the smallest x position will be at index [y][0] and same goes for y. The position of the chunk helps the game know where the player is and when to unload/load the chunk. ArrayList indexs help interact with the blocks inside the chunk.


Visual Representation of chunks and their array indexs

```
World Position:
(-1, 0)|(0, 0)|(1, 0)
+------+------+------+
|      |      |      |
|      |      |      |
+------+------+------+
Array Postion:
(0, 0) |(0, 1)|(0, 2)

```
