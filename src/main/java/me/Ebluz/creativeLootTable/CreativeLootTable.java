package me.Ebluz.creativeLootTable;


import me.Ebluz.creativeLootTable.item.LootTableItemGroup;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CreativeLootTable implements ModInitializer {

    public static final String MOD_ID = "creative-loot-table";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static List<LootTable> lootTables;
    public static LootWorldContext lootWorldContext;

    public static boolean generated = false;


    //Create item group
    public static final RegistryKey<ItemGroup> LOOT_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID,"item_group"));

    public static final ItemGroup LOOT_GROUP = FabricItemGroup.builder()
            .displayName(Text.of("Loot Table Items"))
            .icon(() -> new ItemStack(Items.BUNDLE))
            .build();

    @Override
    public void onInitialize() {

        //Register Item group
        Registry.register(Registries.ITEM_GROUP, LOOT_GROUP_KEY,LOOT_GROUP);


        ServerLifecycleEvents.SERVER_STARTED.register(server ->{
            //generate loot context
            lootWorldContext = LootTableItemGroup.generateDefaultLootWorldContext(server);
        });

        //Load Item group
        LootTableEvents.ALL_LOADED.register((resourceManager,lootTableRegistry)->{

            //Get namespaces and loottables to show
            lootTables = LootTableItemGroup.extractLootTables(resourceManager.getAllNamespaces(), lootTableRegistry);

            //Clear previous items in the group
            LOOT_GROUP.getDisplayStacks().clear();

            //Load items in item group when /reload
            if(generated){
                LootTableItemGroup.fillItemGroup(LOOT_GROUP.getDisplayStacks(),lootTables,lootWorldContext);
            }
        });

        //load items in the item group for the first time
        ItemGroupEvents.modifyEntriesEvent(LOOT_GROUP_KEY).register(lootGroup->{
            if(!lootTables.isEmpty()){
                LootTableItemGroup.fillItemGroup(lootGroup.getDisplayStacks(),lootTables,lootWorldContext);
            }
            generated = true;
        });
    }
}
