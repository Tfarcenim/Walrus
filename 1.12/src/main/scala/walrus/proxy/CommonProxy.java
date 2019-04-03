package walrus.proxy;

import walrus.registries.BlockEnum;
import walrus.registries.ItemEnum;
import walrus.tileentity.TileEntityWalrus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public CommonProxy(){
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void registerBlocks() {
		for (BlockEnum current : BlockEnum.values()) {
			registerBlock(current.getBlock());
			registerItem(current.getItem());
		}
	}

	@SubscribeEvent
	public void onRegister(RegistryEvent.Register<IRecipe> event){
	}

	public void registerItems() {
		for (ItemEnum current : ItemEnum.values()) {
			if (current.shouldRegister()) {
				registerItem(current.getItem());
			}
		}
	}

	public void registerBlock(Block block) {
		ForgeRegistries.BLOCKS.register(block);
	}

	public void registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
	}

	public void registerMovables() {
	}

	public void registerRenderers() {
		// Only Client Side
	}

	public void registerModels() {
		// Only Client Side
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityWalrus.class, "tileEntityWalrus");
	}

	public boolean isClient() {
		return false;
	}

}
