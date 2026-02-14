package com.a1dark.nomadic.datagen;

import com.a1dark.nomadic.Nomadic;
import com.a1dark.nomadic.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.UnknownNullability;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Nomadic.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        basicItem(ModItems.CHINUTE.get());
        basicItem(ModItems.KYMUZ.get());

    }
}

