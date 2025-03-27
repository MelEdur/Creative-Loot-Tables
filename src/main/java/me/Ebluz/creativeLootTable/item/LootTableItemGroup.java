package me.Ebluz.creativeLootTable.item;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextParameterMap;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class LootTableItemGroup {

    public static List<LootTable> extractLootTables(Set<String> namespaces, Registry<LootTable> lootTableRegistry){
        List<LootTable> list = new ArrayList<>();



        for(String namespace : namespaces){
            LootTable lootTable = lootTableRegistry.get(Identifier.of(namespace,"creative_loot_table"));
            if(lootTable != null){
                list.add(lootTableRegistry.get(Identifier.of(namespace,"creative_loot_table")));
            }
        }
        return list;
    }

    public static LootWorldContext generateDefaultLootWorldContext(MinecraftServer server){

        ServerWorld world = server.getOverworld();

        ContextParameterMap parameters = new ContextParameterMap.Builder()
                .add(LootContextParameters.ORIGIN, Vec3d.ZERO)
                .build(LootContextTypes.CHEST);

        Map<Identifier, LootWorldContext.DynamicDrop> dynamicDrops = Collections.emptyMap();

        return new LootWorldContext(
                world,
                parameters,
                dynamicDrops,
                0L
        );
    }

    public static void fillItemGroup(Collection<ItemStack> group,List<LootTable> lootTables, LootWorldContext lootWorldContext){
        for(LootTable lootTable: lootTables){
            List<ItemStack> itemStacks = lootTable.generateLoot(lootWorldContext);
            for(ItemStack itemStack: itemStacks){
                itemStack.setCount(1);
                group.add(itemStack);
            }
        }
    }

}
