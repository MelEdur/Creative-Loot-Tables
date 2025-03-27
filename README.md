
# CREATIVE LOOT TABLES

Creative loot tables is a mod that allows you to show some items of your datapack's loot tables in a new creative intentory tab. 
This items will automatically update when realoading your datapacks with /reload or re entering your world.

**For now the mod only works in single player**

---
### Downloads

You can find all the downloads on <Link>

---
### Usage

To use the mod you need to create a loot table called **creative_loot_table** in your datapack's namespace loot_table folder that allows you to get all the items you want to show in the creative tab without condition.

Example.

in `datapacks\your_datapack\data\your_namespace\loot_table\creative_loot_table.json`

you can have something like this, where you add a pool to each item

```
{
  "pools": [
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:loot_table",
          "value": "minecraft:blocks/dirt"
        }
      ]
    },
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "value": "minecraft:entities/cow",
          "name": "minecraft:diamond_sword",
          "functions": [
            {
              "function": "minecraft:set_name",
              "name": "My cool sword"
            }
          ]
        }
      ]
    }
  ]
}
```

Referencing in each entry a loot table that gives you the item with no condition or directly defining the item.

**Is important that this loot tables is in the root loot_table folder and not in a subfolder like: `datapacks\your_datapack\data\your_namespace\loot_table\swords\creative_loot_table.json`**